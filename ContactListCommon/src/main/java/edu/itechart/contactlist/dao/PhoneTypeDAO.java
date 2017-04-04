package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.PhoneType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneTypeDAO extends AbstractDAO<PhoneType> {
    private static final String SELECT_ALL = "SELECT * FROM phone_type";

    public PhoneTypeDAO(Connection connection) {
        super(connection);
    }

    @Override
    public ArrayList<PhoneType> findAll() throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            ArrayList<PhoneType> phoneTypes = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PhoneType phoneType = new PhoneType(resultSet.getLong("id_phone_type"),
                        resultSet.getString("name"));
                phoneTypes.add(phoneType);
            }
            return phoneTypes;
        } catch (SQLException e) {
            throw new DAOException("Can't get all phone types", e);
        }
    }

    @Override
    public PhoneType findById(long id) throws DAOException {
        return null;
    }

    @Override
    public void insert(PhoneType entity) throws DAOException {
    }

    @Override
    public void delete(long id) throws DAOException {
    }

    @Override
    public void update(long id, PhoneType entity) throws DAOException {
    }
}
