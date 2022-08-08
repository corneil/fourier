package fourier.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexNumber
{
    private double real;
    private double imaginary;
    private double frequency;

    public double getMagnitude()
    {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
    }

    public static List<ComplexNumber> convertToComplex(List<Coordinate> cartesian)
    {
        var complexNumbers = new ArrayList<ComplexNumber>();
        var interval = 1;
        var omission = 0;

        for(int n = 0; n < cartesian.size() - omission; n+=interval)
        {
            var real = cartesian.get(n).getX();
            var imaginary = cartesian.get(n).getY();

            var complex = new ComplexNumber(real, imaginary, 0);
            complexNumbers.add(complex);
        }

        return complexNumbers;
    }

    public double getPhase()
    {
        return Math.atan2(imaginary, real);
    }

    public ComplexNumber multiply(ComplexNumber other)
    {
        var real = this.real * other.real - this.imaginary * other.imaginary;
        var imaginary = this.real * other.imaginary + this.imaginary * other.real;
        return new ComplexNumber(real, imaginary, frequency);
    }

    public ComplexNumber add(ComplexNumber other)
    {
        var real = this.real + other.real;
        var imaginary = this.imaginary + other.imaginary;
        return new ComplexNumber(real, imaginary, frequency);
    }

    @Override
    public String toString()
    {
        return "(Phase: " + getPhase() + " Freq: " + frequency + " Re: " + real + ", Im: " + imaginary + ")\n";
    }
}