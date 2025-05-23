/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class OperacionNot extends Expresion{
     
    private final Expresion operando;

    public OperacionNot(Expresion operando) {
        this.operando = operando;
    }

    public Expresion getOperando() {
        return operando;
    }
 
    @Override
    public String getEtiqueta() {
        return "NOT";
    }
    
    @Override
    protected String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                operando.graficar(miId);       
    }
    
     @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String code = operando.generarCodigoLLVM(ctx);
        String tmp = extraerUltimaTemporal(code);
        String resultado = ctx.nuevoTemporal();
        return code + "\n" + resultado + " = xor i1 " + tmp + ", true";
    }

    @Override
    public String getTipo() {
        return "bool";
    }

    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }

  
}
