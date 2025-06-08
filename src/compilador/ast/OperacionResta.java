/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class OperacionResta extends OperacionBinaria{
     
    public OperacionResta(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    public String getNombreOperacion() {
        return "-";
    }
     
    @Override
    public String operadorLLVM(String tipoOperando) {
        if (tipoOperando.equals("integer")) {
            return "sub";
        } else if (tipoOperando.equals("float")) {
            return "fsub";
        }
        return "; operador no soportado";
    }

}
