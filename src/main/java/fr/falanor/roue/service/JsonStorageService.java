package fr.falanor.roue.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.falanor.roue.model.WheelEntry;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonStorageService {

    private static final File FILE =
            new File("data/wheel.json");

    private final ObjectMapper mapper =
            new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT);

    public List<WheelEntry> load() throws IOException {

        return mapper.readValue(
                FILE,
                new TypeReference<>() {
                });

    }

    public void save(List<WheelEntry> entries) throws IOException {

        FILE.getParentFile().mkdirs();

        mapper.writeValue(FILE, entries);

    }

    public boolean exists() {

        return FILE.exists();

    }

}