#DROP TABLE IF EXISTS usermessages.user;

CREATE TABLE usermessages.user (
    id BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(25)     NOT NULL,
    password VARCHAR(255)    NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

#DROP TABLE IF EXISTS usermessages.user;

CREATE TABLE usermessages.messages (
    id      BIGINT AUTO_INCREMENT NOT NULL,
    user_id BIGINT                NULL,
    message VARCHAR(255)          NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);
ALTER TABLE usermessages.messages
    ADD CONSTRAINT FK_MESSAGES_ON_NAME FOREIGN KEY (user_id) REFERENCES usermessages.user (id);
