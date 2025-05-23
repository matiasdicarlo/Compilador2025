/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class Display extends Nodo {
    private final Expresion expresion;

    public Display(Expresion expresion) {
        this.expresion = expresion;
    }

    @Override
    public String getEtiqueta() {
        return "DISPLAY";
    }

    public Expresion getExpresion() {
        return expresion;
    }

    @Override
    protected String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre)
                + expresion.graficar(miId);
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String tipo = expresion.getTipo();

        switch (tipo) {
            case "integer":
                return generarPrintFormato("@.integer", "i32", expresion, ctx);
            case "float":
                return generarPrintFormato("@.float", "double", expresion, ctx);
            case "string":
                return generarPrintString((Constante) expresion, ctx);
            default:
                return "; tipo no soportado en DISPLAY";
        }
    }

    private String generarPrintFormato(String formato, String tipoLLVM, Expresion exp, ContextoLLVM ctx) {
        String codigoExp = exp.generarCodigoLLVM(ctx);
        String tmp = extraerUltimaTemporal(codigoExp);
        String tmpPtr = ctx.nuevoTemporal();

            return codigoExp + "\n" +
            tmpPtr + " = getelementptr [4 x i8], [4 x i8]* " + formato + ", i32 0, i32 0\n" +
            "call i32 (i8*, ...) @printf(i8* " + tmpPtr + ", " + tipoLLVM + " " + tmp + ")";
    }

    private String generarPrintString(Constante cadena, ContextoLLVM ctx) {
        String texto = (String) cadena.getValor();
        String etiqueta = GestorStringsLLVM.registrar(texto);
        int longitud = texto.length() + 1;
        String tmpPtr = ctx.nuevoTemporal();
        return tmpPtr + " = getelementptr [" + longitud + " x i8], [" + longitud + " x i8]* @" + etiqueta + ", i32 0, i32 0\n" +
           "call i32 @puts(i8* " + tmpPtr + ")";
    }
    
    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }

}