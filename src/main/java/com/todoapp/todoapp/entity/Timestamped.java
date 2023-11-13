package com.todoapp.todoapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Timestamped {

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;


}
