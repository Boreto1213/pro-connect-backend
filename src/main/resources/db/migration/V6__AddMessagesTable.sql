CREATE TABLE messages (
    id varchar(255),
    text varchar(1000) NOT NULL,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    timestamp DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);