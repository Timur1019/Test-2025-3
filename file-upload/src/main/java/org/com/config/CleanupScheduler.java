package org.com.config;


import org.com.service.FileService;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class CleanupScheduler {
    public static void start(FileService service) {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                service.cleanupOldFiles(Duration.ofDays(30));
            }
        }, 60_000, 60_000);
    }
}
