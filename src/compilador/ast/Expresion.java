/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class Expresion extends Nodo {

    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        return "; expresión base sin implementación";
    }
    
    
     public String getTipo() {
        return "i32"; // Por defecto, se sobreescribe en la clases hijas
    }
    
    
    }
