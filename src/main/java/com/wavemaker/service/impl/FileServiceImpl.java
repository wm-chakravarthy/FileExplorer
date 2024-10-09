package com.wavemaker.service.impl;

import com.wavemaker.factory.FileRepositoryFactoryInstanceHandler;
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
    public List<Map<String, Integer>> getLineNumberAndPositionOfAWord(String word) {
        return fileRepository.getLineNumberAndPositionOfAWord(word);
    }

    /**
     * @param word, takes the word to be searched
     * @return
     * returns the list of line number and position of a word in all the files
     */
    @Override
    public Map<String, List<Map<String, Integer>>> searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(String word, String directoryPath) {
        return fileRepository.searchWordInDirectoryAndSubdirectoriesAndGetLineNumberAndPosition(word, directoryPath);
    }
}
