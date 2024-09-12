package rbo13.nem12.application.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rbo13.nem12.application.model.MeterReading;
import rbo13.nem12.application.repository.jdbc.MeterReadingJDBCRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class MeterReadingServiceTest {

    @Mock
    private MeterReadingJDBCRepository repository;

    @InjectMocks
    private MeterReadingService meterReadingService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
    }

    @Test
    public void testInsertStatement() throws Exception {
        MeterReading reading = new MeterReading("NMI123", LocalDateTime.now(), new BigDecimal("123.45"));
        List<MeterReading> readings = List.of(reading);
        meterReadingService.saveAll(readings);
        verify(repository, times(1)).batchInsert(readings);
    }
}
