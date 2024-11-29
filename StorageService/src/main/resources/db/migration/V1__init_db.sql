CREATE TABLE files (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       file_name VARCHAR(255) NOT NULL,
                       file_path VARCHAR(512) NOT NULL,
                       size BIGINT NOT NULL,
                       content_type VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NULL
);

CREATE INDEX idx_file_path ON files(file_path);

CREATE TABLE metadata (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          file_id BIGINT NOT NULL,
                          value VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE
);

CREATE INDEX idx_file_id ON metadata(file_id);