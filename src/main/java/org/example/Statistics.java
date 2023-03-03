package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Statistics {
    private final Path path;
    private final List<String> statistics;
    private final boolean exists;

    public Statistics(Path path) {
        this.exists = path.toFile().exists();
        this.path = Paths.get(path + "_statistic.txt");
        statistics = new ArrayList<>();

    }

    public void addStatistics(String stat) {
        if (!exists) return;
        statistics.add(stat);
        writeToFile();
    }

    public String getStatistics() {
        return statistics.isEmpty() ? "" : String.join("\n", statistics);
    }

    private void writeToFile() {
        if (!exists) return;

        File statFile = path.toFile();
        try {
            statFile.createNewFile();
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(statFile))) {
                bufferedWriter.write(getStatistics());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Could not create statistics");
        }
    }
}
