/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class AccesoArray extends Expresion {
    private final Identificador arreglo;
    private final Constante indice;
    private final Identificador indiceIden;
    

    public AccesoArray(Identificador arreglo, Constante indice) {
        this.arreglo = arreglo;
        this.indice = indice;
        this.indiceIden = null;
        
    }
    public AccesoArray(Identificador arreglo,Identificador indice) {
        this.arreglo = arreglo;
        this.indiceIden = indice;
        this.indice = null;
    }
    
    
    @Override
    public String getEtiqueta() {
        return "FloatArray[]";
    }
    
    @Override
    protected String graficar(String idPadre) {
        final String miId = this.getId();
        if(indice!=null){
        return super.graficar(idPadre)
            + arreglo.graficar(miId)
            + indice.graficar(miId);}
        else {
         return super.graficar(idPadre)
            + arreglo.graficar(miId)
            + indiceIden.graficar(miId);}
        
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
           StringBuilder sb = new StringBuilder();
        String indiceCode="";
        if (indice !=null){
             indiceCode = indice.generarCodigoLLVM(ctx);}
        if (indiceIden !=null){
             indiceCode = indiceIden.generarCodigoLLVM(ctx);}
        String tmpIndice = extraerUltimaTemporal(indiceCode);

        String tmpPtr = ctx.nuevoTemporal();
        String tmpVal = ctx.nuevoTemporal();
        String nombre = arreglo.getNombre(); 

        sb.append(indiceCode).append("\n");
        sb.append(tmpPtr).append(" = getelementptr inbounds [100 x double], [100 x double]* %")
        .append(nombre).append(", i32 0, i32 ").append(tmpIndice).append("\n");

        sb.append(tmpVal).append(" = load double, double* ").append(tmpPtr);

        return sb.toString();
    }
    
    protected String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }
    
    @Override
    public String getTipo() {
        return "float";   
    }

}
