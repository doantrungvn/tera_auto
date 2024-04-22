CREATE TABLE qp_resources
(
	resource_id NUMBER(19,0) NOT NULL,
	category_cd NVARCHAR2(150) NOT NULL,
	resource_cd NVARCHAR2(150) NOT NULL,
	value1 NVARCHAR2(500) NOT NULL,
	value2 NVARCHAR2(500),
	value3 NVARCHAR2(500),
	value4 NVARCHAR2(500),
	value5 NVARCHAR2(500),
	resource_type NVARCHAR2(1),
	is_default NVARCHAR2(1),
	delete_flg NVARCHAR2(1),
	CONSTRAINT qp_resources_pkey PRIMARY KEY (resource_id)
);

CREATE SEQUENCE qp_resources_seq INCREMENT BY 1 START WITH 2655 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER RESOURCE_ID_TRG 
BEFORE INSERT ON qp_resources
FOR EACH ROW
BEGIN
 	 if(:new.resource_id is null) then
		:new.resource_id := qp_resources_seq.NEXTVAL;
 	end if;
END;
/
