package com.wavemaker.util;

import com.wavemaker.exception.FileWriteException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileReaderAndWriter {
    private final File file;

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
