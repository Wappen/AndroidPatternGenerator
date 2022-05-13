package me.wappen.constraints;

import me.wappen.Pattern;

import java.util.ArrayList;
import java.util.List;

public class Constrainer {
    private final List<Constraint> constraints = new ArrayList<>();

    public void addConstraint(Constraint constraint) {
        if (constraint != null)
            constraints.add(constraint);
    }

    public boolean checkPattern(Pattern pattern) {
        return constraints.stream().allMatch(c -> c.test(pattern)); // Check if the pattern is legal according to all constraints
    }
}
