package me.wappen.constraints;

import me.wappen.Pattern;

/**
 * Functional interface for constraints used in the {@code Constrainer}
 * Must return {@code true} when a pattern is ok and {@code false} when a pattern violates the constraint
 */
public interface Constraint {
    boolean test(Pattern p);
}
