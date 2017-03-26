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

    @Override
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

    @Override
    public void update(long id, Contact contact) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT)) {
            fillPreparedStatement(preparedStatement, contact);
            preparedStatement.setLong(11, id);
            preparedStatement.executeUpdate();
            AddressDAO addressDAO = new AddressDAO(connection);
            addressDAO.update(id, contact.getAddress());
            PhoneDAO phoneDAO = new PhoneDAO(connection);
            updateEntityList(contact.getPhones(), phoneDAO.findByContactId(id), phoneDAO);
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            updateEntityList(contact.getAttachments(), attachmentDAO.findByContactId(id), attachmentDAO);
        } catch (DAOException | SQLException e) {
            throw new DAOException("Error in ContactDAO.update()", e);
        }
    }

    @Override
    public void insert(Contact contact) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTACT)) {
            fillPreparedStatement(preparedStatement, contact);
            preparedStatement.executeUpdate();
            long id = getLastId();
            AddressDAO addressDAO = new AddressDAO(connection);
            contact.getAddress().setId(id);
            addressDAO.insert(contact.getAddress());
            contact.getPhones().forEach(item -> item.setContactID(id));
            PhoneDAO phoneDAO = new PhoneDAO(connection);
            insertEntityList(contact.getPhones(), phoneDAO);
            contact.getAttachments().forEach(item -> item.setContactID(id));
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            insertEntityList(contact.getAttachments(), attachmentDAO);
        } catch (DAOException | SQLException e) {
            throw new DAOException("Error in ContactDAO.insert()", e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT)) {
            AddressDAO addressDAO = new AddressDAO(connection);
            addressDAO.delete(id);
            PhoneDAO phoneDAO = new PhoneDAO(connection);
            phoneDAO.deleteByContactId(id);
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            attachmentDAO.deleteByContactId(id);
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


    private <T extends Entity> void insertEntityList(ArrayList<T> list, AbstractDAO<T> dao)
            throws DAOException {
        for (T entity : list) {
            dao.insert(entity);
        }
    }


    private <T extends Entity> void updateEntityList(ArrayList<T> list, ArrayList<T> oldList, AbstractDAO<T> dao)
            throws DAOException {
        ArrayList<T> entityForInsert = new ArrayList<>();
        ArrayList<Long> entityIndexes = new ArrayList<>();
        for (T entity : list) {
            if (entity.getId() == 0) {
                entityForInsert.add(entity);
            } else {
                entityIndexes.add(entity.getId());
            }
        }
        list.removeAll(entityForInsert);
        ArrayList<T> entityForUpdate = new ArrayList<>(list);
        entityForUpdate.removeAll(oldList);
        ArrayList<Long> indexesForDelete = new ArrayList<>();
        for (T entity : oldList) {
            indexesForDelete.add(entity.getId());
        }
        indexesForDelete.removeIf(entityIndexes::contains);
        for (T entity : entityForInsert) {
            dao.insert(entity);
        }
        for (T entity : entityForUpdate) {
            dao.update(entity.getId(), entity);
        }
        for (Long id : indexesForDelete) {
            dao.delete(id);
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
