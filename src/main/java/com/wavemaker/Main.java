package com.wavemaker;

import com.wavemaker.pojo.CharacterOccurrence;
import com.wavemaker.pojo.WordOccurrence;
import com.wavemaker.service.FileService;
import com.wavemaker.service.impl.FileServiceImpl;

import java.util.List;

public class Main {

    private static final String FILE_PATH = "src/main/resources/myFile.txt";

    private static final FileService fileService = new FileServiceImpl(FILE_PATH);

    public static void main(String[] args) {

        System.out.println("File path: " + FILE_PATH);
        fileService.write("Hello World");
        System.out.println("No. of occurrences of 'l' in the file: " + fileService.getOccurrencesOfACharacter('l'));
        System.out.println("No. of occurrences of 'World' in the file: " + fileService.getOccurrencesOfAWord("World"));

//        printLineNumberAndPosition("World");

        List<WordOccurrence> searchResults = fileService.searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition("World", "/home/chakravarthyb_500380/Documents/Demo");

        printSearchResults(searchResults);

//        printLineNumberAndPositionOfACharacter('l');
    }

    private static void printLineNumberAndPosition(String word) {
        List<WordOccurrence> wordOccurrenceList = fileService.getLineNumberAndPositionOfAWord(word);
        if (wordOccurrenceList.isEmpty()) {
            System.out.println("Word \"" + word + "\" not found.");
            return;
        }
        for (WordOccurrence wordOccurrence : wordOccurrenceList) {
            int lineNumber = wordOccurrence.getLineNumber();
            int position = wordOccurrence.getPosition();
            System.out.println("Word \"" + word + "\" found at line " + lineNumber + ", position " + position + ".");
        }
    }

    private static void printLineNumberAndPositionOfACharacter(char character) {
        List<CharacterOccurrence> characterOccurrenceList = fileService.getLineNumberAndPositionOfACharacter(character);
        if (characterOccurrenceList.isEmpty()) {
            System.out.println("Character \"" + character + "\" not found.");
            return;
        }
        System.out.println("File Path : " + characterOccurrenceList.getFirst().getFilePath());
        System.out.println("File Name : " + characterOccurrenceList.getFirst().getFileName());
        System.out.println("Character : " + characterOccurrenceList.getFirst().getCharacter());
        System.out.println("Total Number of Occurrences : " + characterOccurrenceList.size());
        for (CharacterOccurrence characterOccurrence : characterOccurrenceList) {
            int lineNumber = characterOccurrence.getLineNumber();
            int position = characterOccurrence.getPosition();
            System.out.println("Character \"" + character + "\" found at line " + lineNumber + ", position " + position + ".");
        }
    }

    private static void printSearchResults(List<WordOccurrence> searchResults) {
        if (searchResults.isEmpty()) {
            System.out.println("No occurrences found.");
            return;
        }
        System.out.println("Word Search Results:");
        System.out.println("Total Occurrences : " + searchResults.size());
        for (WordOccurrence wordOccurrence : searchResults) {
            System.out.println();
            System.out.println("File Path : " + wordOccurrence.getFilePath());
            System.out.println("File Name : " + wordOccurrence.getFileName());
            System.out.println("Word : " + wordOccurrence.getWord());
            System.out.println("Line Number : " + wordOccurrence.getLineNumber());
            System.out.println("Position : " + wordOccurrence.getPosition());
        }
    }


}
