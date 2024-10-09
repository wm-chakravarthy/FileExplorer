package com.wavemaker.util;

import com.wavemaker.exception.DirectoryNotFoundException;
import com.wavemaker.exception.FileWriteException;
import com.wavemaker.service.impl.ExecutorServiceHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
                String[] words = line.split(" ");
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

    public List<Map<String, Integer>> getLineNumberAndPositionOfAWord(String word) {
        List<Map<String, Integer>> list = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            int lineNumber = 1;  //initial line number is 1
            while (line != null) {
                String[] words = line.split(" ");
                for (int i = 0; i < words.length; i++) {
                    if (words[i].equals(word)) {
                        Map<String, Integer> map = new LinkedHashMap<>();
                        map.put("lineNumber", lineNumber);
                        map.put("position", i + 1);
                        list.add(map);
                    }
                }
                line = bufferedReader.readLine();
                lineNumber++;
            }
            return list;
        } catch (IOException exception) {
            throw new FileWriteException("An error occurred while reading content from file: " + file.getAbsolutePath(), 500);
        } finally {
            closeBufferedReader(bufferedReader);
        }
    }


    public Map<String, List<Map<String, Integer>>> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(String word, String directoryPath) {
        Map<String, List<Map<String, Integer>>> wordSearchMap = new LinkedHashMap<>();

        File directory = new File(directoryPath); //directory to be searched

        checkIfDirectoryExists(directory);  //checks if directory exists

        wordSearchMap = searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, directory, wordSearchMap);
        return wordSearchMap;
    }

    private Map<String, List<Map<String, Integer>>> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition
            (String word, File directory, Map<String, List<Map<String, Integer>>> wordSearchMap) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, file, wordSearchMap);
                } else if (file.isFile()) {
                    List<Map<String, Integer>> list = getLineNumberAndPositionOfAWord(word, file);
                    wordSearchMap.put(file.getAbsolutePath(), list);
                }
            }
        }
        return wordSearchMap;
    }

    private List<Map<String, Integer>> getLineNumberAndPositionOfAWord(String word, File file) {
        List<Map<String, Integer>> list = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            int lineNumber = 1;  //initial line number is 1
            while (line != null) {
                String[] words = line.split(" ");
                for (int i = 0; i < words.length; i++) {
                    if (words[i].equals(word)) {
                        Map<String, Integer> map = new LinkedHashMap<>();
                        map.put("lineNumber", lineNumber);
                        map.put("position", i + 1);
                        list.add(map);
                    }
                }
                line = bufferedReader.readLine();
                lineNumber++;
            }
            return list;
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
