public enum Dot {
    TOP_LEFT(-1, -1), TOP(0, -1), TOP_RIGHT(1, -1),
    LEFT(-1, 0), CENTER(0, 0), RIGHT(1, 0),
    BOTTOM_LEFT(-1, 1), BOTTOM(0, 1), BOTTOM_RIGHT(1, 1);

    private int x;
    private int y;

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

    public boolean isCorner() {
        return x != 0 && y != 0;
    }
}
