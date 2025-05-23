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
public class DeclaracionMultiple extends Nodo {
    private final List<Nodo> declaraciones;

    public DeclaracionMultiple(List<Nodo> declaraciones) {
        this.declaraciones = declaraciones;
    }
    @Override
    public String graficar(String idPadre) {
        StringBuilder sb = new StringBuilder();
        String miId = getId();

        sb.append(miId).append("[label=\"DeclaracionMultiple\"];\n");
        if (idPadre != null) {
            sb.append(idPadre).append(" -- ").append(miId).append(";\n");
    }

        for (Nodo declaracion : declaraciones) {
            sb.append(declaracion.graficar(miId));
    }

        return sb.toString();
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        StringBuilder sb = new StringBuilder();
        for (Nodo dec : declaraciones) {
            sb.append(dec.generarCodigoLLVM(ctx)).append("\n");
        }
        return sb.toString();
    }

}
