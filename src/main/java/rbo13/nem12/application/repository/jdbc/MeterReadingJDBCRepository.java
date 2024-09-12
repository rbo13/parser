package rbo13.nem12.application.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rbo13.nem12.application.model.MeterReading;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

@Repository
public class MeterReadingJDBCRepository implements MeterReadingJDBC {

    private final DataSource dataSource;
    private static final String INSERT_STATEMENT = "INSERT INTO meter_readings (nmi, timestamp, consumption) VALUES (?, ?, ?);";

    @Autowired
    public MeterReadingJDBCRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void batchInsert(Collection<MeterReading> collection) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_STATEMENT);
            collection.forEach(reading -> {
                System.out.println(preparedStatement.toString());
                try {
                    preparedStatement.setString(1, reading.getNmi());
                    preparedStatement.setTimestamp(2, Timestamp.valueOf(reading.getTimestamp()));
                    preparedStatement.setBigDecimal(3, reading.getConsumption());
                    preparedStatement.addBatch();
                } catch (SQLException e) {
                    System.out.println("Prepared Statement Error: " + e.getMessage());
                }
            });

            preparedStatement.executeBatch();
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
    }
}
