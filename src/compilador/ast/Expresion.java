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
    
    public Expresion getExp(){return null;}
     
    public String getNombre(){return null;}
     
    public  Object getValor(){return null;}
     
    public void generarCodigoCondicionalLLVM(ContextoLLVM ctx, String etiquetaThen, String etiquetaElse, StringBuilder sb){
        String condCode = this.generarCodigoLLVM(ctx);
        String tmp = extraerUltimaTemporal(condCode);
        sb.append(condCode).append("\n");
        sb.append("br i1 ").append(tmp)
        .append(", label %").append(etiquetaThen)
        .append(", label %").append(etiquetaElse).append("\n");
    }
    
    
    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    } 
}
