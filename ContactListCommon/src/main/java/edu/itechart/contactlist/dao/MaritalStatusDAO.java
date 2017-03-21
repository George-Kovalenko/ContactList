package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.MaritalStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaritalStatusDAO extends AbstractDAO {
    private static final String SELECT_ALL = "SELECT * FROM marital_statuses";

    public MaritalStatusDAO() throws DAOException {
        super();
    }

    public ArrayList<MaritalStatus> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<MaritalStatus> maritalStatuses = new ArrayList<>();
            while (resultSet.next()) {
                MaritalStatus maritalStatus = new MaritalStatus(resultSet.getLong("id"),
                        resultSet.getString("name"));
                maritalStatuses.add(maritalStatus);
            }
            return maritalStatuses;
        } catch (SQLException e) {
            throw new DAOException("Error in MaritalStatusDAO.findAll()", e);
        }
    }
}
