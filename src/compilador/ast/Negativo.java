/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class Negativo extends Expresion {
    private final Expresion operando;

    public Negativo(Expresion operando) {
        this.operando = operando;
    }
   
    @Override
    public String getEtiqueta() {
        return "negativo";
    }
    
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                operando.graficar(miId);
            
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String codigoOp = operando.generarCodigoLLVM(ctx);
        String tmp = extraerUltimaTemporal(codigoOp);
        String tipo = operando.getTipo();
        String tmpResult = ctx.nuevoTemporal();

        if (tipo.equals("integer")) {
            return codigoOp + "\n" + tmpResult + " = sub i32 0, " + tmp;
        } else if (tipo.equals("float")) {
            return codigoOp + "\n" + tmpResult + " = fsub double 0.0, " + tmp;
        } else {
            return "; Error: tipo no soportado en operación Negativo";
        }
    }

    public Expresion getOperando() {
        return operando;
    }
    
    @Override
    public String getTipo() {
        return operando.getTipo(); // mismo tipo que el operando
    }

    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }
    
  
    
}
