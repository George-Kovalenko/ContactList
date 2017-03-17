package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entityfactory.ContactFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO extends AbstractDAO {
    private static final String SELECT_ALL = "SELECT * FROM contacts";

    public ContactDAO() throws DAOException {
        super();
    }

    public List<Contact> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                Contact contact = new ContactFactory().createInstanceFromResultSet(resultSet);
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            throw new DAOException("Error in findAll()", e);
        }
    }
}
