CREATE TABLE qp_role_permission
(
	role_id numeric(16,0) NOT NULL,
	permission_id numeric(16,0) NOT NULL,
	CONSTRAINT qp_role_permission_pkey PRIMARY KEY (role_id, permission_id),
	CONSTRAINT qp_permission_role_id_fkey FOREIGN KEY (permission_id)
			REFERENCES qp_permission (permission_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT qp_role_role_id_fkey FOREIGN KEY (role_id)
			REFERENCES qp_role (role_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT
);

