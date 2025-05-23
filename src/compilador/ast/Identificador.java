/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

import ejemplo.jflex.SymbolTable;

/**
 *
 * @author Usuario
 */
public class Identificador extends Expresion{
    private String nombre;

    public Identificador(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String getEtiqueta() {
        return nombre;
    }
    
    @Override
    protected String graficar(String idPadre) {
        return super.graficar(idPadre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
     
     
    
    public String getTipo() {
        return SymbolTable.getTipo(nombre); // "integer", "float", "bool"
    }

    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String tipo = getTipo(); // de la tabla de simbolos
        String tipoLLVM = convertirATipoLLVM(tipo);
        String tmp = ctx.nuevoTemporal();
        return String.format("%s = load %s, %s* %%%s", tmp, tipoLLVM, tipoLLVM, nombre);
    }

    private String convertirATipoLLVM(String tipo) {
        switch (tipo) {
            case "integer": return "i32";
            case "float": return "double";
            case "bool": return "i1";
            default: return "i32";
        }
    }


}
