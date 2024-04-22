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