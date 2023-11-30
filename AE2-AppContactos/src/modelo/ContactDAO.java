package modelo;

import java.util.List;

public interface ContactDAO {
	List<ContactoPOJO> getAllContacts();

	void addContact(ContactoPOJO contact);

	void updateContact(String oldName, ContactoPOJO newContact);

	void removeContact(String contactName);
}