package com.wavemaker.service;


import java.util.List;
import java.util.Map;

public interface FileService {
    public boolean write(String content);

    public int getOccurrencesOfACharacter(char character);

    public int getOccurrencesOfAWord(String word);

    public List<Map<String, Integer>> getLineNumberAndPositionOfAWord(String word);


}
