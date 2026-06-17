CREATE TABLE events
(
    id            UUID PRIMARY KEY,
    title         VARCHAR(255)             NOT NULL,
    description   TEXT                     NOT NULL,
    starts_at     TIMESTAMP WITH TIME ZONE NOT NULL,
    ends_at       TIMESTAMP WITH TIME ZONE NOT NULL,
    venue_name    VARCHAR(255)             NOT NULL,
    venue_address TEXT                     NOT NULL,
    capacity      INTEGER                  NOT NULL
        CHECK (capacity > 0),
    status        VARCHAR(50)              NOT NULL
        CHECK (
            status IN (
                       'DRAFT',
                       'PUBLISHED',
                       'CANCELLED',
                       'COMPLETED'
                )
            ),
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at    TIMESTAMP WITH TIME ZONE NOT NULL,
    CHECK (ends_at > starts_at)
);