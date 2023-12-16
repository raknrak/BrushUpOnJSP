package org.zerock.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zerock.jdbcex.domain.TodoVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;


public class ConnectionTests {
    // junit 테스트를 했기 때문에 에러가 나면 DB 문제임
    @Test
    public void testConnection() throws Exception { // 예외처리
        //throw exception 이나 try-catch는 언제 사용하는가?
        //runtime error :
        Class.forName("org.mariadb.jdbc.Driver"); // 외부에서 들어오는거라 예외처리를 만들어야함.
        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/webdb",
                "webuser",
                "webuser"
        );

        Assertions.assertNotNull(connection);

        connection.close();

    }
    @Test
    public void testHikariCP() throws Exception{
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("webuser");
        config.setPassword("webuser");
        config.addDataSourceProperty("cachePrepStmts","true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit","2048");

        HikariDataSource ds = new HikariDataSource(config);
        Connection connection = ds.getConnection();
        System.out.println(connection);

        connection.close();
    }

}
