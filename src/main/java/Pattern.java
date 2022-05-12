import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Pattern {
    Dot[] line;

    public Pattern(Dot[] line) {
        this.line = line;
    }

    public String toFormattedString() {
        StringBuilder str = new StringBuilder();

        str.append("Length: ").append(line.length).append('\n');

        for (Dot dot : line) {
            str.append(dot.name()).append(' ');
        }

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
                    str.append(" ").append(dotIndex);
                else
                    str.append(" •");
            }
            str.append('\n');
        }

        return str.toString();
    }

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
