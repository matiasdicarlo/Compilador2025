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
    @Override
    public String getEtiqueta() {
        return "INPUT_ARRAY";
    }
    

@Override
public String generarCodigoLLVM(ContextoLLVM ctx) {
    String tmpPtr = ctx.nuevoTemporal();
    String tmpScan = ctx.nuevoTemporal();
    String tmpVal = ctx.nuevoTemporal();

    StringBuilder sb = new StringBuilder();
    sb.append(tmpPtr).append(" = alloca double\n");
    sb.append(tmpScan).append(" = call i32 (i8*, ...) @scanf(i8* getelementptr ")
      .append("([4 x i8], [4 x i8]* @.float, i32 0, i32 0), double* ")
      .append(tmpPtr).append(")\n");
    sb.append(tmpVal).append(" = load double, double* ").append(tmpPtr);

    return sb.toString();
}


    @Override
    public String getTipo() {
        return "float";
    }

}
