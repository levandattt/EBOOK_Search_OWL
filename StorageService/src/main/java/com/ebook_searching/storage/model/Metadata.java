package com.ebook_searching.storage.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "metadata", indexes = {
        @Index(name = "idx_file_id", columnList = "file_id")
})
@Getter
@Setter
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @Column(nullable = false, length = 255)
    private String value;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
