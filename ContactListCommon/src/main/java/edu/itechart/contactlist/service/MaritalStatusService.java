package edu.itechart.contactlist.service;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.dao.MaritalStatusDAO;
import edu.itechart.contactlist.entity.MaritalStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaritalStatusService {
    public static ArrayList<MaritalStatus> findAll() throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MaritalStatusDAO maritalStatusDAO = new MaritalStatusDAO(connection);
            return maritalStatusDAO.findAll();
        } catch (SQLException | ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
