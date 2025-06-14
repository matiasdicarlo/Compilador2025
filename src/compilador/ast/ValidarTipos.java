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

    //Caso  asignacion := ....
    public static String validarAsignacion(String tipoVar, String tipoExpr, String nombreVar) {
        if (tipoVar == null || tipoExpr == null) {
            throw new Error("Error semántico: tipo no definido.");
        }
        switch (tipoVar) {
            case "integer":
                if (tipoExpr.equals("integer")) {
                    return "ninguna";
                }
                break;
            case "float":
                if (tipoExpr.equals("float")) {
                    return "ninguna";
                }
                if (tipoExpr.equals("integer")) {
                    return "Conversion";
                }
                break;
            case "bool":
                if (tipoExpr.equals("bool")) {
                    return "ninguna";
                }
                break;
            default:
                if (tipoVar.startsWith("float_array")) {
                    // Solo permitido si se accede a una posición específica
                    return "ninguna"; 
                }
                break;
        }
        throw new Error("Error semántico: no se puede asignar una expresión de tipo '" + tipoExpr +
                    "' a la variable '" + nombreVar + "' de tipo '" + tipoVar + "'.");
    }

    //Caso mi_arreglo[0] := 2.5
    public static void validarAsignacionArray(String tipoArreglo, String tipoExpr, String nombreArreglo, Object indice, String tipoIndice) {
        // Validar que sea un arreglo flotante
        if (!tipoArreglo.startsWith("float_array")) {
            throw new Error("Error: '" + nombreArreglo + "' no es un arreglo.");
        }
        // Validar índice: debe ser Constante 
        if (!(indice instanceof Constante)) {
            throw new Error("Error: solo se pueden usar Integer como índices.");
        }
        // Si es constante, validar que sea de tipo entero y que el índice esté dentro del rango
        if (indice instanceof Constante) {
            if (!tipoIndice.equals("integer")) {
                throw new Error("Error: solo se pueden usar Integer como índices.");
            }
            int longitud = SymbolTable.getIndice(nombreArreglo);
            int valorIndice = (int) ((Constante) indice).getValor();
            if (valorIndice > longitud) {
                throw new Error("Error: posición inválida en el array.");
            }
        }
        // Validar que el tipo a asignar sea compatible (float o integer)
        if (!tipoExpr.equals("float") && !tipoExpr.equals("integer")) {
            throw new Error("Error semántico: solo se pueden asignar valores numéricos a una posición de arreglo.");
        }
    }

    //Caso  mi_arreglo :=[5.2, 8.2]
    public static void validarAsignacionEntreArrays(Object Constipo1, Object Constipo2, SymbolTable table) {
        if (!(Constipo2 instanceof InputArray)){
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
        }else if (Constipo2 instanceof InputArray){
            String tipo1= (String)Constipo1;
            InputArray tipo2= (InputArray)Constipo2;
            if (!table.getTipo(tipo1.toString()).startsWith("float_array") || !(tipo2.getTipo()=="float_array")) {
                    throw new Error("Error: ambos deben ser arreglos.");
            }     
            Object longtipo1 = table.getLongitud(tipo1);         
            int tam2 = tipo2.getLongitud();
            if ((int)longtipo1 != tam2) {
                throw new Error("Error: los arreglos no tienen el mismo tamaño.");
                }
            }
        }

    
    //Caso  mi_arreglo := mi_arreglo1
    public static void validarAsignacionEntreArraysNoLiterales(Object Constipo1, Object Constipo2, SymbolTable table) {
        if (!(Constipo2 instanceof InputArray)){
            String tipo1= (String)Constipo1;
            Identificador tipo3 =(Identificador)Constipo2;
            String tipo2= tipo3.getNombre();
            if (!table.getTipo(tipo1.toString()).startsWith("float_array") || !(table.getTipo(tipo2.toString()).startsWith("float_array"))) {
                throw new Error("Error: ambos deben ser arreglos.");
            }     
            Object longtipo1 = table.getLongitud(tipo1);  
            Object longtipo2 = table.getLongitud(tipo2); 
                  
            if (longtipo1 != longtipo2) {
                throw new Error("Error: los arreglos no tienen el mismo tamaño.");
                }
       
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
        if (t1.equals("float") && t2.equals("float")) return "float";                
        if (t1.equals("integer") &&t2.equals("float")) return "Conversion";
        if (t1.equals("float") &&t2.equals("integer")) return "Conversion";  
        throw new Error("Error semántico: no se puede operar '" + t1 + "' con '" + t2 + "'");
    }
    
    //Caso operaciones binarias OP_IGUAL y OP_DISTINTO
    public static String verificarOperacionBinariaLogica(Expresion izq, Expresion der) {
        String t1 = izq.getTipo();
        String t2 = der.getTipo();

        if (t1.equals("integer") && t2.equals("integer")){ return "bool";}
        else if (t1.equals("float") && t2.equals("float")){ return "bool";}              
        else if (t1.equals("integer") &&t2.equals("float")){ return "Conversion";}
        else if (t1.equals("float") &&t2.equals("integer")){ return "Conversion"; //mismas reglas que para las aritmeticas
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

    private static void validarOperacionBinaria(OperacionBinaria operacionBinaria) {
        if (operacionBinaria.getDerecha() instanceof Identificador || !(operacionBinaria.getDerecha().getTipo().equals("integer"))){throw new Error("Error: solo se pueden usar Integer como indices.");}
        else if (operacionBinaria.getIzquierda() instanceof Identificador || !(operacionBinaria.getIzquierda().getTipo().equals("integer"))){throw new Error("Error: solo se pueden usar Integer como indices.");}
       
    }
    
}

