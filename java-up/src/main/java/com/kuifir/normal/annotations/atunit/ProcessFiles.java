package com.kuifir.normal.annotations.atunit;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;

/**
 * 工具类来遍历命令行中的每个参数，以及确定它是目录还是文件，并进行相应的处理。
 * 其中包含了一个可定制的Strategy(策略)接口，因此可以应用于多种方案实现。
 *
 * @author kuifir
 * @date 2023/6/22 21:19
 */
public class ProcessFiles {
    /**
     * 一个可定制的Strategy(策略)接口，因此可以应用于多种方案实现。
     */
    public interface Strategy {
        /**
         * 一个可定制的Strategy(策略)接口，因此可以应用于多种方案实现。
         *
         * @param file
         */
        void process(File file);
    }

    private Strategy strategy;
    private String ext;

    public ProcessFiles(Strategy strategy, String ext) {
        this.strategy = strategy;
        this.ext = ext;
    }

    public void start(String[] args) {
        try {
            if (args.length == 0) {
                processDirectoryTree(new File("."));
            } else {
                for (String arg : args) {
                    File fileArg = new File(arg);
                    if (fileArg.isDirectory()) {
                        processDirectoryTree(fileArg);
                    } else {
                        // 用户可以去掉用户名
                        if (arg.endsWith("." + ext)) {
                            arg += "." + ext;
                        }
                        strategy.process(new File(arg).getCanonicalFile());
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void processDirectoryTree(File root) throws IOException {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{" + ext + "}");
        Files.walk(root.toPath())
                .filter(pathMatcher::matches)
                .forEach(p -> strategy.process(p.toFile()));
    }
}
