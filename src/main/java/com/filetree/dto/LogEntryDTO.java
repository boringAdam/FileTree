package com.filetree.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LogEntryDTO {

    private String user;
    private LocalDateTime time;
    private JsonNode data;
    private List<String> sortedResult;

    public LogEntryDTO(String user, LocalDateTime time, JsonNode data, List<String> sortedResult) {

        this.user = user;
        this.time = time;
        this.data = data;
        this.sortedResult = sortedResult;
    }
}
