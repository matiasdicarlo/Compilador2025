/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class InputFloat extends Expresion {
    @Override
    public String getEtiqueta() {
        return "INPUT_FLOAT";
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String ptr = ctx.nuevoTemporal();
        String call = ctx.nuevoTemporal();
        String load = ctx.nuevoTemporal();
        StringBuilder sb = new StringBuilder();
        sb.append(ptr).append(" = alloca double\n");
        sb.append(call).append(" = call i32 (i8*, ...) @scanf(")
        .append("i8* getelementptr inbounds ([4 x i8], [4 x i8]* @double_read_format, i64 0, i64 0), ")
        .append("double* ").append(ptr).append(")\n");
        sb.append(load).append(" = load double, double* ").append(ptr);
        return sb.toString();
    }
    
    @Override
    public String getTipo() {
        return "float";
    }
}
