package me.wappen.lockpatterns.constraints;

import me.wappen.lockpatterns.Pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * A constrainer that constraints the generator to generate patterns only following its constraints
 */
public class Constrainer {
    private final List<Constraint> constraints = new ArrayList<>();

    /**
     * Adds a constraint to the constraints-list
     * @param constraint The constraint to add
     */
    public void addConstraint(Constraint constraint) {
        if (constraint != null)
            constraints.add(constraint);
    }

    /**
     * Checks if a given pattern passes all constraints
     * @param pattern The pattern to check
     * @return True if all tests returned true
     */
    public boolean checkPattern(Pattern pattern) {
        return constraints.stream().allMatch(c -> c.test(pattern)); // Check if the pattern is legal according to all constraints
    }
}
