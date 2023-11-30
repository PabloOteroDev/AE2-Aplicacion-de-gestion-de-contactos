package vista;

import modelo.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controlador.ManejadorEventos;

class BackgroundPanel extends JPanel { //VAMOS A AÑADIR UNA IMAGEN DE FONDO
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}


public class VentanaPrincipal extends JFrame {

	// propiedades de la clase objetos

	private JLabel etiqueta;
	private JTextField cajaTexto;
	private JButton botonAgregar;
	private JButton botonModificar;
	private JButton botonEliminar;
	private JTable tabla;
	private DefaultTableModel modelo;
	private JScrollPane scrollPane;
	private ContactDAOImpl dao;

	public VentanaPrincipal() {
		setSize(700, 600); // DIMENSIONES
		setLocationRelativeTo(null); // LOCALIZACION RELATIVA , NULL CENTRADO
		setDefaultCloseOperation(EXIT_ON_CLOSE); // COMPORTAMIENTO DEL BOTON
        setContentPane(new BackgroundPanel("./src/matrix.jpg")); // Set the background image
		setResizable(false); // EVITAR QUE EL USUARIO REDIMENSIONE LA VENTANA
		setTitle("AppContactos: ");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./src/resources/image.png")); // CAMBIO DE ICONO
		setLayout(null);
        this.getContentPane().setBackground(Color.BLACK); //PONER EL FONDO NEGRO, ESTILO RETRO
        inicializarComponentes();
		setVisible(true); // ULTIMA INSTRUCCION PARA LA VISUALIZACION
	}

	
	public JLabel getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(JLabel etiqueta) {
		this.etiqueta = etiqueta;
	}

	public JTextField getCajaTexto() {
		return cajaTexto;
	}

	public void setCajaTexto(JTextField cajaTexto) {
		this.cajaTexto = cajaTexto;
	}

	public JButton getBoton_agregar() {
		return botonAgregar;
	}

	public void setBoton_agregar(JButton boton_agregar) {
		this.botonAgregar = boton_agregar;
	}

	public JButton getBoton_eliminar() {
		return botonEliminar;
	}

	public void setBoton_eliminar(JButton boton_eliminar) {
		this.botonEliminar = boton_eliminar;
	}

	public JButton getBoton_modificar() {
		return botonModificar;
	}

	public void setBoton_modificar(JButton boton_modificar) {
		this.botonModificar = boton_modificar;
	}

	private void inicializarComponentes() {

		// CREAMOS DEFAULT TABLE MODEL PARA LA TABLA
		modelo = new DefaultTableModel();
		modelo.addColumn("Nombre");
		modelo.addColumn("Teléfono");

		// GENERAMOS CARGA DE DATOS INICIAL PARA LA TABLA

		dao = new ContactDAOImpl();
		dao.cargarDatos();

		// CREACION DE TABLA Y AGREGAR CONTACTOS A FILAS
		actualizarTabla();
		tabla = new JTable(modelo);
		scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(150, 50, 300, 400);
		scrollPane.getViewport().setBackground(Color.GRAY); //PARA PONER EL FONDO DE LA TABLA EN GRIS
		tabla.getSelectionModel().setSelectionMode(1);
		tabla.setBackground(Color.BLACK);
		tabla.setForeground(new Color(0, 255, 0));
		tabla.setGridColor(new Color(0, 128, 0)); // PARA LA REJILLA EN UN TONO DE VERDE
		tabla.setFont(new Font("Monospaced", Font.PLAIN, 16)); //LA FUENTE
		add(scrollPane);
		
		JTableHeader header = tabla.getTableHeader(); //CREAMOS TAMBIÉN UN HEADER
		header.setBackground(Color.BLACK);
		header.setForeground(new Color(0, 255, 0));
		header.setFont(new Font("Monospaced", Font.BOLD, 12));


		// CREACION JBUTON Y POSICIONAMIENTO

		botonEliminar = new JButton("Eliminar");
		botonEliminar.setBounds(540, 170, 100, 30); //TAMAÑO Y POSICIÓN
		botonEliminar.setBackground(Color.GREEN); // FONDO EN VERDE
		botonEliminar.setForeground(new Color(0, 0, 0)); // TEXTO DEL BOTÓN EN NEGRO CON RGB
		botonEliminar.setFont(new Font("Monospaced", Font.BOLD, 12)); // FUENTE MONOESPACIADA
		botonEliminar.addActionListener(new ActionListener() { //VAMOS A PONERLE EFECTOS DE SONIDO A LOS BOTONES
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./src/click.wav"); 
            }});
		add(botonEliminar);

		botonModificar = new JButton("Editar");
		botonModificar.setBounds(540, 230, 100, 30);
		botonModificar.setBackground(Color.GREEN); 
		botonModificar.setForeground(new Color(0, 0, 0)); 
		botonModificar.setFont(new Font("Monospaced", Font.BOLD, 12));
		botonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./src/click.wav"); 
            }});
		add(botonModificar);

		botonAgregar = new JButton("Agregar Contacto");
		botonAgregar.setBounds(200, 470, 200, 50);
		botonAgregar.setBackground(Color.GREEN); 
		botonAgregar.setForeground(new Color(0, 0, 0)); 
		botonAgregar.setFont(new Font("Monospaced", Font.BOLD, 12));
		botonAgregar.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./src/click.wav"); 
            }});
		add(botonAgregar);

	}

	public void iniciarManejador(ManejadorEventos manejador) {
		// AGREGAMOS LISTENER A LOS OBJETOS QUE QUEREMOS ESCUCHAR
		
		botonAgregar.addActionListener(manejador);
		botonModificar.addActionListener(manejador);
		botonEliminar.addActionListener(manejador);
		tabla.getSelectionModel().addListSelectionListener(manejador);
		

	}

	// AGREGA FILA AL MODELO
	
	// SE OBTIENE FILA SELECCIONADA
	
	public int obtenerFila() {
		int fila = tabla.getSelectedRow();
		return fila;
	}

	// SE OBTIENE STRING NOMBRE

	public String obtenerObjetoNombre() {
		String nombreObjeto = modelo.getValueAt(tabla.getSelectedRow(), 0).toString();
		return nombreObjeto;
	}

	// SE OBTIENE STRING TELEFONO

	public String obtenerObjetoTelefono() {
		String nombreObjeto = modelo.getValueAt(tabla.getSelectedRow(), 1).toString();
		return nombreObjeto;
	}

	// ELIMINA FILA SELECCIONADA

	public void eliminarFila() {
		if (tabla.getSelectedRow() != -1) {
			modelo.removeRow(tabla.getSelectedRow());
		}
	}

	// SE VACIA MODELO Y CARGA UTILIZANDO LA LISTACONTACTOS

	public void actualizarTabla() {
		modelo.setRowCount(0);
		List<ContactoPOJO> listacontactos = dao.getAllContacts();
		for (ContactoPOJO contacto : listacontactos) {
			modelo.addRow(new Object[] { contacto.getNombre(), contacto.getTelefono() });
		}
	}

	// VISUALIZACION MENSAJE ERROR
	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	
	//METODO PARA EL SONIDO, HAY QUE METERLO EN TODAS LAS VENTANAS
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
