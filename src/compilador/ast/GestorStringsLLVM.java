/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class GestorStringsLLVM {
    private static Map<String, String> stringConstantes = new LinkedHashMap<>();
    private static int contador = 0;

    public static String registrar(String contenido) {
        if (!stringConstantes.containsKey(contenido)) {
            String etiqueta = ".str" + contador++;
            stringConstantes.put(contenido, etiqueta);
        }
        return stringConstantes.get(contenido);
    }

    public static String generarDefiniciones() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entrada : stringConstantes.entrySet()) {
        String texto = entrada.getKey();
        String etiqueta = entrada.getValue();
        int longitud = texto.length() + 1; //+1 por ternimar nulo
        String contenido = texto.replace("\\", "\\\\").replace("\"", "\\22") + "\\00";
        sb.append("@").append(etiqueta)
          .append(" = private constant [")
          .append(longitud).append(" x i8] c\"")
          .append(contenido).append("\"\n");
        }
        return sb.toString();
    }
    
    public static void reset() {
        stringConstantes.clear();
        contador = 0;
    }
    
    
}