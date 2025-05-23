/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class OperacionOr extends OperacionBinaria{
    
    public OperacionOr(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
    @Override
    protected String getNombreOperacion() {
        return "OR";
    }
    
     @Override
    protected String operadorLLVM(String tipo) {
        return "or"; 
    }

    @Override
    public String getTipo() {
        return "bool";
    }
}
