import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Program {
    public static void main(String... args) {
        Generator generator = new Generator();

        generator.addConstraint(AndroidUtil::patternConstraint);

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

            out = new PrintWriter("human-readable-out.txt");

            int i = 1;
            for (Pattern pattern : patterns) {
                out.println("#" + i++ + " " + pattern.toFormattedString());
            }

            out.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
