package rbo13.nem12.application.repository.spring;

import org.springframework.data.repository.CrudRepository;
import rbo13.nem12.application.model.MeterReading;

import java.util.UUID;

public interface MeterReadingSpringDataJdbc extends CrudRepository<MeterReading, UUID> {
}
