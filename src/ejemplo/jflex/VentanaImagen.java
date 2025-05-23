/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejemplo.jflex;

/**
 *
 * @author Usuario
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class VentanaImagen extends JFrame {
    private JLabel etiquetaImagen;
    private ImageIcon imagenOriginal;
    private double escala = 1.0;

    public VentanaImagen(String rutaImagen) {
        super("Arbol de Sintaxis Abstracta");

        // Cargar imagen
        imagenOriginal = new ImageIcon(rutaImagen);
        etiquetaImagen = new JLabel(imagenOriginal);

        // ScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(etiquetaImagen);

        // Zoom con rueda del mouse
        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (notches < 0) {
                    escala *= 1.1;
                } else {
                    escala /= 1.1;
                }

                // Escalar imagen
                int nuevoAncho = (int) (imagenOriginal.getIconWidth() * escala);
                int nuevoAlto = (int) (imagenOriginal.getIconHeight() * escala);

                Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
                etiquetaImagen.setIcon(new ImageIcon(imagenEscalada));
                etiquetaImagen.revalidate();
            }
        });

        // Configurar ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        add(scrollPane);
    }

    public void mostrar() {
        setVisible(true);
    }
}
