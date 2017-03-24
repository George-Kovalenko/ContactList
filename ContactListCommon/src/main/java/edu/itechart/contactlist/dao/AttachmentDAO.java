package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Attachment;
import edu.itechart.contactlist.entityfactory.AttachmentFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttachmentDAO extends AbstractDAO {
    private static final String SELECT_BY_CONTACT_ID = "SELECT * FROM attachments WHERE contact_id=?";

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
            throw new DAOException("Error in AttachmentDAO.findByContactId", e);
        }
    }
}
