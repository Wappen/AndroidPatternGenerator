import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Program {
    public static void main(String... args) {
        Generator generator = new Generator();

        generator.addConstraint(p -> {
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
                }
                prev = dot;
            }
            return true;
        });

        // Reddit user's constraints:

        generator.addConstraint(p -> {
            return p.line[0] == Dot.CENTER && p.line[1].isCorner() || p.line[0].isCorner() && p.line[1] == Dot.CENTER; // Starts at corner and goes to center or reversed
        });

        generator.addConstraint(p -> {
            for (int i = 0; i < p.line.length - 2; i++) {
                Dot a = p.line[i];
                Dot b = p.line[i + 1];
                Dot c = p.line[i + 2];

                // Don't allow full vertical or horizontal columns or rows
                if (a.getY() == b.getY() && b.getY() == c.getY() || a.getX() == b.getX() && b.getX() == c.getX())
                    return false;
            }
            return true;
        });

        generator.addConstraint(p -> {
            // Must span all columns
            if (!(Arrays.stream(p.line).anyMatch(d -> d.getX() == -1) &&
                    Arrays.stream(p.line).anyMatch(d -> d.getX() == 0) &&
                    Arrays.stream(p.line).anyMatch(d -> d.getX() == 1)))
                return false;

            // Must span all rows
            if (!(Arrays.stream(p.line).anyMatch(d -> d.getY() == -1) &&
                    Arrays.stream(p.line).anyMatch(d -> d.getY() == 0) &&
                    Arrays.stream(p.line).anyMatch(d -> d.getY() == 1)))
                return false;

            return true;
        });

        List<Pattern> patterns = generator.generatePatterns(5, 6);

        System.out.printf("Number of allowed patterns: %d%n", patterns.size());

        try {
            PrintWriter out = new PrintWriter("out.txt");

            for (Pattern pattern : patterns) {
                out.println(pattern.toString());
            }

            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
