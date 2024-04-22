CREATE SEQUENCE qp_account_rule_definition_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 1
	CACHE 1;
ALTER TABLE qp_account_rule_definition_seq
	OWNER TO postgres;
		
CREATE TABLE qp_account_rule_definition
(
	account_rule_definition_id numeric(16,0) NOT NULL DEFAULT nextval('qp_account_rule_definition_seq'::regclass),
	account_rule_definition_code character varying(200),
	account_rule_definition_name character varying(400),
	range_of_string_minimum smallint,
	range_of_string_maximum smallint,
	characters_type_upper boolean,
	characters_type_lower boolean,
	characters_type_numeric boolean,
	generations_count smallint,
	life_time smallint,
	login_continuous_failure_count smallint,
	account_lock_time smallint,
	initial_password boolean,
	initial_password_force_change boolean,
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	CONSTRAINT qp_account_rule_definition_pk PRIMARY KEY (account_rule_definition_id)
);


