package me.wappen.constraints;

import me.wappen.Dot;
import me.wappen.Pattern;

import java.util.function.Predicate;

public class AndroidUtil {
    public static boolean patternConstraint(Pattern p) {
        Dot prev = null;
        for (Dot dot : p.line) {
            if (prev != null) {
                if (dot.getX() == prev.getX() &&
                        dot.getY() + prev.getY() == 0 && // Check if the pattern skips a dot in y-direction
                        noneBefore(p.line, prev, d -> d.getX() == dot.getX() && d.getY() == 0)) // If so then only allow it if it was already used
                    return false;
                else if (dot.getY() == prev.getY() &&
                        dot.getX() + prev.getX() == 0 && // Check if the pattern skips a dot in y-direction
                        noneBefore(p.line, prev, d -> d.getX() == 0 && d.getY() == dot.getY())) // If so then only allow it if it was already used
                    return false;
                else if (dot.getX() + prev.getX() == 0 && dot.getY() + prev.getY() == 0 &&
                        noneBefore(p.line, prev, d -> d.equals(Dot.CENTER)))
                    return false;
            }
            prev = dot;
        }
        return true;
    }

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
