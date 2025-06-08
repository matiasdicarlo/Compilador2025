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
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre)
                + expresion.graficar(miId);
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String tipo = expresion.getTipo();
        if (tipo.startsWith("float_array")){tipo="float_array";}
        switch (tipo) {
            case "integer":
                return generarPrintFormato("@.integer", "i32", expresion, ctx);
            case "float":
                return generarPrintFormato("@.float", "double", expresion, ctx);
            case "string":
                return generarPrintString((Constante) expresion, ctx);
            case "bool": 
                 return generarPrintBool(ctx); 
           case "float_array":
                return generarPrintArray(ctx);
            default:
                return "; tipo no soportado en DISPLAY";
        }
    }

    private String generarPrintBool(ContextoLLVM ctx){
        String codigoExp = expresion.generarCodigoLLVM(ctx);
        String tmpBool = extraerUltimaTemporal(codigoExp);
        String tmpPtrTrue = ctx.nuevoTemporal();
        String tmpPtrFalse = ctx.nuevoTemporal();
        String tmpSelect = ctx.nuevoTemporal();
        String tmpPrintPtr = ctx.nuevoTemporal();

        return codigoExp + "\n" +
            tmpPtrTrue + " = getelementptr [5 x i8], [5 x i8]* @.true_str, i32 0, i32 0\n" +
            tmpPtrFalse + " = getelementptr [6 x i8], [6 x i8]* @.false_str, i32 0, i32 0\n" +
            tmpSelect + " = select i1 " + tmpBool + ", i8* " + tmpPtrTrue + ", i8* " + tmpPtrFalse + "\n" +
            "call i32 @puts(i8* " + tmpSelect + ")";
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
    
    private String generarPrintArray(ContextoLLVM ctx) { //Se genera un bucle para imrpimir cada uno de los elementos del array
       StringBuilder sb = new StringBuilder();
       String nombre = ((Identificador) expresion).getNombre(); 
       int longitud =SymbolTable.getIndice(expresion.getNombre()); 
       String tmpI = ctx.nuevoTemporal();
       String ptrI = ctx.nuevoTemporal();
       String cmp = ctx.nuevoTemporal();
       String etiquetaCond = ctx.nuevaEtiqueta("cond");
       String etiquetaBody = ctx.nuevaEtiqueta("body");
       String etiquetaFin = ctx.nuevaEtiqueta("fin");
       String formato = "@.float"; 
       sb.append(tmpI).append(" = alloca i32\n");
       sb.append("store i32 0, i32* ").append(tmpI).append("\n");
       sb.append("br label %").append(etiquetaCond).append("\n\n");
       // Condición del bucle
       sb.append(etiquetaCond).append(":\n");
       String valI = ctx.nuevoTemporal();
       sb.append(valI).append(" = load i32, i32* ").append(tmpI).append("\n");
       sb.append(cmp).append(" = icmp slt i32 ").append(valI).append(", ").append(longitud).append("\n");
       sb.append("br i1 ").append(cmp).append(", label %").append(etiquetaBody).append(", label %").append(etiquetaFin).append("\n\n");
       // Cuerpo del bucle
       sb.append(etiquetaBody).append(":\n");
       String ptrElem = ctx.nuevoTemporal();
       sb.append(ptrElem).append(" = getelementptr [").append(longitud).append(" x double], [").append(longitud).append(" x double]* %").append(nombre).append(", i32 0, i32 ").append(valI).append("\n");
       String valElem = ctx.nuevoTemporal();
       sb.append(valElem).append(" = load double, double* ").append(ptrElem).append("\n");
       // Imprimir el valor
       String ptrFormat = ctx.nuevoTemporal();
       sb.append(ptrFormat).append(" = getelementptr [4 x i8], [4 x i8]* ").append(formato).append(", i32 0, i32 0\n");
       sb.append("call i32 (i8*, ...) @printf(i8* ").append(ptrFormat).append(", double ").append(valElem).append(")\n");
       // Incremento
       String inc = ctx.nuevoTemporal();
       sb.append(inc).append(" = add i32 ").append(valI).append(", 1\n");
       sb.append("store i32 ").append(inc).append(", i32* ").append(tmpI).append("\n");
       sb.append("br label %").append(etiquetaCond).append("\n\n");
       // Fin del bucle
       sb.append(etiquetaFin).append(":\n");
       return sb.toString();

    }

}