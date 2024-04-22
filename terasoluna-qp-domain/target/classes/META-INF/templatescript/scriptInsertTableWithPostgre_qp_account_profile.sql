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


