/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class NodoConversion extends Expresion{
    private Expresion exp;

    public NodoConversion(Expresion exp) {
        this.exp = exp;
    }

    public Expresion getExp() {
        return exp;
    }
    
    
    @Override
    public String getEtiqueta() {
        return "Coversion de tipo";
    }
    
    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre)+exp.graficar(miId);
    }
    
    
     @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String codeOrig = exp.generarCodigoLLVM(ctx);
        String tmpOrig = extraerUltimaTemporal(codeOrig);
        String tipoOrig = exp.getTipo();
        String convertido = ctx.nuevoTemporal();
        String conversion = "";

        if (exp.getTipo().equals("integer")) {
            conversion = convertido + " = sitofp i32 " + tmpOrig + " to double";
        } else if (exp.getTipo().equals("float")) {
            conversion = convertido + " = fptosi double " + tmpOrig + " to i32";
        } else {
            throw new RuntimeException("Conversión no soportada: " + tipoOrig );
        }

        return codeOrig + "\n" + conversion;
    }
    
    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }
}
