/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Bucle extends Nodo{
    private  Expresion condicion;
    private  List<Nodo> cuerpo;
    
    
    public Bucle(Expresion condicion, List<Nodo> cuerpo) {
        this.condicion = condicion;
        this.cuerpo = cuerpo;
        
    }

    @Override
    public String getEtiqueta() {
         return  "LOOP";
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

        // THEN (cuerpo del bucle)
        String cuerpoId = "loop_" + getId();
        sb.append(cuerpoId).append("[label=\"WHEN\"];\n");
        sb.append(miId).append(" -- ").append(cuerpoId).append(";\n");
        
        for (Nodo nodo : cuerpo) {
            sb.append(nodo.graficar(cuerpoId));
        }

        return sb.toString();
    }
    
   @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String etiquetaInicio = ctx.nuevaEtiqueta("loop");
        String etiquetaCond = ctx.nuevaEtiqueta("cond");
        String etiquetaFin = ctx.nuevaEtiqueta("fin");     
        ctx.pushEtiquetaBreak(etiquetaFin);
        ctx.pushEtiquetaContinue(etiquetaCond);
        StringBuilder sb = new StringBuilder();
        sb.append("br label %").append(etiquetaCond).append("\n");
        sb.append(etiquetaCond).append(":\n");
        String condCode = condicion.generarCodigoLLVM(ctx);
        String condTmp = extraerUltimaTemporal(condCode);
        sb.append(condCode).append("\n");
        sb.append("br i1 ").append(condTmp).append(", label %")
            .append(etiquetaInicio).append(", label %").append(etiquetaFin).append("\n");
        sb.append(etiquetaInicio).append(":\n");
        for (Nodo n : cuerpo) {
            sb.append(n.generarCodigoLLVM(ctx)).append("\n");
        }
        sb.append("br label %").append(etiquetaCond).append("\n");
        sb.append(etiquetaFin).append(":\n");
        ctx.popEtiquetaBreak();
        ctx.popEtiquetaContinue();
        return sb.toString();
    }
    
    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }

    public Expresion getCondicion() {
        return condicion;
    }

    public void setCondicion(Expresion condicion) {
        this.condicion = condicion;
    }

    public List<Nodo> getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(List<Nodo> cuerpo) {
        this.cuerpo = cuerpo;
    }

    
    
   

}
