package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.Entity;
import edu.itechart.contactlist.entity.Phone;
import edu.itechart.contactlist.entityfactory.ContactFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactDAO extends AbstractDAO<Contact> {
    private static final String SELECT_ALL = "SELECT * FROM contacts";
    private static final String SELECT_BY_ID = "SELECT * FROM contacts WHERE id=?";
    private static final String INSERT_CONTACT = "INSERT INTO contacts (first_name, last_name, middle_name, " +
            "birth_date, nationality, gender, marital_status, website, email, job) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CONTACT = "UPDATE contacts SET first_name=?, last_name=?, middle_name=?, " +
            "birth_date=?, nationality=?, gender=?, marital_status=?, website=?, email=?, job=? WHERE id=?";
    private static final String DELETE_CONTACT = "DELETE FROM contacts WHERE id=?";
    private static final String GET_LAST_ID = "SELECT last_insert_id() AS last_id FROM contacts";

    public ContactDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Contact> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {;
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Contact> contacts = new ArrayList<>();
            ContactFactory contactFactory = new ContactFactory();
            while (resultSet.next()) {
                Contact contact = contactFactory.createInstanceFromResultSet(resultSet);
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.findAll()", e);
        }
    }

    @Override
    public Contact findById(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Contact contact = new Contact();
            if (resultSet.next()) {
                contact = new ContactFactory().createInstanceFromResultSet(resultSet);
            }
            return contact;
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.findById()", e);
        }
    }

    @Override
    public void update(long id, Contact contact) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT)) {
            fillPreparedStatement(preparedStatement, contact);
            preparedStatement.setLong(11, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.update()", e);
        }
    }

    @Override
    public void insert(Contact contact) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTACT)) {
            fillPreparedStatement(preparedStatement, contact);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.insert()", e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.delete()", e);
        }
    }

    public long getLastId() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LAST_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            long id = 0;
            if (resultSet.next()) {
                id = resultSet.getLong("last_id");
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException("Error in ContactDAO.getLastId()", e);
        }
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Contact contact) throws SQLException {
        preparedStatement.setString(1, contact.getFirstName());
        preparedStatement.setString(2, contact.getLastName());
        preparedStatement.setString(3, contact.getMiddleName());
        preparedStatement.setDate(4, contact.getBirthDate());
        preparedStatement.setString(5, contact.getNationality());
        preparedStatement.setString(6, contact.getGender());
        preparedStatement.setInt(7, contact.getMaritalStatus());
        preparedStatement.setString(8, contact.getWebsite());
        preparedStatement.setString(9, contact.getEmail());
        preparedStatement.setString(10, contact.getJob());
    }
}
