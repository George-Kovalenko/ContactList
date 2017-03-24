package edu.itechart.contactlist.service;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;
import edu.itechart.contactlist.dao.ContactDAO;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.entity.Contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactService {
    public static Contact findById(long id) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            return contactDAO.findById(id);
        } catch (SQLException | ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    public static ArrayList<Contact> findAll() throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            return contactDAO.findAll();
        } catch (SQLException | ConnectionPoolException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    public static void update(Contact contact) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            connection.setAutoCommit(false);
            try {
                contactDAO.update(contact);
                connection.commit();
            } catch (DAOException e) {
                connection.rollback();
                throw new ServiceException(e);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }
}
