package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.PhoneType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneTypeDAO extends AbstractDAO {
    private static final String SELECT_ALL = "SELECT * FROM phone_type";

    public PhoneTypeDAO() throws DAOException {
        super();
    }

    public ArrayList<PhoneType> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ArrayList<PhoneType> phoneTypes = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PhoneType phoneType = new PhoneType(resultSet.getLong("id"),
                        resultSet.getString("name"));
                phoneTypes.add(phoneType);
            }
            return phoneTypes;
        } catch (SQLException e) {
            throw new DAOException("Error in PhoneTypeDAO.findAll()", e);
        }
    }
}
