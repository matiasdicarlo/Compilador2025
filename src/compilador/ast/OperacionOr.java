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
    public String getNombreOperacion() {
        return "OR";
    }
    
     @Override
    public String operadorLLVM(String tipo) {
        return "or"; 
    }

    @Override
    public String getTipo() {
        return "bool";
    }
    
    @Override
    public void generarCodigoCondicionalLLVM(ContextoLLVM ctx, String etiquetaThen, String etiquetaElse, StringBuilder sb) {
        String etiquetaIntermedia = ctx.nuevaEtiqueta("or_rhs");
        // Si izquierda es true, ir directo a then
        this.getIzquierda().generarCodigoCondicionalLLVM(ctx, etiquetaThen, etiquetaIntermedia, sb);
        // Si izquierda fue false, derecha decide
        sb.append(etiquetaIntermedia).append(":\n");
        this.getDerecha().generarCodigoCondicionalLLVM(ctx, etiquetaThen, etiquetaElse, sb);
    }
}
