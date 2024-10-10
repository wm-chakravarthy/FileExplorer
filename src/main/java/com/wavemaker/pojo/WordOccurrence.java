package com.wavemaker.pojo;

public class WordOccurrence implements Comparable<WordOccurrence> {
    private String filePath;
    private String fileName;
    private String word;
    private int lineNumber;
    private int position;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(WordOccurrence o) {
        return this.fileName.compareToIgnoreCase(o.fileName);
    }
}
