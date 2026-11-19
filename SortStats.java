
public class SortStats {

    public String sortName;
    public String fileName;
    public long comparisons;
    public long swaps;
    public long loops;
    public long timeNano;

    /*
     * PRE: sortName and fileName are valid strings.
     * POST: Creates statistics with all counters set to zero.
     */
    public SortStats(String sortName, String fileName) {
        this.sortName = sortName;
        this.fileName = fileName;
        this.comparisons = 0;
        this.swaps = 0;
        this.loops = 0;
        this.timeNano = 0;
    }
}
