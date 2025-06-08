/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public abstract class OperacionBinaria extends Expresion{
    private Expresion izquierda;
    private Expresion derecha;

    public OperacionBinaria(Expresion izquierda, Expresion derecha) {
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    public Expresion getIzquierda() {
        return izquierda;
    }

    public Expresion getDerecha() {
        return derecha;
    }
 
    
    @Override
    public String getEtiqueta() {
        return String.format("%s", this.getNombreOperacion());
    }

    protected abstract String getNombreOperacion();

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                izquierda.graficar(miId) +
                derecha.graficar(miId);
    }
    
    protected abstract String operadorLLVM(String tipoLLVM); 

    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        /*if (izquierda instanceof NodoConversion){ izquierda=izquierda.getExp();}
        if (derecha instanceof NodoConversion){ derecha=derecha.getExp();}*/
        String codeIzq = izquierda.generarCodigoLLVM(ctx);
        String tmpIzq = extraerUltimaTemporal(codeIzq);
        String codeDer = derecha.generarCodigoLLVM(ctx);
        String tmpDer = extraerUltimaTemporal(codeDer);
        String tipoIzq = izquierda.getTipo();
        String tipoDer = derecha.getTipo();
        String tipoFinal = promoverTipos(tipoIzq, tipoDer); //por defecto Float (tipo mas permisivo)
        String tipoLLVM = convertirATipoLLVM(tipoFinal);
        // conversion de int a float si necesario
        /*if (!tipoIzq.equals(tipoDer)) {
            if (tipoIzq.equals("integer") && tipoDer.equals("float")) {
                String convertido = ctx.nuevoTemporal();
                codeIzq += "\n" + convertido + " = sitofp i32 " + tmpIzq + " to double";
                tmpIzq = convertido;
            } else if (tipoIzq.equals("float") && tipoDer.equals("integer")) {
                String convertido = ctx.nuevoTemporal();
                codeDer += "\n" + convertido + " = sitofp i32 " + tmpDer + " to double";
                tmpDer = convertido;
            }
        }*/
        String opLLVM = operadorLLVM(tipoFinal); 
        String resultado = ctx.nuevoTemporal();
        return codeIzq + "\n" + codeDer + "\n" +
               resultado + " = " + opLLVM + " " + tipoLLVM + " " + tmpIzq + ", " + tmpDer;
    }

    
    @Override
    public String getTipo() {
        String t1 = izquierda.getTipo();
        String t2 = derecha.getTipo();
        if (t1.equals("float") || t2.equals("float")) return "float";
        return t1;
    }

    public String promoverTipos(String t1, String t2) {
        if (t1.equals("float") || t2.equals("float")) return "float";
        if (t1.equals("integer") || t2.equals("integer")) return "integer";
        return t1; // Caso "bool"
    }
    
    
    public String convertirATipoLLVM(String tipo) {
        switch (tipo) {
            case "integer": return "i32";
            case "float": return "double";
            case "bool": return "i1";
            default: return "i32";
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
