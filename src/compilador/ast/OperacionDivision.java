/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class OperacionDivision extends OperacionBinaria{
     
    public OperacionDivision(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    protected String getNombreOperacion() {
        return "/";
    }
    
    @Override
    protected String operadorLLVM(String tipoOperando) {
        if (tipoOperando.equals("integer")) {
            return "sdiv";
        } else if (tipoOperando.equals("float")) {
            return "fdiv";
        }
        return "; operador no soportado";
    }
    
}
