package org.com;


import io.javalin.Javalin;
import org.com.config.CleanupScheduler;
import org.com.controller.FileController;
import org.com.service.FileService;

public class FileServerApp {
    public static void main(String[] args) throws Exception {
        FileService service = new FileService();
        FileController controller = new FileController(service);

        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                });
            });
        }).start(7070);


        app.post("/upload", controller::handleUpload);
        app.get("/download/{id}", controller::handleDownload);
        app.get("/stats", controller::handleStats);

        CleanupScheduler.start(service);
    }
}
