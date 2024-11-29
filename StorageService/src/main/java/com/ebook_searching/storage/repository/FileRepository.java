package com.ebook_searching.storage.repository;

import com.ebook_searching.storage.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByFilePathContaining(String keyword);

    Optional<File> findByFilePath(String filePath);
}
