/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class DeclaracionArray extends Nodo {
    private final String tipo;
    private final String nombre;
    private final int tamanio;

    public DeclaracionArray(String tipo, String nombre, int tamanio) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.tamanio = tamanio;
    }

    @Override
    public String getEtiqueta() {
        return tipo + "["+tamanio+"] " + nombre;
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        switch (tipo) {
            case "float_array":
            case "float":
                return "%" + nombre + " = alloca [" + tamanio + " x double]";
            case "integer":
                return "%" + nombre + " = alloca [" + tamanio + " x i32]";
            default:
                return "; tipo array no soportado: " + tipo;
        }
    }

}
