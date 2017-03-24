package edu.itechart.contactlist.service;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.dao.PhoneTypeDAO;
import edu.itechart.contactlist.entity.PhoneType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneTypeService {
    public static ArrayList<PhoneType> findAll() throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            PhoneTypeDAO phoneTypeDAO = new PhoneTypeDAO(connection);
            return phoneTypeDAO.findAll();
        } catch (SQLException | ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
