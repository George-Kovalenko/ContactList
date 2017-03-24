package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Address;
import edu.itechart.contactlist.entityfactory.AddressFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO extends AbstractDAO {
    private static final String SELECT_BY_ID = "SELECT * FROM addresses WHERE contacts_id=?";
    private static final String UPDATE_ADDRESS = "UPDATE addresses SET country=?, city=?, street=?, house_number=?," +
            "flat_number=?, postcode=? WHERE contacts_id=?";

    public AddressDAO(Connection connection) {
        super(connection);
    }

    public Address findById(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1,  id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Address address = new Address();
            if (resultSet.next()) {
                address = new AddressFactory().createInstanceFromResultSet(resultSet);
            }
            return address;
        } catch (SQLException e) {
            throw new DAOException("Error in AddressDAO.findById()", e);
        }
    }

    public void update(long id, Address address) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS)) {
            fillPreparedStatement(preparedStatement, address);
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in AddressDAO.update()", e);
        }
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, Address address) throws SQLException {
        preparedStatement.setString(1, address.getCountry());
        preparedStatement.setString(2, address.getCity());
        preparedStatement.setString(3, address.getStreet());
        preparedStatement.setInt(4, address.getHouseNumber());
        preparedStatement.setInt(5, address.getFlatNumber());
        preparedStatement.setString(6, address.getPostcode());
    }
}
