package org.com.model;

import java.time.Instant;

public record FileMetadata(String id, Instant lastDownloaded) {}
