package rbo13.nem12.application.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rbo13.nem12.application.parser.Parser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MeterReadingServiceTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks
    private MeterReadingService meterReadingService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
    }

    @Test
    public void testInsertStatement() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement("INSERT INTO meter_readings (nmi, timestamp, consumption) VALUES (?, ?, ?);")).thenAnswer(invocation -> {
            String sql = invocation.getArgument(0);
            assertEquals("INSERT INTO meter_readings (nmi, timestamp, consumption) VALUES (?, ?, ?);", sql);
            return preparedStatement;
        });

        meterReadingService.process("./meter_readings.csv");

        verify(connection).prepareStatement("INSERT INTO meter_readings (nmi, timestamp, consumption) VALUES (?, ?, ?);");
    }
}
