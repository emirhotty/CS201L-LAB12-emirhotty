
public class Sorts {

    public static SortStats stats;

    /*
     * PRE: arr is a valid integer array.
     * POST: Sorts arr in ascending order using selection sort.
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            stats.loops++;
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                stats.loops++;
                stats.comparisons++;

                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                swap(arr, i, minIndex);
            }
        }
    }

    /*
     * PRE: arr is a valid integer array.
     * POST: Sorts arr in ascending order using insertion sort.
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            stats.loops++;
            int key = arr[i];
            int j = i - 1;

            while (j >= 0) {
                stats.loops++;
                stats.comparisons++;

                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    stats.swaps++;
                    j--;
                } else {
                    break;
                }
            }

            arr[j + 1] = key;
        }
    }

    /*
     * PRE: arr is a valid integer array.
     * POST: Sorts arr in ascending order using bubble sort.
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            stats.loops++;

            for (int j = 0; j < n - i - 1; j++) {
                stats.loops++;
                stats.comparisons++;

                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /*
     * PRE: arr is a valid integer array.
     * POST: Sorts arr in ascending order using merge sort.
     */
    public static void mergeSort(int[] arr) {
        if (arr.length > 1) {
            mergeSortHelper(arr, 0, arr.length - 1);
        }
    }

    /*
     * PRE: left and right are valid array boundaries.
     * POST: Recursively sorts the specified section.
     */
    private static void mergeSortHelper(int[] arr,
            int left, int right) {

        if (left < right) {
            int middle = left + (right - left) / 2;

            mergeSortHelper(arr, left, middle);
            mergeSortHelper(arr, middle + 1, right);

            merge(arr, left, middle, right);
        }
    }

    /*
     * PRE: Both halves of the given range are sorted.
     * POST: Merges the halves into one sorted range.
     */
    private static void merge(int[] arr,
            int left, int middle, int right) {

        int[] temp = new int[right - left + 1];

        int i = left;
        int j = middle + 1;
        int k = 0;

        while (i <= middle && j <= right) {
            stats.loops++;
            stats.comparisons++;

            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= middle) {
            stats.loops++;
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            stats.loops++;
            temp[k++] = arr[j++];
        }

        for (int x = 0; x < temp.length; x++) {
            stats.loops++;
            arr[left + x] = temp[x];
        }
    }

    /*
     * PRE: arr is a valid integer array.
     * POST: Sorts arr in ascending order using quick sort.
     */
    public static void quickSort(int[] arr) {
        if (arr.length > 1) {
            quickSortHelper(arr, 0, arr.length - 1);
        }
    }

    /*
     * PRE: low and high identify a valid array section.
     * POST: Recursively sorts that section.
     */
    private static void quickSortHelper(int[] arr,
            int low, int high) {

        if (low < high) {
            int pivotIndex = partition(arr, low, high);

            quickSortHelper(arr, low, pivotIndex - 1);
            quickSortHelper(arr, pivotIndex + 1, high);
        }
    }

    /*
     * PRE: low and high are valid array indices.
     * POST: Places pivot in its final sorted position.
     */
    private static int partition(int[] arr,
            int low, int high) {

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            stats.loops++;
            stats.comparisons++;

            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);

        return i + 1;
    }

    /*
     * PRE: arr contains nonnegative integers.
     * POST: Sorts arr using radix sort.
     */
    public static void radixSort(int[] arr) {
        if (arr.length == 0) {
            return;
        }

        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            stats.loops++;
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        for (long exp = 1; max / exp > 0; exp *= 10) {
            stats.loops++;
            countingSort(arr, exp);
        }
    }

    /*
     * PRE: arr contains nonnegative integers and exp > 0.
     * POST: Stably sorts arr by the digit at exp.
     */
    private static void countingSort(int[] arr, long exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            stats.loops++;
            int digit = (int) ((arr[i] / exp) % 10);
            count[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            stats.loops++;
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            stats.loops++;
            int digit = (int) ((arr[i] / exp) % 10);
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        for (int i = 0; i < n; i++) {
            stats.loops++;
            arr[i] = output[i];
            stats.swaps++;
        }
    }

    /*
     * PRE: i and j are valid indices in arr.
     * POST: Exchanges the two values and counts the swap.
     */
    private static void swap(int[] arr, int i, int j) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            stats.swaps++;
        }
    }
}
