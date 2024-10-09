package com.wavemaker.repository;

import java.util.List;
import java.util.Map;

public interface FileRepository {
    public boolean write(String content);

    public int getOccurrencesOfACharacter(char character);

    public int getOccurrencesOfAWord(String word);

    /**
     * @param word
     * @return
     * Returns the list of line number and position of a word
     * line and position
     */
    public List<Map<String, Integer>>getLineNumberAndPositionOfAWord(String word);

    /**
     * @param word, takes the word to be searched
     * @return
     * returns the list of line number and position of a word in all the files
     */
    public Map<String, List<Map<String,Integer>>> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(String word, String directoryPath);
}
