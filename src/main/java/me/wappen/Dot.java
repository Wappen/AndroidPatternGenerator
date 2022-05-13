package me.wappen;

/**
 * Enum of dots that can be used in a lock pattern
 * Each dot holds its position in coordinates relative to the center
 */
public enum Dot {
    TOP_LEFT(-1, -1), TOP(0, -1), TOP_RIGHT(1, -1),
    LEFT(-1, 0), CENTER(0, 0), RIGHT(1, 0),
    BOTTOM_LEFT(-1, 1), BOTTOM(0, 1), BOTTOM_RIGHT(1, 1);

    private final int x;
    private final int y;

    Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dot next() {
        return values()[(this.ordinal()+1) % values().length];
    }

    public static Dot fromInt(int i) {
        return values()[i % values().length];
    }

    /**
     * Checks if the dot is a corner dot
     * @return {@code true} for {@code TOP_LEFT}, {@code TOP_RIGHT}, {@code BOTTOM_LEFT}, {@code BOTTOM_RIGHT}
     */
    public boolean isCorner() {
        return x != 0 && y != 0;
    }

    /**
     * Checks if the dot is an edge dot
     * @return {@code true} for {@code TOP}, {@code LEFT}, {@code RIGHT}, {@code BOTTOM}
     */
    public boolean isEdge() {
        return x != y && (x == 0 || y == 0);
    }
}
