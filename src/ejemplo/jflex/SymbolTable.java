/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplo.jflex;

/**
 *
 * @author Usuario
 */
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SymbolTable {
    private static HashMap<String, String> table;

    public SymbolTable() {
        table = new HashMap<>();
    }

    public void add(String id, String type) {
        table.put(id, type);
    }

    public boolean exists(String id) {
        return table.containsKey(id);
    }

    public int getLongitud(String nombre){
        String texto=table.get(nombre);
        int inicio = texto.indexOf('[') + 1;
        int fin = texto.indexOf(']');
        String contenido = texto.substring(inicio, fin);
        return Integer.parseInt(contenido);
    }
    
     public static String getTipo(String nombre) {
         return table.getOrDefault(nombre, "integer"); // default
    }
     
     public static boolean existe(String nombre) {
        return table.containsKey(nombre);
    }
    
    public void print() {
    System.out.println("\n=== Tabla de Símbolos ===");

    if (table.isEmpty()) {
        System.out.println("  (No hay símbolos registrados)");
        return;
    }

System.out.printf("----------------------------------------------%n");
System.out.printf("| %-20s | %-12s |%n", "Identificador/Literal", "Tipo/Valor");
System.out.printf("----------------------------------------------%n");

    for (String id : table.keySet()) {
        System.out.printf("| %-20s | %s |%n", id, table.get(id));
    }
    System.out.printf("----------------------------------------------%n");
}
    // NUEVO MÉTODO PARA MOSTRAR EN GUI
    public void mostrarTablaEnGUI() {
        if (table.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay símbolos registrados.", "Tabla Vacía", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnas = {"Identificador/Literal", "Tipo/Valor"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

       for (HashMap.Entry<String, String> entry : table.entrySet()) {
            modelo.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }


        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        JFrame frame = new JFrame("Tabla de Símbolos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // O EXIT_ON_CLOSE si es tu app principal
        frame.add(scroll);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null); // Centrar
        frame.setVisible(true);
    }
}
