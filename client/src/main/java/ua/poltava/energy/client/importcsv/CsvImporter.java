package ua.poltava.energy.client.importcsv;

import javafx.concurrent.Task;
import ua.poltava.energy.client.api.ApiClient;
import ua.poltava.energy.client.model.ZapysSpozhyvannia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvImporter extends Task<Void> {
    private final File file;
    private final ApiClient apiClient = new ApiClient();

    public CsvImporter(File file) {
        this.file = file;
    }

    @Override
    protected Void call() throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }

        if (lines.isEmpty()) {
            throw new Exception("Файл порожній");
        }

        int startIndex = lines.get(0).contains("obiekt_id") ? 1 : 0;
        int total = lines.size() - startIndex;
        List<ZapysSpozhyvannia> records = new ArrayList<>();

        for (int i = startIndex; i < lines.size(); i++) {
            if (isCancelled()) break;

            String[] parts = lines.get(i).split(",");
            if (parts.length >= 4) {
                ZapysSpozhyvannia z = new ZapysSpozhyvannia();
                z.setObiektId(Integer.parseInt(parts[0].trim()));
                z.setDataZapysu(parts[1].trim());
                z.setSpozhyvanniaKvtHod(Double.parseDouble(parts[2].trim()));
                z.setVartistHryvnia(Double.parseDouble(parts[3].trim()));
                records.add(z);
            }

            updateProgress(i - startIndex + 1, total);
            Thread.sleep(200);
        }

        if (!records.isEmpty()) {
            apiClient.importRecords(records);
        }
        return null;
    }
}