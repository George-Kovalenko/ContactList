package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.MaritalStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaritalStatusDAO extends AbstractDAO<MaritalStatus> {
    private static final String SELECT_ALL = "SELECT * FROM marital_statuses";
    private static final String SELECT_BY_ID = "SELECT * FROM marital_statuses WHERE id_marital_status=?";

    public MaritalStatusDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<MaritalStatus> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<MaritalStatus> maritalStatuses = new ArrayList<>();
            while (resultSet.next()) {
                MaritalStatus maritalStatus = new MaritalStatus(resultSet.getLong("id_marital_status"),
                        resultSet.getString("name"));
                maritalStatuses.add(maritalStatus);
            }
            return maritalStatuses;
        } catch (SQLException e) {
            throw new DAOException("Can't get all marital statuses", e);
        }
    }

    @Override
    public MaritalStatus findById(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            MaritalStatus maritalStatus = new MaritalStatus();
            if (resultSet.next()) {
                maritalStatus.setId(resultSet.getLong("id_marital_status"));
                maritalStatus.setName(resultSet.getString("name"));
            }
            return maritalStatus;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't get marital status by id = %d", id), e);
        }
    }

    @Override
    public void insert(MaritalStatus entity) throws DAOException {
    }

    @Override
    public void delete(long id) throws DAOException {
    }

    @Override
    public void update(long id, MaritalStatus entity) throws DAOException {
    }
}
