package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExportUtil {

    public static void exportToCSV(String filePath, List<String[]> rows) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Date,Category,Description,Amount\n");
            for (String[] row : rows) {
                writer.write(String.join(",", row));
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
