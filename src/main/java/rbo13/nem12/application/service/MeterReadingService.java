package rbo13.nem12.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rbo13.nem12.application.model.MeterReading;
import rbo13.nem12.application.parser.Parser;
import rbo13.nem12.application.parser.nem12.Nem12Parser;

import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.List;

@Service
public class MeterReadingService {

    private final DataSource dataSource;
    private final Parser parser;
    private static final String INSERT_STATEMENT = "INSERT INTO meter_readings (nmi, timestamp, consumption) VALUES (?, ?, ?);";

    @Autowired
    public MeterReadingService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.parser = new Nem12Parser();
    }

    public void process(String path) throws Exception {
        File nem12File = new File(path);

        List<MeterReading> readings = parser.parse(nem12File).stream().toList();
        saveAll(readings);
    }

    private void saveAll(List<MeterReading> readings) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_STATEMENT);
            readings.forEach(reading -> {
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
