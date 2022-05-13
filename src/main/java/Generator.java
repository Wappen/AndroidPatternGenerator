import java.util.*;
import java.util.function.Predicate;

public class Generator {
    private final List<Predicate<Pattern>> constraints = new ArrayList<>();

    public void addConstraint(Predicate<Pattern> constraint) {
        constraints.add(constraint);
    }

    public List<Pattern> generatePatterns(int length) {
        return generatePatterns(length, length);
    }

    public List<Pattern> generatePatterns(int minLength, int maxLength) {
        List<Pattern> allowedPatterns = new LinkedList<>();

        for (int i = minLength; i <= maxLength; i++) {
            List<Pattern> patterns = allPermutations(i);

            for (Pattern pattern : patterns) {
                if (checkConstraints(pattern)) {
                    allowedPatterns.add(pattern);
                }
            }
        }

        return allowedPatterns;
    }

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

    private List<Pattern> allPerms(Pattern start, int length, List<Dot> dots) {
        List<Pattern> patterns = new LinkedList<>();

        if (length == 0) {
            patterns.add(start);
            return patterns;
        }

        for (int i = 0; i < dots.size(); i++) {
            Dot dot = dots.get(i);
            Dot[] line = new Dot[start.line.length + 1];
            System.arraycopy(start.line, 0, line, 0, start.line.length);
            line[line.length - 1] = dot;

            Pattern newStart = new Pattern(line);
            List<Dot> newDots = dots.stream().filter(d -> d != dot).toList();

            patterns.addAll(allPerms(newStart, length - 1, newDots));
        }

        return patterns;
    }

    private boolean checkConstraints(Pattern pattern) {
        return constraints.stream().allMatch(c -> c.test(pattern)); // Check if the pattern is legal according to all constraints
    }
}
