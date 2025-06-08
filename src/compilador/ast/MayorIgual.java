/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class MayorIgual extends OperacionBinaria {
    
    public MayorIgual(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    public String getNombreOperacion() {
        return ">=";
    }
    
    @Override
    public String operadorLLVM(String tipoOperando) {
        if (tipoOperando.equals("integer") || tipoOperando.equals("bool")) {
            return "icmp sge";
        } else if (tipoOperando.equals("float")) {
            return "fcmp oge";
        }
        return "; operador no soportado";
    }
    
    @Override 
    public String getTipo() { return "bool";
    
    }
}
