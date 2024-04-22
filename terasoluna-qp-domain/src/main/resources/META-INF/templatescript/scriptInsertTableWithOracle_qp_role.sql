CREATE TABLE qp_role
(
	role_id NUMBER(16,0) NOT NULL,
	role_name NVARCHAR2(200) NOT NULL,
	role_code NVARCHAR2(150) NOT NULL,
	status NUMBER(10) NOT NULL,
	remark NVARCHAR2(2000),
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	CONSTRAINT qp_role_pkey PRIMARY KEY (role_id)
);

CREATE SEQUENCE qp_role_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER ROLE_ID_TRG 
BEFORE INSERT ON qp_role
FOR EACH ROW
BEGIN
		if(:new.role_id is null) then
		 	:new.role_id := qp_role_seq.NEXTVAL;
 	end if;
END;
/
