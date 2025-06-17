package org.com.service;


import org.com.model.FileMetadata;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class FileService {
    private final Path uploadDir = Paths.get("uploads");
    private final Map<String, FileMetadata> metadataMap = new HashMap<>();

    public FileService() throws IOException {
        Files.createDirectories(uploadDir);
    }

    public String saveFile(InputStream inputStream) throws IOException {
        String id = UUID.randomUUID().toString();
        Path path = uploadDir.resolve(id);

        try (OutputStream os = Files.newOutputStream(path)) {
            inputStream.transferTo(os);
        }

        metadataMap.put(id, new FileMetadata(id, Instant.now()));
        return id;
    }

    public InputStream downloadFile(String id) throws IOException {
        Path path = uploadDir.resolve(id);
        if (!Files.exists(path)) throw new FileNotFoundException();

        metadataMap.put(id, new FileMetadata(id, Instant.now()));
        return Files.newInputStream(path);
    }

    public Map<String, FileMetadata> getStats() {
        return metadataMap;
    }

    public void cleanupOldFiles(Duration maxAge) {
        Instant threshold = Instant.now().minus(maxAge);
        Iterator<Map.Entry<String, FileMetadata>> iter = metadataMap.entrySet().iterator();

        while (iter.hasNext()) {
            var entry = iter.next();
            if (entry.getValue().lastDownloaded().isBefore(threshold)) {
                try {
                    Files.deleteIfExists(uploadDir.resolve(entry.getKey()));
                } catch (IOException ignored) {}
                iter.remove();
            }
        }
    }
}
