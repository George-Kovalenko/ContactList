package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Address;
import edu.itechart.contactlist.entityfactory.AddressFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO extends AbstractDAO {
    private static final String SELECT_BY_ID = "SELECT * FROM addresses WHERE contacts_id=?";

    public AddressDAO() throws DAOException {
        super();
    }

    public Address findById(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
             CountryDAO countryDAO = new CountryDAO()) {
            preparedStatement.setLong(1,  id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Address address = new Address();
            if (resultSet.next()) {
                address = new AddressFactory().createInstanceFromResultSet(resultSet);
                address.setCountry(countryDAO.findById(resultSet.getLong("country_id")));
            }
            return address;
        } catch (SQLException e) {
            throw new DAOException("Error in Address.findById()", e);
        }
    }
}
