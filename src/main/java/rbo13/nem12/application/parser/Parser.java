package rbo13.nem12.application.parser;

import rbo13.nem12.application.model.MeterReading;

import java.io.File;
import java.util.Collection;

public interface Parser {

    Collection<MeterReading> parse(File file) throws Exception;
}
