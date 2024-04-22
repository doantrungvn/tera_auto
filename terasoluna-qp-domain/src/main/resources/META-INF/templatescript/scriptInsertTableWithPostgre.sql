CREATE TABLE qp_language
(
	language_code character varying(50) NOT NULL,
	language_name character varying(400) NOT NULL,
	country_code character varying(50) NOT NULL,
	region_code character varying(50),
	CONSTRAINT qp_language_pkey PRIMARY KEY (language_code, country_code)
);

CREATE SEQUENCE qp_message_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 1
	CACHE 20;
	
CREATE TABLE qp_message
(
	message_id bigint NOT NULL DEFAULT nextval('qp_message_seq'::regclass),
	language_code character varying(50) NOT NULL,
	message_code character varying(200) NOT NULL,
	message_string character varying(400) NOT NULL,
	country_code character varying(50) NOT NULL,
	remark character varying(400),
	module_resource character varying(200),
	CONSTRAINT qp_message_pk PRIMARY KEY (language_code, message_code, country_code),
	--CONSTRAINT qp_message_pkey PRIMARY KEY (message_id),
	CONSTRAINT qp_language_fk FOREIGN KEY (language_code, country_code)
			REFERENCES qp_language (language_code, country_code) MATCH SIMPLE
			ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE qp_permission_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 1
	CACHE 1;
	
CREATE TABLE qp_permission
(
	permission_id numeric(16,0) NOT NULL DEFAULT nextval('qp_permission_seq'::regclass),
	permission_name character varying(400) NOT NULL,
	permission_code character varying(200) NOT NULL,
	sort_key numeric(3,0) NOT NULL,
	remark character varying(2000),
	action_path character varying(200) NOT NULL,
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	module_code character varying(150),
	CONSTRAINT qp_permission_pkey PRIMARY KEY (permission_id)
);

CREATE SEQUENCE qp_account_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 2
	CACHE 1;
		
CREATE TABLE qp_account
(
	account_id numeric(16,0) NOT NULL DEFAULT nextval('qp_account_seq'::regclass),
	username character varying(200) NOT NULL,
	password_ character varying(200) NOT NULL,
	account_non_expired boolean DEFAULT true,
	account_non_locked boolean DEFAULT true,
	credentials_non_expired boolean DEFAULT true,
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	account_rule_definition_id numeric(16,0),
	force_change_password boolean DEFAULT false,
	CONSTRAINT qp_account_pkey PRIMARY KEY (account_id)
)
WITH (
	OIDS=FALSE
);
ALTER TABLE qp_account
	OWNER TO postgres;

CREATE SEQUENCE qp_role_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 2
	CACHE 1;
	
CREATE TABLE qp_role
(
	role_id numeric(16,0) NOT NULL DEFAULT nextval('qp_role_seq'::regclass),
	role_name character varying(200) NOT NULL,
	role_code character varying(150) NOT NULL,
	status integer NOT NULL,
	remark character varying(2000),
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	CONSTRAINT qp_role_pkey PRIMARY KEY (role_id)
);

CREATE TABLE qp_role_permission
(
	role_id numeric(16,0) NOT NULL,
	permission_id numeric(16,0) NOT NULL,
	CONSTRAINT qp_role_permission_pkey PRIMARY KEY (role_id, permission_id),
	CONSTRAINT qp_permission_role_id_fkey FOREIGN KEY (permission_id)
			REFERENCES qp_permission (permission_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT qp_role_role_id_fkey FOREIGN KEY (role_id)
			REFERENCES qp_role (role_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE qp_account_role
(
	role_id numeric(16,0) NOT NULL,
	account_id numeric(16,0) NOT NULL,
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	CONSTRAINT qp_account_role_pkey PRIMARY KEY (role_id, account_id),
	CONSTRAINT qp_account_account_id_fkey FOREIGN KEY (account_id)
			REFERENCES qp_account (account_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT qp_account_role_id_fkey FOREIGN KEY (role_id)
			REFERENCES qp_role (role_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE TABLE qp_account_profile
(
	account_id numeric(16,0) NOT NULL,
	integer_format character varying(50),
	float_format character varying(50),
	date_format character varying(50),
	time_format character varying(50),
	datetime_format character varying(50),
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	currency_format character varying(50),
	currency_code character varying(5),
	pagesize_value character varying(2000),
	default_language character varying(50),
	session_timeout smallint,
	proxy_host character varying(50),
	proxy_port character varying(10),
	proxy_user character varying(50),
	proxy_password character varying(50),
	bing_client_id character varying(50),
	bing_client_secret character varying(50),
	proxy_level smallint,
	interval_reload integer,
	connection_flg smallint,
	currency_code_position character varying(5),
	CONSTRAINT qp_account_profile_pkey PRIMARY KEY (account_id)
);

COMMENT ON COLUMN qp_account_profile.proxy_level IS '0:None
1:System
2:Manual';
COMMENT ON COLUMN qp_account_profile.connection_flg IS '0: system
1: manual';

CREATE SEQUENCE qp_account_rule_definition_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 25
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

CREATE SEQUENCE qp_account_login_attempt_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 1
	CACHE 1;

		
CREATE TABLE qp_account_login_attempt
(
	account_login_attempt_id numeric(16,0) NOT NULL DEFAULT nextval('qp_account_login_attempt_seq'::regclass),
	account_id numeric(16,0),
	generations_count smallint,
	login_continuous_failure_count smallint,
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	last_login timestamp with time zone DEFAULT timezone('utc'::text, now()),
	CONSTRAINT qp_account_login_attempt_pk PRIMARY KEY (account_login_attempt_id)
)
WITH (
	OIDS=FALSE
);
ALTER TABLE qp_account_login_attempt
	OWNER TO postgres;

CREATE TABLE qp_account_permission
(
	permission_id numeric(16,0) NOT NULL,
	account_id numeric(16,0) NOT NULL,
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	CONSTRAINT qp_account_permission_pkey PRIMARY KEY (permission_id, account_id),
	CONSTRAINT qp_account_permission_account_id_fkey FOREIGN KEY (account_id)
			REFERENCES qp_account (account_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT qp_account_permission_permission_id_fkey FOREIGN KEY (permission_id)
			REFERENCES qp_permission (permission_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE SEQUENCE qp_account_theme_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 5949
	CACHE 1;
	
CREATE TABLE qp_account_theme
(
	account_theme_id numeric(16,0) NOT NULL DEFAULT nextval('qp_account_theme_seq'::regclass),
	account_id numeric(16,0) NOT NULL,
	code character varying(150),
	value_ character varying(50),
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	CONSTRAINT qp_account_theme_pkey PRIMARY KEY (account_theme_id),
	CONSTRAINT qp_account_theme_account_id_fkey FOREIGN KEY (account_id)
			REFERENCES qp_account (account_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT
);

CREATE SEQUENCE qp_resources_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9999999999999999
	START 2655
	CACHE 1;

CREATE TABLE qp_resources
(
	resource_id bigint NOT NULL DEFAULT nextval('qp_resources_seq'::regclass),
	category_cd character varying(150) NOT NULL,
	resource_cd character varying(150) NOT NULL,
	value1 character varying(500) NOT NULL,
	value2 character varying(500),
	value3 character varying(500),
	value4 character varying(500),
	value5 character varying(500),
	resource_type character varying(1),
	is_default character varying(1),
	delete_flg character varying(1),
	CONSTRAINT qp_resources_pkey PRIMARY KEY (resource_id)
);

