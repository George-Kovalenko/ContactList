package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO extends AbstractDAO {
    private static final String FIND_BY_ID = "SELECT * FROM countries WHERE id=?";

    public CountryDAO() {
    }

    public CountryDAO(Connection connection) {
        super(connection);
    }

    public Country findById(long id) throws DAOException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Country(resultSet.getLong("id"), resultSet.getString("name"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error in findById()", e);
        }
    }
}
