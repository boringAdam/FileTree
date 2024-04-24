package com.filetree.controller;

import com.filetree.controller.FileTreeController;
import com.filetree.dto.LogEntryDTO;
import com.filetree.model.LogEntry;
import com.filetree.service.FileService;
import com.filetree.service.LogEntryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootTest
@AutoConfigureMockMvc
public class FileTreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    @Mock
    private LogEntryService logEntryService;

    @InjectMocks
    private FileTreeController fileTreeController;



}