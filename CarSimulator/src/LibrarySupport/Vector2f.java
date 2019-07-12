package LibrarySupport;

public class Vector2f {

    public float x;
    public float y;

    public Vector2f() {

    }

    public Vector2f(float newX, float newY) {
        x = newX;
        y = newY;
    }

    public Vector2f(Vector2f clone) {
        x = clone.x;
        y = clone.y;
    }

    public void set(float newX, float newY) {
        x = newX;
        y = newY;
    }

    public void set(Vector2f v) {
        x = v.x;
        y = v.y;
    }

    public static void perpendicular(Vector2f v, Vector2f dest) {
        dest.x = v.y;
        dest.y = v.x * -1;
    }

    public void perpendicular() {
        set(y, x * -1);
    }

    public static void sub(Vector2f a, Vector2f b, Vector2f dest) {
        dest.x = a.x - b.x;
        dest.y = a.y - b.y;
    }

    public void sub(Vector2f v) {
        x -= v.x;
        y -= v.y;
    }

    public static float dot(Vector2f a, Vector2f b) {
        return ((a.x * b.x) + (a.y * b.y));
    }

    public float dot(Vector2f v) {
        return ((x * v.x) + (y * v.y));
    }

    public static float length(Vector2f a) {
        return (float) Math.sqrt((a.x * a.x) + (a.y * a.y));
    }

    public float length() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public static float distance(Vector2f start, Vector2f end) {
        return (float) Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y));
    }

    public float distance(Vector2f v) {
        return (float) Math.sqrt((v.x - x) * (v.x - x)
                + (v.y - y) * (v.y - y));
    }

    public static void normalize(Vector2f a, Vector2f dest) {
        float length = (float) Math.sqrt((a.x * a.x) + (a.y * a.y));
        dest.x = a.x / length;
        dest.y = a.y / length;
    }

    public void normalize() {
        float length = (float) Math.sqrt((x * x) + (y * y));
        x /= length;
        y /= length;
    }

    public void add(Vector2f v) {
        x += v.x;
        y += v.y;
    }

    public static void add(Vector2f a, Vector2f b, Vector2f dest) {
        dest.x = a.x + b.x;
        dest.y = a.y + b.y;
    }
}
