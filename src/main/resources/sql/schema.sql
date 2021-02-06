    CREATE TABLE workday
    (
        id             BIGSERIAL PRIMARY KEY,
        started_at     TIMESTAMP NOT NULL,
        paused_at      TIMESTAMP,
        returned_at    TIMESTAMP,
        ended_at       TIMESTAMP,
        seconds_worked BIGINT NOT NULL DEFAULT 0
    );

    CREATE TABLE time_allocation
    (
        id                BIGSERIAL PRIMARY KEY,
        workday_id        BIGINT,
        description       VARCHAR,
        seconds_allocated BIGINT NOT NULL,

        CONSTRAINT FK_workday_time_allocation FOREIGN KEY (workday_id)
            REFERENCES workday (id)
    );