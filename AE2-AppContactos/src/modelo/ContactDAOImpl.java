package modelo;

import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {
	private static List<ContactoPOJO> contactos = new ArrayList<>();

	private ContactoPOJO contacto1 = new ContactoPOJO("paco", "123456789");
	private ContactoPOJO contacto2 = new ContactoPOJO("Juan", "987654321");
	private ContactoPOJO contacto3 = new ContactoPOJO("Antonio", "123456789");

	
	
	public void cargarDatos() {
		contactos.add(contacto1);
		contactos.add(contacto2);
		contactos.add(contacto3);
	}

	// LISTADO CONTACTOS

	public List<ContactoPOJO> getAllContacts() {
		if (contactos.size() >= 1) {
			for (ContactoPOJO contacto : contactos) {
				System.out.println("Los nombres de los contactos son: " + contacto.getNombre());
			}
		} else
			System.out.println("No hay contactos para mostrar");
		return contactos;
	}

	// AGREGAR CONTACTOS

	public void addContact(ContactoPOJO contact) {
		contactos.add(contact);
	}

	public void addContact(String nombre, String telefono) {
		ContactoPOJO nuevoContacto = new ContactoPOJO(nombre, telefono);
		addContact(nuevoContacto);
	}

	// ACTUALIZAR CONTACTOS CON NOMBRE ANTERIOR

	public void updateContact(String nombreViejo, ContactoPOJO nuevoContacto) {
		for (int i = 0; i < contactos.size(); i++) {
			if (contactos.get(i).getNombre().equals(nombreViejo)) {
				contactos.set(i, nuevoContacto);
				return;
			}
		}
		System.out.println("Contact not found: " + nombreViejo);
	}

	// ELIMINA CONTACTO BUSCANDO NOMBRE

	public void removeContact(String contactoNombre) {
		contactos.removeIf(contact -> contact.getNombre().equals(contactoNombre));
		System.out.println(contactoNombre + " HA SIDO ELIMINADO **");
		System.out.println("--------------------------------------");
		getAllContacts();
	}

}
