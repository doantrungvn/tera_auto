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

