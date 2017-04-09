package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Address;
import edu.itechart.contactlist.entity.factory.AddressFactory;
import edu.itechart.contactlist.util.StatementUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressDAO extends AbstractDAO<Address> {
    private static final String SELECT_BY_ID = "SELECT * FROM addresses WHERE contact_id=?";
    private static final String UPDATE_ADDRESS = "UPDATE addresses SET country=?, city=?, street=?, house_number=?,"
            + "flat_number=?, postcode=? WHERE contact_id=?";
    private static final String INSERT_ADDRESS = "INSERT INTO addresses (country, city, street, house_number, "
            + "flat_number, postcode, contact_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ADDRESS = "DELETE FROM addresses WHERE contact_id=?";

    public AddressDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Address findById(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Address address = new Address();
            if (resultSet.next()) {
                address = new AddressFactory().createInstanceFromResultSet(resultSet);
            }
            return address;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't get address by id = %d", id), e);
        }
    }

    @Override
    public void update(long id, Address address) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS)) {
            fillPreparedStatement(preparedStatement, address);
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't update %s by id = %d", address, id), e);
        }
    }

    @Override
    public ArrayList<Address> findAll() throws DAOException {
        return null;
    }

    @Override
    public void insert(Address address) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADDRESS)) {
            fillPreparedStatement(preparedStatement, address);
            preparedStatement.setLong(7, address.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't insert %s", address), e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't delete address by id = %d", id), e);
        }
    }

    private void fillPreparedStatement(PreparedStatement statement, Address address) throws SQLException {
        StatementUtils.setStringValue(statement, 1, address.getCountry());
        StatementUtils.setStringValue(statement, 2, address.getCity());
        StatementUtils.setStringValue(statement, 3, address.getStreet());
        StatementUtils.setStringValue(statement, 4, address.getHouseNumber());
        StatementUtils.setStringValue(statement, 5, address.getFlatNumber());
        StatementUtils.setStringValue(statement, 6, address.getPostcode());
    }
}
