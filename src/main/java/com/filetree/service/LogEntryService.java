package com.filetree.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.filetree.dto.LogEntryDTO;
import com.filetree.model.LogEntry;
import com.filetree.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The LogEntryService handles the logging operations
 */
@Service
public class LogEntryService {

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public LogEntryService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    /**
     * Returns all log entries and converts them into DTOs.
     * @return Returns a list of LogEntryDTO objects representing the log entries.
     */
    public List<LogEntryDTO> getAllLogEntries() {
        List<LogEntry> logEntries = logEntryRepository.findAll();
        return logEntries.stream()
                .map(logEntry -> {
                    List<String> sortedResult = sortResult(logEntry.getResult());
                    logEntry.setSortedResult(sortedResult);

                    ObjectMapper mapper = new ObjectMapper();
                    ObjectNode jsonData = mapper.createObjectNode();
                    Arrays.stream(logEntry.getData().split(", "))
                            .map(kv -> kv.split(": "))
                            .forEach(kv -> jsonData.put(kv[0], kv[1]));

                    return new LogEntryDTO(logEntry.getUser(), logEntry.getTime(), jsonData, sortedResult);
                })
                .collect(Collectors.toList());
    }

    /**
     * Saves a log entry to the database.
     * @param logEntry The logEntry object to be saved.
     */
    public void saveLogEntry(LogEntry logEntry) {
        logEntryRepository.save(logEntry);
    }

    /**
     * Sorts the result string of a log entry in natural order.
     * @param result The result string to be sorted.
     * @return Returns a list of strings sorted in order.
     */
    private List<String> sortResult(String result) {
        List<String> resultList = new ArrayList<>(Arrays.asList(result.split(",")));

        resultList.replaceAll(s -> s.trim().replaceAll("\\[|\\]", ""));
        resultList.sort(Comparator.naturalOrder());

        return resultList;
    }

}
