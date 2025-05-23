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
    String nombre = identificador.getEtiqueta();
    String tipoFuente = SymbolTable.getTipo(nombre); // "integer", "float", "bool"
    String tipoLLVM = convertirATipoLLVM(tipoFuente);  // i32, double, i1

    if (expresion.getTipo().equals("float_array")) {
        // Generar código del array literal
        String codigoLiteral = expresion.generarCodigoLLVM(ctx);
        String nombreLiteral = ((Constante) expresion).getReferenciaLLVM();
        int tamaño = ((Constante) expresion).getTamanioArray();
        String nombreDestino = "%" + identificador.getNombre();

        StringBuilder copia = new StringBuilder();
        for (int i = 0; i < tamaño; i++) {
            String ptrSrc = ctx.nuevoTemporal();
            String val = ctx.nuevoTemporal();
            String ptrDst = ctx.nuevoTemporal();

            copia.append(ptrSrc).append(" = getelementptr inbounds [")
                .append(tamaño).append(" x double], [").append(tamaño)
                .append(" x double]* ").append(nombreLiteral)
                .append(", i32 0, i32 ").append(i).append("\n");

            copia.append(val).append(" = load double, double* ").append(ptrSrc).append("\n");

            copia.append(ptrDst).append(" = getelementptr inbounds [")
                .append(tamaño).append(" x double], [").append(tamaño)
                .append(" x double]* ").append(nombreDestino)
                .append(", i32 0, i32 ").append(i).append("\n");

            copia.append("store double ").append(val).append(", double* ").append(ptrDst).append("\n");
        }

        return codigoLiteral + "\n" + copia.toString();
    }

    // Caso común
    String codigoARetornar="";
    String codigoExp = expresion.generarCodigoLLVM(ctx);
    String valor = extraerUltimaTemporal(codigoExp);
    if (tipoFuente=="float" && expresion.getTipo()=="integer"){
        String convertido = ctx.nuevoTemporal();
        codigoExp += "\n" + convertido + " = sitofp i32 " + valor + " to double";
        String valorConversion = extraerUltimaTemporal(codigoExp);
        codigoARetornar=codigoExp + "\nstore " + tipoLLVM + " " + valorConversion + ", " + tipoLLVM + "* %" + nombre;
        return codigoARetornar;
            }
    codigoARetornar=codigoExp + "\nstore " + tipoLLVM + " " + valor + ", " + tipoLLVM + "* %" + nombre;   
    return codigoARetornar;
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
