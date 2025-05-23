/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class OperacionAnd extends OperacionBinaria{
   
    public OperacionAnd(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    protected String getNombreOperacion() {
        return "AND";
    }
    
    @Override
    protected String operadorLLVM(String tipo) {
        return "and"; 
    }

    @Override
    public String getTipo() {
        return "bool";
    }
    
}
