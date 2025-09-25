
package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;

/** Recursively compute directory size and list files by depth */
public class RecursionUtil {

    public static long directorySize(Path p) throws IOException {
        if (!Files.exists(p)) return 0;
        return Files.walk(p).filter(Files::isRegularFile).mapToLong(pp -> {
            try { return Files.size(pp); } catch (Exception e) { return 0L; }
        }).sum();
    }

    public static void listByDepth(Path p, int depth) throws IOException {
        listByDepthInternal(p, 0, depth);
    }

    private static void listByDepthInternal(Path p, int curDepth, int maxDepth) throws IOException {
        if (!Files.exists(p) || curDepth > maxDepth) return;
        Files.list(p).forEach(pp -> System.out.println("  ".repeat(curDepth) + pp.getFileName()));
        for (Path sub : Files.list(p).filter(Files::isDirectory).toArray(Path[]::new)) {
            listByDepthInternal(sub, curDepth+1, maxDepth);
        }
    }
}
