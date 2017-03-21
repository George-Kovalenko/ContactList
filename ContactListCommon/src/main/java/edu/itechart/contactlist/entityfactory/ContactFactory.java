package edu.itechart.contactlist.entityfactory;

import edu.itechart.contactlist.entity.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactFactory extends AbstractEntityFactory<Contact> {
    @Override
    public Contact createInstanceFromResultSet(ResultSet resultSet) throws SQLException {
        return new Contact(resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("middle_name"),
                resultSet.getDate("birth_date"),
                resultSet.getString("nationality"),
                resultSet.getString("gender"),
                resultSet.getInt("marital_status"),
                resultSet.getString("website"),
                resultSet.getString("email"),
                resultSet.getString("job"));
    }
}
