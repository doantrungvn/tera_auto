CREATE TABLE qp_account_rule_definition
(
	account_rule_definition_id NUMBER(16,0) NOT NULL,
	account_rule_definition_code NVARCHAR2(200),
	account_rule_definition_name NVARCHAR2(400),
	range_of_string_minimum NUMBER(5),
	range_of_string_maximum NUMBER(5),
	characters_type_upper NUMBER(1),
	characters_type_lower NUMBER(1),
	characters_type_numeric NUMBER(1),
	generations_count NUMBER(5),
	life_time NUMBER(5),
	login_continuous_failure_count NUMBER(5),
	account_lock_time NUMBER(5),
	initial_password NUMBER(1),
	initial_password_force_change NUMBER(1),
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	CONSTRAINT qp_account_rule_definition_pk PRIMARY KEY (account_rule_definition_id)
);

CREATE SEQUENCE qp_account_rule_definition_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER ACCOUNT_RULE_DEF_ID_TRG 
BEFORE INSERT ON qp_account_rule_definition
FOR EACH ROW
BEGIN
		if(:new.account_rule_definition_id is null) then
			:new.account_rule_definition_id := qp_account_rule_definition_seq.NEXTVAL;
 	end if;
END;
/
