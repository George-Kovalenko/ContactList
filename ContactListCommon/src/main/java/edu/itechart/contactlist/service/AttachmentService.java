package edu.itechart.contactlist.service;

import edu.itechart.contactlist.connectionpool.ConnectionPool;
import edu.itechart.contactlist.connectionpool.ConnectionPoolException;
import edu.itechart.contactlist.dao.AttachmentDAO;
import edu.itechart.contactlist.dao.DAOException;
import edu.itechart.contactlist.entity.Attachment;

import java.sql.Connection;
import java.sql.SQLException;

public class AttachmentService {
    public static Attachment findById(long id) throws ServiceException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AttachmentDAO attachmentDAO = new AttachmentDAO(connection);
            return attachmentDAO.findById(id);
        } catch (SQLException | DAOException | ConnectionPoolException e) {
            throw new ServiceException(e);
        }
    }
}
