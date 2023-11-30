package controlador;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.zip.CheckedOutputStream;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modelo.ContactDAO;
import modelo.ContactDAOImpl;
import modelo.ContactoPOJO;

import java.awt.event.ActionEvent;

import vista.VentanaModificarContacto;
import vista.VentanaNuevoContacto;
import vista.VentanaPrincipal;

public class ManejadorEventos implements ActionListener, ListSelectionListener {

	private VentanaPrincipal ventanaPrincipal;
	private VentanaNuevoContacto ventanaContacto;
	private VentanaModificarContacto ventanaModificar;
	private ContactDAOImpl contactodao;

	public ManejadorEventos(VentanaPrincipal ventanaPrincipal) {
		this.ventanaPrincipal = ventanaPrincipal;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// LOGICA QUE CONTROLA LOS EVENTOS

		// NUEVA VENTANA NUEVO CONTACTO (AGREGAR CONTACTO)
		// 1 - INSTANCIAMOS NUEVA VENTANA
		// 2 - UTILIZAMOS METODO EN VENTANA GENERADA PARA INICIAR EVENTOS
		// 3-VISUALIZAMOS
		if (e.getSource() == ventanaPrincipal.getBoton_agregar()) {
			ventanaContacto = new VentanaNuevoContacto();
			System.out.println("Se muestra ventana NUEVO ");
			ventanaContacto.iniciarManejador(this);
			ventanaContacto.setVisible(true);
			if (ventanaContacto != null && e.getSource() == ventanaContacto.getBoton_cancelar()) {
				ventanaContacto.dispose();
			}

			// NUEVA VENTANA MODIFICAR CONTACTO (MODIFICAR)
			// 1- VALIDA LOS CAMPOS VACIOS
			// 2- SE VALIDA SI EL TELEFONO ES UN NUMERO
			// 2- RECOGE EL TEXTO EN EL CONTENEDOR, AGREGA CONTACTO NUEVO Y ACTUALIZA VISTA
		}
		if (ventanaContacto != null && e.getSource() == ventanaContacto.getBoton_enviar()) {
			ventanaContacto.getCajaTextoNombre().requestFocus();
			if (ventanaContacto.getCajaTextoNombre().getText().isEmpty()
					|| ventanaContacto.getCajaTextoTelefono().getText().isEmpty()) {
				ventanaContacto.mostrarMensajeError("Los campos No pueden estar vacíos.");
			}
			String textorecibido = ventanaContacto.getCajaTextoNombre().getText();
			String telefonorecibido = ventanaContacto.getCajaTextoTelefono().getText();
			System.out.println(textorecibido + "  " + telefonorecibido);
			if (ventanaContacto.validarTelefono(telefonorecibido)
					&& !ventanaContacto.getCajaTextoNombre().getText().isEmpty()
					&& !ventanaContacto.getCajaTextoTelefono().getText().isEmpty()) {
				System.out.println("Enviando datos...");
				contactodao = new ContactDAOImpl();
				contactodao.addContact(textorecibido, telefonorecibido);
				ventanaPrincipal.actualizarTabla();
				ventanaContacto.dispose();
			}
		}

		// MODIFICAR CONTACTO
		// 1- SE EVALUA SI SE SELECCIONÓ UNA FILA
		// 2- OBTIENE LA FILA, EL NOMBRE DEL OBJETO Y TELEFONO
		// 3- ENVIA MENSAJE DE EDICION
		if (e.getSource() == ventanaPrincipal.getBoton_modificar() && ventanaPrincipal.obtenerFila() >= 0) {
			ventanaModificar = new VentanaModificarContacto();
			System.out.println("Se muestra ventana MODIFICAR");
			ventanaModificar.iniciarManejador(this);
			ventanaPrincipal.obtenerFila();
			ventanaPrincipal.obtenerObjetoNombre();
			ventanaModificar.getCajaTextoNombre().setText(ventanaPrincipal.obtenerObjetoNombre());
			ventanaModificar.getCajaTextoTelefono().setText(ventanaPrincipal.obtenerObjetoTelefono());
			ventanaModificar.setVisible(true);
			ventanaModificar.mostrarMensajeInformacion("EDITE LOS CAMPOS A CONTINUACION: ");
		} else if (e.getSource() == ventanaPrincipal.getBoton_modificar() && ventanaPrincipal.obtenerFila() <= -1) {
			ventanaPrincipal.mostrarMensajeError("** SE DEBE SELECCIONAR UNA FILA PARA EDITAR ** ");
		}

		// 1- VALIDA SI SE INTRODUCE UN NUMERO VALIDO
		// 2- AL PRESIONAR ENVIAR SE GUARDARA EL TEXTO ANTERIOR PARA BUSCAR EL CONTACTO
		// 3- GUARDAMOS NUEVO TEXTO PARA MODIFICAR
		// 4- ACTUALIZAMOS EL MODELO Y LA VISTA CORRESPONDIENTE
		if (ventanaModificar != null && e.getSource() == ventanaModificar.getBotonEnviar()) {
			System.out.println("** MODIFICANDO contacto **");
			String textoViejo = ventanaPrincipal.obtenerObjetoNombre();
			String textorecibido = ventanaModificar.getCajaTextoNombre().getText();
			String telefonorecibido = ventanaModificar.getCajaTextoTelefono().getText();
			System.out.println(textorecibido + "  " + telefonorecibido);
			if (ventanaModificar.validarTelefono(telefonorecibido)) {
				System.out.println("Enviando datos...");
				contactodao = new ContactDAOImpl();
				contactodao.updateContact(textoViejo, new ContactoPOJO(textorecibido, telefonorecibido));
				ventanaPrincipal.actualizarTabla();
				ventanaModificar.dispose();
			}
		}

		// BOTON CANCELAR CIERRA VENTANA
		if (ventanaModificar != null && e.getSource() == ventanaModificar.getBoton_cancelar()) {
			ventanaModificar.dispose();
		}

		// BOTON ELIMINAR
		// 1- EVALUA FILA SELECCIONADA
		// 2- OBTIENE EL NOMBRE A ELIMINAR
		// 3- BORRA CONTACTO
		// 4- ACTUALIZA VISTA
		if (e.getSource() == ventanaPrincipal.getBoton_eliminar() && ventanaPrincipal.obtenerFila() != -1) {
			contactodao = new ContactDAOImpl();
			ventanaPrincipal.obtenerFila();
			ventanaPrincipal.obtenerObjetoNombre();
			contactodao.removeContact(ventanaPrincipal.obtenerObjetoNombre());
			ventanaPrincipal.eliminarFila();
		} else if (e.getSource() == ventanaPrincipal.getBoton_eliminar() && ventanaPrincipal.obtenerFila() == -1) {
			ventanaPrincipal.mostrarMensajeError("** SE DEBE SELECCIONAR UNA FILA PARA ELIMINAR ** ");
		}

		// BOTON CANCELAR CIERRA VENTANA
		if (ventanaContacto != null && e.getSource() == ventanaContacto.getBoton_cancelar()) {
			ventanaContacto.dispose();
		}
	}

	// LISTENER CORRESPONDIENTE A LA LISTA DE LA TABLA
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
	}
}
