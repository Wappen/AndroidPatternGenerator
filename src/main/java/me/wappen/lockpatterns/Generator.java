package me.wappen.lockpatterns;

import me.wappen.lockpatterns.constraints.Constrainer;

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
     * Returns a list of all permutations with a desired length
     * @param length The length that all permutations should have
     * @return A list of all permutations
     */
    private List<Pattern> allPermutations(int length) {
        return permutationsRecursive(new LinkedList<>(), Arrays.stream(Dot.values()).toList(), length);
    }

    /**
     * Recursively generates all permutations that start with the given starting dots,
     * and end with a tail of the given length consisting of the given dots in {@code dotPalette}
     * @param start Beginning of the resulting pattern
     * @param dotPalette Palette of dots that are left to be used
     * @param length The desired length
     * @return A list of all pattern permutations with the given length of the given dots
     */
    private List<Pattern> permutationsRecursive(List<Dot> start, List<Dot> dotPalette, int length) {
        if (length == 0)
            return Collections.singletonList(new Pattern(start.toArray(new Dot[0])));

        List<Pattern> patterns = new LinkedList<>();

        for (int i = 0; i < dotPalette.size(); i++) {
            Dot dot = dotPalette.get(i);
            List<Dot> newStart = new LinkedList<>(start);
            newStart.add(dot);
            List<Dot> newDots = dotPalette.stream().filter(d -> d != dot).toList();

            patterns.addAll(permutationsRecursive(newStart, newDots, length - 1));
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
