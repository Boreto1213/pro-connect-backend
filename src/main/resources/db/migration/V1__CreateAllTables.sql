CREATE TABLE users (
    id int AUTO_INCREMENT,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(50) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    phone VARCHAR(13) NOT NULL,
    city VARCHAR(30) NOT NULL,
    address VARCHAR(30) NOT NULL,
    entity_type VARCHAR(7) NOT NULL,
    bio VARCHAR(500),
    profession VARCHAR(50),
    years_of_experience int,
    likes int,
    dislikes int,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE services (
    id int AUTO_INCREMENT,
    expert_id int NOT NULL,
    description VARCHAR(1000) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (expert_id) REFERENCES users(id)
);

CREATE TABLE tags (
    id int AUTO_INCREMENT,
    text VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE service_tags (
    service_id int NOT NULL,
    tag_id int NOT NULL,
    PRIMARY KEY (service_id, tag_id),
    FOREIGN KEY (service_id) REFERENCES services(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);

CREATE TABLE reviews (
    id int AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR(1000) NOT NULL,
    rating DECIMAL(2,1) NOT NULL,
    created_by int NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(id)
);