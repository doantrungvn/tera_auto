CREATE TABLE qp_message
(
	message_id NUMBER(19,0) NOT NULL,
	language_code NVARCHAR2(50) NOT NULL,
	message_code NVARCHAR2(200) NOT NULL,
	message_string NVARCHAR2(400) NOT NULL,
	country_code NVARCHAR2(50) NOT NULL,
	remark NVARCHAR2(400),
	module_resource NVARCHAR2(200),
	--CONSTRAINT qp_message_pk PRIMARY KEY (message_id)
	CONSTRAINT qp_message_pk PRIMARY KEY (language_code, message_code, country_code)
);
ALTER TABLE qp_message
ADD CONSTRAINT qp_language_fk FOREIGN KEY(language_code, country_code) REFERENCES qp_language(language_code, country_code) ENABLE;

CREATE SEQUENCE qp_message_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER MESSAGE_ID_TRG 
BEFORE INSERT ON qp_message
FOR EACH ROW
BEGIN
		if(:new.message_id is null) then
		 	:new.message_id:= qp_message_seq.NEXTVAL;
 	end if;
END;
/
