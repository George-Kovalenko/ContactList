package edu.itechart.contactlist.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class StatementUtils {
    public static void setDateValue(PreparedStatement statement, int index, Date value) throws SQLException {
        if (value != null) {
            statement.setDate(index, value);
        } else {
            statement.setNull(index, Types.DATE);
        }
    }

    public static void setStringValue(PreparedStatement statement, int index, String value) throws SQLException {
        if (StringUtils.isNotEmpty(value)) {
            statement.setString(index, value);
        } else {
            statement.setNull(index, Types.VARCHAR);
        }
    }

    public static void setLongValue(PreparedStatement statement, int index, Long value) throws SQLException {
        if (value != null) {
            statement.setLong(index, value);
        } else {
            statement.setNull(index, Types.BIGINT);
        }
    }

    public static void setIntValue(PreparedStatement statement, int index, Integer value) throws SQLException {
        if (value != null) {
            statement.setInt(index, value);
        } else {
            statement.setNull(index, Types.INTEGER);
        }
    }
}
