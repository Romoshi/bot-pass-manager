package edu.romoshi.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBUtilsTest {
    @Test
    public void shouldGetJdbcConnection() throws SQLException {
        try(Connection connection = DBUtils.getNewConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }
}