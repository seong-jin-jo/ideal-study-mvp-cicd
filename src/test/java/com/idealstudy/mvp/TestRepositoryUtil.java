package com.idealstudy.mvp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class TestRepositoryUtil {

    @PersistenceContext
    private EntityManager entityManager;

    private final DataSource dataSource;

    @Autowired
    public TestRepositoryUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long getAutoIncrement(String tableName) {
        fetchTable(tableName);
        String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_NAME = '"+tableName+"'";
        return (Long) entityManager.createNativeQuery(sql).getSingleResult();
    }

    private void fetchTable(String tableName) {
        String sql = "ANALYZE TABLE " + tableName;
        executeDDL(sql);
    }

    private void executeDDL(String query) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
