/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

import ejemplo.jflex.SymbolTable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class All  {
    
   public static Identificador crearAllComoAST(int str, String operador, Expresion valorComparacion, String arreglo, List<Nodo> declaraciones, List<Nodo> instruccionesAll, SymbolTable table) {
    // Variables auxiliares
        str=str+1;
        Identificador varI = new Identificador("_iAll_"+str);
        
        List<Nodo> nodosBool= new ArrayList();
        nodosBool.add(new DeclaracionVariable("bool","_condicionLocalAll_"+str));
        nodosBool.add(new DeclaracionVariable("bool","_resultadoAll_"+str));
        table.add("_condicionLocalAll_"+str, "bool");
        List<Nodo> nodosEnteros=new ArrayList<>();
        nodosEnteros.add(new DeclaracionVariable("integer","_iAll_"+str));
        table.add("_iAll_"+str, "integer");
        nodosEnteros.add(new DeclaracionVariable("integer","_longitudAll_"+str));
        table.add("_longitudAll_"+str, "integer");
        
        Identificador varLongitud = new Identificador("_longitudAll_"+str);
        Identificador varCond = new Identificador("_condicionLocalAll_"+str);
        Identificador varRes = new Identificador("_resultadoAll_"+str);

        // 1. i := 0
        List<Nodo> instrucciones = new ArrayList();
        Asignacion inicializarI = new Asignacion(varI, new Constante(0));
        instrucciones.add(inicializarI);

        // 2. longitud := longitud(arreglo)
        Object longtipo1 = table.getLongitud(arreglo); 
        Constante longitud=new Constante(longtipo1);
        Asignacion inicializarLongitud = new Asignacion(varLongitud, longitud);
        instrucciones.add(inicializarLongitud);

        // 3. res := true
        Asignacion inicializarRes = new Asignacion(varRes, new Constante(true));
        instrucciones.add(inicializarRes);
        
        // 4. condicionLocal := arreglo[i] OP valorComparacion
        Identificador idArreglo= new Identificador(arreglo);
        Identificador indice= new Identificador(varI.getEtiqueta().toString());
        Expresion accesoElemento = new AccesoArray(idArreglo, indice);
        Expresion comparacion;
        switch (operador) {
            case ">": comparacion = new Mayor(accesoElemento, valorComparacion); break;
            case ">=": comparacion = new MayorIgual(accesoElemento, valorComparacion); break;
            case "<": comparacion = new Menor(accesoElemento, valorComparacion); break;
            case "<=": comparacion = new MenorIgual(accesoElemento, valorComparacion); break;
            case "==": comparacion = new Igual(accesoElemento, valorComparacion); break;
            case "!=": comparacion = new Distinto(accesoElemento, valorComparacion); break;
            default: throw new RuntimeException("Operador no soportado: " + operador);
        }
        Asignacion asignarCond = new Asignacion(varCond, comparacion);
        
        
        // 5. IF condicionLocal == false THEN { res := false; BREAK }
        Identificador res=new Identificador("_resultadoAll_"+str);
        Identificador condicionLocal= new Identificador(varCond.getEtiqueta().toString());
        Expresion condNegada = new Igual(condicionLocal, new Constante(false));
        List<Nodo> cuerpoThen = List.of(
                new Asignacion(res, new Constante(false)),
                new Break()
        );
        ConditionThenElse condicionCorte = new ConditionThenElse(condNegada, cuerpoThen, null);
        
        
        // 6. i := i + 1
        Identificador indiceRectificado= new Identificador(varI.getEtiqueta().toString());
        Identificador indiceSumado= new Identificador(varI.getEtiqueta().toString());
        Asignacion incremento = new Asignacion(indiceRectificado, new OperacionSuma(indiceSumado, new Constante(1)));
        
        // 7. BUCLE
        Identificador varLongitudBucle= new Identificador(varLongitud.getEtiqueta().toString());
        Identificador varIndiceBucle= new Identificador(varI.getEtiqueta().toString());
        Expresion condicionBucle = new Menor(varIndiceBucle, varLongitudBucle);
        List<Nodo> cuerpoBucle = List.of(asignarCond, condicionCorte, incremento);
        Bucle bucle = new Bucle(condicionBucle, cuerpoBucle);
        instrucciones.add(bucle);
        // 8. Retornar todo 
        declaraciones.add(new DeclaracionMultiple(nodosEnteros));
        declaraciones.add(new DeclaracionMultiple(nodosBool));
        instruccionesAll.addAll(instrucciones);
        Identificador resultado= new Identificador("_resultadoAll_"+str);
        table.add("_resultadoAll_"+str, "bool");
        return resultado;
    }
   
    public static Identificador crearAllLiteralComoAST(int str, String operador, Expresion valorComparacion, Constante literalArray, List<Nodo> declaraciones, List<Nodo> instruccionesAll, SymbolTable table) {
        // 1. Nombre del array auxiliar
        String nombreArrayAux = "_arrayAll_" + (str + 1);

        // 2. Obtener tamaño del array literal
        Object valor = literalArray.getValor();
        if (!(valor instanceof String)) {
            throw new RuntimeException("El valor del literal array debe ser un String");
        }
        int tamanio = contarElementosArrayLiteral((String) valor);
        // 3. Declarar el array con tamaño fijo
        table.add(nombreArrayAux, "float_array"+"["+tamanio+"]");
        List<Nodo> declaracionesAuxiliares= new ArrayList();
        declaracionesAuxiliares.add(new DeclaracionArray("float_array", nombreArrayAux, tamanio));
        declaraciones.add(new DeclaracionMultiple(declaracionesAuxiliares));
        // 4. Asignar el literal array al identificador
        Asignacion asignarArray = new Asignacion(new Identificador(nombreArrayAux), literalArray);
        instruccionesAll.add(asignarArray);
        // 5. Usar método original para construir el resto del AST
        return crearAllComoAST(str, operador, valorComparacion, nombreArrayAux, declaraciones, instruccionesAll, table);
    }

    private static int contarElementosArrayLiteral(String arrayLiteralTexto) {
        arrayLiteralTexto = arrayLiteralTexto.replace("[", "").replace("]", "").replace(" ", "");
        if (arrayLiteralTexto.isEmpty()) return 0;
            return arrayLiteralTexto.split(",").length;
    }
}
