package fr.falanor.roue.ui;

import fr.falanor.roue.model.WheelEntry;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.VPos;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class WheelCanvas extends Canvas {

    /**
     * Marge entre la roue et le bord du Canvas.
     */
    private static final double PADDING = 30;

    /**
     * Entrées actuellement affichées.
     */
    private final List<WheelEntry> entries = new ArrayList<>();

    /**
     * Rotation actuelle de la roue.
     */
    private double rotationDegrees = 0;

    /**
     * Constructeur.
     */
    public WheelCanvas(double width, double height) {

        super(width, height);

        widthProperty().addListener((o, oldValue, newValue) -> redraw());
        heightProperty().addListener((o, oldValue, newValue) -> redraw());

    }

    /**
     * Remplace complètement la liste des entrées.
     */
    public void setEntries(List<WheelEntry> list) {

        entries.clear();
        entries.addAll(list);

        redraw();

    }

    /**
     * Retourne les entrées affichées.
     */
    public List<WheelEntry> getEntries() {

        return entries;

    }

    /**
     * Rotation actuelle.
     */
    public double getRotationDegrees() {

        return rotationDegrees;

    }

    /**
     * Modifie la rotation.
     */
    public void setRotationDegrees(double rotationDegrees) {

        this.rotationDegrees = rotationDegrees;

        redraw();

    }

    /**
     * Rayon de la roue.
     */
    private double radius() {

        return Math.min(getWidth(), getHeight()) / 2 - PADDING;

    }

    /**
     * Centre X.
     */
    private double centerX() {

        return getWidth() / 2;

    }

    /**
     * Centre Y.
     */
    private double centerY() {

        return getHeight() / 2;

    }

    /**
     * Somme des poids.
     */
    private int totalWeight() {

        return entries.stream()
                .mapToInt(WheelEntry::getWeight)
                .sum();

    }

    /**
     * Redessine complètement la roue.
     */
    public void redraw() {

        GraphicsContext gc = getGraphicsContext2D();

        gc.clearRect(0, 0, getWidth(), getHeight());

        drawBackground(gc);

        if (entries.isEmpty()) {
            return;
        }

        gc.save();

        gc.translate(centerX(), centerY());
        gc.rotate(rotationDegrees);

        drawSlices(gc);

        drawLabels(gc);

        gc.restore();

        drawHub(gc);

        drawPointer(gc);
    }

        /**
         * Fond.
         */
        private void drawBackground(GraphicsContext gc) {

            gc.setFill(Color.web("#202225"));

            gc.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

        }

        /**
         * Dessine uniquement les secteurs.
         * Les textes seront dessinés dans la partie suivante.
         */
        private void drawSlices(GraphicsContext gc) {

            double startAngle = 0;

            double diameter = radius() * 2;

            for (WheelEntry entry : entries) {

                double angle =
                        360.0
                                * entry.getWeight()
                                / totalWeight();

                Color base = Color.web(entry.getColor());

                RadialGradient gradient =
                        new RadialGradient(
                                0,
                                0,
                                0.35,
                                0.35,
                                1,
                                true,
                                CycleMethod.NO_CYCLE,
                                new Stop(0, base.brighter()),
                                new Stop(1, base.darker())
                        );

                gc.setFill(gradient);

                gc.fillArc(
                        -radius(),
                        -radius(),
                        diameter,
                        diameter,
                        startAngle,
                        angle,
                        javafx.scene.shape.ArcType.ROUND
                );

                gc.setStroke(Color.web("#111111"));
                gc.setLineWidth(2);

                gc.strokeArc(
                        -radius(),
                        -radius(),
                        diameter,
                        diameter,
                        startAngle,
                        angle,
                        javafx.scene.shape.ArcType.ROUND
                );

                startAngle += angle;

            }

        }

    private void drawLabels(GraphicsContext gc) {

        double currentAngle = 0;

        for (WheelEntry entry : entries) {

            double angle =
                    360.0
                            * entry.getWeight()
                            / totalWeight();

            double middleAngle =
                    currentAngle + angle / 2;

            double textRadius =
                    radius() * 0.65;

            double radians =
                    Math.toRadians(middleAngle);

            double x =
                    Math.cos(radians) * textRadius;

            double y =
                    -Math.sin(radians) * textRadius;

            String label =
                    fitLabel(
                            entry.getName(),
                            angle
                    );

            double fontSize =
                    computeFontSize(angle);

            gc.setTextAlign(
                    TextAlignment.CENTER
            );

            gc.setTextBaseline(
                    VPos.CENTER
            );

            gc.setFont(
                    Font.font(
                            "Segoe UI",
                            FontWeight.BOLD,
                            fontSize
                    )
            );

            gc.setFill(
                    Color.rgb(0,0,0,0.45)
            );

            gc.fillText(
                    label,
                    x + 2,
                    y + 2
            );

            gc.setFill(Color.WHITE);

            gc.fillText(
                    label,
                    x,
                    y
            );

            currentAngle += angle;

        }

    }
    private double computeFontSize(double sectorAngle) {

        if (sectorAngle >= 40) {
            return 18;
        }

        if (sectorAngle >= 25) {
            return 16;
        }

        if (sectorAngle >= 15) {
            return 14;
        }

        return 12;

    }

    private String fitLabel(
            String text,
            double sectorAngle
    ) {

        int maxLength;

        if (sectorAngle >= 40) {

            maxLength = 22;

        } else if (sectorAngle >= 25) {

            maxLength = 16;

        } else if (sectorAngle >= 15) {

            maxLength = 12;

        } else {

            maxLength = 8;

        }

        if (text.length() <= maxLength) {

            return text;

        }

        return text.substring(
                0,
                Math.max(3,maxLength-3)
        ) + "...";

    }

    private void drawHub(GraphicsContext gc) {

        double r = radius() * 0.09;

        RadialGradient gradient =
                new RadialGradient(
                        0,
                        0,
                        centerX() - r * 0.3,
                        centerY() - r * 0.3,
                        r,
                        false,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#F0F0F0")),
                        new Stop(0.4, Color.web("#C8C8C8")),
                        new Stop(1, Color.web("#666666"))
                );

        gc.setFill(gradient);

        gc.fillOval(
                centerX() - r,
                centerY() - r,
                r * 2,
                r * 2
        );

        gc.setStroke(Color.web("#2B2B2B"));
        gc.setLineWidth(2);

        gc.strokeOval(
                centerX() - r,
                centerY() - r,
                r * 2,
                r * 2
        );

    }
    private void drawPointer(GraphicsContext gc) {

        double cx = centerX();

        double top = centerY() - radius() - 12;

        gc.setFill(Color.web("#D32F2F"));

        gc.fillPolygon(
                new double[]{
                        cx,
                        cx - 14,
                        cx + 14
                },
                new double[]{
                        top,
                        top - 28,
                        top - 28
                },
                3
        );

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);

        gc.strokePolygon(
                new double[]{
                        cx,
                        cx - 14,
                        cx + 14
                },
                new double[]{
                        top,
                        top - 28,
                        top - 28
                },
                3
        );

    }

    }