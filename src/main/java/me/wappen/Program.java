package me.wappen;

import me.wappen.constraints.AndroidPatternConstrainer;
import me.wappen.constraints.Constrainer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Program {
    public static void main(String... args) {
        Generator generator = new Generator();
        Constrainer constrainer = new AndroidPatternConstrainer();

        // Reddit user's constraints:

        constrainer.addConstraint(p -> {
            return p.line()[0] == me.wappen.Dot.CENTER && p.line()[1].isCorner() || p.line()[0].isCorner() && p.line()[1] == me.wappen.Dot.CENTER; // Starts at corner and goes to center or reversed
        });

        constrainer.addConstraint(p -> {
            for (int i = 0; i < p.line().length - 2; i++) {
                me.wappen.Dot a = p.line()[i];
                me.wappen.Dot b = p.line()[i + 1];
                me.wappen.Dot c = p.line()[i + 2];

                // Don't allow full vertical or horizontal columns or rows
                if (a.getY() == b.getY() && b.getY() == c.getY() || a.getX() == b.getX() && b.getX() == c.getX())
                    return false;
            }
            return true;
        });

        constrainer.addConstraint(p -> // Must span all columns
            Arrays.stream(p.line()).anyMatch(d -> d.getX() == -1) &&
            Arrays.stream(p.line()).anyMatch(d -> d.getX() == 0) &&
            Arrays.stream(p.line()).anyMatch(d -> d.getX() == 1));

        constrainer.addConstraint(p -> // Must span all rows
            Arrays.stream(p.line()).anyMatch(d -> d.getY() == -1) &&
            Arrays.stream(p.line()).anyMatch(d -> d.getY() == 0) &&
            Arrays.stream(p.line()).anyMatch(d -> d.getY() == 1));

        generator.setConstrainer(constrainer);
        List<Pattern> patterns = generator.generatePatterns(5, 6);

        System.out.printf("Number of allowed patterns: %d%n", patterns.size());

        try {
            PrintWriter out = new PrintWriter("results/out.txt");

            for (Pattern pattern : patterns) {
                out.println(pattern.toString());
            }

            out.close();

            out = new PrintWriter("results/human-readable-out.txt");

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
