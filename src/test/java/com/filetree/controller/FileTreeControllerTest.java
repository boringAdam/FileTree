package com.filetree.controller;

import com.filetree.dto.LogEntryDTO;
import com.filetree.service.FileService;
import com.filetree.service.LogEntryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class FileTreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @MockBean
    private LogEntryService logEntryService;

    @Test
    public void testGetUniqueFiles() throws Exception {
        Set<String> files = new HashSet<>();
        files.add("file1.txt");
        files.add("file2.txt");

        when(fileService.findFiles("rootFolder", "txt")).thenReturn(files);

        mockMvc.perform(MockMvcRequestBuilders.get("/getunique")
                        .param("rootFolder", "rootFolder")
                        .param("extension", "txt"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(files.size()));
    }

    @Test
    public void testGetHistory() throws Exception {
        List<LogEntryDTO> logEntries = new ArrayList<>();
        LogEntryDTO logEntry1 = new LogEntryDTO("user1", LocalDateTime.now(), null, null);
        LogEntryDTO logEntry2 = new LogEntryDTO("user2", LocalDateTime.now(), null, null);
        logEntries.add(logEntry1);
        logEntries.add(logEntry2);

        when(logEntryService.getAllLogEntries()).thenReturn(logEntries);

        mockMvc.perform(MockMvcRequestBuilders.get("/history"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(logEntries.size()));
    }

    @Test
    public void testGenerateFileStructure() throws Exception {
        doNothing().when(fileService).createFileStructure(any(), anyInt());

        mockMvc.perform(MockMvcRequestBuilders.get("/gen"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("File structure generated successfully."));
    }

    @Test
    public void testGetDocumentation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/doc"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

}