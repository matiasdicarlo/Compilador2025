/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

import ejemplo.jflex.SymbolTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Any {
  

    public static Identificador crearAnyComoAST(int str, String operador, Expresion valorComparacion, String arreglo, List<Nodo> declaraciones, List<Nodo> instruccionesAny, SymbolTable table) {
        // Variables auxiliares
        Identificador varI = new Identificador("_iAny_"+str);
        List<Nodo> nodosBool= new ArrayList();
        nodosBool.add(new DeclaracionVariable("bool","_condicionLocalAny_"+str));
        nodosBool.add(new DeclaracionVariable("bool","_resultadoAny_"+str));
        table.add("_condicionLocalAny_"+str, "bool");
        List<Nodo> nodosEnteros=new ArrayList<>();
        nodosEnteros.add(new DeclaracionVariable("integer","_iAny_"+str));
        table.add("_iAny_"+str, "integer");
        nodosEnteros.add(new DeclaracionVariable("integer","_longitudAny_"+str));
        table.add("_longitudAny_"+str, "integer");
        Identificador varLongitud = new Identificador("_longitudAny_"+str);
        Identificador varCond = new Identificador("_condicionLocalAny_"+str);
        Identificador varRes = new Identificador("_resultadoAny_"+str);

        //i := 0
        List<Nodo> instrucciones = new ArrayList();
        Asignacion inicializarI = new Asignacion(varI, new Constante(0));
        instrucciones.add(inicializarI);

        // longitud := longitud(arreglo)
        Object longtipo1 = table.getLongitud(arreglo); 
        Constante longitud=new Constante(longtipo1);
        Asignacion inicializarLongitud = new Asignacion(varLongitud, longitud);
        instrucciones.add(inicializarLongitud);

        // res := true
        Asignacion inicializarRes = new Asignacion(varRes, new Constante(false));
        instrucciones.add(inicializarRes);
        
        //condicionLocal := arreglo[i] OP valorComparacion
        Identificador idArreglo= new Identificador(arreglo);
        Identificador indice= new Identificador(varI.getNombre().toString());
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
        
        
        //IF condicionLocal == false THEN { res := false; BREAK }
        Identificador res=new Identificador("_resultadoAny_"+str);
        Identificador condicionLocal= new Identificador(varCond.getNombre().toString());
        Expresion condNegada = new Igual(condicionLocal, new Constante(true));
        List<Nodo> cuerpoThen = Collections.unmodifiableList(Arrays.asList(
                new Asignacion(res, new Constante(true)),
                new Break()
        ));
        ConditionThenElse condicionCorte = new ConditionThenElse(condNegada, cuerpoThen, null);
        //i := i + 1
        Identificador indiceRectificado= new Identificador(varI.getNombre().toString());
        Identificador indiceSumado= new Identificador(varI.getNombre().toString());
        Asignacion incremento = new Asignacion(indiceRectificado, new OperacionSuma(indiceSumado, new Constante(1)));
        
        //BUCLE
        Identificador varLongitudBucle= new Identificador(varLongitud.getNombre().toString());
        Identificador varIndiceBucle= new Identificador(varI.getNombre().toString());
        Expresion condicionBucle = new Menor(varIndiceBucle, varLongitudBucle);
        List<Nodo> cuerpoBucle = Collections.unmodifiableList(Arrays.asList(asignarCond, condicionCorte, incremento));
        Bucle bucle = new Bucle(condicionBucle, cuerpoBucle);
        instrucciones.add(bucle);
        //Retornar todo 
        declaraciones.add(new DeclaracionMultiple(nodosEnteros));
        declaraciones.add(new DeclaracionMultiple(nodosBool));
        instruccionesAny.addAll(instrucciones);
        Identificador resultado= new Identificador("_resultadoAny_"+str);
        table.add("_resultadoAny_"+str, "bool");
        return resultado;
    }
   
    

    public static Identificador crearAnyLiteralComoAST(int str, String operador, Expresion valorComparacion, Constante literalArray, List<Nodo> declaraciones, List<Nodo> instruccionesAny, SymbolTable table) {
        //Nombre del array auxiliar
        String nombreArrayAux = "_arrayAny_" + (str + 1);
        //Obtener tamaño del array literal
        Object valor = literalArray.getValor();
        if (!(valor instanceof String)) {
            throw new RuntimeException("El valor del literal array debe ser un String");
        }
        int tamanio = contarElementosArrayLiteral((String) valor);
        //Declarar el array con tamaño fijo
        table.add(nombreArrayAux, "float_array"+"["+tamanio+"]");
        List<Nodo> declaracionesAuxiliares= new ArrayList();
        declaracionesAuxiliares.add(new DeclaracionArray("float_array", nombreArrayAux, tamanio));
        declaraciones.add(new DeclaracionMultiple(declaracionesAuxiliares));
        //Asignar el literal array al identificador
        Asignacion asignarArray = new Asignacion(new Identificador(nombreArrayAux), literalArray);
        instruccionesAny.add(asignarArray);
        //Usar método original para construir el resto del AST
        return crearAnyComoAST(str, operador, valorComparacion, nombreArrayAux, declaraciones, instruccionesAny, table);
    }

    private static int contarElementosArrayLiteral(String arrayLiteralTexto) {
        arrayLiteralTexto = arrayLiteralTexto.replace("[", "").replace("]", "").replace(" ", "");
        if (arrayLiteralTexto.isEmpty()) return 0;
            return arrayLiteralTexto.split(",").length;
    }
}



