package com.wavemaker.util;

import com.wavemaker.exception.FileCreationException;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static File createFileIfNotExists(String FILE_PATH) throws FileCreationException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new FileCreationException("Failed to create a new file at path: " + FILE_PATH, 500);
                }
            } catch (IOException exception) {
                throw new FileCreationException("An error occurred while creating the file at path: " + FILE_PATH, 500); //500 = internal server error
            }
        }
        return file;
    }
}