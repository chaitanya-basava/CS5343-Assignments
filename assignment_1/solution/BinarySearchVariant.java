package solution;

public class BinarySearchVariant {
    public static int bsearchv(int[] A, int t) {
        int n = A.length;
        int l = 0;
        int h = n - 1;

        while(l <= h) {
            int m = l + (h - l) / 2;
            if(A[m] <= t) {
                if(m < n - 1 && t < A[m + 1]) return m;
                l = m + 1;
            } else h = m - 1;
        }

        if(h < 0) return -1;
        return n - 1;
    }
}
