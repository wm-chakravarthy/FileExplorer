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

}
