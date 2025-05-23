/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class DeclaracionVariable extends Nodo {
    private final String tipo;
    private final String nombre;

    public DeclaracionVariable(String tipo, String nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }
    
    @Override
    public String getEtiqueta() {
        return tipo + ":" + nombre;
    }
    
    @Override
    public String graficar(String idPadre) {
        StringBuilder sb = new StringBuilder();
        String miId = getId();

        sb.append(miId).append("[label=\" ").append(tipo).append(" ").append(nombre).append("\"];\n");
        if (idPadre != null) {
            sb.append(idPadre).append(" -- ").append(miId).append(";\n");
        }

        return sb.toString();
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        switch (tipo) {
            case "integer": return "%" + nombre + " = alloca i32";
            case "float": return "%" + nombre + " = alloca double";
            case "bool": return "%" + nombre + " = alloca i1";
            default: return "; tipo no soportado: " + tipo;
        }
    }
    

}
