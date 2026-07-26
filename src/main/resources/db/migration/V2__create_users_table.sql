CREATE TABLE users
(
    id           UUID PRIMARY KEY,
    email        VARCHAR(320)             NOT NULL UNIQUE,
    display_name VARCHAR(255)             NOT NULL,
    first_name   VARCHAR(255)             NOT NULL,
    last_name    VARCHAR(255)             NOT NULL,
    role         VARCHAR(50)              NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT users_role_check
        CHECK (role IN ('MEMBER', 'ORGANISER', 'ADMIN')),

    CONSTRAINT users_updated_at_check
        CHECK (updated_at >= created_at)
);
