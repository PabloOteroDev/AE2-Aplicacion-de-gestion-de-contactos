package controlador;

import javax.swing.SwingUtilities;

import vista.VentanaInicial;
import vista.VentanaPrincipal;

public class Main {

	public static void main(String[] args) {
        VentanaInicial splash = new VentanaInicial("./src/matrix.gif", 5000);
        splash.setVisible(true);

		// INSTANCIAMOS UNA VENTANAPRINCIPAL Y UN MANEJADOR, LOS VINCULAMOS Y LLAMADA AL
		// METODO CORRESPONDIENTE
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
        	VentanaPrincipal ventana = new VentanaPrincipal();
        	ManejadorEventos manejador = new ManejadorEventos(ventana);
        	ventana.iniciarManejador(manejador);
          }
        });
	}
}
