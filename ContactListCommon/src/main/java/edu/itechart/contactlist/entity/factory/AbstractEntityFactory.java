package edu.itechart.contactlist.entity.factory;

import edu.itechart.contactlist.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractEntityFactory<T extends Entity> {
    public abstract T createInstanceFromResultSet(ResultSet resultSet) throws SQLException;
}
