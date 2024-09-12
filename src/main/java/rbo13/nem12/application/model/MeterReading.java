package rbo13.nem12.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "meter_readings")
public class MeterReading {

    @Id
    private UUID id;
    private String nmi;
    private LocalDateTime timestamp;
    private BigDecimal consumption;

    public MeterReading() {
    }

    public MeterReading(String nmi, LocalDateTime timestamp, BigDecimal consumption) {
        this.id = null;
        this.nmi = nmi;
        this.timestamp = timestamp;
        this.consumption = consumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNmi() {
        return nmi;
    }

    public void setNmi(String nmi) {
        this.nmi = nmi;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nmi, timestamp, consumption);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterReading that = (MeterReading) o;
        return nmi.equals(that.nmi) && timestamp.equals(that.timestamp) && consumption.equals(that.consumption);
    }

    @Override
    public String toString() {
        return "MeterReading {" +
                "nmi='" + nmi + '\'' +
                ", timestamp=" + timestamp +
                ", consumption=" + consumption +
                '}';
    }
}
