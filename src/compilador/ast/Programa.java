/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

import java.io.IOException;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class Programa extends Nodo {
    private final List<Nodo> declaraciones;
    private final List<Nodo> instrucciones;

    public Programa(List<Nodo> declaraciones, List<Nodo> instrucciones) {
        this.declaraciones = declaraciones;
        this.instrucciones = instrucciones;
    }

    @Override
    public String getEtiqueta() {
        return "programa";
    }

    public List<Nodo> getDeclaraciones() {
        return declaraciones;
    }

    public List<Nodo> getInstrucciones() {
        return instrucciones;
    }
      
    
    public String graficar() throws IOException {
        StringBuilder resultado = new StringBuilder();
        resultado.append("graph G {\n");
        resultado.append(this.graficar(null));
        for (Nodo decl : declaraciones) {
            resultado.append(decl.graficar(this.getId()));
        }
        for (Nodo inst : instrucciones) {
            resultado.append(inst.graficar(this.getId()));
        }
        resultado.append("}");
        return resultado.toString();
    }
    
    @Override
    public String generarCodigoLLVM(ContextoLLVM ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("; Módulo generado por el compilador\n");
        sb.append("declare void @printInt(i32)\n\n");
        sb.append("declare i32 @puts(i8*)\n");
        sb.append("declare i32 @printf(i8*, ...)\n");    
        sb.append("@.integer = private constant [4 x i8] c\"%d\\0A\\00\"\n");
        sb.append("@.float = private constant [4 x i8] c\"%f\\0A\\00\"\n");        
        sb.append("@.true_str = private constant [5 x i8] c\"true\\00\"\n");
        sb.append("@.false_str = private constant [6 x i8] c\"false\\00\"\n");
        sb.append("declare i32 @scanf(i8*, ...)\n");
        sb.append("@int_read_format = unnamed_addr constant [3 x i8] c\"%d\\00\"\n");
        sb.append("@double_read_format = unnamed_addr constant [4 x i8] c\"%lf\\00\"\n");
        sb.append("declare i32 @leerArray(double*, i32)");
        sb.append(GestorStringsLLVM.generarDefiniciones());
        sb.append("define i32 @main() {\n");
      
        for (Nodo dec : declaraciones) {
            sb.append("  ").append(dec.generarCodigoLLVM(ctx)).append("\n");
        }
        for (Nodo stmt : instrucciones) {
            sb.append("  ").append(stmt.generarCodigoLLVM(ctx)).append("\n");
        }
        sb.append("  ret i32 0\n");
        sb.append("}\n");
        return sb.toString();
    }
    

}
