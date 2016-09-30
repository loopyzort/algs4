package old;

/**
 * Created by loopyzort on 2/28/15.
 */
public class Mergesort {
    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid,
                             int hi) {
        for (int i = lo; i <= hi; i++) {
            aux[i] = a[i];
        }

        int j = lo;
        int k = mid + 1;
        for (int i = lo; i <= hi; i++) {
            if (j > mid) {
                a[i] = aux[k++];
            } else if (k > hi) {
                a[i] = aux[j++];
            } else if (aux[j].compareTo(aux[k]) <= 0) {
                a[i] = aux[j++];
            } else {
                a[i] = aux[k++];
            }
        }
    }

    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void main(String[] args) {
        Integer[] vals = {3, 4, 1, 9, 8, 2, 15};
        Integer[] aux = new Integer[vals.length];
        sort(vals, aux, 0, vals.length - 1);
        assert vals[0] == 1;
        assert vals[1] == 2;
        assert vals[2] == 3;
        assert vals[3] == 4;
        assert vals[4] == 8;
        assert vals[5] == 9;
        assert vals[6] == 15;
    }
}
