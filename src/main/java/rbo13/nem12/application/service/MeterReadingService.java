package rbo13.nem12.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rbo13.nem12.application.model.MeterReading;
import rbo13.nem12.application.parser.Parser;
import rbo13.nem12.application.parser.nem12.Nem12Parser;
import rbo13.nem12.application.repository.jdbc.MeterReadingJDBCRepository;
import java.io.File;
import java.util.List;

@Service
public class MeterReadingService {

    private final MeterReadingJDBCRepository repository;
    private final Parser parser;

    @Autowired
    public MeterReadingService(MeterReadingJDBCRepository repository) {
        this.repository = repository;
        this.parser = new Nem12Parser();
    }

    public void process(String path) throws Exception {
        File nem12File = new File(path);

        List<MeterReading> readings = parser.parse(nem12File).stream().toList();
        saveAll(readings);
    }

    public void saveAll(List<MeterReading> readings) {
        repository.batchInsert(readings);
    }
}
