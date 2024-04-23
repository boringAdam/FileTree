package com.filetree.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@JsonSerialize
@Getter
@Setter
@Table(name = "log_entry")

public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String user;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "data")
    private String data;

    @Column(name = "result")
    private String result;


    public LogEntry() {
        // default
    }

    public LogEntry(String user, LocalDateTime time, String data, String result) {
        this.user = user;
        this.time = time;
        this.data = data;
        this.result = result;
    }

}
