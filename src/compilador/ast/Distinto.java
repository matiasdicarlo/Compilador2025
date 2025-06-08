/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class Distinto extends OperacionBinaria {
    
    public Distinto(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    public String getNombreOperacion() {
        return "!=";
    }
    
    @Override
    protected String operadorLLVM(String tipoOperando) {
        if (tipoOperando.equals("integer") || tipoOperando.equals("bool")) {
            return "icmp ne";
        } else if (tipoOperando.equals("float")) {
            return "fcmp one" ;
        }
        return "; operador distinto no soportado";
    }
    
    @Override 
    public String getTipo() { return "bool";
    }
}

