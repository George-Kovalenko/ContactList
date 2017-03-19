package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entityfactory.ContactFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactDAO extends AbstractDAO {
    private static final String SELECT_ALL = "SELECT * FROM contacts";

    public ContactDAO() throws DAOException {
        super();
    }

    public ArrayList<Contact> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Contact> contacts = new ArrayList<>();
            ContactFactory contactFactory = new ContactFactory();
            AddressDAO addressDAO = new AddressDAO();
            while (resultSet.next()) {
                Contact contact = contactFactory.createInstanceFromResultSet(resultSet);
                contact.setAddress(addressDAO.findById(contact.getId()));
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            throw new DAOException("Error in findAll()", e);
        }
    }
}
