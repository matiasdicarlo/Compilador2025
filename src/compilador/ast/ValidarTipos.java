/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;


import ejemplo.jflex.SymbolTable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Usuario
 */

public class ValidarTipos {

    public static void validarAsignacion(String tipoVar, String tipoExpr, String nombreVar) {
        if (tipoVar == null || tipoExpr == null) {
            throw new Error("Error semántico: tipo no definido.");
        }

        boolean valido = false;

        switch (tipoVar) {
            case "integer":
                valido = tipoExpr.equals("integer");
                break;
            case "float":
                valido = tipoExpr.equals("integer") || tipoExpr.equals("float");
                break;
            case "bool":
                valido = tipoExpr.equals("bool");
                break;
            default:
                if (tipoVar.startsWith("float_array")) {
                    // Solo permitido si se accede a una posición específica 
                    valido = false;
                }
                break;
        }

        if (!valido) {
            throw new Error("Error semántico: no se puede asignar una expresión de tipo '" + tipoExpr +
                            "' a la variable '" + nombreVar + "' de tipo '" + tipoVar + "'.");
        }
    }

    //Caso mi_arreglo[0] := 2.5
    public static void validarAsignacionArray(String tipoArreglo, String tipoExpr, String nombreArreglo) {
        if (!tipoArreglo.startsWith("float_array")) {
            throw new Error("Error: '" + nombreArreglo + "' no es un arreglo.");
        }

        if (!tipoExpr.equals("float") && !tipoExpr.equals("integer")) {
            throw new Error("Error semántico: solo se pueden asignar valores numéricos a una posición de arreglo.");
        }
    }

    //Caso  mi_arreglo :=[5.2, 8.2]
    public static void validarAsignacionEntreArrays(Object Constipo1, Object Constipo2, SymbolTable table) {
        String tipo1= (String)Constipo1;
        Constante tipo2= (Constante)Constipo2;
        if (!table.getTipo(tipo1.toString()).startsWith("float_array") || !(tipo2.getTipo()=="float_array")) {
            throw new Error("Error: ambos deben ser arreglos.");
        }     
        Object longtipo1 = table.getLongitud(tipo1);         
        Longitud long2 = new Longitud(tipo2);
        Object tam2 = long2.evaluar(table);

        if (longtipo1 != tam2) {
            throw new Error("Error: los arreglos no tienen el mismo tamaño.");
        }
    }

   
   //Caso nombre de variable no declarada en DECLARE_SECTION
    public static void verificarDeclaracion(String nombreVar, SymbolTable tabla) {
    if (!tabla.exists(nombreVar)) {
        throw new Error("Error semántico: la variable '" + nombreVar + "' no fue declarada.");
        }
    }
    
    //Caso operaciones binarias, menos OP_IGUAL y OP_DISTINTO
    public static String verificarOperacionBinaria(Expresion izq, Expresion der) {
        String t1 = izq.getTipo();
        String t2 = der.getTipo();

        if (t1.equals("integer") && t2.equals("integer")) return "integer";
        if ((t1.equals("float") && (t2.equals("float") || t2.equals("integer"))) ||
        (t2.equals("float") && t1.equals("integer"))) return "float";
        throw new Error("Error semántico: no se puede operar '" + t1 + "' con '" + t2 + "'");
    }
    
    //Caso operaciones binarias OP_IGUAL y OP_DISTINTO
    public static String verificarOperacionBinariaLogica(Expresion izq, Expresion der) {
        String t1 = izq.getTipo();
        String t2 = der.getTipo();

        if ((t1.equals("integer") || t1.equals("float")) && (t2.equals("integer") || t2.equals("float"))) { //mismas reglas que para las op aritmeticas
                return "bool";
        }else if (t1.equals("bool") && t2.equals("bool")){ return "bool"; }// se agrega la posibilad de bool==bool
        throw new Error("Error semántico: no se puede operar de manera relacional '" + t1 + "' con '" + t2 + "'");
    }
    
    //Caso condicion en Condition
    public static String verificarCondicion(Expresion exp) {
        String tipoCond = exp.getTipo();
        if (!tipoCond.equals("bool")) {
            throw new Error("Error semántico: la condición debe ser de tipo booleano.");
        }
        return "bool";
        
    }
    //Caso Acceso a un elemento de un array, y indice no es un INT
    public static void verificarAccesoArray(String id, Constante e) {
        String tipoVar = SymbolTable.getTipo(id);
        if (!tipoVar.startsWith("float_array")) {
            throw new Error("Error: '" + id + "' no es un arreglo.");
        }
        String tipoIndice = e.getTipo();
        if (!tipoIndice.equals("integer")) {
            throw new Error("Error: el índice de un arreglo debe ser de tipo 'integer'.");
        }
    }
    
    //Caso OperacionNot
    public static void verificarOperacionNot(OperacionNot n){
        String tipo = n.getOperando().getTipo();
        if (!tipo.equals("bool")) {
            throw new RuntimeException("Error de tipo: NOT sólo se puede aplicar a booleanos");
        }
    }
    
    //Caso Negativo
    public static void verificarNegativo(Negativo n){
    String tipo = n.getOperando().getTipo();
        if (!tipo.equals("integer") && !tipo.equals("float")) {
            throw new RuntimeException("Error de tipo: negativo no se puede aplicar a tipo " + tipo);
        }
    }
    
    //Caso ALL y ANY
    public static void validarFuncionLogica(String operador, String tipoExpr, String tipoArreglo) {
        if (!tipoExpr.equals("integer") && !tipoExpr.equals("float")) {
        throw new Error("Error: la expresión escalar debe ser de tipo numérico.");
        }

     
        List<String> opsValidos = Arrays.asList("==", "!=", ">", "<", ">=", "<=");
        if (!opsValidos.contains(operador)) {
            throw new Error("Error: operador '" + operador + "' inválido en ALL/ANY.");
            }
    }
    
}

