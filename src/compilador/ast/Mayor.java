/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class Mayor extends OperacionBinaria {
    
    public Mayor(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    protected String getNombreOperacion() {
        return ">";
    }
    
    @Override
    protected String operadorLLVM(String tipoOperando) {
        if (tipoOperando.equals("integer") || tipoOperando.equals("bool")) {
            return "icmp sgt";
        } else if (tipoOperando.equals("float")) {
            return "fcmp ogt";
        }
        return "; operador no soportado";
    }
    @Override 
    public String getTipo() { return "bool";
    }
    
}
