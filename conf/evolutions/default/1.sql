# Users restaurants

# --- !Ups

CREATE TABLE RESTAURANT (
    USER_TYPE_ID varchar(255) NOT NULL,
    RESTAURANT_NAME varchar(255) NOT NULL,
    PRIMARY KEY (USER_TYPE_ID)
);

# --- !Downs

DROP TABLE RESTAURANT;