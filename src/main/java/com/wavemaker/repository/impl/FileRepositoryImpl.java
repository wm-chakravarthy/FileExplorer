package com.wavemaker.repository.impl;

import com.wavemaker.pojo.CharacterOccurrence;
import com.wavemaker.pojo.WordOccurrence;
import com.wavemaker.repository.FileRepository;
import com.wavemaker.util.FileReaderAndWriter;
import com.wavemaker.util.FileUtil;

import java.util.List;

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
    public List<CharacterOccurrence> getLineNumberAndPositionOfACharacter(char character) {
        return fileReaderAndWriter.getLineNumberAndPositionOfACharacter(character);
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
    public List<WordOccurrence> getLineNumberAndPositionOfAWord(String word) {
        return fileReaderAndWriter.addWordOccurrencesFromFile(word);
    }

    /**
     * @param word, takes the word to be searched
     * @return
     * returns the list of line number and position of a word in all the files
     */
    @Override
    public List<WordOccurrence> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(String word, String directoryPath) {
        return fileReaderAndWriter.searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, directoryPath);
    }
}
