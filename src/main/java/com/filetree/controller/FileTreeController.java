package com.filetree.controller;

import com.filetree.model.LogEntry;
import com.filetree.dto.LogEntryDTO;
import com.filetree.service.FileService;
import com.filetree.service.LogEntryService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.time.LocalDateTime;

/**
 * The FileTreeController class handles all the endpoints
 */
@Controller
public class FileTreeController {

    private final FileService fileService;
    private final LogEntryService logEntryService;

    /**
     * Constructor to initialize the FileTreeController
     * @param logEntryService The LogEntryService the to log the callings of the /getunique endpoint.
     */
    @Autowired
    public FileTreeController(LogEntryService logEntryService, FileService fileService) {
        this.fileService = fileService;
        this.logEntryService = logEntryService;
    }

    /**
     * Endpoint to retrieve a set of unique filenames matching the specified extension.
     * @param rootFolder The root folder to search.
     * @param extension  The file extension to match.
     * @return Returns with the list of the distinct filenames.
     */
    @GetMapping("/getunique")
    @ResponseBody
    public Set<String> findFiles(@RequestParam String rootFolder, @RequestParam String extension) {

        return fileService.findFiles(rootFolder, extension);
    }

    @GetMapping("/history")
    @ResponseBody
    public List<LogEntryDTO> getHistory() {

        return logEntryService.getAllLogEntries();
    }

    @GetMapping("/doc")
    public String getDocumentation(){

        return "redirect:/";
    }

    @GetMapping("/gen")
    @ResponseBody
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
            fileService.createFileStructure(rootFolder, 0);
            return "File structure generated successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to generate file structure.";
        }
    }

}
