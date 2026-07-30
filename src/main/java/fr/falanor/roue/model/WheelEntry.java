package fr.falanor.roue.model;

public class WheelEntry {

    private String name;

    private int weight;

    private String color;

    public WheelEntry() {
    }

    public WheelEntry(String name, int weight, String color) {

        this.name = name;
        this.weight = Math.max(weight,2);
        this.color = color;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;

    }

    public int getWeight() {

        return weight;

    }

    public void setWeight(int weight) {

        this.weight = Math.max(weight,2);

    }

    public String getColor() {

        return color;

    }

    public void setColor(String color) {

        this.color = color;

    }

    public void win() {

        weight++;

    }

    public void lose() {

        weight = Math.max(2,weight-1);

    }

    public void draw() {

    }

    @Override
    public String toString() {

        return name+" ("+weight+")";

    }

}