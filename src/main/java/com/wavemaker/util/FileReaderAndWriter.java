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
    private File file;

    private final ExecutorService executorService = ExecutorServiceHandler.getExecutorServiceInstance();

    public FileReaderAndWriter(File file) {
        this.file = file;
    }

    public boolean writeContent(String content) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            return true;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while writing content to file: " + file.getAbsolutePath(), 500);
        } finally {
            closeBufferedWriter(bufferedWriter);
        }
    }

    public int getOccurrencesOfACharacter(char character) {
        int occurrences = 0;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while (line != null) {
                for (char c : line.toCharArray()) {
                    if (c == character) occurrences++;
                }
                line = bufferedReader.readLine();
            }
            return occurrences;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        } finally {
            closeBufferedReader(bufferedReader);
        }
    }

    public List<CharacterOccurrence> getLineNumberAndPositionOfACharacter(char character) {
        List<CharacterOccurrence> characterOccurrenceList = new LinkedList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
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
                lineNumber++;
            }
            return characterOccurrenceList;
        } catch (IOException exception) {
            throw new FileReadException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        } finally {
            closeBufferedReader(bufferedReader);
        }
    }

    /**
     * @param word
     * @return Returns the list of line number and position of a word
     * line and position
     */
    public int getOccurrencesOfAWord(String word) {
        int occurrences = 0;
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                for (String w : words) {
                    if (w.equals(word)) occurrences++;
                }
                line = bufferedReader.readLine();
            }
            return occurrences;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        } finally {
            closeBufferedReader(bufferedReader);
        }
    }

    public List<WordOccurrence> addWordOccurrencesFromFile(String word) {
        List<WordOccurrence> wordOccurrenceList = new LinkedList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            int lineNumber = 1;  //initial line number is 1
            while (line != null) {
                int index = 0;
                String[] words = line.split("\\s+");
                for (String w : words) {
                    if (w.equals(word)) {
                        WordOccurrence wordOccurrence = new WordOccurrence();
                        wordOccurrence.setFilePath(file.getAbsolutePath());
                        wordOccurrence.setLineNumber(lineNumber);
                        wordOccurrence.setPosition(index + 1);
                        wordOccurrenceList.add(wordOccurrence);
                    }
                    index += w.length() + 1;
                }
                line = bufferedReader.readLine();
                lineNumber++;
            }
            return wordOccurrenceList;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        } finally {
            closeBufferedReader(bufferedReader);
        }
    }


    public List<WordOccurrence> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(String word, String directoryPath) {
        List<WordOccurrence> wordSearchMap = new LinkedList<>();
        File directory = new File(directoryPath); //directory to be searched

        checkIfDirectoryExists(directory);  //checks if directory exists

        searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, directory, wordSearchMap);

        return wordSearchMap;
    }

    private void searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition
            (String word, File directory, List<WordOccurrence> wordSearchMap) {
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
    }

    private void addWordOccurrencesFromFile(String word, File file, List<WordOccurrence> wordSearchMap) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            int lineNumber = 1;  //initial line number is 1
            while (line != null) {
                int index = 0;
                String[] words = line.split("\\s+");
                for (String w : words) {
                    if (w.equals(word)) {
                        WordOccurrence wordOccurrence = new WordOccurrence();
                        wordOccurrence.setFilePath(file.getAbsolutePath());
                        wordOccurrence.setFileName(file.getName());
                        wordOccurrence.setWord(word);
                        wordOccurrence.setLineNumber(lineNumber);
                        wordOccurrence.setPosition(index + 1);
                        wordSearchMap.add(wordOccurrence);
                    }
                    index += w.length() + 1;
                }
                line = bufferedReader.readLine();
                lineNumber++;
            }
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        } finally {
            closeBufferedReader(bufferedReader);
        }
    }

    private void checkIfDirectoryExists(File directoryPath) {
        if (!directoryPath.exists() || !directoryPath.isDirectory()) {
            throw new DirectoryNotFoundException("Directory not found: " + directoryPath.getAbsolutePath(), 500);
        }
    }

    private void closeBufferedWriter(BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                System.err.println("Failed to close the writer: " + e.getMessage());
            }
        }
    }

    private void closeBufferedReader(BufferedReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Failed to close the reader: " + e.getMessage());
            }
        }
    }
}
