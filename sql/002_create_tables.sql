CREATE TABLE IF NOT EXISTS "user" (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS task (
    id BIGSERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    deadline_date DATE NOT NULL,
    category VARCHAR(255),
    is_done BOOLEAN NOT NULL DEFAULT FALSE,
    priority INTEGER NOT NULL CHECK (priority BETWEEN 1 AND 3),
    user_id BIGINT NOT NULL,

    CONSTRAINT fk_task_user
        FOREIGN KEY (user_id)
        REFERENCES "user" (id)
        ON DELETE CASCADE
);
