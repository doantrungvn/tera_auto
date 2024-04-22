CREATE TABLE qp_language
(
	language_code character varying(50) NOT NULL,
	language_name character varying(400) NOT NULL,
	country_code character varying(50) NOT NULL,
	region_code character varying(50),
	CONSTRAINT qp_language_pkey PRIMARY KEY (language_code, country_code)
);
	


