package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    public static List<RecordRow> loadSalesCsv(Path csvPath) throws IOException {
        List<RecordRow> rows = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String header = br.readLine(); // skip header
            if (header == null) return rows;

            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", -1); // safe for our generated CSV
                if (p.length < 5) continue;

                String orderId = p[0].trim();
                String orderDate = p[1].trim();
                String region = p[2].trim();
                String category = p[3].trim();
                double sales = parseDoubleSafe(p[4].trim());

                rows.add(new RecordRow(orderId, orderDate, region, category, sales));
            }
        }
        return rows;
    }

    private static double parseDoubleSafe(String s) {
        try { return Double.parseDouble(s); }
        catch (Exception e) { return 0.0; }
    }
}