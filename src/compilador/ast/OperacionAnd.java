/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class OperacionAnd extends OperacionBinaria{
   
    public OperacionAnd(Expresion izquierda, Expresion derecha) {
        super(izquierda, derecha);
    }
    
    @Override
    public String getNombreOperacion() {
        return "AND";
    }
    
    @Override
    public String operadorLLVM(String tipo) {
        return "and"; 
    }

    @Override
    public String getTipo() {
        return "bool";
    }
    
    @Override
    public void generarCodigoCondicionalLLVM(ContextoLLVM ctx, String etiquetaThen, String etiquetaElse, StringBuilder sb) {
        String etiquetaIntermedia = ctx.nuevaEtiqueta("and_rhs");
        // Si izquierda es true, evaluar derecha
        this.getIzquierda().generarCodigoCondicionalLLVM(ctx, etiquetaIntermedia, etiquetaElse, sb);
        // Derecha decide entre then o else
        sb.append(etiquetaIntermedia).append(":\n");
        this.getDerecha().generarCodigoCondicionalLLVM(ctx, etiquetaThen, etiquetaElse, sb);
    }

    
}
