package com.filetree.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The LogEntry object to store the necessary logging information.
 */
@Entity
@JsonSerialize
@Getter
@Setter
@Table(name = "log_entry")
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Transient
    private List<String> sortedResult;

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
