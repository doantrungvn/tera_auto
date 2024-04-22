CREATE TABLE qp_account_theme
(
	account_theme_id NUMBER(16,0) NOT NULL,
	account_id NUMBER(16,0) NOT NULL,
	code NVARCHAR2(150),
	value_ NVARCHAR2(50),
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	CONSTRAINT qp_account_theme_pkey PRIMARY KEY (account_theme_id),
	CONSTRAINT qp_acc_theme_account_id_fkey FOREIGN KEY (account_id)
			REFERENCES qp_account (account_id)
);

CREATE SEQUENCE qp_account_theme_seq INCREMENT BY 1 START WITH 5949 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER ACCOUNT_THEME_TRG 
BEFORE INSERT ON qp_account_theme
FOR EACH ROW
BEGIN
		if(:new.account_theme_id is null) then
			:new.account_theme_id := qp_account_theme_seq.NEXTVAL;
 	end if;
END;
/
