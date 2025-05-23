/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class Continue extends Nodo {
    
    @Override
    public String getEtiqueta() {
        return "CONTINUE"; 
    }
    @Override
    protected String graficar(String idPadre) {
        return super.graficar(idPadre);
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        return "br label %" + ctx.getEtiquetaContinue();
    }
    

}
