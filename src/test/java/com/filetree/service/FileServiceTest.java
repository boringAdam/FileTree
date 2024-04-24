package com.filetree.service;

import com.filetree.repository.LogEntryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @InjectMocks
    FileService fileService;
    @Mock
    LogEntryService logEntryService;

    @Test
    public void testFindFiles() throws IOException {
        File tempDir = createTemporaryDirectory();
        createTestFiles(tempDir);

        Set<String> filenames = fileService.findFiles(tempDir.getPath(), ".txt");

        assertEquals(3, filenames.size());

        Mockito.verify(logEntryService).saveLogEntry(any());
    }

    @Test
    public void testCreateFileStructure() throws IOException {
        File tempDir = createTemporaryDirectory();

        fileService.createFileStructure(tempDir, 0);
    }

    private File createTemporaryDirectory() throws IOException {
        File tempDir = File.createTempFile("temp", Long.toString(System.nanoTime()));
        tempDir.delete();
        tempDir.mkdir();
        return tempDir;
    }

    private void createTestFiles(File directory) throws IOException {
        File txtFile1 = new File(directory, "file1.txt");
        txtFile1.createNewFile();
        File txtFile2 = new File(directory, "file2.txt");
        txtFile2.createNewFile();
        File txtFile3 = new File(directory, "file3.txt");
        txtFile3.createNewFile();
        File otherFile1 = new File(directory, "file4.doc");
        otherFile1.createNewFile();
        File otherFile2 = new File(directory, "file5.xml");
        otherFile2.createNewFile();
    }
}

