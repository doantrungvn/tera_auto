CREATE TABLE qp_language
(
	language_code NVARCHAR2(50) NOT NULL,
	language_name NVARCHAR2(400) NOT NULL,
	country_code NVARCHAR2(50) NOT NULL,
	region_code NVARCHAR2(50),
	CONSTRAINT qp_language_pkey PRIMARY KEY (language_code, country_code)
);
