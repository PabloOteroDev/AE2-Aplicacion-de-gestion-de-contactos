package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import controlador.ManejadorEventos;

public class VentanaNuevoContacto extends JFrame {

	private JLabel etiqueta;
	private JTextField cajaTextoNombre;
	private JTextField cajaTextoTelefono;
	private JButton boton_enviar;
	private JButton boton_cancelar;

	public VentanaNuevoContacto() {
		setSize(300, 300); // DIMENSIONES
		setLocationRelativeTo(null); // LOCALIZACION RELATIVA , NULL CENTRADO
		setResizable(false); // EVITAR QUE EL USUARIO REDIMENSIONE LA VENTANA
		setTitle("AÑADIR CONTACTO ");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./src/resources/image.png")); // CAMBIO DE ICONO
		this.getContentPane().setBackground(Color.BLACK);
		setLayout(null);
		inicializarComponentes();
		setVisible(true); // ULTIMA INSTRUCCION PARA LA VISUALIZACION
	}

	public JButton getBoton_cancelar() {
		return boton_cancelar;
	}

	public JButton getBoton_enviar() {
		return boton_enviar;
	}

	public JTextField getCajaTextoNombre() {
		return cajaTextoNombre;
	}

	public void setCajaTextoNombre(JTextField cajaTextoNombre) {
		this.cajaTextoNombre = cajaTextoNombre;
	}

	public JTextField getCajaTextoTelefono() {
		return cajaTextoTelefono;
	}

	public void setCajaTextoTelefono(JTextField cajaTextoTelefono) {
		this.cajaTextoTelefono = cajaTextoTelefono;
	}

	private void inicializarComponentes() {

		etiqueta = new JLabel("Nombre");
		etiqueta.setBounds(30, 30, 50, 30);
		etiqueta.setForeground(new Color(0, 255, 0)); 
		etiqueta.setFont(new Font("Monospaced", Font.PLAIN, 12));
		add(etiqueta);

		etiqueta = new JLabel("Telefono");
		etiqueta.setBounds(30, 80, 60, 30);
		etiqueta.setForeground(new Color(0, 255, 0)); 
		etiqueta.setFont(new Font("Monospaced", Font.PLAIN, 12));
		add(etiqueta);

		cajaTextoNombre = new JTextField();
		cajaTextoNombre.setBounds(100, 30, 150, 30);
		cajaTextoNombre.setBackground(Color.GRAY);
		cajaTextoNombre.setForeground(new Color(0, 255, 0)); 
		cajaTextoNombre.setFont(new Font("Monospaced", Font.PLAIN, 12));
		add(cajaTextoNombre);

		cajaTextoTelefono = new JTextField();
		cajaTextoTelefono.setBounds(100, 80, 150, 30);
		cajaTextoTelefono.setBackground(Color.GRAY);
		cajaTextoTelefono.setForeground(new Color(0, 255, 0)); 
		cajaTextoTelefono.setFont(new Font("Monospaced", Font.PLAIN, 12));
		add(cajaTextoTelefono);

		boton_enviar = new JButton("Enviar");
		boton_enviar.setBounds(50, 150, 80, 30);
		boton_enviar.setBackground(Color.GREEN);
		boton_enviar.setForeground(new Color(0, 0, 0)); 
		boton_enviar.setFont(new Font("Monospaced", Font.BOLD, 12));
		boton_enviar.addActionListener(new ActionListener() { //VAMOS A PONERLE EFECTOS DE SONIDO A LOS BOTONES
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./src/click.wav"); 
            }});
		add(boton_enviar);

		boton_cancelar = new JButton("Cancelar");
		boton_cancelar.setBounds(150, 150, 100, 30);
		boton_cancelar.setBackground(Color.GREEN);
		boton_cancelar.setForeground(new Color(0, 0, 0)); 
		boton_cancelar.setFont(new Font("Monospaced", Font.BOLD, 12));
		boton_cancelar.addActionListener(new ActionListener() { //VAMOS A PONERLE EFECTOS DE SONIDO A LOS BOTONES
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./src/click.wav"); 
            }});
		add(boton_cancelar);

	}

	public void iniciarManejador(ManejadorEventos manejador) {
		// TODO Auto-generated method stub
		cajaTextoNombre.addActionListener(manejador);
		cajaTextoTelefono.addActionListener(manejador);
		boton_enviar.addActionListener(manejador);
		boton_cancelar.addActionListener(manejador);
	}

	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public boolean validarTelefono(String telefono) {
		for (char c : telefono.toCharArray()) {
			if (!Character.isDigit(c)) {
				JOptionPane.showMessageDialog(this, "TELEFONO INCORRECTO", "Error", JOptionPane.ERROR_MESSAGE);
				return false; // Se encontró un caracter no numérico
			}
		}
		return true; // Todos los caracteres son numéricos
	}
	

	public void playSound(String soundFilePath) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error al reproducir sonido.");
	        ex.printStackTrace();
	    }
	}

}

