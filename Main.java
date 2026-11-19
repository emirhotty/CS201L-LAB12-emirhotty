
import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class Main {

    /*
     * PRE: Input files are in the project folder.
     * POST: Runs each sort and creates report.txt.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Sort Evaluator");

        String[] files = {"data1.txt", "data2.txt", "data3.txt"};
        String[] labels = {
            "1 (random)", "2 (ascending)", "3 (descending)"
        };

        List<SortStats> results = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            try {
                int[] original = readFile(files[i]);

                System.out.println("Processing " + files[i]
                        + " (" + original.length + " values)");

                runSort(original, labels[i],
                        "Bubble", Sorts::bubbleSort, results);
                runSort(original, labels[i],
                        "Insertion", Sorts::insertionSort, results);
                runSort(original, labels[i],
                        "MergeSort", Sorts::mergeSort, results);
                runSort(original, labels[i],
                        "QuickSort", Sorts::quickSort, results);
                runSort(original, labels[i],
                        "RadixSort", Sorts::radixSort, results);
                runSort(original, labels[i],
                        "Selection", Sorts::selectionSort, results);

            } catch (IOException | NumberFormatException e) {
                System.out.println("Error reading " + files[i]
                        + ": " + e.getMessage());
            }
        }

        try {
            writeReport(results, "report.txt");
            System.out.println("Report saved to report.txt");
        } catch (IOException e) {
            System.out.println("Error writing report: " + e.getMessage());
        }
    }

    /*
     * PRE: File contains comma or whitespace separated integers.
     * POST: Returns an array containing all integers in the file.
     */
    public static int[] readFile(String fileName) throws IOException {
        List<Integer> numbers = new ArrayList<>();

        try (Scanner input = new Scanner(new File(fileName))) {
            input.useDelimiter("[,\\s]+");

            while (input.hasNext()) {
                numbers.add(Integer.parseInt(input.next()));
            }
        }

        int[] arr = new int[numbers.size()];

        for (int i = 0; i < numbers.size(); i++) {
            arr[i] = numbers.get(i);
        }

        return arr;
    }

    /*
     * PRE: original is a valid integer array.
     * POST: Sorts a copy, checks its order and stores statistics.
     */
    public static void runSort(int[] original, String fileName,
            String sortName, Consumer<int[]> algorithm,
            List<SortStats> results) {

        int[] copy = original.clone();

        Sorts.stats = new SortStats(sortName, fileName);
        long start = System.nanoTime();

        algorithm.accept(copy);

        Sorts.stats.timeNano = System.nanoTime() - start;

        if (!isSorted(copy)) {
            System.out.println("ERROR: " + sortName
                    + " failed on " + fileName);
        } else {
            System.out.println(sortName + " completed.");
        }

        results.add(Sorts.stats);
    }

    /*
     * PRE: arr is a valid integer array.
     * POST: Returns true if the array is in ascending order.
     */
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }

    /*
     * PRE: results contains the completed sorting statistics.
     * POST: Writes formatted results to the specified output file.
     */
    public static void writeReport(List<SortStats> results,
            String fileName) throws IOException {

        try (PrintWriter output =
                new PrintWriter(new FileWriter(fileName))) {

            output.printf("%-20s %-22s %15s %20s %20s %20s%n",
                    "Sort Name", "File", "Swaps",
                    "Comparisons", "Loops", "NanoSeconds");

            String[] order = {
                "Bubble", "Insertion", "MergeSort",
                "QuickSort", "RadixSort", "Selection"
            };

            for (String name : order) {
                for (SortStats stat : results) {
                    if (stat.sortName.equals(name)) {
                        output.printf(
                            "%-20s %-22s %,15d %,20d %,20d %18dns%n",
                            stat.sortName,
                            stat.fileName,
                            stat.swaps,
                            stat.comparisons,
                            stat.loops,
                            stat.timeNano
                        );
                    }
                }
            }
        }
    }
}
