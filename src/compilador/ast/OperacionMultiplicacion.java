/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class OperacionMultiplicacion extends OperacionBinaria{
    
    public OperacionMultiplicacion(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
 
    @Override
    protected String getNombreOperacion() {
        return "*";
    }
    
    @Override
    protected String operadorLLVM(String tipoOperando) {
        if (tipoOperando.equals("integer")) {
            return "mul";
        } else if (tipoOperando.equals("float")) {
            return "fmul";
        }
        return "; operador  no soportado";
    }
    
}
