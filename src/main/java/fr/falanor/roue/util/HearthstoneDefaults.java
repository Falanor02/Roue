package fr.falanor.roue.util;

import fr.falanor.roue.model.WheelEntry;

import java.util.ArrayList;
import java.util.List;

public final class HearthstoneDefaults {

    private HearthstoneDefaults() {
    }

    public static List<WheelEntry> create() {

        List<WheelEntry> list = new ArrayList<>();

        list.add(new WheelEntry("Guerrier", 5, "#C69B6D"));
        list.add(new WheelEntry("Chasseur", 5, "#AAD372"));
        list.add(new WheelEntry("Mage", 5, "#3FC7EB"));
        list.add(new WheelEntry("Paladin", 5, "#F48CBA"));
        list.add(new WheelEntry("Prêtre", 5, "#FFFFFF"));
        list.add(new WheelEntry("Voleur", 5, "#FFF468"));
        list.add(new WheelEntry("Chaman", 5, "#0070DD"));
        list.add(new WheelEntry("Démoniste", 5, "#8788EE"));
        list.add(new WheelEntry("Druide", 5, "#FF7C0A"));
        list.add(new WheelEntry("Chasseur de démons", 5, "#A330C9"));
        list.add(new WheelEntry("Chevalier de la mort", 5, "#C41E3A"));
        list.add(new WheelEntry("Battlegrounds", 5, "#E67E22"));

        return list;
    }

}