package edu.itechart.contactlist.entityfactory;

import edu.itechart.contactlist.entity.Attachment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AttachmentFactory extends AbstractEntityFactory<Attachment> {
    @Override
    public Attachment createInstanceFromResultSet(ResultSet resultSet) throws SQLException {
        return new Attachment(resultSet.getLong("id"),
                resultSet.getString("file_name"),
                resultSet.getTimestamp("upload_date"),
                resultSet.getString("comment"),
                resultSet.getLong("contact_id"));
    }
}
