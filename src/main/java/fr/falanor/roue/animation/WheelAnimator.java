package fr.falanor.roue.animation;

import fr.falanor.roue.model.WheelEntry;
import fr.falanor.roue.ui.WheelCanvas;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.List;

public class WheelAnimator {

    private final WheelCanvas canvas;

    public WheelAnimator(WheelCanvas canvas) {
        this.canvas = canvas;
    }

    public void spinTo(
            List<WheelEntry> entries,
            WheelEntry winner,
            Runnable onFinished) {

        double targetAngle = computeWinnerAngle(entries, winner);

        double startRotation = canvas.getRotationDegrees();

        double endRotation =
                startRotation
                        + 360 * 8
                        - targetAngle;

        Transition transition = new Transition() {

            {
                setCycleDuration(Duration.seconds(10));
                setInterpolator(Interpolator.EASE_OUT);
            }

            @Override
            protected void interpolate(double frac) {

                double angle =
                        startRotation +
                                (endRotation - startRotation) * frac;

                canvas.setRotationDegrees(angle);

            }

        };

        transition.setOnFinished(e -> {

            canvas.setRotationDegrees(endRotation % 360);

            if (onFinished != null)
                onFinished.run();

        });

        transition.play();

    }

    private double computeWinnerAngle(
            List<WheelEntry> entries,
            WheelEntry winner) {

        int total =
                entries.stream()
                        .mapToInt(WheelEntry::getWeight)
                        .sum();

        double current = 0;

        for (WheelEntry entry : entries) {

            double sector =
                    360.0
                            * entry.getWeight()
                            / total;

            if (entry == winner) {

                double margin = sector * 0.20;

                double randomInside =
                        margin +
                                Math.random() * (sector - margin * 2);

                return current
                        + randomInside
                        - 90;

            }

            current += sector;

        }

        return 0;

    }

}