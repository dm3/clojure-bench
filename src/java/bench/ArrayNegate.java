package bench;

public class ArrayNegate {
    public static void bitNotArrayWhile(long[] arr) {
        int i = arr.length - 1;
        while (i >= 0) {
            arr[i] = ~arr[i];
            i--;
        }
    }
}
