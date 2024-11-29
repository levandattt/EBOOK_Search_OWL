package com.ebook_searching.storage.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "files", indexes = {
        @Index(name = "idx_file_path", columnList = "filePath")
})
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, length = 512)
    private String filePath;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, length = 255)
    private String contentType;

    @Column(nullable = false, length = 255)
    private boolean isConfirmed;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Metadata> metadata = new HashSet<>();

    public void addMetadata(Metadata metadata) {
        this.metadata.add(metadata);
        metadata.setFile(this);
    }

    public void removeMetadata(Metadata metadata) {
        this.metadata.remove(metadata);
        metadata.setFile(null);
    }
}
