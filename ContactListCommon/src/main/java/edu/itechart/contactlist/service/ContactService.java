package edu.itechart.contactlist.service;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;
import edu.itechart.contactlist.dao.ContactDAO;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.entity.Contact;
import org.apache.commons.fileupload.FileItem;

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

    public static void insert(Contact contact, ArrayList<FileItem> fileItems) throws  ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            connection.setAutoCommit(false);
            try {
                contactDAO.insert(contact);
                long id = contactDAO.getLastId();
                AttachmentWriterService.writeAttachments(id, fileItems);
                connection.commit();
            } catch (DAOException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }

    public static void update(long id, Contact contact, ArrayList<FileItem> fileItems) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            connection.setAutoCommit(false);
            try {
                contactDAO.update(id, contact);
                AttachmentWriterService.writeAttachments(id, fileItems);
                connection.commit();
            } catch (ServiceException | DAOException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }

    public static void delete(long id) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            ContactDAO contactDAO = new ContactDAO(connection);
            connection.setAutoCommit(false);
            try {
                contactDAO.delete(id);
                connection.commit();
            } catch (DAOException e) {
                connection.rollback();
                throw new ServiceException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }
}
