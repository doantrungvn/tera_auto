CREATE TABLE qp_account_role
(
	role_id NUMBER(16,0) NOT NULL,
	account_id NUMBER(16,0) NOT NULL,
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	CONSTRAINT qp_account_role_pkey PRIMARY KEY (role_id, account_id)
);
ALTER TABLE qp_account_role
ADD CONSTRAINT qp_account_account_id_fkey FOREIGN KEY(account_id) REFERENCES qp_account(account_id) ENABLE;
ALTER TABLE qp_account_role
ADD CONSTRAINT qp_account_role_id_fkey FOREIGN KEY(role_id) REFERENCES qp_role(role_id) ENABLE;
