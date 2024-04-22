CREATE TABLE qp_account_role
(
	role_id numeric(16,0) NOT NULL,
	account_id numeric(16,0) NOT NULL,
	created_by numeric(16,0),
	created_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	updated_by numeric(16,0),
	updated_date timestamp with time zone DEFAULT timezone('utc'::text, now()),
	CONSTRAINT qp_account_role_pkey PRIMARY KEY (role_id, account_id),
	CONSTRAINT qp_account_account_id_fkey FOREIGN KEY (account_id)
			REFERENCES qp_account (account_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT,
	CONSTRAINT qp_account_role_id_fkey FOREIGN KEY (role_id)
			REFERENCES qp_role (role_id) MATCH SIMPLE
			ON UPDATE RESTRICT ON DELETE RESTRICT
);

