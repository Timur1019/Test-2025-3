package org.com.controller;

import io.javalin.http.Context;
import org.com.service.FileService;

import java.io.IOException;


public class FileController {
    private final FileService service;

    public FileController(FileService service) {
        this.service = service;
    }

    public void handleUpload(Context ctx) {
        var file = ctx.uploadedFile("file");
        if (file == null) {
            ctx.status(400).result("No file uploaded");
            return;
        }

        try {
            var id = service.saveFile(file.content());
            ctx.result("http://localhost:7070/download/" + id);
        } catch (Exception e) {
            ctx.status(500).result("Upload failed");
        }
    }

    public void handleDownload(Context ctx) {
        var id = ctx.pathParam("id");

        try {
            ctx.result(service.downloadFile(id));
        } catch (IOException e) {
            ctx.status(404).result("File not found");
        }
    }

    public void handleStats(Context ctx) {
        ctx.json(service.getStats());
    }
}
