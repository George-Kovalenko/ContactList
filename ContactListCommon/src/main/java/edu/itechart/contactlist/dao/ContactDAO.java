package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Address;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.dto.DateSearchType;
import edu.itechart.contactlist.dto.SearchParameters;
import edu.itechart.contactlist.entity.factory.AddressFactory;
import edu.itechart.contactlist.entity.factory.ContactFactory;
import edu.itechart.contactlist.util.StatementUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;

public class ContactDAO extends AbstractDAO<Contact> {
    private static final String SELECT_ALL = "SELECT * FROM contacts";
    private static final String SELECT_CERTAIN_COUNT_BY_OFFSET = "SELECT * FROM contacts LIMIT ? OFFSET ?";
    private static final String SELECT_BY_ID = "SELECT * FROM contacts WHERE id=?";
    private static final String SELECT_BY_BIRTH_DATE ="SELECT * FROM contacts WHERE MONTH(birth_date)=MONTH(CURDATE()) " +
            "AND DAY(birth_date)=DAY(CURDATE())";
    private static final String INSERT_CONTACT = "INSERT INTO contacts (first_name, last_name, middle_name, " +
            "birth_date, nationality, gender, marital_status, website, email, job) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CONTACT = "UPDATE contacts SET first_name=?, last_name=?, middle_name=?, " +
            "birth_date=?, nationality=?, gender=?, marital_status=?, website=?, email=?, job=? WHERE id=?";
    private static final String DELETE_CONTACT = "DELETE FROM contacts WHERE id=?";
    private static final String GET_LAST_ID = "SELECT last_insert_id() AS last_id FROM contacts";
    private static final String PARAM_QUERY = "SELECT * FROM contacts " +
            "LEFT JOIN addresses ON addresses.contact_id = contacts.id WHERE TRUE";
    private static final String GET_CONTACT_COUNT = "SELECT COUNT(*) AS count FROM contacts";

    public ContactDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<Contact> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Contact> contacts = new ArrayList<>();
            ContactFactory contactFactory = new ContactFactory();
            while (resultSet.next()) {
                Contact contact = contactFactory.createInstanceFromResultSet(resultSet);
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            throw new DAOException("Can't get all contacts", e);
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
            throw new DAOException(String.format("Can't find contact by id = %d", id), e);
        }
    }

    @Override
    public void update(long id, Contact contact) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT)) {
            fillPreparedStatement(preparedStatement, contact);
            preparedStatement.setLong(11, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't update %s by id = %d", contact, id), e);
        }
    }

    @Override
    public void insert(Contact contact) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CONTACT)) {
            fillPreparedStatement(preparedStatement, contact);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException(String.format("Can't insert %s", contact), e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't delete contact by id = %d", id), e);
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
            throw new DAOException("Can't get last id", e);
        }
    }

    public ArrayList<Contact> findAllByParameters(SearchParameters searchParameters) throws DAOException {
        String query = buildParamQuery(searchParameters, PARAM_QUERY);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Contact> contacts = new ArrayList<>();
            ContactFactory contactFactory = new ContactFactory();
            AddressFactory addressFactory = new AddressFactory();
            while (resultSet.next()) {
                Contact contact = contactFactory.createInstanceFromResultSet(resultSet);
                Address address = addressFactory.createInstanceFromResultSet(resultSet);
                contact.setAddress(address);
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't find contact by %s", searchParameters), e);
        }
    }

    public int getContactCount() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_CONTACT_COUNT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
            return count;
        } catch (SQLException e) {
            throw new DAOException("Can't get contact count from table 'contacts'" ,e);
        }
    }

    public ArrayList<Contact> findCertainCountsByOffset(long offset, long limit) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CERTAIN_COUNT_BY_OFFSET)) {
            preparedStatement.setLong(1, limit);
            preparedStatement.setLong(2, offset);
            ArrayList<Contact> contacts = new ArrayList<>();
            ContactFactory contactFactory = new ContactFactory();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contact contact = contactFactory.createInstanceFromResultSet(resultSet);
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't get contacts by offset = %d limit = %d", offset, limit), e);
        }
    }

    public ArrayList<Contact> findByBirthDate() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_BIRTH_DATE)) {
            ArrayList<Contact> contacts = new ArrayList<>();
            ContactFactory contactFactory = new ContactFactory();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Contact contact = contactFactory.createInstanceFromResultSet(resultSet);
                contacts.add(contact);
            }
            return contacts;
        } catch (SQLException e) {
            throw new DAOException("Can't get contacts by birth date", e);
        }
    }

    private void fillPreparedStatement(PreparedStatement statement, Contact contact) throws SQLException {
        StatementUtils.setStringValue(statement, 1, contact.getFirstName());
        StatementUtils.setStringValue(statement, 2, contact.getLastName());
        StatementUtils.setStringValue(statement, 3, contact.getMiddleName());
        StatementUtils.setDateValue(statement, 4, contact.getBirthDate());
        StatementUtils.setStringValue(statement, 5, contact.getNationality());
        StatementUtils.setStringValue(statement, 6, contact.getGender());
        StatementUtils.setIntValue(statement, 7, contact.getMaritalStatus());
        StatementUtils.setStringValue(statement, 8, contact.getWebsite());
        StatementUtils.setStringValue(statement, 9, contact.getEmail());
        StatementUtils.setStringValue(statement, 10, contact.getJob());
    }

    private String buildParamQuery(SearchParameters parameters, String query) {
        query += createSearchStringPart("first_name", parameters.getFirstName());
        query += createSearchStringPart("last_name", parameters.getLastName());
        query += createSearchStringPart("middle_name", parameters.getMiddleName());
        query += createSearchStringPart("nationality", parameters.getNationality());
        query += createSearchGenderPart(parameters.getGender());
        query += createSearchIntPart("marital_status", parameters.getMaritalStatus());
        query += createSearchDatePart(parameters.getBirthDate(), parameters.getDateSearchType());
        query += createSearchStringPart("country", parameters.getCountry());
        query += createSearchStringPart("city", parameters.getCity());
        query += createSearchStringPart("street", parameters.getStreet());
        query += createSearchStringPart("house_number", parameters.getHouseNumber());
        query += createSearchStringPart("flat_number", parameters.getFlatNumber());
        query += createSearchStringPart("postcode", parameters.getPostcode());
        return query;
    }

    private String createSearchStringPart(String paramName, String paramValue) {
        return StringUtils.isNotEmpty(paramValue) ? String.format(" AND %s LIKE '%s'", paramName, paramValue)
                : StringUtils.EMPTY;
    }

    private String createSearchGenderPart(String gender) {
        return StringUtils.equalsAny(gender, "f", "m") ? createSearchStringPart("gender", gender)
                : StringUtils.EMPTY;
    }

    private String createSearchIntPart(String paramName, Integer number) {
        if (number != null && number != 0) {
            return createSearchStringPart(paramName, number.toString());
        }
        return StringUtils.EMPTY;
    }

    private String createSearchDatePart(Date date, DateSearchType dateSearchType) {
        if (date != null) {
            String queryPart = " AND birth_date ";
            switch (dateSearchType) {
                case OLDER:
                    queryPart += "< ";
                    break;
                case YOUNGER:
                    queryPart += "> ";
                    break;
                case EQUALS:
                    queryPart += "= ";
                    break;
            }
            queryPart += String.format("'%s'", date.toString());
            return queryPart;
        }
        return StringUtils.EMPTY;
    }
}
