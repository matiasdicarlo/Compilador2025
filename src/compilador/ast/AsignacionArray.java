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
public class AsignacionArray extends Nodo {
    private final Identificador arreglo;
    private final Expresion indice;
    private final Expresion valor;

    public AsignacionArray(Identificador arreglo, Expresion indice, Expresion valor) {
        this.arreglo = arreglo;
        this.indice = indice;
        this.valor = valor;
    }

    public Identificador getArreglo() {
        return arreglo;
    }

    public Expresion getIndice() {
        return indice;
    }

    public Expresion getValor() {
        return valor;
    }
    
  
       
    @Override
    public String getEtiqueta() {
        return "["+indice.getValor()+"] := "; 
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        StringBuilder builder = new StringBuilder();
        builder.append(super.graficar(idPadre));
        // para el arreglo
        builder.append(arreglo.graficar(miId));
        
        // para el valor a asignar
        builder.append(valor.graficar(miId));
            return builder.toString();
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String nombre = arreglo.getNombre();
        String codeIdx = indice.generarCodigoLLVM(ctx);
        String tmpIdx = extraerUltimaTemporal(codeIdx);
        String codeVal = valor.generarCodigoLLVM(ctx);
        String tmpVal = extraerUltimaTemporal(codeVal);
        String ptr = ctx.nuevoTemporal();
        StringBuilder sb = new StringBuilder();
        sb.append(codeIdx).append("\n");
        sb.append(codeVal).append("\n");
        // Si el valor es integer, convertirlo a double
        if (valor.getTipo().equals("integer")) {
            String convertido = ctx.nuevoTemporal();
            sb.append(convertido).append(" = sitofp i32 ").append(tmpVal).append(" to double\n");
            tmpVal = convertido;
        }
        int longitud =SymbolTable.getIndice(arreglo.getNombre());
        sb.append(ptr).append(" = getelementptr inbounds ["+longitud+" x double], ["+longitud+"x double]* %")
        .append(nombre).append(", i32 0, i32 ").append(tmpIdx).append("\n");
        sb.append("store double ").append(tmpVal).append(", double* ").append(ptr);

        return sb.toString();
    }

    private String extraerUltimaTemporal(String codigo) {
        String[] lines = codigo.split("\\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            if (lines[i].startsWith("%")) return lines[i].split(" ", 2)[0];
        }
        return "";
    }
}
