import java.util.Arrays;

public class AndroidUtil {
    public static boolean patternConstraint(Pattern p) {
        Dot prev = null;
        for (Dot dot : p.line) {
            if (prev != null) {
                if (dot.getX() == prev.getX()) {
                    if (dot.getY() + prev.getY() == 0 && // Check if the pattern skips a dot in y-direction
                            Arrays.stream(p.line).noneMatch(d -> d.getX() == dot.getX() && d.getY() == 0)) // If so then only allow it if it was already used
                        return false;
                }
                else if (dot.getY() == prev.getY()) {
                    if (dot.getX() + prev.getX() == 0 && // Check if the pattern skips a dot in y-direction
                            Arrays.stream(p.line).noneMatch(d -> d.getX() == 0 && d.getY() == dot.getY())) // If so then only allow it if it was already used
                        return false;
                }
                else if (dot.getX() + prev.getX() == 0 && dot.getY() + prev.getY() == 0)
                    return false;
            }
            prev = dot;
        }
        return true;
    }
}
