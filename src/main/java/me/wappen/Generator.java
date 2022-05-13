package me.wappen;

import me.wappen.constraints.Constrainer;

import java.util.*;

/**
 * The generator that is used to generate all possible pattern combinations
 * with given constraints using a {@code Constrainer}
 */
public class Generator {
    Constrainer constrainer;

    /**
     * Creates a generator that generates patterns of the given length
     * @param length The length that all patterns should have
     * @return A new Generator instance
     */
    public List<Pattern> generatePatterns(int length) {
        return generatePatterns(length, length);
    }

    /**
     * Creates a generator that generates patterns with a min length of
     * {@code minLength} and a max length of {@code maxLength}
     * @param minLength Min length of generated patterns
     * @param maxLength Max length of generated patterns
     * @return A new Generator instance
     */
    public List<Pattern> generatePatterns(int minLength, int maxLength) {
        List<Pattern> allowedPatterns = new LinkedList<>();

        for (int i = minLength; i <= maxLength; i++) {
            List<Pattern> patterns = allPermutations(i);

            for (Pattern pattern : patterns) {
                if (constrainer.checkPattern(pattern)) {
                    allowedPatterns.add(pattern);
                }
            }
        }

        return allowedPatterns;
    }

    /**
     * Returns a list of all permutations with a given length
     * @param length The length all permutations should have
     * @return A list of all pattern permutations
     */
    private List<Pattern> allPermutations(int length) {
        List<Pattern> patterns = new LinkedList<>();
        List<Dot> dotList = Arrays.stream(Dot.values()).toList();

        for (int i = 0; i < Dot.values().length; i++) {
            Dot dot = Dot.fromInt(i);
            Dot[] line = {dot};

            Pattern newStart = new Pattern(line);
            List<Dot> newDots = dotList.stream().filter(d -> d != dot).toList();

            patterns.addAll(allPerms(newStart, length - 1, newDots));
        }

        return patterns;
    }

    /**
     * Recursively generates all permutations with a given length, a start for the pattern and all dots that are left to use
     * @param start Beginning of the resulting pattern
     * @param length The desired length
     * @param dots Dots that are left to be used
     * @return A list of all pattern permutations with the given length of the given dots
     */
    private List<Pattern> allPerms(Pattern start, int length, List<Dot> dots) {
        List<Pattern> patterns = new LinkedList<>();

        if (length == 0) {
            patterns.add(start);
            return patterns;
        }

        for (int i = 0; i < dots.size(); i++) {
            Dot dot = dots.get(i);
            Dot[] line = new Dot[start.line().length + 1];
            System.arraycopy(start.line(), 0, line, 0, start.line().length);
            line[line.length - 1] = dot;

            Pattern newStart = new Pattern(line);
            List<Dot> newDots = dots.stream().filter(d -> d != dot).toList();

            patterns.addAll(allPerms(newStart, length - 1, newDots));
        }

        return patterns;
    }

    /**
     * Sets the constrainer that is used to generate patterns
     * @param constrainer The constrainer that will be used
     */
    public void setConstrainer(Constrainer constrainer) {
        this.constrainer = constrainer;
    }

    /**
     * Returns the used constrainer
     * @return The used constrainer
     */
    public Constrainer getConstrainer() {
        return constrainer;
    }
}
