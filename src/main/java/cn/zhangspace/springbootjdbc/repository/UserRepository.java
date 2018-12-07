package cn.zhangspace.springbootjdbc.repository;


import cn.zhangspace.springbootjdbc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;


@Repository
public class UserRepository {


    private DataSource dataSource;

    private final DataSource masterDataSource;

    private final DataSource salveDataSource;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource,
                          @Qualifier("masterDataSource") DataSource masterDataSource,
                          @Qualifier("salveDataSource") DataSource salveDataSource,
                          JdbcTemplate jdbcTemplate
                          ){
        this.dataSource = dataSource;
        this.masterDataSource = masterDataSource;
        this.salveDataSource = salveDataSource;
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean jdbcSave(User user){
        boolean success = false;
        System.out.printf("[Thread : %s ] starting saving user....\n",Thread.currentThread().getName());
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false); //关闭自动提交
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name) VALUES (?);");
            preparedStatement.setString(1, user.getName());
            success = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.commit(); //提交
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }


    @Transactional
    public boolean save(User user){
        boolean success = false;
        success = jdbcTemplate.execute("INSERT INTO users(name) VALUES (?);",
                new PreparedStatementCallback<Boolean>() {

                    @Nullable
                    @Override
                    public Boolean doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
                        preparedStatement.setString(1, user.getName());
                        return preparedStatement.executeUpdate() > 0;
                    }
                });
        return success;
    }

    public Collection<User> findAll(){
        return Collections.emptyList();
    }

}
