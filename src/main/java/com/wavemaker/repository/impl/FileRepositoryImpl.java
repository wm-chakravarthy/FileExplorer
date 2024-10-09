package com.wavemaker.repository.impl;

import com.wavemaker.repository.FileRepository;
import com.wavemaker.util.FileReaderAndWriter;
import com.wavemaker.util.FileUtil;

import java.util.List;
import java.util.Map;

public class FileRepositoryImpl implements FileRepository {

    private static String FILE_PATH = null;

    private final FileReaderAndWriter fileReaderAndWriter;

    public FileRepositoryImpl(String filePath) {
        if (filePath != null) {
            FILE_PATH = filePath;
        }
        this.fileReaderAndWriter = new FileReaderAndWriter(FileUtil.createFileIfNotExists(FILE_PATH));
    }

    @Override
    public boolean write(String content) {
        return fileReaderAndWriter.writeContent(content);
    }

    @Override
    public int getOccurrencesOfACharacter(char character) {
        return fileReaderAndWriter.getOccurrencesOfACharacter(character);
    }

    @Override
    public int getOccurrencesOfAWord(String word) {
        return fileReaderAndWriter.getOccurrencesOfAWord(word);
    }

    /**
     * @param word
     * @return
     * Returns the list of line number and position of a word
     * line and position
     */
    @Override
    public List<Map<String, Integer>> getLineNumberAndPositionOfAWord(String word) {
        return fileReaderAndWriter.getLineNumberAndPositionOfAWord(word);
    }

    /**
     * @param word, takes the word to be searched
     * @return
     * returns the list of line number and position of a word in all the files
     */
    @Override
    public Map<String, List<Map<String, Integer>>> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(String word, String directoryPath) {
        return fileReaderAndWriter.searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, directoryPath);
    }
}
