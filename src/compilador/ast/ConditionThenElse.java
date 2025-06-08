/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;


import java.util.List;

/**
 *
 * @author Usuario
 */
public class ConditionThenElse extends Nodo{
    private final Expresion condicion;
    private final List<Nodo> cuerpoThen;
    private final List<Nodo> cuerpoElse;
    

    public ConditionThenElse(Expresion condicion, List<Nodo> cuerpoThen, List<Nodo> cuerpoElse) {
        this.condicion = condicion;
        this.cuerpoThen = cuerpoThen;
        this.cuerpoElse = cuerpoElse;
        
    }
 
    public Expresion getCondicion() {
        return condicion;
    }

    public List<Nodo> getCuerpoThen() {
        return cuerpoThen;
    }

    public List<Nodo> getCuerpoElse() {
        return cuerpoElse;
    }

    @Override
    public String getEtiqueta() {
        return  "CONDITION";
    }
    
    @Override
    public String graficar(String idPadre) {
        StringBuilder sb = new StringBuilder();
        String miId = getId();
        sb.append(miId).append("[label=\"").append(getEtiqueta()).append("\"];\n");
        if (idPadre != null) {
            sb.append(idPadre).append(" -- ").append(miId).append(";\n");
        }

        // Condición
        sb.append(condicion.graficar(miId));

        // THEN
        String thenId = "then_" + getId();
        sb.append(thenId).append("[label=\"THEN\"];\n");
        sb.append(miId).append(" -- ").append(thenId).append(";\n");
        
        for (Nodo nodo : cuerpoThen) {
            sb.append(nodo.graficar(thenId));
        }

        // ELSE (si existe)
        if (cuerpoElse != null) {
            String elseId = "else_" + getId();
            sb.append(elseId).append("[label=\"ELSE\"];\n");
            sb.append(miId).append(" -- ").append(elseId).append(";\n");
            
            for (Nodo nodo : cuerpoElse) {
                sb.append(nodo.graficar(elseId));
            }
        }

        return sb.toString();
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String etiquetaThen = ctx.nuevaEtiqueta("then");
        String etiquetaElse = ctx.nuevaEtiqueta("else");
        String etiquetaFin = ctx.nuevaEtiqueta("ifend");
        StringBuilder sb = new StringBuilder();
        // Aplicar evaluación condicional con cortocircuito
        condicion.generarCodigoCondicionalLLVM(ctx, etiquetaThen, etiquetaElse, sb);
        sb.append(etiquetaThen).append(":\n");
        for (Nodo n : cuerpoThen) {
            sb.append(n.generarCodigoLLVM(ctx)).append("\n");
        }
        sb.append("br label %").append(etiquetaFin).append("\n");
        sb.append(etiquetaElse).append(":\n");
        if (cuerpoElse != null) {
            for (Nodo n : cuerpoElse) {
                sb.append(n.generarCodigoLLVM(ctx)).append("\n");
            }
        }
        sb.append("br label %").append(etiquetaFin).append("\n");
        sb.append(etiquetaFin).append(":\n");

        return sb.toString();
    }
    
    
    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }

 
}
