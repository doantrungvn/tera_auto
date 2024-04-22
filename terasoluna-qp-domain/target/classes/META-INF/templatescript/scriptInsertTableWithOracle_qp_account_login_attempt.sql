CREATE TABLE qp_account_login_attempt
(
	account_login_attempt_id NUMBER(16,0) NOT NULL,
	account_id NUMBER(16,0),
	generations_count NUMBER(5),
	login_continuous_failure_count NUMBER(5),
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	last_login DATE DEFAULT SYSDATE,
	CONSTRAINT qp_account_login_attempt_pk PRIMARY KEY (account_login_attempt_id)
);

CREATE SEQUENCE qp_account_login_attempt_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;

CREATE OR REPLACE TRIGGER ACCOUNT_LOGIN_ATTEMPT_TRG 
BEFORE INSERT ON qp_account_login_attempt
FOR EACH ROW
BEGIN
		if(:new.account_login_attempt_id is null) then
			:new.account_login_attempt_id := qp_account_login_attempt_seq.NEXTVAL;
 	end if;
END;
/
