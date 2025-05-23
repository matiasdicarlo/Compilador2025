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
public class Longitud extends Expresion {
    private Expresion array;

    public Longitud(Expresion array) {
        this.array = array;
    }
   
    public Object evaluar(SymbolTable tabla) {
        
        // Si el array es un identificador, consultamos la tabla directamente
        if (array instanceof Identificador) {
            Identificador id = (Identificador) array;
            Object contenido = tabla.getLongitud(id.getEtiqueta());
                 
            if (contenido instanceof Integer) {
                return contenido;
            }
            else{  throw new RuntimeException("El identificador no referencia a un array"); }

        }
        // Si es una constante tipo array literal
        if (array instanceof Constante) {
            Constante cte = (Constante) array;
            String arrayComoTexto = (String)cte.getValor();
            arrayComoTexto = arrayComoTexto.replaceAll("\\[|\\]|\\s", ""); 

        int cantidadComas = arrayComoTexto.length() - arrayComoTexto.replace(",", "").length();
        int cantidadElementos = cantidadComas + 1;
            return cantidadElementos;
           
        }

        throw new RuntimeException("No se puede calcular la longitud del valor: tipo no compatible");
    }
}
