package com.filetree.service;

import com.filetree.dto.LogEntryDTO;
import com.filetree.model.LogEntry;
import com.filetree.repository.LogEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogEntryServiceTest {
    @InjectMocks
    LogEntryService logEntryService;
    @Mock
    LogEntryRepository logEntryRepository;
    @Test
    public void testGetAllLogEntries() {
        LogEntry logEntry1 = new LogEntry("user1", LocalDateTime.now(), "root_folder: folder1 extension: .txt", "result1");
        LogEntry logEntry2 = new LogEntry("user2", LocalDateTime.now(), "root_folder: folder2 extension: .java", "result2");
        List<LogEntry> mockEntries = Arrays.asList(logEntry1, logEntry2);

        when(logEntryRepository.findAll()).thenReturn(mockEntries);

        List<LogEntryDTO> logEntryDTOs = logEntryService.getAllLogEntries();

        assertEquals(2, logEntryDTOs.size());
    }

    @Test
    public void testSaveLogEntry() {
        LogEntryService logEntryService = new LogEntryService(logEntryRepository);
        LogEntry logEntry = new LogEntry("user1", LocalDateTime.now(), "data1", "result1");

        logEntryService.saveLogEntry(logEntry);

        verify(logEntryRepository).save(logEntry);
    }
}