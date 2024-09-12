package rbo13.nem12.application.parser.nem12;

import org.junit.jupiter.api.Test;
import rbo13.nem12.application.model.MeterReading;
import rbo13.nem12.application.parser.Parser;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Nem12ParserTest {

    @Test
    void testItYieldsCorrectNumberOfReadings() throws Exception {
        Parser parser = new Nem12Parser();
        List<MeterReading> meterReadings = parser.parse(new File("meter_readings.csv")).stream().toList();

        assertEquals(8 * 48, meterReadings.size());
    }

    @Test
    void testItYieldsCorrectValues() throws Exception {
        Parser parser = new Nem12Parser();
        List<MeterReading> meterReadings = parser.parse(new File("meter_readings.csv")).stream().toList();

        // Get the readings from the first `300` record
        List<MeterReading> readings = meterReadings.subList(0, 48);

        assertEquals(
                new MeterReading(
                        "NEM1201009",
                        LocalDateTime.of(2005, 3, 1, 0, 30),
                        BigDecimal.ZERO
                ),
                readings.getFirst()
        );
        assertEquals(
                new MeterReading(
                        "NEM1201009",
                        LocalDateTime.of(2005, 3, 1, 6, 30),
                        new BigDecimal("0.461")
                ),
                readings.get(12)
        );
        assertEquals(
                new MeterReading(
                        "NEM1201009",
                        LocalDateTime.of(2005, 3, 1, 15, 0),
                        new BigDecimal("0.555")
                ),
                readings.get(29)
        );
        assertEquals(
                new MeterReading(
                        "NEM1201009",
                        LocalDateTime.of(2005, 3, 2, 0, 0),
                        new BigDecimal("0.231")
                ),
                readings.get(47)
        );
    }
}
