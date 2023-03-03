package org.example;

import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Book {
    private static final String directory = Paths.get(System.getProperty("user.dir"), "/src/main/java/books").toString();
    private final String bookName;
    private final StringBuilder text;

    private final Statistics statistics;
    private boolean exists;

    public Book(String bookName) {
        Path path = Paths.get(directory, bookName);
        text = new StringBuilder();
        statistics = new Statistics(path);
        this.bookName = bookName;

        try (BufferedReader bookReader =
                     new BufferedReader(new InputStreamReader( new FileInputStream(path.toFile()), "cp1251"))) {
            bookReader.lines().forEach(text::append);
            this.exists = true;
        } catch (FileNotFoundException e) {
            System.out.printf("Book '%s' was not found\n", this.bookName);
            this.exists = false;
        } catch (IOException e) {
            statistics.addStatistics(e.getMessage());
            e.printStackTrace();
        }
    }

    public String getText() {
        return text.toString();
    }

    public String getBookName() {
        return bookName;
    }

    private String[] getTextAsArray() {
        if (text.isEmpty()) return new String[0];
        return text.toString().split("[ \n]");
    }

    public List<String> countPopularWords() {
        return countPopularWords(2, 10);
    }

    public List<String> countPopularWords(int wordLengthThreshold, int totalWords) {
        Map<String, Long> wordCount = Arrays.stream(getTextAsArray())
                .filter(s -> s.length() > wordLengthThreshold)
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<String> strings = wordCount.entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry<String, Long>::getValue).reversed())
                .limit(totalWords)
                .map(Map.Entry::getKey)
                .toList();

        statistics.addStatistics("Popular words:\n" + strings);
        return strings;
    }

    public long countUniqueWords() {
        int uniqueCount = new HashSet<>(List.of(getTextAsArray())).size();
        statistics.addStatistics("Unique words count: " + uniqueCount);
        return uniqueCount;
    }


    public void printStatistics() {
        if (!exists) return;
        System.out.printf("\nBook '%s' stats: \n", bookName);
        System.out.println(statistics.getStatistics());
    }
}
