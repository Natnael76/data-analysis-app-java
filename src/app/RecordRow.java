package app;

public record RecordRow(
        String orderId,
        String orderDate,
        String region,
        String category,
        double sales
) {}