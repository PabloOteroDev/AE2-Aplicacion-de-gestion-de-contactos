package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import controlador.ManejadorEventos;

public class VentanaModificarContacto extends JFrame {

	private JLabel etiqueta;
	private JTextField cajaTextoNombre;
	private JTextField cajaTextoTelefono;
	private JButton botonEnviar;
	private JButton botonCancelar;
	private ManejadorEventos manejador2;

	public VentanaModificarContacto() {
		setSize(300, 300); // DIMENSIONES
		setLocationRelativeTo(null); // LOCALIZACION RELATIVA , NULL CENTRADO
		setResizable(false); // EVITAR QUE EL USUARIO REDIMENSIONE LA VENTANA
		setTitle("MODIFICAR CONTACTO ");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./src/resources/image.png")); // CAMBIO DE ICONO
		this.getContentPane().setBackground(Color.BLACK);
		setLayout(null);
		inicializarComponentes();
		setVisible(true); // ULTIMA INSTRUCCION PARA LA VISUALIZACION
	}

	public JButton getBoton_cancelar() {
		return botonCancelar;
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

	public JButton getBotonEnviar() {
		return botonEnviar;
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

		botonEnviar = new JButton("Enviar");
		botonEnviar.setBounds(50, 150, 80, 30);
		botonEnviar.setBackground(Color.GREEN);
		botonEnviar.setForeground(new Color(0, 0, 0)); 
		botonEnviar.setFont(new Font("Monospaced", Font.BOLD, 12));
		botonEnviar.addActionListener(new ActionListener() { //VAMOS A PONERLE EFECTOS DE SONIDO A LOS BOTONES
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./src/click.wav"); 
            }});
		add(botonEnviar);

		botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(150, 150, 100, 30);
		botonCancelar.setBackground(Color.GREEN);
		botonCancelar.setForeground(new Color(0, 0, 0)); 
		botonCancelar.setFont(new Font("Monospaced", Font.BOLD, 12));
		botonCancelar.addActionListener(manejador2);
		botonCancelar.addActionListener(new ActionListener() { //VAMOS A PONERLE EFECTOS DE SONIDO A LOS BOTONES
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./src/click.wav"); 
            }});

		add(botonCancelar);

	}

	public void iniciarManejador(ManejadorEventos manejador) {
		// TODO Auto-generated method stub
		botonEnviar.addActionListener(manejador);
		botonCancelar.addActionListener(manejador);
		cajaTextoNombre.addActionListener(manejador);
		cajaTextoTelefono.addActionListener(manejador);
	}

	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void mostrarMensajeInformacion(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Informacion: ", JOptionPane.INFORMATION_MESSAGE);
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
