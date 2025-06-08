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
public class PromedioPonderado {
    
    public static Identificador crearPPComoAST(int str, List<Nodo> declaracionesPP, List<Nodo> instruccionesPP, SymbolTable tablaSimbolos, ParDeArrays la) {
 
                    //Generar declaraciones unicas
                    List<Nodo> nodos=new ArrayList<>();
                    nodos.add(new DeclaracionVariable("float","_suma_valores_"+str));
                    tablaSimbolos.add("_suma_valores_"+str, "float");
                    nodos.add(new DeclaracionVariable("float","_resultado_"+str));
                    tablaSimbolos.add("_resultado_"+str, "float");
                    nodos.add(new DeclaracionVariable("float","_suma_pesos_"+str));
                    tablaSimbolos.add("_suma_pesos_"+str, "float");
                    declaracionesPP.add(new DeclaracionMultiple(nodos));
                    List<Nodo> nodosEnteros=new ArrayList<>();
                    nodosEnteros.add(new DeclaracionVariable("integer","_i_"+str));
                    tablaSimbolos.add("_i_"+str, "integer");
                    declaracionesPP.add(new DeclaracionMultiple(nodosEnteros)); 
                    
                    // Generar identificadores auxiliares
                    Identificador varI = new Identificador("_i_"+str);
                    Identificador varN = new Identificador("_suma_valores_"+str);
                    Identificador varD = new Identificador("_suma_pesos_"+str);
                    Identificador resultado = new Identificador("_resultado_"+str);
                   
                    // _i = 0
                    instruccionesPP.add(new Asignacion(varI, new Constante(0)));
                    // _suma_valores = 0
                    instruccionesPP.add(new Asignacion(varN, new Constante(0)));
                    // _suma_pesos = 0
                    instruccionesPP.add(new Asignacion(varD, new Constante(0)));

                    Expresion arrayValores = la.getValores();
                    Expresion arrayPesos = la.getPesos();
                    
                    //Condition para Diferencia de longitud (arrayValores.lenght != arrayPesos.lenght)
                    Expresion condicionLongitud = new Distinto(new Constante ((new Longitud(arrayValores).evaluar(tablaSimbolos))), new Constante (new Longitud(arrayPesos).evaluar(tablaSimbolos)));
                    List<Nodo> instruccionVerificacion= new ArrayList<>();
                    Identificador respuesta= new Identificador("_resultado_"+str);
                    Constante constante= new Constante(0.0);
                    Display displayDif= new Display(new Constante("Las listas deben tener la misma longitud"));
                    instruccionVerificacion.add(displayDif);
                    instruccionVerificacion.add(new Asignacion (respuesta, constante));
                    List<Nodo> instruccionElse= new ArrayList<>();
                    ConditionThenElse verificacion= new ConditionThenElse(condicionLongitud, instruccionVerificacion, instruccionElse);
                     
                    
                    //Aca se resuelve concretamente el PP
                    // Loop_when (__i < length(arrayValores))
                    Expresion condicion = new Menor(new Identificador(varI.getNombre()), new Constante (new Longitud(arrayValores).evaluar(tablaSimbolos)));
                    Identificador indiceValorMultiplicando= new Identificador(varI.getNombre().toString());
                    Identificador indiceValorMultiplicador= new Identificador(varI.getNombre().toString());
                    List<Nodo> cuerpo = new ArrayList<>();
                    arrayValores=la.casteoValores();
                    if(arrayValores.getNombre().equals("_valores_"+str)){
                        List<Nodo> nodosArrays=new ArrayList<>();
                        instruccionesPP.add(new Asignacion(new Identificador("_valores_"+str), (Constante)la.getValores()));
                        Constante longitudArray=(Constante)la.getValores();
                        int tamanio=longitudArray.getTamanioArray();
                        nodosArrays.add(new DeclaracionArray("float_array","_valores_"+str, tamanio));
                        tablaSimbolos.add("_valores_"+str, "float_array["+tamanio+"]");
                        declaracionesPP.add(new DeclaracionMultiple(nodosArrays));
                    }
                    arrayPesos=la.casteoPesos();
                    if(arrayPesos.getNombre().equals("_pesos_"+str)){
                        List<Nodo> nodosArrays=new ArrayList<>();
                        instruccionesPP.add(new Asignacion(new Identificador("_pesos_"+str), (Constante)(la.getPesos())));
                        Constante longitudArray=(Constante)la.getPesos();
                        int tamanio=longitudArray.getTamanioArray();
                        nodosArrays.add(new DeclaracionArray("float_array","_pesos_"+str, tamanio));
                        tablaSimbolos.add("_pesos_"+str, "float_array["+tamanio+"]");
                        declaracionesPP.add(new DeclaracionMultiple(nodosArrays));
                    }
  
                    // _suma_valores = _suma_valores + arrayValores[_i] * arrayPesos[_i];
                    Expresion multiplicacion = new OperacionMultiplicacion(new AccesoArray((Identificador)arrayValores, indiceValorMultiplicando), new AccesoArray((Identificador)arrayPesos, indiceValorMultiplicador));
                    Expresion sumaNumerador = new OperacionSuma(new Identificador(varN.getNombre()), multiplicacion);
                    cuerpo.add(new Asignacion(new Identificador(varN.getNombre()), sumaNumerador));

                    // _suma_pesos = _suma_pesos + arrayPesos[_i];
                    Identificador sumaPesos= new Identificador(varD.getNombre().toString());
                    Identificador indicePesos= new Identificador(varI.getNombre().toString());
                    Expresion arrayPesosDenominador= la.casteoPesos();
                    Expresion sumaDenominador = new OperacionSuma(sumaPesos, new AccesoArray((Identificador)arrayPesosDenominador, indicePesos));
                    cuerpo.add(new Asignacion(new Identificador(varD.getNombre()), sumaDenominador));
                    
                    //i=i+1; FUNDAMENTAL!
                    Identificador indicePaso= new Identificador(varI.getNombre().toString());
                    Identificador indiceSumado= new Identificador(varI.getNombre().toString());
                    Asignacion incremento = new Asignacion(indicePaso, new OperacionSuma(indiceSumado, new Constante(1)));
                    cuerpo.add(incremento);
                    
                    // agregar el bucle
                    instruccionElse.add(new Bucle(condicion, cuerpo));
                    
                    //_suma_pesos == 1?
                    Expresion condicionIgualUno = new Distinto(new Identificador (varD.getNombre()), new Constante (1));
                    Expresion condicioneslIgualCero= new Igual(new Identificador (varD.getNombre()), new Constante (0));
                    Expresion operacionOr= new OperacionOr(condicionIgualUno,condicioneslIgualCero);
                    List<Nodo> instruccionesVerifIgualUno= new ArrayList<>();
                    Identificador respuestaIgualUno= new Identificador("_resultado_"+str);
                    Constante constanteIgualUno= new Constante(0.0);
                    Display display= new Display(new Constante("La suma de los pesos debe dar 1"));
                    instruccionesVerifIgualUno.add(display);
                    instruccionesVerifIgualUno.add(new Asignacion (respuestaIgualUno, constanteIgualUno));
                    List<Nodo> instruccionElseIgualUno= new ArrayList<>();
                    ConditionThenElse verificacionIgualUno= new ConditionThenElse(operacionOr, instruccionesVerifIgualUno, instruccionElseIgualUno);
                     
                    
                    // _resultado = _suma_valores / _suma_pesos
                    instruccionElseIgualUno.add(new Asignacion(new Identificador(resultado.getNombre()), new OperacionDivision(new Identificador(varN.getNombre()), new Identificador(varD.getNombre()))));
                    
                    //se agregan ambas verificaciones
                    instruccionesPP.add(verificacion);
                    instruccionesPP.add(verificacionIgualUno);
                    return resultado;
    }
    
}
