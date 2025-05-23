/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class ParDeArrays extends Nodo{
    private Expresion valores;
    private Expresion pesos;
    private int str;

    public ParDeArrays(Expresion valores, Expresion pesos, int str) {
        this.valores = valores;
        this.pesos = pesos;
        this.str=str+1;
    }

    public Expresion getValores() {
        return valores;
    }

    public Expresion getPesos() {
        return pesos;
    }
    
    //Cuando son dos LIT_ARRAY
    public Identificador casteoValores(){
        if (valores instanceof Constante) {
            Constante cte = (Constante) valores;
            return new Identificador("_valores_"+str);    
        }     
        else{ return new Identificador(valores.getEtiqueta());}
    }
    
    //Cuando son dos LIT_ARRAY
    public Identificador casteoPesos(){
        if (pesos instanceof Constante) {
            Constante cte = (Constante) pesos;
            return new Identificador("_pesos_"+str);
        }else{ return new Identificador(pesos.getEtiqueta());}
    
    }
     @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        return "; expresión base sin implementación";
    }
    
   
}

