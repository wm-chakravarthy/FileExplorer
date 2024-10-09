package com.wavemaker.factory;

import com.wavemaker.repository.FileRepository;
import com.wavemaker.repository.impl.FileRepositoryImpl;

public class FileRepositoryFactoryInstanceHandler {
    private static volatile FileRepository fileRepository;

    private FileRepositoryFactoryInstanceHandler() {
    }

    public static FileRepository getFileRepositoryInstance(String filePath) {
        if (fileRepository == null) {
            synchronized (FileRepositoryFactoryInstanceHandler.class) {
                if (fileRepository == null) {
                    fileRepository = new FileRepositoryImpl(filePath);
                }
            }
        }
        return fileRepository;
    }
}
