package com.filetree.controller;

import com.filetree.model.LogEntry;
import com.filetree.dto.LogEntryDTO;
import com.filetree.service.LogEntryService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.time.LocalDateTime;

@RestController
public class FileTreeController {

    private final LogEntryService logEntryService;

    @Autowired
    public FileTreeController(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    @GetMapping("/getunique")
    public Set<String> findFiles(@RequestParam String rootFolder, @RequestParam String extension) {
        Set<String> filenames = new HashSet<>();
        findFiles(new File(rootFolder), extension, filenames);

        String user = executeWhoAmICommand();
        logRequest(user, LocalDateTime.now(), "Root Folder: " + rootFolder + ", Extension: " + extension , "Result: " + filenames);
        System.out.println("Request logged!");

        return filenames;
    }

    @GetMapping("/history")
    public List<LogEntryDTO> getHistory() {
        return logEntryService.getAllLogEntries();
    }

    @GetMapping("/gen")
    public String generateFileStructure() {
        File rootFolder = new File("generated_root");

        if (rootFolder.exists()) {
            try {
                FileUtils.deleteDirectory(rootFolder);
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to delete existing folder.";
            }
        }

        rootFolder.mkdirs();

        try {
            createFileStructure(rootFolder, 0);
            return "File structure generated successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to generate file structure.";
        }
    }

    private String executeWhoAmICommand() {
        try {
            Process process = new ProcessBuilder("whoami").start();
            process.waitFor();
            java.util.Scanner scanner = new java.util.Scanner(process.getInputStream()).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next().trim() : "Unknown";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    private void findFiles(File rootFolder, String extension, Set<String> filenames) {
        File[] files = rootFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(extension)) {
                    filenames.add(file.getName());
                } else if (file.isDirectory()) {
                    findFiles(file, extension, filenames);
                }
            }
        }
    }

    private void createFileStructure(File folder, int depth) throws IOException {
        Random random = new Random();

        if (random.nextBoolean()) {
            int fileNameNumber = random.nextInt(10) + 1;
            File file = new File(folder, fileNameNumber + ".txt");
            file.createNewFile();
        }

        int numFolders = random.nextInt(4);
        if (depth < 10 && numFolders > 0) {
            for (int i = 1; i <= numFolders; i++) {
                File subFolder = new File(folder, "Folder" + i);
                subFolder.mkdirs();
                createFileStructure(subFolder, depth + 1);
            }
        }
    }

    private void logRequest(String user, LocalDateTime time, String data, String result) {
        LogEntry logEntry = new LogEntry(user, time, data, result);
        logEntryService.saveLogEntry(logEntry);
    }

}
