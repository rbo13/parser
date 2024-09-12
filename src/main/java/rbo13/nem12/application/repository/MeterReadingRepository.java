package rbo13.nem12.application.repository;

import rbo13.nem12.application.model.MeterReading;

import java.util.Collection;

public interface MeterReadingRepository {

    void batchInsert(Collection<MeterReading> collection);
}
