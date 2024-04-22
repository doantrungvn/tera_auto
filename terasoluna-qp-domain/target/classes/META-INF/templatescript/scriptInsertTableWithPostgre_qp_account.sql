CREATE SEQUENCE qp_account_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 2
  CACHE 1;
ALTER TABLE qp_account_seq
  OWNER TO postgres;

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


