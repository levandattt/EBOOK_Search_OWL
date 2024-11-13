ALTER TABLE books
DROP COLUMN genres;

CREATE TABLE genres (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    image       LONGTEXT NULL,
    uuid        CHAR(36) NOT NULL,
    created_at  DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT uq_genre_name UNIQUE (name),
    CONSTRAINT uq_genre_uuid UNIQUE (uuid)
);

CREATE TABLE book_genres (
     book_id BIGINT NOT NULL,
     genre_id BIGINT NOT NULL,
     PRIMARY KEY (book_id, genre_id),
     CONSTRAINT fk_book_genres_book FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
     CONSTRAINT fk_book_genres_genre FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE CASCADE
);
