/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.ast;

/**
 *
 * @author Usuario
 */
public class ContextoSTR {
    private int str;

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public ContextoSTR(int str) {
        this.str = str;
    }
    
    public int getStrYIncrementar() {
        this.str=str+1;
        return str;
    }
    
}
