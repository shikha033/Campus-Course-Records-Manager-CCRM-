
package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Backup service that creates a timestamped folder and copies files into it
 */
public class BackupService {
    private final Path base;
    public BackupService(Path base) { this.base = base; }

    public Path backupFolder() throws IOException {
        String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path target = base.resolve("backup_" + stamp);
        Files.createDirectories(target);
        return target;
    }

    public void copyFile(Path src, Path destDir) throws IOException {
        Files.copy(src, destDir.resolve(src.getFileName()), StandardCopyOption.REPLACE_EXISTING);
    }
}
