package edu.itechart.contactlist.entityfactory;

import edu.itechart.contactlist.entity.Address;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressFactory extends AbstractEntityFactory<Address> {
    @Override
    public Address createInstanceFromResultSet(ResultSet resultSet) throws SQLException {
        return new Address(resultSet.getLong("contact_id"),
                resultSet.getString("country"),
                resultSet.getString("city"),
                resultSet.getString("street"),
                resultSet.getString("house_number"),
                resultSet.getString("flat_number"),
                resultSet.getString("postcode"));
    }
}
