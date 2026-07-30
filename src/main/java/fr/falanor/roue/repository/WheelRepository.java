package fr.falanor.roue.repository;

import fr.falanor.roue.model.WheelEntry;
import fr.falanor.roue.service.JsonStorageService;
import fr.falanor.roue.util.HearthstoneDefaults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WheelRepository {

    private final JsonStorageService storage =
            new JsonStorageService();

    private List<WheelEntry> entries =
            new ArrayList<>();

    public WheelRepository() {

        load();

    }

    public void load() {

        try {

            if (storage.exists()) {

                entries = storage.load();

            } else {

                entries = HearthstoneDefaults.create();

                storage.save(entries);

            }

        } catch (IOException e) {

            entries = HearthstoneDefaults.create();

        }

    }

    public void save() {

        try {

            storage.save(entries);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public List<WheelEntry> getEntries() {

        return entries;

    }

}