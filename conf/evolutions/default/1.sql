# Users restaurants

# --- !Ups

CREATE TABLE RESTAURANT (
    ID SERIAL,
    USER_TYPE_ID varchar(255) NOT NULL,
    RESTAURANT_NAME varchar(255) NOT NULL,
    PRIMARY KEY (ID)
);
CREATE INDEX RESTAURANT_USER_TYPE_ID_INDEX ON RESTAURANT (USER_TYPE_ID);

# --- !Downs

DROP TABLE RESTAURANT;