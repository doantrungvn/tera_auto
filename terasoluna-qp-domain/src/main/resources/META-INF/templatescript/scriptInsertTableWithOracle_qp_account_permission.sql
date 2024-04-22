CREATE TABLE qp_account_permission
(
	permission_id NUMBER(16,0) NOT NULL,
	account_id NUMBER(16,0) NOT NULL,
	created_by NUMBER(16,0),
	created_date DATE DEFAULT SYSDATE,
	updated_by NUMBER(16,0),
	updated_date DATE DEFAULT SYSDATE,
	CONSTRAINT qp_account_permission_pkey PRIMARY KEY (permission_id, account_id),
	CONSTRAINT qp_acc_permis_account_id_fkey FOREIGN KEY (account_id)
			REFERENCES qp_account (account_id),
	CONSTRAINT qp_acc_permis_permis_id_fkey FOREIGN KEY (permission_id)
			REFERENCES qp_permission (permission_id)
);
