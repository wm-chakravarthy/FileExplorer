package com.wavemaker;

import com.wavemaker.service.FileService;
import com.wavemaker.service.impl.FileServiceImpl;

import java.util.List;
import java.util.Map;

public class Main {

    private static final String FILE_PATH = "src/main/resources/myFile.txt";

    private static final FileService fileService = new FileServiceImpl(FILE_PATH);

    public static void main(String[] args) {

        System.out.println("File path: " + FILE_PATH);
        fileService.write("Hello World");
        System.out.println("No. of occurrences of 'l' in the file: " + fileService.getOccurrencesOfACharacter('l'));
        System.out.println("No. of occurrences of 'World' in the file: " + fileService.getOccurrencesOfAWord("World"));

        printLineNumberAndPosition("World");

        Map<String, List<Map<String, Integer>>> searchResults = fileService.searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition("World", "/home/chakravarthyb_500380/Documents/Demo");

        printSearchResults(searchResults);
    }

    private static void printLineNumberAndPosition(String word) {
        List<Map<String, Integer>> occurrences = fileService.getLineNumberAndPositionOfAWord(word);

        System.out.println("Occurrences of the word \"" + word + "\":");
        for (Map<String, Integer> occurrence : occurrences) {
            int lineNumber = occurrence.get("lineNumber");
            int position = occurrence.get("position");
            System.out.println("Line " + lineNumber + ", Position " + position);
        }
    }

    private static void printSearchResults(Map<String, List<Map<String, Integer>>> searchResults) {
        if (searchResults.isEmpty()) {
            System.out.println("No occurrences found.");
            return;
        }
        System.out.println("Word Search Results:");
        for (Map.Entry<String, List<Map<String, Integer>>> entry : searchResults.entrySet()) {
            String filePath = entry.getKey();
            List<Map<String, Integer>> occurrences = entry.getValue();

            System.out.println("\nFile: " + filePath);

            if (occurrences.isEmpty()) {
                System.out.println("  - No occurrences found in this file.");
            } else {
                System.out.println("  - Occurrences:");
                for (Map<String, Integer> occurrence : occurrences) {
                    int lineNumber = occurrence.get("lineNumber");
                    int position = occurrence.get("position");
                    System.out.printf("    - Line %d, Position %d%n", lineNumber, position);
                }
            }
        }
    }


}
