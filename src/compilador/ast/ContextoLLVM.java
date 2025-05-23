/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
import java.util.*;

public class ContextoLLVM {
    private int contadorTemporales = 0;
    private int contadorEtiquetas = 0;
    private final Map<String, String> formatosRegistrados = new HashMap<>();
    private final Map<String, Integer> longitudes = new HashMap<>();
    private final Stack<String> etiquetasBreak = new Stack<>();
    private final Stack<String> etiquetasContinue = new Stack<>();

    public String nuevoTemporal() {
        return "%t" + (contadorTemporales++);
    }

    public String nuevaEtiqueta(String base) {
        return base + "_label" + (contadorEtiquetas++);
    }
    
    public void pushEtiquetaBreak(String etiqueta) {
        etiquetasBreak.push(etiqueta);
    }
    
    public void popEtiquetaBreak() {
        etiquetasBreak.pop();
    }

    public String getEtiquetaBreak() {
        return etiquetasBreak.peek();
    }

    public void pushEtiquetaContinue(String etiqueta) {
        etiquetasContinue.push(etiqueta);
    }

    public void popEtiquetaContinue() {
        etiquetasContinue.pop();
    }

    public String getEtiquetaContinue() {
        return etiquetasContinue.peek();
    }
    
    public String generarDefinicionesDeFormatos() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entrada : formatosRegistrados.entrySet()) {
            String texto = entrada.getKey().replace("\\00", "\\00").replace("%", "\\25");
            String nombre = entrada.getValue();
            int longitud = longitudes.get(entrada.getKey());
            sb.append(nombre).append(" = private constant [")
            .append(longitud).append(" x i8] c\"").append(texto).append("\\00\"\n");
            }
        return sb.toString();
    }   
    public String registrarFormato(String formatoTexto, String nombreBase) {
        if (!formatosRegistrados.containsKey(formatoTexto)) {
            String nombre = nombreBase + formatosRegistrados.size();
            formatosRegistrados.put(formatoTexto, "@" + nombre);
            longitudes.put(formatoTexto, formatoTexto.length() + 1); // +1 por el null terminator
        }
        return formatosRegistrados.get(formatoTexto);
    }

 }
