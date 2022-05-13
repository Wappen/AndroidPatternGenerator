package me.wappen;

import java.util.Arrays;
import java.util.List;

/**
 * A pattern consisting of dots that are in a specific order
 * @param line The line of dots that are connected
 */
public record Pattern(Dot[] line) {
    /**
     * Converts the pattern into a nice human-readable formatted string
     * @return A formatted {@code String}
     */
    public String toFormattedString() {
        StringBuilder str = new StringBuilder();

        str.append("Length: ").append(line.length).append('\n');

        for (Dot dot : line) {
            str.append(dot.name()).append(", ");
        }
        str.delete(str.length() - 2, str.length() - 1);

        str.append('\n');

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = j;
                int y = i;

                int dotIndex = -1;

                List<Dot> a = Arrays.stream(line).filter(d -> d.getX() == x && d.getY() == y).toList();
                if (a.size() > 0) {
                    dotIndex = Arrays.asList(line).indexOf(a.get(0));
                }

                if (dotIndex != -1)
                    str.append(" ").append(dotIndex + 1);
                else
                    str.append(" •");
            }
            str.append('\n');
        }

        return str.toString();
    }

    /**
     * Converts the pattern to a string in the format of a number where each digit represents the index a dot
     * @return A {@code String} representing the pattern
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Dot dot : line) {
            str.append(dot.ordinal() + 1);
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pattern pattern = (Pattern) o;
        return Arrays.equals(line, pattern.line);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(line);
    }
}
