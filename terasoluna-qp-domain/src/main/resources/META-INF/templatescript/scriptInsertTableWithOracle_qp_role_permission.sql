CREATE TABLE qp_role_permission
(
	role_id NUMBER(16,0) NOT NULL,
	permission_id NUMBER(16,0) NOT NULL,
	CONSTRAINT qp_role_permission_pkey PRIMARY KEY (role_id, permission_id)
);
ALTER TABLE qp_role_permission
ADD CONSTRAINT qp_permission_role_id_fkey FOREIGN KEY(permission_id) REFERENCES qp_permission(permission_id) ENABLE;
ALTER TABLE qp_role_permission
ADD CONSTRAINT qp_role_role_id_fkey FOREIGN KEY(role_id) REFERENCES qp_role(role_id) ENABLE;

