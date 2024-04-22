CREATE TABLE qp_account_permission
(
	permission_id numeric(16,0) NOT NULL,
	account_id numeric(16,0) NOT NULL,
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	CONSTRAINT qp_account_permission_pkey PRIMARY KEY (permission_id, account_id),
	CONSTRAINT qp_account_permission_account_id_fkey FOREIGN KEY (account_id)
			REFERENCES qp_account (account_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT qp_account_permission_permission_id_fkey FOREIGN KEY (permission_id)
			REFERENCES qp_permission (permission_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT
);

