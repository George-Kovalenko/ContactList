package edu.itechart.contactlist.dao;

import edu.itechart.contactlist.entity.Phone;
import edu.itechart.contactlist.entityfactory.PhoneFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneDAO extends AbstractDAO {
    private static final String SELECT_BY_CONTACT_ID = "SELECT * FROM phones WHERE contact_id=?";

    public PhoneDAO() throws DAOException {
        super();
    }

    public ArrayList<Phone> findByContactId(long id) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CONTACT_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Phone> phones = new ArrayList<>();
            PhoneFactory phoneFactory = new PhoneFactory();
            while (resultSet.next()) {
                Phone phone = phoneFactory.createInstanceFromResultSet(resultSet);
                phones.add(phone);
            }
            return phones;
        } catch (SQLException e) {
            throw new DAOException("Error in Phone.findByContactId", e);
        }
    }
}
