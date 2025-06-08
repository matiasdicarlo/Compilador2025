/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class InputArray extends Expresion {
    private int longitud;
    private String ultimaReferenciaLLVM;
    
    public InputArray(int tamaño) {
        this.longitud = tamaño;
    }
      
    @Override
    public String getEtiqueta() {
        return "INPUT_ARRAY";
    }
    

    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        this.ultimaReferenciaLLVM = ctx.nuevoTemporal();
        StringBuilder sb = new StringBuilder();
        sb.append(ultimaReferenciaLLVM).append(" = alloca [")
        .append(longitud).append(" x double]\n");
        String ptr = ctx.nuevoTemporal();
        sb.append(ptr).append(" = getelementptr inbounds [")
        .append(longitud).append(" x double], [").append(longitud)
        .append(" x double]* ").append(ultimaReferenciaLLVM).append(", i32 0, i32 0\n");
        String tmpCall = ctx.nuevoTemporal();
        sb.append(tmpCall).append(" = call i32 @leerArray(double* ").append(ptr)
        .append(", i32 ").append(longitud).append(")\n");
        return sb.toString();
    }

    public String getUltimaReferencia() {
        return this.ultimaReferenciaLLVM;
     }

    
    public int getLongitud() {
        return this.longitud;
    }

    @Override
    public String getTipo() {
        return "float_array";
    }

}
