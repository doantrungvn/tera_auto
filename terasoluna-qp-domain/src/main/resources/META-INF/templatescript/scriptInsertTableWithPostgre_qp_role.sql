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


