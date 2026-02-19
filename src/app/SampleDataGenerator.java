package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Random;

public class SampleDataGenerator {

    public static void generate(Path outCsv, int rows) throws IOException {
        Files.createDirectories(outCsv.getParent());

        String[] regions = {"East", "West", "Central", "South"};
        String[] categories = {"Technology", "Furniture", "Office Supplies"};
        Random rng = new Random(42);

        StringBuilder sb = new StringBuilder();
        sb.append("OrderID,OrderDate,Region,Category,Sales\n");

        LocalDate start = LocalDate.of(2022, 1, 1);
        for (int i = 1; i <= rows; i++) {
            String orderId = "ORD" + String.format("%05d", i);
            LocalDate date = start.plusDays(rng.nextInt(365 * 2));
            String region = regions[rng.nextInt(regions.length)];
            String category = categories[rng.nextInt(categories.length)];

            double base = 10 + rng.nextDouble() * 490;
            if ("Technology".equals(category)) base *= 1.25;
            if ("West".equals(region)) base *= 1.10;
            double sales = Math.round(base * 100.0) / 100.0;

            sb.append(orderId).append(",")
              .append(date).append(",")
              .append(region).append(",")
              .append(category).append(",")
              .append(sales).append("\n");
        }

        Files.writeString(outCsv, sb.toString());
    }
}