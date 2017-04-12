package edu.itechart.contactlist.service;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.dao.MaritalStatusDAO;
import edu.itechart.contactlist.entity.Contact;
import edu.itechart.contactlist.entity.MaritalStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaritalStatusService {
    public static ArrayList<MaritalStatus> findAll() throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            return new MaritalStatusDAO(connection).findAll();
        } catch (SQLException | ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    public static MaritalStatus findById(long id) throws  ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            return new MaritalStatusDAO(connection).findById(id);
        } catch (SQLException | ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
