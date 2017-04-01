package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Phone;
import edu.itechart.contactlist.entityfactory.PhoneFactory;
import edu.itechart.contactlist.util.StatementUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneDAO extends AbstractDAO<Phone> {
    private static final String SELECT_BY_CONTACT_ID = "SELECT * FROM phones WHERE contact_id=?";
    private static final String UPDATE_PHONE = "UPDATE phones SET country_code=?, operator_code=?, number=?, " +
            "phone_type=?, comment=? WHERE id=?";
    private static final String INSERT_PHONE = "INSERT INTO phones (country_code, operator_code, number, phone_type, " +
            "comment, contact_id) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE_PHONE = "DELETE FROM phones WHERE id=?";
    private static final String DELETE_BY_CONTACT_ID = "DELETE FROM phones WHERE contact_id=?";

    public PhoneDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<Phone> findByContactId(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CONTACT_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Phone> phones = new ArrayList<>();
            PhoneFactory phoneFactory = new PhoneFactory();
            while (resultSet.next()) {
                Phone phone = phoneFactory.createInstanceFromResultSet(resultSet);
                phones.add(phone);
            }
            return phones;
        } catch (SQLException e) {
            throw new DAOException("Error in PhoneDAO.findByContactId", e);
        }
    }

    @Override
    public void insert(Phone phone) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PHONE)) {
            fillPreparedStatement(preparedStatement, phone);
            StatementUtils.setLongValue(preparedStatement, 6, phone.getContactID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in PhoneDAO.insert()", e);
        }
    }

    @Override
    public void update(long id, Phone phone) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PHONE)) {
            fillPreparedStatement(preparedStatement, phone);
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in PhoneDAO.update()", e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PHONE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in PhoneDAO.delete()", e);
        }
    }

    @Override
    public ArrayList<Phone> findAll() throws DAOException {
        return null;
    }

    @Override
    public Phone findById(long id) throws DAOException {
        return null;
    }

    public void deleteByContactId(long contactId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_CONTACT_ID)) {
            preparedStatement.setLong(1, contactId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in PhoneDAO.deleteByContactId()", e);
        }
    }

    private void fillPreparedStatement(PreparedStatement statement, Phone phone) throws SQLException {
        StatementUtils.setStringValue(statement, 1, phone.getCountryCode());
        StatementUtils.setStringValue(statement, 2, phone.getOperatorCode());
        StatementUtils.setStringValue(statement, 3, phone.getNumber());
        StatementUtils.setIntValue(statement, 4, phone.getPhoneType());
        StatementUtils.setStringValue(statement, 5, phone.getComment());
    }
}
