CREATE USER topicmgr
IDENTIFIED BY dev
DEFAULT TABLESPACE tbsdata
TEMPORARY TABLESPACE temp
PROFILE mgr_user
ACCOUNT UNLOCK
/

GRANT CONNECT TO topicmgr
/

ALTER USER topicmgr DEFAULT ROLE ALL
/

-- 9 System Privileges for topicmgr 
GRANT CREATE SESSION TO topicmgr
/
GRANT CREATE SEQUENCE TO topicmgr
/
GRANT CREATE TYPE TO topicmgr
/
GRANT ALTER SESSION TO topicmgr
/
GRANT CREATE MATERIALIZED VIEW TO topicmgr
/
GRANT CREATE TRIGGER TO topicmgr
/
GRANT CREATE TABLE TO topicmgr
/
GRANT CREATE PROCEDURE TO topicmgr
/
GRANT CREATE VIEW TO topicmgr
/

-- 4 Tablespace Quotas for topicmgr 
ALTER USER topicmgr QUOTA UNLIMITED ON tbsdata
/
ALTER USER topicmgr QUOTA UNLIMITED ON tbsclob
/
ALTER USER topicmgr QUOTA UNLIMITED ON tbsblob
/
ALTER USER topicmgr QUOTA UNLIMITED ON tbsidx
/

CREATE TABLE topicmgr.topic (
  id NUMBER NOT NULL
, name VARCHAR2(4000) NOT NULL
, description VARCHAR2(4000) NOT NULL
, status VARCHAR2(4000) NOT NULL
, updated_datetime DATE NOT NULL
) TABLESPACE tbsdata
/

ALTER TABLE topicmgr.topic ADD (
  CONSTRAINT topic_pk PRIMARY KEY (id)
  USING INDEX TABLESPACE tbsidx
)
/

CREATE SEQUENCE topicmgr.topic_id_seq
  START WITH 1
  MAXVALUE 9999999999
  MINVALUE 0
  NOCACHE
  ORDER
/

CREATE TABLE topicmgr.course (
  id NUMBER NOT NULL
, name VARCHAR2(4000) NOT NULL
, description VARCHAR2(4000) NOT NULL
, topic_id NUMBER NOT NULL
, status VARCHAR2(4000) NOT NULL
, updated_datetime DATE NOT NULL
) TABLESPACE tbsdata
/

ALTER TABLE topicmgr.course ADD (
  CONSTRAINT course_pk PRIMARY KEY (id)
  USING INDEX TABLESPACE tbsidx
)
/

ALTER TABLE topicmgr.course ADD (
  CONSTRAINT course_fk1
  FOREIGN KEY (topic_id)
  REFERENCES topicmgr.topic (id)
)
/

CREATE INDEX topicmgr.course_fk1 ON topicmgr.course (topic_id)
TABLESPACE tbsidx
/

CREATE SEQUENCE topicmgr.course_id_seq
  START WITH 1
  MAXVALUE 9999999999
  MINVALUE 0
  NOCACHE
  ORDER
/