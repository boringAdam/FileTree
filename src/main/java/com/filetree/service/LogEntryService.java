package com.filetree.service;

import com.filetree.dto.LogEntryDTO;
import com.filetree.model.LogEntry;
import com.filetree.repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogEntryService {

    private final LogEntryRepository logEntryRepository;

    @Autowired
    public LogEntryService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    public List<LogEntryDTO> getAllLogEntries() {
        List<LogEntry> logEntries = logEntryRepository.findAll();
        return logEntries.stream()
                .map(logEntry -> new LogEntryDTO(logEntry.getUser(), logEntry.getTime(), logEntry.getData(), logEntry.getResult()))
                .collect(Collectors.toList());
    }

    public void saveLogEntry(LogEntry logEntry) {
        logEntryRepository.save(logEntry);
    }
}
