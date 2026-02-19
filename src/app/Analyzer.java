package app;

import java.util.*;
import java.util.stream.Collectors;

public class Analyzer {

    // FILTERS
    public static List<RecordRow> filterByCategory(List<RecordRow> data, String category) {
        return data.stream()
                .filter(r -> r.category().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public static List<RecordRow> filterByRegion(List<RecordRow> data, String region) {
        return data.stream()
                .filter(r -> r.region().equalsIgnoreCase(region))
                .collect(Collectors.toList());
    }

    // SORT
    public static List<RecordRow> sortBySalesDesc(List<RecordRow> data) {
        return data.stream()
                .sorted(Comparator.comparingDouble(RecordRow::sales).reversed())
                .collect(Collectors.toList());
    }

    // STATS
    public static SummaryStats summaryStats(List<RecordRow> data) {
        int n = data.size();
        double sum = 0.0;
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        for (RecordRow r : data) {
            double v = r.sales();
            sum += v;
            if (v < min) min = v;
            if (v > max) max = v;
        }

        if (n == 0) {
            min = 0.0;
            max = 0.0;
        }

        double avg = (n == 0) ? 0.0 : sum / n;
        return new SummaryStats(n, sum, avg, min, max);
    }

    public record SummaryStats(int count, double sum, double avg, double min, double max) {}
}