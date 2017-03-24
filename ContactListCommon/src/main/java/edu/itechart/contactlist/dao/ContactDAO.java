package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entityfactory.ContactFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactDAO extends AbstractDAO {
    private static final String SELECT_ALL = "SELECT * FROM contacts";
    private static final String SELECT_BY_ID = "SELECT * FROM contacts WHERE id=?";
    private static final String UPDATE_CONTACT = "UPDATE contacts SET first_name=?, last_name=?, middle_name=?, " +
            "marital_status=?, nationality=? WHERE id=?";

    public ContactDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<Contact> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            AddressDAO addressDAO = new AddressDAO(connection);
            PhoneDAO phoneDAO = new PhoneDAO(connection);
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Contact> contacts = new ArrayList<>();
            ContactFactory contactFactory = new ContactFactory();
            while (resultSet.next()) {
                Contact contact = contactFactory.createInstanceFromResultSet(resultSet);
                contact.setAddress(addressDAO.findById(contact.getId()));
                contact.setPhones(phoneDAO.findByContactId(contact.getId()));
                contact.setAttachments(attachmentDAO.findByContactId(contact.getId()));
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.findAll()", e);
        }
    }

    public Contact findById(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            AddressDAO addressDAO = new AddressDAO(connection);
            PhoneDAO phoneDAO = new PhoneDAO(connection);
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Contact contact = new Contact();
            if (resultSet.next()) {
                contact = new ContactFactory().createInstanceFromResultSet(resultSet);
                contact.setAddress(addressDAO.findById(contact.getId()));
                contact.setPhones(phoneDAO.findByContactId(contact.getId()));
                contact.setAttachments(attachmentDAO.findByContactId(contact.getId()));
            }
            return contact;
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.findById()", e);
        }
    }

    public void update(Contact contact) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT)) {
            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setString(3, contact.getMiddleName());
            preparedStatement.setInt(4, contact.getMaritalStatus());
            preparedStatement.setString(5, contact.getNationality());
            preparedStatement.setLong(6, contact.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.update()", e);
        }
    }
}
