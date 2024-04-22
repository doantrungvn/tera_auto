CREATE TABLE qp_permission
(
	permission_id NUMBER(16,0) NOT NULL,
	permission_name NVARCHAR2(400) NOT NULL,
	permission_code NVARCHAR2(200) NOT NULL,
	sort_key NUMBER(3,0) NOT NULL,
	remark NVARCHAR2(2000),
	action_path NVARCHAR2(200) NOT NULL,
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE ,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE ,
	module_code NVARCHAR2(150),
	CONSTRAINT qp_permission_pkey PRIMARY KEY (permission_id)
);

CREATE SEQUENCE qp_permission_seq INCREMENT BY 1 START WITH 1 MINVALUE 1 CACHE 20;
CREATE OR REPLACE TRIGGER PERMISSION_ID_TRG 
BEFORE INSERT ON qp_permission
FOR EACH ROW
BEGIN
		if(:new.permission_id is null) then
		 	:new.permission_id:= qp_permission_seq.NEXTVAL;
 	end if;
END;
/
