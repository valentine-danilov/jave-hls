package com.epam.hls.util;

import com.epam.hls.entity.RenderRule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemUtils {

    private FileSystemUtils() {
        /*Private constructor for the utility class*/
    }

    public static void prepareDirectories(File source, File outputDirectory) throws IOException {
        String outputDirectoryPath = outputDirectory.getPath();
        if (Files.notExists(Paths.get(outputDirectoryPath))) {
            Files.createDirectory(Paths.get(outputDirectory.getPath()));
        }

        String rootDirectoryPath = String.format("%s/%s", outputDirectory.getPath(), getFileName(source.getName()));
        if (Files.notExists(Paths.get(rootDirectoryPath))) {
            Files.createDirectory(Paths.get(rootDirectoryPath));
        }
    }

    public static String getFileName(String fileNameWithExtension) {
        int extensionIndex = fileNameWithExtension.lastIndexOf(".");
        return fileNameWithExtension.substring(0, extensionIndex);
    }

    public static File getTargetFile(File source, File outputDirectory, RenderRule renderRule) throws IOException {
        String sourceFileName = getFileName(source.getName());
        String outputFileName = String.format("%s_%s.ts", sourceFileName, renderRule.getHeight());
        Path outputFilePath = Files.createFile(Paths.get(String.format("%s/%s/%s", outputDirectory.getPath(), sourceFileName, outputFileName)));
        return new File(outputFilePath.toString());
    }

    public static File getTargetFile(File source, File outputDirectory, RenderRule renderRule, long segmentNumber) throws IOException {
        String sourceFileName = getFileName(source.getName());
        String outputFileName = String.format("%s_%s_%s.ts", sourceFileName, renderRule.getHeight(), segmentNumber);
        Path outputFilePath = Files.createFile(Paths.get(String.format("%s/%s/%s", outputDirectory.getPath(), sourceFileName, outputFileName)));
        return new File(outputFilePath.toString());
    }

}
