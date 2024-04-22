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


