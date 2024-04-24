package com.filetree.service;

import com.filetree.model.LogEntry;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class FileService {

    private final LogEntryService logEntryService;

    @Autowired
    public FileService(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    public Set<String> findFiles(String rootFolder, String extension) {
        Set<String> filenames = new HashSet<>();
        searchFiles(new File(rootFolder), extension, filenames);

        String user = executeWhoAmICommand();
        logRequest(user, LocalDateTime.now(), "root_older: " + rootFolder + ", extension: " + extension ,filenames.toString());

        return filenames;
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

    private void searchFiles(File rootFolder, String extension, Set<String> filenames) {
        File[] files = rootFolder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(extension)) {
                    filenames.add(file.getName());
                } else if (file.isDirectory()) {
                    searchFiles(file, extension, filenames);
                }
            }
        }
    }

    public void createFileStructure(File folder, int depth) throws IOException {
        Random random = new Random();

        if (random.nextBoolean()) {
            char fileNameLetter = (char) (random.nextInt(26) + 'a');
            File file = new File(folder, fileNameLetter + ".txt");
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
