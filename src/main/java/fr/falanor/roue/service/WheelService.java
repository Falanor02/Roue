package fr.falanor.roue.service;

import fr.falanor.roue.model.GameResult;
import fr.falanor.roue.model.WheelEntry;
import fr.falanor.roue.repository.WheelRepository;
import fr.falanor.roue.util.ColorPalette;

import java.util.List;
import java.util.Random;

public class WheelService {

    private final WheelRepository repository =
            new WheelRepository();

    private final Random random =
            new Random();

    public List<WheelEntry> getEntries() {

        return repository.getEntries();

    }

    public WheelEntry spin() {

        int total =
                repository.getEntries()
                        .stream()
                        .mapToInt(WheelEntry::getWeight)
                        .sum();

        int value =
                random.nextInt(total);

        int current = 0;

        for (WheelEntry entry : repository.getEntries()) {

            current += entry.getWeight();

            if (value < current) {

                return entry;

            }

        }

        return repository.getEntries().getLast();

    }

    public void applyResult(
            WheelEntry entry,
            GameResult result) {

        switch (result) {

            case WIN -> entry.win();

            case LOSS -> entry.lose();

            case DRAW -> entry.draw();

        }

        repository.save();

    }

    public void addEntry(String name) {

        repository.getEntries().add(

                new WheelEntry(
                        name,
                        5,
                        ColorPalette.randomColor()
                )

        );

        repository.save();

    }

    public void removeEntry(WheelEntry entry) {

        repository.getEntries().remove(entry);

        repository.save();

    }

    public void resetDefaults() {

        repository.getEntries().clear();

        repository.getEntries().addAll(
                fr.falanor.roue.util.HearthstoneDefaults.create()
        );

        repository.save();

    }

    public void save() {
        repository.save();
    }

}