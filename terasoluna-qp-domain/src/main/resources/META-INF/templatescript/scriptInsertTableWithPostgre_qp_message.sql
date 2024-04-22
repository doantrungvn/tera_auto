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