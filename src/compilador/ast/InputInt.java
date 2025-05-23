
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class InputInt extends Expresion {
    
    @Override
    public String getEtiqueta() {
        return "INPUT_INT";
    }
    
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        String ptr = ctx.nuevoTemporal();
        String call = ctx.nuevoTemporal();
        String load = ctx.nuevoTemporal();
        StringBuilder sb = new StringBuilder();
        sb.append(ptr).append(" = alloca i32\n");
        sb.append(call).append(" = call i32 (i8*, ...) @scanf(")
        .append("i8* getelementptr inbounds ([3 x i8], [3 x i8]* @int_read_format, i64 0, i64 0), ")
        .append("i32* ").append(ptr).append(")\n");
        sb.append(load).append(" = load i32, i32* ").append(ptr);
        return sb.toString();
    }

    
    @Override
    public String getTipo() {
        return "integer"; 
    }
    
}
