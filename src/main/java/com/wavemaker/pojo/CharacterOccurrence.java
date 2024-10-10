package com.wavemaker.pojo;

public class CharacterOccurrence implements Comparable<CharacterOccurrence> {
    private String filePath;
    private String fileName;
    private char character;
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

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
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
    public int compareTo(CharacterOccurrence other) {
        return this.fileName.compareToIgnoreCase(other.fileName);
    }

}
