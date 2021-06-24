-- Initial Database Creation Script

CREATE TABLE IF NOT EXISTS TMS_TASK (
  TASK_ID BIGINT NOT NULL PRIMARY KEY,
  USER_ID BIGINT NOT NULL,
  TASK_NAME VARCHAR(100) NOT NULL,
  DESCRIPTION VARCHAR(4000),
  DATE_TIME TIMESTAMP,
  STATUS VARCHAR(10),
  ACTIVE_FLAG SMALLINT DEFAULT 1,
  CREATION_DATE TIMESTAMP,
  LAST_UPDATE_DATE TIMESTAMP,
  VERSION BIGINT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS TMS_TASK_U1 ON TMS_TASK(TASK_ID);
CREATE INDEX IF NOT EXISTS TMS_TASK_N1 ON TMS_TASK(USER_ID);
CREATE INDEX IF NOT EXISTS TMS_TASK_N2 ON TMS_TASK(LAST_UPDATE_DATE);
CREATE INDEX IF NOT EXISTS TMS_TASK_N3 ON TMS_TASK(DATE_TIME);

CREATE TABLE IF NOT EXISTS TMS_USER (
  USER_ID BIGINT NOT NULL PRIMARY KEY,
  USER_NAME VARCHAR(100) NOT NULL,
  FIRST_NAME VARCHAR(100),
  LAST_NAME VARCHAR(100),
  ACTIVE_FLAG SMALLINT DEFAULT 1,
  CREATION_DATE TIMESTAMP,
  LAST_UPDATE_DATE TIMESTAMP,
  VERSION BIGINT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS TMS_USER_U1 ON TMS_USER(USER_ID);
CREATE UNIQUE INDEX IF NOT EXISTS TMS_USER_U2 ON TMS_USER(USER_NAME);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1