package edu.itechart.contactlist.entityfactory;

import edu.itechart.contactlist.entity.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneFactory extends AbstractEntityFactory<Phone> {
    @Override
    public Phone createInstanceFromResultSet(ResultSet resultSet) throws SQLException {
        return new Phone(resultSet.getLong("id"),
                resultSet.getString("country_code"),
                resultSet.getString("operator_code"),
                resultSet.getString("number"),
                resultSet.getString("phone_type").charAt(0),
                resultSet.getString("comment"),
                resultSet.getLong("contact_id"));
    }
}
