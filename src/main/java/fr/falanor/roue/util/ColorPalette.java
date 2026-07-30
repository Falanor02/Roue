package fr.falanor.roue.util;

import java.util.List;
import java.util.Random;

public class ColorPalette {

    private static final List<String> COLORS = List.of(

            "#C69B6D",
            "#ABD473",
            "#69CCF0",
            "#F58CBA",
            "#FFFFFF",
            "#FFF569",
            "#0070DE",
            "#9482C9",
            "#FF7D0A",
            "#A330C9",
            "#C41E3A",
            "#E6CC80",

            "#FF6B6B",
            "#4ECDC4",
            "#45B7D1",
            "#96CEB4",
            "#FFE66D",
            "#D65DB1",
            "#6BCB77",
            "#4D96FF",
            "#F08A5D",
            "#845EC2"

    );

    private static final Random RANDOM = new Random();

    private ColorPalette() {
    }

    public static String randomColor() {

        return COLORS.get(
                RANDOM.nextInt(COLORS.size())
        );

    }

}