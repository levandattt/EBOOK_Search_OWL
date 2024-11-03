-- Users Table to store basic login information
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       uuid CHAR(36) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- User Profile Table to store additional user information
CREATE TABLE user_profiles (
                               user_id INT PRIMARY KEY,
                               full_name VARCHAR(100),
                               gender ENUM('male', 'female', 'other') DEFAULT NULL,
                               date_of_birth DATE,
                               setup BOOLEAN DEFAULT FALSE,
                               uuid CHAR(36) NOT NULL UNIQUE,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Roles Table to define available roles
CREATE TABLE roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       role_name VARCHAR(50) NOT NULL UNIQUE,
                       description VARCHAR(255),
                       uuid CHAR(36) NOT NULL UNIQUE
);

-- User Roles Table to link users with roles (Many-to-Many relationship)
CREATE TABLE user_roles (
                            user_id INT,
                            role_id INT,
                            PRIMARY KEY (user_id, role_id),
                            uuid CHAR(36) NOT NULL UNIQUE,
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Token Table to manage user tokens
CREATE TABLE tokens (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        access_token VARCHAR(500) NOT NULL,
                        uuid CHAR(36) NOT NULL UNIQUE,
                        expiration_timestamp BIGINT NOT NULL,  -- Unix timestamp format
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);