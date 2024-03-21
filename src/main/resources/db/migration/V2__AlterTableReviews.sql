ALTER TABLE reviews
    ADD COLUMN service_id INT,
ADD CONSTRAINT fk_service_id
    FOREIGN KEY (service_id)
    REFERENCES services(id);