CREATE TABLE event (
    id bigint GENERATED ALWAYS AS IDENTITY,
    aggregate_id VARCHAR(255) NOT NULL,
    aggregate_type VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    event_data JSONB NOT NULL,
    event_metadata JSONB,
    event_timestamp TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);
