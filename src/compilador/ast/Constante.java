/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Usuario
 */
public class Constante extends Expresion {
    private Object valor;
    private String nombreTemporalLLVM;
    private int tamanioArray;


    public Constante(Object valor) {
        this.valor = valor;
        if (valor instanceof String) {
        GestorStringsLLVM.registrar((String) valor); //registrar el LitString para el Display
        }
    }

    public Object getValor() {
        return valor;
    }
    
    @Override
    public String getEtiqueta() {
        return String.format(String.format("LIT %s", getValor()));
    }
    
    @Override
    protected String graficar(String idPadre) {
        return super.graficar(idPadre);
    }
        
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String tipo = getTipo();

        if (tipo.equals("float_array")) {
            String nombreTemp = ctx.nuevoTemporal().substring(1);
            nombreTemporalLLVM = nombreTemp;  // GUARDAMOS el nombre para otras referencias

            String arrayStr = valor.toString().trim();
            arrayStr = arrayStr.substring(1, arrayStr.length() - 1);
            String[] elementos = arrayStr.split(",");
            int longitud = elementos.length;

            StringBuilder sb = new StringBuilder();
            sb.append("%").append(nombreTemp)
            .append(" = alloca [").append(longitud).append(" x double]\n");

            for (int i = 0; i < longitud; i++) {
                String elemStr = elementos[i].trim();
                String ptr = ctx.nuevoTemporal();
                String val = ctx.nuevoTemporal();

                sb.append(ptr).append(" = getelementptr inbounds [")
                .append(longitud).append(" x double], [").append(longitud)
                .append(" x double]* %").append(nombreTemp)
                .append(", i32 0, i32 ").append(i).append("\n");

                sb.append(val).append(" = fadd double 0.0, ").append(elemStr).append("\n");
                sb.append("store double ").append(val).append(", double* ").append(ptr).append("\n");
            }

            return sb.toString(); 
        }

    String tmp = ctx.nuevoTemporal();
    switch (tipo) {
        case "integer":
            return tmp + " = add i32 0, " + valor;
        case "float":
            return tmp + " = fadd double 0.0, " + valor;
        case "bool":
            if (valor.equals("true")) valor = true;
            if (valor.equals("false")) valor = false;
            int boolVal = (Boolean) valor ? 1 : 0;
            return tmp + " = xor i1 0, " + boolVal;
        default:
            return "; tipo no soportado en constante";
    }
}

    public String getReferenciaLLVM() {
        return "%" + nombreTemporalLLVM;
    }

    
    
    @Override
    public String getTipo() {
        if (valor instanceof Integer) return "integer";
        if (valor instanceof Float || valor instanceof Double) return "float";
        if (valor instanceof Boolean) return "bool";
        if (valor.equals("true") || valor.equals("false")){return "bool";}
        if (esLitArray(valor.toString()))return "float_array";
        if (valor instanceof String) return "string";
        
    return "unknown";
    }

    public Boolean esLitArray(String array){
        String regex = "\\[\\s*(-?(?:[0-9]+\\.[0-9]+|[0-9]+\\.|\\.[0-9]+))" +
                       "(\\s*,\\s*-?(?:[0-9]+\\.[0-9]+|[0-9]+\\.|\\.[0-9]+))*\\s*\\]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(array);
        if (matcher.matches()) {
           return true;
        } else {
            return false;
        }
    }
    
    public int getTamanioArray() {
        if (valor instanceof String && esLitArray(valor.toString())) {
            String texto = valor.toString().trim();
            if (texto.startsWith("[") && texto.endsWith("]")) {
                texto = texto.substring(1, texto.length() - 1); // quitar [ ]
                if (texto.isEmpty()) return 0;
                return texto.split(",").length;
                }
            }
        return 0;       
    }
        
    
}
