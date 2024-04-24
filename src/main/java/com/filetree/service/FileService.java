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

/**
 * The FileService handles all the file operations
 */
@Service
public class FileService {

    private final LogEntryService logEntryService;

    @Autowired
    public FileService(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    /**
     * Searches for files with the specified extension in the given root folder.
     * @param rootFolder The root folder to search.
     * @param extension The file extension to match.
     * @return Returns a set of filenames with the specified extension.
     */
    public Set<String> findFiles(String rootFolder, String extension) {
        Set<String> filenames = new HashSet<>();
        searchFiles(new File(rootFolder), extension, filenames);

        String user = executeWhoAmICommand();
        logRequest(user, LocalDateTime.now(), "root_older: " + rootFolder + ", extension: " + extension ,filenames.toString());

        return filenames;
    }

    /**
     * Executes the "whoami" command to get the current user.
     * @return Returns the username of the current user.
     */
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

    /**
     * Recursively searches for files with the specified extension in the given root folder.
     * @param rootFolder The root folder to start searching.
     * @param extension The file extension to match.
     * @param filenames A set to store the filenames matching the extension.
     */
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

    /**
     * Creates a random file structure within the specified folder with the maximum depth of 10.
     * Every folder can have 0-3 sub folders, with a 25% chance.
     * In every folder there is a 50% chance for a random txt file to spawn.
     * @param folder The folder in which to create the file structure.
     * @param depth The current depth of recursion to avoid infinite recursive calling.
     * @throws IOException In case an IO error occurs while creating the files.
     */
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

    /**
     * Logs the file search request and the results.
     * @param user The user who made the request.
     * @param time The timestamp of the request.
     * @param data The search parameters.
     * @param result The search result.
     */
    private void logRequest(String user, LocalDateTime time, String data, String result) {
        LogEntry logEntry = new LogEntry(user, time, data, result);
        logEntryService.saveLogEntry(logEntry);
    }

}
