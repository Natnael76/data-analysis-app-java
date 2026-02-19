package app;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Path csvPath = Path.of("data", "sales_10000.csv");

        // Generate dataset if missing
        if (!csvPath.toFile().exists()) {
            SampleDataGenerator.generate(csvPath, 10_000);
        }

        List<RecordRow> data = CsvLoader.loadSalesCsv(csvPath);
        List<RecordRow> working = data;

        System.out.println("Loaded rows: " + data.size());

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Data Analysis Application (Java) ===");
            System.out.println("1) Filter by Category");
            System.out.println("2) Filter by Region");
            System.out.println("3) Sort by Sales (desc)");
            System.out.println("4) Show Summary Stats");
            System.out.println("5) Generate Report (output/report.txt)");
            System.out.println("6) Reset Filters");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter category (Technology/Furniture/Office Supplies): ");
                    String cat = sc.nextLine().trim();
                    working = Analyzer.filterByCategory(working, cat);
                    System.out.println("Rows after category filter: " + working.size());
                }
                case "2" -> {
                    System.out.print("Enter region (East/West/Central/South): ");
                    String reg = sc.nextLine().trim();
                    working = Analyzer.filterByRegion(working, reg);
                    System.out.println("Rows after region filter: " + working.size());
                }
                case "3" -> {
                    working = Analyzer.sortBySalesDesc(working);
                    System.out.println("Sorted by Sales (desc). Top 3 rows:");
                    working.stream().limit(3).forEach(r ->
                            System.out.println(
                                    r.orderId() + " | " + r.category() + " | " + r.region() + " | " + r.sales()
                            )
                    );
                }
                case "4" -> {
                    var stats = Analyzer.summaryStats(working);
                    System.out.println("Records: " + stats.count());
                    System.out.println("Total Sales: " + String.format("%.2f", stats.sum()));
                    System.out.println("Avg Sales: " + String.format("%.2f", stats.avg()));
                    System.out.println("Min Sales: " + String.format("%.2f", stats.min()));
                    System.out.println("Max Sales: " + String.format("%.2f", stats.max()));
                }
                case "5" -> {
                    var stats = Analyzer.summaryStats(working);
                    var top10 = Analyzer.sortBySalesDesc(working).stream().limit(10).toList();
                    ReportWriter.writeReport(Path.of("output", "report.txt"), stats, top10);
                    System.out.println("Report written to output/report.txt");
                }
                case "6" -> {
                    working = data;
                    System.out.println("Filters reset. Rows: " + working.size());
                }
                case "0" -> {
                    System.out.println("Goodbye.");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}