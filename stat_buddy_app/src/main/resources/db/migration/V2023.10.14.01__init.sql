CREATE TABLE actor
(
    id   uuid         NOT NULL,
    code varchar(512) NOT NULL,
    CONSTRAINT actor_code_unique UNIQUE (code),
    CONSTRAINT actor_pk PRIMARY KEY (id)
);

CREATE TABLE region
(
    id       uuid         NOT NULL,
    code     varchar(512) NOT NULL,
    actor_id uuid         NOT NULL,
    CONSTRAINT region_pk PRIMARY KEY (id),
    CONSTRAINT region_code_unique UNIQUE (code)
);

CREATE TABLE stat_layer
(
    id          uuid         NOT NULL,
    code        varchar(512) NOT NULL,
    description text         NOT NULL,
    CONSTRAINT stat_layer_code_unique UNIQUE (code),
    CONSTRAINT stat_layer_pk PRIMARY KEY (id)
);
CREATE TABLE statistical_indicator
(
    id            uuid         NOT NULL,
    code          varchar(512) NOT NULL,
    stat_layer_id uuid         NOT NULL,
    description   text         NOT NULL,
    unit_id       uuid         NOT NULL,
    period_id     uuid         NOT NULL,
    CONSTRAINT statistical_indicator_pk PRIMARY KEY (id)
);

CREATE TABLE unit
(
    id          uuid         NOT NULL,
    code        varchar(512) NOT NULL,
    description text         NOT NULL,
    CONSTRAINT unit_code_unique UNIQUE (code),
    CONSTRAINT unit_pk PRIMARY KEY (id)
);

CREATE TABLE period
(
    id          uuid         NOT NULL,
    code        varchar(512) NOT NULL,
    description varchar(512) NOT NULL,
    CONSTRAINT code UNIQUE (code),
    CONSTRAINT period_pk PRIMARY KEY (id)
);

CREATE TABLE region_indicator
(
    id                       uuid         NOT NULL,
    region_id                uuid         NOT NULL,
    statistical_indicator_id uuid         NOT NULL,
    value                    varchar(512) NOT NULL,
    CONSTRAINT region_indicator_pk PRIMARY KEY (id)
);

ALTER TABLE region
    ADD CONSTRAINT actor_fk FOREIGN KEY (actor_id)
        REFERENCES actor (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE statistical_indicator
    ADD CONSTRAINT stat_layer_fk FOREIGN KEY (stat_layer_id)
        REFERENCES stat_layer (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE statistical_indicator
    ADD CONSTRAINT unit_fk FOREIGN KEY (unit_id)
        REFERENCES unit (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE statistical_indicator
    ADD CONSTRAINT period_fk FOREIGN KEY (period_id)
        REFERENCES period (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE region_indicator
    ADD CONSTRAINT region_fk FOREIGN KEY (region_id)
        REFERENCES region (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE region_indicator
    ADD CONSTRAINT statistical_indicator_fk FOREIGN KEY (statistical_indicator_id)
        REFERENCES statistical_indicator (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;


