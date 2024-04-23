package com.filetree.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LogEntryDTO {

    private String user;
    private LocalDateTime time;
    private String data;
    private String result;

    public LogEntryDTO(String user, LocalDateTime time, String data, String result) {
        this.user = user;
        this.time = time;
        this.data = data;
        this.result = result;
    }
}
