CREATE TABLE qp_language
(
	language_code NVARCHAR2(50) NOT NULL,
	language_name NVARCHAR2(400) NOT NULL,
	country_code NVARCHAR2(50) NOT NULL,
	region_code NVARCHAR2(50),
	CONSTRAINT qp_language_pkey PRIMARY KEY (language_code, country_code)
);

CREATE TABLE qp_message
(
	message_id NUMBER(19,0) NOT NULL,
	language_code NVARCHAR2(50) NOT NULL,
	message_code NVARCHAR2(200) NOT NULL,
	message_string NVARCHAR2(400) NOT NULL,
	country_code NVARCHAR2(50) NOT NULL,
	remark NVARCHAR2(400),
	module_resource NVARCHAR2(200),
	--CONSTRAINT qp_message_pk PRIMARY KEY (message_id)
	CONSTRAINT qp_message_pk PRIMARY KEY (language_code, message_code, country_code)
);
ALTER TABLE qp_message
ADD CONSTRAINT qp_language_fk FOREIGN KEY(language_code, country_code) REFERENCES qp_language(language_code, country_code) ENABLE;

CREATE SEQUENCE qp_message_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER MESSAGE_ID_TRG 
BEFORE INSERT ON qp_message
FOR EACH ROW
BEGIN
		if(:new.message_id is null) then
		 	:new.message_id:= qp_message_seq.NEXTVAL;
 	end if;
END;
/

CREATE TABLE qp_permission
(
	permission_id NUMBER(16,0) NOT NULL,
	permission_name NVARCHAR2(400) NOT NULL,
	permission_code NVARCHAR2(200) NOT NULL,
	sort_key NUMBER(3,0) NOT NULL,
	remark NVARCHAR2(2000),
	action_path NVARCHAR2(200) NOT NULL,
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE ,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE ,
	module_code NVARCHAR2(150),
	CONSTRAINT qp_permission_pkey PRIMARY KEY (permission_id)
);

CREATE SEQUENCE qp_permission_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER PERMISSION_ID_TRG 
BEFORE INSERT ON qp_permission
FOR EACH ROW
BEGIN
		if(:new.permission_id is null) then
		 	:new.permission_id:= qp_permission_seq.NEXTVAL;
 	end if;
END;
/

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

CREATE SEQUENCE qp_account_seq INCREMENT BY 1 START WITH 2 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER ACCOUNT_ID_TRG 
BEFORE INSERT ON qp_account
FOR EACH ROW
BEGIN
		if(:new.account_id is null) then
		 	:new.account_id := qp_account_seq.NEXTVAL;
 	end if;
END;
/

CREATE TABLE qp_role
(
	role_id NUMBER(16,0) NOT NULL,
	role_name NVARCHAR2(200) NOT NULL,
	role_code NVARCHAR2(150) NOT NULL,
	status NUMBER(10) NOT NULL,
	remark NVARCHAR2(2000),
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	CONSTRAINT qp_role_pkey PRIMARY KEY (role_id)
);

CREATE SEQUENCE qp_role_seq INCREMENT BY 1 START WITH 2 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER ROLE_ID_TRG 
BEFORE INSERT ON qp_role
FOR EACH ROW
BEGIN
		if(:new.role_id is null) then
		 	:new.role_id := qp_role_seq.NEXTVAL;
 	end if;
END;
/
 
CREATE TABLE qp_role_permission
(
	role_id NUMBER(16,0) NOT NULL,
	permission_id NUMBER(16,0) NOT NULL,
	CONSTRAINT qp_role_permission_pkey PRIMARY KEY (role_id, permission_id)
);
ALTER TABLE qp_role_permission
ADD CONSTRAINT qp_permission_role_id_fkey FOREIGN KEY(permission_id) REFERENCES qp_permission(permission_id) ENABLE;
ALTER TABLE qp_role_permission
ADD CONSTRAINT qp_role_role_id_fkey FOREIGN KEY(role_id) REFERENCES qp_role(role_id) ENABLE;

CREATE TABLE qp_account_role
(
	role_id NUMBER(16,0) NOT NULL,
	account_id NUMBER(16,0) NOT NULL,
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	CONSTRAINT qp_account_role_pkey PRIMARY KEY (role_id, account_id)
);
ALTER TABLE qp_account_role
ADD CONSTRAINT qp_account_account_id_fkey FOREIGN KEY(account_id) REFERENCES qp_account(account_id) ENABLE;
ALTER TABLE qp_account_role
ADD CONSTRAINT qp_account_role_id_fkey FOREIGN KEY(role_id) REFERENCES qp_role(role_id) ENABLE;

CREATE TABLE qp_account_profile
(
	account_id NUMBER(16,0) NOT NULL,
	integer_format NVARCHAR2(50),
	float_format NVARCHAR2(50),
	date_format NVARCHAR2(50),
	time_format NVARCHAR2(50),
	datetime_format NVARCHAR2(50),
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	currency_format NVARCHAR2(50),
	currency_code NVARCHAR2(5),
	pagesize_value NVARCHAR2(2000),
	default_language NVARCHAR2(50),
	current_project_id NUMBER(16,0),
	session_timeout NUMBER(5),
	proxy_host NVARCHAR2(50),
	proxy_port NVARCHAR2(10),
	proxy_user NVARCHAR2(50),
	proxy_password NVARCHAR2(50),
	bing_client_id NVARCHAR2(50),
	bing_client_secret NVARCHAR2(50),
	proxy_level NUMBER(5),
	interval_reload NUMBER(10),
	connection_flg NUMBER(5),
	currency_code_position character varying(5),
	CONSTRAINT qp_account_profile_pkey PRIMARY KEY (account_id)
);
 
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

CREATE TABLE qp_account_permission
(
	permission_id NUMBER(16,0) NOT NULL,
	account_id NUMBER(16,0) NOT NULL,
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	CONSTRAINT qp_account_permission_pkey PRIMARY KEY (permission_id, account_id),
	CONSTRAINT qp_acc_permis_account_id_fkey FOREIGN KEY (account_id)
			REFERENCES qp_account (account_id),
	CONSTRAINT qp_acc_permis_permis_id_fkey FOREIGN KEY (permission_id)
			REFERENCES qp_permission (permission_id)
);

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

CREATE TABLE qp_resources
(
	resource_id NUMBER(19,0) NOT NULL,
	category_cd NVARCHAR2(150) NOT NULL,
	resource_cd NVARCHAR2(150) NOT NULL,
	value1 NVARCHAR2(500) NOT NULL,
	value2 NVARCHAR2(500),
	value3 NVARCHAR2(500),
	value4 NVARCHAR2(500),
	value5 NVARCHAR2(500),
	resource_type NVARCHAR2(1),
	is_default NVARCHAR2(1),
	delete_flg NVARCHAR2(1),
	CONSTRAINT qp_resources_pkey PRIMARY KEY (resource_id)
);

CREATE SEQUENCE qp_resources_seq INCREMENT BY 1 START WITH 2655 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER RESOURCE_ID_TRG 
BEFORE INSERT ON qp_resources
FOR EACH ROW
BEGIN
 	 if(:new.resource_id is null) then
		:new.resource_id := qp_resources_seq.NEXTVAL;
 	end if;
END;
/
