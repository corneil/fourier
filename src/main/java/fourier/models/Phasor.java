package fourier.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Phasor
{
    private Coordinate terminal;
    private double frequency;
    public double getMagnitude()
    {
        return Math.sqrt(Math.pow(terminal.getX(), 2) + Math.pow(terminal.getY(), 2));
    }
    public double getPhase()
    {
        return Math.atan2(terminal.getY(), terminal.getX());
    }

    public Phasor multiply(Phasor other)
    {
        var thisX = terminal.getX();
        var thisY = terminal.getY();
        var otherX = other.terminal.getX();
        var otherY = other.terminal.getY();

        var real = thisX * otherX - thisY * otherY;
        var imaginary = thisX * otherY + thisY * otherX;
        return new Phasor(new Coordinate(real, imaginary), frequency);
    }

    public Phasor add(Phasor other)
    {
        var real = terminal.getX() + other.terminal.getX();
        var imaginary = terminal.getY() + other.terminal.getY();
        return new Phasor(new Coordinate(real, imaginary), frequency);
    }
}