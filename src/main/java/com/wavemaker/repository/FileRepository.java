package com.wavemaker.repository;

import com.wavemaker.pojo.CharacterOccurrence;
import com.wavemaker.pojo.WordOccurrence;

import java.util.List;

public interface FileRepository {
    public boolean write(String content);

    public int getOccurrencesOfACharacter(char character);

    public List<CharacterOccurrence> getLineNumberAndPositionOfACharacter(char character);

    public int getOccurrencesOfAWord(String word);

    /**
     * @param word
     * @return Returns the list of line number and position of a word
     * line and position
     */
    public List<WordOccurrence> getLineNumberAndPositionOfAWord(String word);

    /**
     * @param word, takes the word to be searched
     * @return returns the list of line number and position of a word in all the files
     */
    public List<WordOccurrence> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(String word, String directoryPath);
}
