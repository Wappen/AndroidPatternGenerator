public class Utils {
    public static int fact(int v) {
        if (v == 0 || v == 1) return 1;
        return fact(v - 1);
    }

    public static <T> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
