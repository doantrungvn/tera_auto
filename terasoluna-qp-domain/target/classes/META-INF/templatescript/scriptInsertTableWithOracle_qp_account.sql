CREATE TABLE qp_account
(
	account_id NUMBER(16,0) NOT NULL,
	username	NVARCHAR2(200) NOT NULL,
	password_	NVARCHAR2(200) NOT NULL,
	account_non_expired NUMBER(1) DEFAULT '1',
	account_non_locked NUMBER(1) DEFAULT '1',
	credentials_non_expired NUMBER(1) DEFAULT '1',
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	account_rule_definition_id NUMBER(16,0),
	force_change_password NUMBER(1) DEFAULT '0',
	CONSTRAINT qp_account_pkey PRIMARY KEY (account_id)
);

CREATE SEQUENCE qp_account_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER ACCOUNT_ID_TRG 
BEFORE INSERT ON qp_account
FOR EACH ROW
BEGIN
		if(:new.account_id is null) then
		 	:new.account_id := qp_account_seq.NEXTVAL;
 	end if;
END;
/
