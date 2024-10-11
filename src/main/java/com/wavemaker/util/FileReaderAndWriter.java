package com.wavemaker.util;

import com.wavemaker.exception.DirectoryNotFoundException;
import com.wavemaker.exception.FileReadException;
import com.wavemaker.exception.FileWriteException;
import com.wavemaker.pojo.CharacterOccurrence;
import com.wavemaker.pojo.WordOccurrence;
import com.wavemaker.service.impl.ExecutorServiceHandler;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class FileReaderAndWriter {
    private final ExecutorService executorService = ExecutorServiceHandler.getExecutorServiceInstance();
    private final File file;

    public FileReaderAndWriter(File file) {
        this.file = file;
    }

    public boolean writeContent(String content) {
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            return true;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while writing content to file: " + file.getAbsolutePath(), 500);
        }
    }


    public int getOccurrencesOfACharacter(char character) {
        int occurrences = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            if (line != null) line = line.toLowerCase();
            while (line != null) {
                for (char c : line.toCharArray()) {
                    if (c == character) occurrences++;
                }
                line = bufferedReader.readLine();
                if (line != null) line = line.toLowerCase();
            }
            return occurrences;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        }
    }

    public List<CharacterOccurrence> getLineNumberAndPositionOfACharacter(char character) {
        List<CharacterOccurrence> characterOccurrenceList = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            if (line != null) line = line.toLowerCase();
            int lineNumber = 1;  //initial line number is 1
            while (line != null) {
                int position = 0;
                for (char c : line.toCharArray()) {
                    position++;
                    if (c == character) {
                        CharacterOccurrence characterOccurrence = new CharacterOccurrence();
                        characterOccurrence.setFilePath(file.getAbsolutePath());
                        characterOccurrence.setFileName(file.getName());
                        characterOccurrence.setCharacter(character);
                        characterOccurrence.setLineNumber(lineNumber);
                        characterOccurrence.setPosition(position);

                        characterOccurrenceList.add(characterOccurrence);
                    }
                }
                line = bufferedReader.readLine();
                if (line != null) line = line.toLowerCase();
                lineNumber++;
            }
            return characterOccurrenceList;
        } catch (IOException exception) {
            throw new FileReadException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        }
    }

    /**
     * @param word
     * @return Returns the list of line number and position of a word
     * line and position
     */
    public int getOccurrencesOfAWord(String word) {
        int occurrences = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            if (line != null) line = line.toLowerCase();
            while (line != null) {
                int fromIndex = 0;
                fromIndex = line.indexOf(word, fromIndex);
                while (fromIndex != -1) {
                    occurrences++;
                    fromIndex++;
                    fromIndex = line.indexOf(word, fromIndex);
                }
                line = bufferedReader.readLine();
                if (line != null) line = line.toLowerCase();
            }
            return occurrences;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        }
    }

    public List<WordOccurrence> addWordOccurrencesFromFile(String word) {
        List<WordOccurrence> wordOccurrenceList = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            if (line != null) line = line.toLowerCase();
            int lineNumber = 1;  //initial line number is 1
            while (line != null) {
                int fromIndex = 0;
                fromIndex = line.indexOf(word, fromIndex);
                while (fromIndex != -1) {
                    WordOccurrence wordOccurrence = new WordOccurrence();
                    wordOccurrence.setFilePath(file.getAbsolutePath());
                    wordOccurrence.setLineNumber(lineNumber);
                    wordOccurrence.setPosition(fromIndex + 1);
                    wordOccurrenceList.add(wordOccurrence);
                    fromIndex++;
                    fromIndex = line.indexOf(word, fromIndex);
                }
                line = bufferedReader.readLine();
                if (line != null) line = line.toLowerCase();
                lineNumber++;
            }
            return wordOccurrenceList;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        }
    }


    public List<WordOccurrence> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition
            (String word, String directoryPath) {
        List<WordOccurrence> wordSearchMap = new LinkedList<>();
        File directory = new File(directoryPath); //directory to be searched

        checkIfDirectoryExists(directory);  //checks if directory exists

        searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, directory, wordSearchMap);

        return wordSearchMap;
    }

    private void searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition
            (String word, File directory, List<WordOccurrence> wordSearchMap) {
        try {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, file, wordSearchMap);
                    } else if (file.isFile()) {
                        addWordOccurrencesFromFile(word, file, wordSearchMap);
                    }
                }
            }
        } catch (SecurityException exception) {
            throw new FileReadException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        }
    }

    private void addWordOccurrencesFromFile(String word, File file, List<WordOccurrence> wordSearchMap) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            if (line != null) line = line.toLowerCase();
            int lineNumber = 1;  //initial line number is 1
            while (line != null) {
                int fromIndex = 0;
                fromIndex = line.indexOf(word, fromIndex);
                while (fromIndex != -1) {
                    WordOccurrence wordOccurrence = new WordOccurrence();
                    wordOccurrence.setFilePath(file.getAbsolutePath());
                    wordOccurrence.setWord(word);
                    wordOccurrence.setFileName(file.getName());
                    wordOccurrence.setLineNumber(lineNumber);
                    wordOccurrence.setPosition(fromIndex + 1);
                    wordSearchMap.add(wordOccurrence);
                    fromIndex++;
                    fromIndex = line.indexOf(word, fromIndex);
                }
                line = bufferedReader.readLine();
                if (line != null) line = line.toLowerCase();
                lineNumber++;
            }
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        }
    }

    private void checkIfDirectoryExists(File directoryPath) {
        if (!directoryPath.exists() || !directoryPath.isDirectory()) {
            throw new DirectoryNotFoundException("Directory not found: " + directoryPath.getAbsolutePath(), 500);
        }
    }
}
