package rbo13.nem12.application.parser.nem12;

import org.junit.jupiter.api.Test;
import rbo13.nem12.application.model.MeterReading;
import rbo13.nem12.application.parser.Parser;

import java.io.File;
import java.util.List;

public class Nem12ParserTest {

    @Test
    void testItYieldsCorrectNumberOfReadings() throws Exception {
        Nem12Parser parser = new Parser();
        List<MeterReading> meterReadings = parser.parse(new File("meter_readings.csv"));
    }
}
