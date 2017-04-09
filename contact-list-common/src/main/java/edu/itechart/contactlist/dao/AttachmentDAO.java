package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Attachment;
import edu.itechart.contactlist.entity.factory.AttachmentFactory;
import edu.itechart.contactlist.util.StatementUtils;

import java.sql.*;
import java.util.ArrayList;

public class AttachmentDAO extends AbstractDAO<Attachment> {
    private static final String SELECT_BY_CONTACT_ID = "SELECT * FROM attachments WHERE contact_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM attachments WHERE id=?";
    private static final String UPDATE_ATTACHMENT = "UPDATE attachments SET file_name=?, comment=? WHERE id=?";
    private static final String INSERT_ATTACHMENT = "INSERT INTO attachments (file_name, upload_date, comment, "
            + "contact_id) VALUES(?, ?, ?, ?)";
    private static final String DELETE_ATTACHMENT = "DELETE FROM attachments WHERE id=?";
    private static final String DELETE_BY_CONTACT_ID = "DELETE FROM attachments WHERE contact_id=?";

    public AttachmentDAO(Connection connection) {
        super(connection);
    }

    public ArrayList<Attachment> findByContactId(long contactId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CONTACT_ID)) {
            preparedStatement.setLong(1, contactId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Attachment> attachments = new ArrayList<>();
            AttachmentFactory attachmentFactory = new AttachmentFactory();
            while (resultSet.next()) {
                Attachment attachment = attachmentFactory.createInstanceFromResultSet(resultSet);
                attachments.add(attachment);
            }
            return attachments;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't get attachment by contactId = %d", contactId), e);
        }
    }

    @Override
    public void insert(Attachment attachment) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ATTACHMENT,
                Statement.RETURN_GENERATED_KEYS)) {
            StatementUtils.setStringValue(preparedStatement, 1, attachment.getFileName());
            StatementUtils.setDateValue(preparedStatement, 2, attachment.getUploadDate());
            StatementUtils.setStringValue(preparedStatement, 3, attachment.getComment());
            StatementUtils.setLongValue(preparedStatement, 4, attachment.getContactId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                attachment.setId(resultSet.getLong(1));
            } else {
                throw new DAOException(String.format("Can't insert %s, no ID obtained", attachment));
            }
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't insert %s", attachment), e);
        }
    }

    @Override
    public void update(long id, Attachment attachment) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ATTACHMENT)) {
            StatementUtils.setStringValue(preparedStatement, 1, attachment.getFileName());
            StatementUtils.setStringValue(preparedStatement, 2, attachment.getComment());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't update %s by id = %d", attachment, id), e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ATTACHMENT)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't delete attachment by id = %d", id), e);
        }
    }

    @Override
    public ArrayList<Attachment> findAll() throws DAOException {
        return null;
    }

    @Override
    public Attachment findById(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Attachment attachment = new Attachment();
            if (resultSet.next()) {
                attachment = new AttachmentFactory().createInstanceFromResultSet(resultSet);
            }
            return attachment;
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't get attachment by id = %d", id), e);
        }
    }

    public void deleteByContactId(long contactId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_CONTACT_ID)) {
            preparedStatement.setLong(1, contactId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(String.format("Can't delete attachment by contactId = %d", contactId), e);
        }
    }
}
