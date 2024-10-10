package com.wavemaker.service.impl;

import com.wavemaker.factory.FileRepositoryFactoryInstanceHandler;
import com.wavemaker.pojo.CharacterOccurrence;
import com.wavemaker.pojo.WordOccurrence;
import com.wavemaker.repository.FileRepository;
import com.wavemaker.service.FileService;

import java.util.List;
import java.util.Map;

public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(String filePath) {
        fileRepository = FileRepositoryFactoryInstanceHandler.getFileRepositoryInstance(filePath);
    }

    @Override
    public boolean write(String content) {
        return fileRepository.write(content);
    }

    @Override
    public int getOccurrencesOfACharacter(char character) {
        return fileRepository.getOccurrencesOfACharacter(character);
    }

    @Override
    public List<CharacterOccurrence> getLineNumberAndPositionOfACharacter(char character) {
        return fileRepository.getLineNumberAndPositionOfACharacter(character);
    }

    @Override
    public int getOccurrencesOfAWord(String word) {
        return fileRepository.getOccurrencesOfAWord(word);
    }

    /**
     * @param word
     * @return
     * Returns the list of line number and position of a word
     * line and position
     */
    @Override
    public List<WordOccurrence> getLineNumberAndPositionOfAWord(String word) {
        return fileRepository.getLineNumberAndPositionOfAWord(word);
    }

    /**
     * @param word, takes the word to be searched
     * @return
     * returns the list of line number and position of a word in all the files
     */
    @Override
    public List<WordOccurrence> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(String word, String directoryPath) {
        return fileRepository.searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, directoryPath);
    }
}
