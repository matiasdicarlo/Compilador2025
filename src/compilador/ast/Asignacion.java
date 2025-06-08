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
public class Asignacion extends Nodo {
    private final Identificador identificador;
    private final Expresion expresion;

    public Asignacion(Identificador identificador, Expresion expresion) {
        this.identificador = identificador;
        this.expresion = expresion;
    }
    
    @Override
    public String getEtiqueta() {
        return ":=";
    }

  
    public Expresion getExpresion() {
        return expresion;
    }

    public Identificador getIdentificador() {
        return identificador;
    }
    
    
    @Override
    public String graficar(String idPadre) {
        String miId = getId();
        return super.graficar(idPadre) +
            identificador.graficar(miId) +
            expresion.graficar(miId);
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String nombre = identificador.getNombre();
        String tipoFuente = SymbolTable.getTipo(nombre); // "integer", "float", "bool", "float_array"
        String tipoLLVM = convertirATipoLLVM(tipoFuente);  // i32, double, i1
        if (expresion.getTipo().equals("float_array") && !(expresion instanceof InputArray)) {
            return generarAsignacionDesdeArrayLiteral(ctx, nombre);
        }
        if (tipoFuente.startsWith("float_array") && expresion instanceof Identificador) {
            return generarAsignacionDesdeIdentificadorArray(ctx, nombre, (Identificador) expresion);
        }
        if (expresion instanceof InputArray) {
            return generarAsignacionDesdeInputArray(ctx, nombre, (InputArray) expresion);
        }
        return generarAsignacionComun(ctx, nombre, tipoFuente, tipoLLVM);
    }

    
    private String generarAsignacionDesdeArrayLiteral(ContextoLLVM ctx, String nombreDestino) {
        String codigoLiteral = expresion.generarCodigoLLVM(ctx);
        String nombreLiteral = ((Constante) expresion).getReferenciaLLVM();
        int tamaño = ((Constante) expresion).getTamanioArray();
        String destino = "%" + nombreDestino;
        return codigoLiteral + "\n" + generarCopiaDeArray(ctx, nombreLiteral, destino, tamaño);
    }

    private String generarAsignacionDesdeInputArray(ContextoLLVM ctx, String nombreDestino, InputArray inputArray) {
        int tamaño = inputArray.getLongitud();
        String codigoInput = inputArray.generarCodigoLLVM(ctx);
        String nombreFuente = inputArray.getUltimaReferencia();
        String destino = "%" + nombreDestino;
        return codigoInput + "\n" + generarCopiaDeArray(ctx, nombreFuente, destino, tamaño);
    }

    private String generarAsignacionDesdeIdentificadorArray(ContextoLLVM ctx, String nombreDestino, Identificador fuente) {
        int tamaño = SymbolTable.getIndice(nombreDestino);  
        String nombreFuente = "%" + fuente.getNombre();
        String destino = "%" + nombreDestino;
        return generarCopiaDeArray(ctx, nombreFuente, destino, tamaño);
    }

    private String generarAsignacionComun(ContextoLLVM ctx, String nombre, String tipoFuente, String tipoLLVM) {
        String codigoExp = expresion.generarCodigoLLVM(ctx);
        String valor = extraerUltimaTemporal(codigoExp);
        return codigoExp + "\nstore " + tipoLLVM + " " + valor + ", " + tipoLLVM + "* %" + nombre;
    }

    private String generarCopiaDeArray(ContextoLLVM ctx, String fuente, String destino, int tamaño) {
        StringBuilder copia = new StringBuilder();
        for (int i = 0; i < tamaño; i++) {
            String ptrSrc = ctx.nuevoTemporal();
            String val = ctx.nuevoTemporal();
            String ptrDst = ctx.nuevoTemporal();
            copia.append(ptrSrc).append(" = getelementptr inbounds [")
             .append(tamaño).append(" x double], [").append(tamaño)
             .append(" x double]* ").append(fuente)
             .append(", i32 0, i32 ").append(i).append("\n");
            copia.append(val).append(" = load double, double* ").append(ptrSrc).append("\n");
            copia.append(ptrDst).append(" = getelementptr inbounds [")
             .append(tamaño).append(" x double], [").append(tamaño)
             .append(" x double]* ").append(destino)
             .append(", i32 0, i32 ").append(i).append("\n");
            copia.append("store double ").append(val).append(", double* ").append(ptrDst).append("\n");
        }
        return copia.toString();
    }
    
    private String convertirATipoLLVM(String tipoFuente) {
        switch (tipoFuente) {
            case "integer": return "i32";
            case "float": return "double";
            case "bool": return "i1";
            default: return "i32"; // por defecto
        }
    }

    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }
    
    
    

}
