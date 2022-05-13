package me.wappen.constraints;

import me.wappen.Dot;
import me.wappen.Pattern;

import java.util.function.Predicate;

/**
 * A specific constrainer that implements the default android pattern lock behavior
 */
public class AndroidPatternConstrainer extends Constrainer {
    public AndroidPatternConstrainer() {
        addConstraint(AndroidPatternConstrainer::isValidAndroidPattern);
    }

    /**
     * Tests if a {@code pattern} matches Android's pattern constraints
     * @param pattern The pattern to test
     * @return {@code true} if the pattern is valid
     */
    private static boolean isValidAndroidPattern(Pattern pattern) {
        if (pattern.line().length < 4) // Patterns must connect more than 4 dots
            return false;

        Dot prev = null;
        for (Dot dot : pattern.line()) {
            if (prev != null) {
                if (dot.getX() == prev.getX() &&
                        dot.getY() + prev.getY() == 0 && // Check if the pattern skips a dot in y-direction
                        noneBefore(pattern.line(), prev, d -> d.getX() == dot.getX() && d.getY() == 0)) // If so then only allow it if it was already used
                    return false;
                else if (dot.getY() == prev.getY() &&
                        dot.getX() + prev.getX() == 0 && // Check if the pattern skips a dot in y-direction
                        noneBefore(pattern.line(), prev, d -> d.getX() == 0 && d.getY() == dot.getY())) // If so then only allow it if it was already used
                    return false;
                else if (dot.getX() + prev.getX() == 0 && dot.getY() + prev.getY() == 0 &&
                        noneBefore(pattern.line(), prev, d -> d.equals(Dot.CENTER)))
                    return false;
            }
            prev = dot;
        }
        return true;
    }

    /**
     * Checks if any dot matches the given {@code condition} before the end-dot is reached
     * @param dots Line of dots
     * @param end End dot that stops further checks when reached
     * @param condition condition that is tested against
     * @return {@code true} if none of the {@code dots} before the {@code end} dot matched the {@code condition}
     */
    private static boolean noneBefore(Dot[] dots, Dot end, Predicate<Dot> condition) {
        for (Dot dot : dots) {
            if (condition.test(dot))
                return false;
            else if (dot.equals(end))
                break;
        }
        return true;
    }
}
