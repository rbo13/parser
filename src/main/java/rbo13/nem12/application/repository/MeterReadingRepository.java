package rbo13.nem12.application.repository;

import org.springframework.data.repository.CrudRepository;
import rbo13.nem12.application.model.MeterReading;

import java.util.UUID;

public interface MeterReadingRepository extends CrudRepository<MeterReading, UUID> {
}
