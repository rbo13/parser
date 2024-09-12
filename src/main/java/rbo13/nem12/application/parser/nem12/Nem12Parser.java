package rbo13.nem12.application.parser.nem12;

import rbo13.nem12.application.model.MeterReading;
import rbo13.nem12.application.parser.Parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Nem12Parser implements Parser {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final int MINUTES_PER_DAY = 60 * 24;

    private static class RecordCodes {
        public static final int NMI_DETAILS_RECORD = 200;
        public static final int INTERVAL_DATA_RECORD = 300;
        public static final int END_OF_DATA_RECORD = 900;
    }

    @Override
    public Collection<MeterReading> parse(File file) throws Exception {
        try (InputStream input = new FileInputStream(file)) {
            return parseInputStream(input);
        }
    }

    private Collection<MeterReading> parseInputStream(InputStream input) {
        Scanner scanner = new Scanner(new BufferedInputStream(input)).useDelimiter("[,\n]");
        List<MeterReading> meterReadings = new ArrayList<>();
        String currentNmi = null;
        Long currentInterval = null;

        while (scanner.hasNext()) {
            int recordIndicator = getNextIntOrDefault(scanner);

            switch (recordIndicator) {
                case RecordCodes.NMI_DETAILS_RECORD -> {
                    currentNmi = parseNmiDetails(scanner);
                    currentInterval = getNextLongOrDefault(scanner);
                }
                case RecordCodes.INTERVAL_DATA_RECORD -> {
                    if (currentNmi != null) {
                        meterReadings.addAll(parseIntervalData(scanner, currentNmi, currentInterval));
                    }
                }
                case RecordCodes.END_OF_DATA_RECORD -> {
                    scanner.close();
                    return meterReadings;
                }
                default -> skipToNextLine(scanner);
            }
        }

        scanner.close();
        return meterReadings;
    }

    private String parseNmiDetails(Scanner scanner) {
        String nmi = scanner.next();
        skipElements(scanner, 6); // Skip elements 3 to 8
        return nmi;
    }

    private Collection<MeterReading> parseIntervalData(Scanner scanner, String nmi, Long interval) {
        List<MeterReading> readings = new ArrayList<>();
        LocalDate currentDate = LocalDate.parse(scanner.next(), DATE_FORMATTER);
        int numReadings = MINUTES_PER_DAY / interval.intValue();

        for (int i = 1; i <= numReadings; i++) {
            BigDecimal consumption = new BigDecimal(scanner.next());
            LocalDateTime timestamp = currentDate.atStartOfDay().plusMinutes(interval * i);
            readings.add(new MeterReading(nmi, timestamp, consumption));
        }

        return readings;
    }

    private int getNextIntOrDefault(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (NoSuchElementException e) {
            return -1;
        }
    }

    private long getNextLongOrDefault(Scanner scanner) {
        try {
            return scanner.nextLong();
        } catch (NoSuchElementException e) {
            return -1L;
        }
    }

    private void skipElements(Scanner scanner, int count) {
        for (int i = 0; i < count; i++) {
            scanner.next();
        }
    }

    private void skipToNextLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }
}
