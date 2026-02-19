package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ReportWriter {

    public static void writeReport(Path outPath, Analyzer.SummaryStats stats, List<RecordRow> topRows) throws IOException {
        Files.createDirectories(outPath.getParent());

        StringBuilder sb = new StringBuilder();
        sb.append("Sales Report (Java Data Analysis Application)\n");
        sb.append("===========================================\n\n");

        sb.append("Summary Stats\n");
        sb.append("- Records: ").append(stats.count()).append("\n");
        sb.append("- Total Sales: ").append(String.format("%.2f", stats.sum())).append("\n");
        sb.append("- Avg Sales: ").append(String.format("%.2f", stats.avg())).append("\n");
        sb.append("- Min Sales: ").append(String.format("%.2f", stats.min())).append("\n");
        sb.append("- Max Sales: ").append(String.format("%.2f", stats.max())).append("\n\n");

        sb.append("Top 10 Rows by Sales\n");
        int i = 1;
        for (RecordRow r : topRows) {
            sb.append(i++).append(") ")
              .append(r.orderId()).append(" | ")
              .append(r.orderDate()).append(" | ")
              .append(r.region()).append(" | ")
              .append(r.category()).append(" | ")
              .append(String.format("%.2f", r.sales()))
              .append("\n");
        }

        Files.writeString(outPath, sb.toString());
    }
}