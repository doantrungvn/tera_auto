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