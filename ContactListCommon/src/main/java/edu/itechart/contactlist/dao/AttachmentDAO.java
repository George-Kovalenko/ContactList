package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Attachment;
import edu.itechart.contactlist.entityfactory.AttachmentFactory;

import java.sql.*;
import java.util.ArrayList;

public class AttachmentDAO extends AbstractDAO<Attachment> {
    private static final String SELECT_BY_CONTACT_ID = "SELECT * FROM attachments WHERE contact_id=?";
    private static final String UPDATE_ATTACHMENT = "UPDATE attachments SET file_name=?, comment=? WHERE id=?";
    private static final String INSERT_ATTACHMENT = "INSERT INTO attachments (file_name, upload_date, comment, " +
            "contact_id) VALUES(?, ?, ?, ?)";
    private static final String DELETE_ATTACHMENT = "DELETE FROM attachments WHERE id=?";
    private static final String DELETE_BY_CONTACT_ID = "DELETE FROM attachments WHERE contact_id=?";

    public AttachmentDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<Attachment> findByContactId(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CONTACT_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Attachment> attachments = new ArrayList<>();
            AttachmentFactory attachmentFactory = new AttachmentFactory();
            while (resultSet.next()) {
                Attachment attachment = attachmentFactory.createInstanceFromResultSet(resultSet);
                attachments.add(attachment);
            }
            return attachments;
        } catch (SQLException e) {
            throw new DAOException("Error in AttachmentDAO.findByContactId()", e);
        }
    }

    @Override
    public void insert(Attachment attachment) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ATTACHMENT,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, attachment.getFileName());
            preparedStatement.setDate(2, attachment.getUploadDate());
            preparedStatement.setString(3, attachment.getComment());
            preparedStatement.setLong(4, attachment.getContactID());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                attachment.setId(resultSet.getLong(1));
            } else {
                throw new DAOException("Inserting attachment failed, no ID obtained");
            }
        } catch (SQLException e) {
            throw new DAOException("Error in AttachmentDAO.insert()", e);
        }
    }

    @Override
    public void update(long id, Attachment attachment) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ATTACHMENT)) {
            preparedStatement.setString(1, attachment.getFileName());
            preparedStatement.setString(2, attachment.getComment());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in AttachmentDAO.update()", e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ATTACHMENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in AttachmentDAO.delete()", e);
        }
    }

    @Override
    public ArrayList<Attachment> findAll() throws DAOException {
        return null;
    }

    @Override
    public Attachment findById(long id) throws DAOException {
        return null;
    }

    public void deleteByContactId(long contactId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_CONTACT_ID)) {
            preparedStatement.setLong(1, contactId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in AttachmentDAO.delete()", e);
        }
    }
}
