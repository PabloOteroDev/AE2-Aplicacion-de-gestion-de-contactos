package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaInicial extends JWindow {
    public VentanaInicial (String imagePath, int duracion) {
        ImageIcon splashImage = new ImageIcon(imagePath);
        JLabel label = new JLabel(splashImage);
        getContentPane().add(label, BorderLayout.CENTER);
        pack(); // AJUSTAR EL TAMAÑO SEGÚN LA IMAGEN
        setLocationRelativeTo(null); //CENTRAR

        // Show the splash screen for the specified duration
        try {
            setVisible(true);
            Thread.sleep(duracion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            setVisible(false);
            dispose();
        }
    }
}
