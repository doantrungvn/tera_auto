CREATE OR REPLACE TYPE array as table of varchar2(255);
/

CREATE OR REPLACE PACKAGE qp_common
AS
	FUNCTION qp_split_str(
		p_list	VARCHAR2,
		p_del	VARCHAR2 := ',' )
	RETURN array pipelined;
	
	FUNCTION qp_intersect_str(
		p_str1	VARCHAR2,
		p_str2	VARCHAR2,
		p_del	VARCHAR2 := ',' )
	RETURN PLS_INTEGER;
	
	PROCEDURE qp_del_object(
		p_obj_name VARCHAR2,
		p_obj_type VARCHAR2);
END qp_common;
/
--------------------------------------------------------
--	DDL for Package Body QP_COMMON
--------------------------------------------------------

create or replace PACKAGE body qp_common
AS
	FUNCTION qp_split_str(
			p_list	VARCHAR2,
			p_del	VARCHAR2 := ',' )
		RETURN array pipelined
		IS
			l_idx pls_integer;
			l_list VARCHAR2(32767) := p_list;
		BEGIN
			LOOP
				l_idx	:= instr(l_list,p_del);
				IF l_idx > 0 THEN
					pipe row(SUBSTR(l_list,1,l_idx-1));
					l_list := SUBSTR(l_list,l_idx +LENGTH(p_del));
				ELSE
					pipe row(l_list);
					EXIT;
				END IF;
			END LOOP;
			RETURN;
		END qp_split_str;
	
	FUNCTION qp_intersect_str(
				p_str1 VARCHAR2,
				p_str2 VARCHAR2,
				p_del	VARCHAR2 := ',' )
			RETURN PLS_INTEGER
		IS
			total PLS_INTEGER := 0;
			strSQL VARCHAR2(4000);
		BEGIN
			strSQL := 'select column_value from table(qp_common.qp_split_str(''' || p_str1 || ''',''' || p_del || '''))';
			strSQL := strSQL || ' intersect select column_value from table(qp_common.qp_split_str(''' || p_str2 || ''',''' || p_del || '''))';
			--dbms_output.put_line(strSQL);
			EXECUTE IMMEDIATE 'SELECT COUNT(*) FROM (' || strSQL || ')' INTO total;
			RETURN total;
		END qp_intersect_str;
	
	
	PROCEDURE qp_del_object(
			p_obj_name VARCHAR2,
			p_obj_type VARCHAR2)
		IS
			v_counter NUMBER := 0;
		BEGIN
			IF p_obj_type = 'TABLE' THEN
				SELECT COUNT(*)
				INTO v_counter
				FROM user_tables
				WHERE table_name = upper(p_obj_name);
				IF v_counter > 0 THEN
					EXECUTE immediate 'drop table ' || p_obj_name || ' cascade constraints';
				END IF;
			END IF;
			IF p_obj_type = 'PROCEDURE' THEN
				SELECT COUNT(*)
				INTO v_counter
				FROM User_Objects
				WHERE object_type = 'PROCEDURE'
				AND OBJECT_NAME	 = upper(p_obj_name);
				IF v_counter > 0 THEN
					EXECUTE immediate 'DROP PROCEDURE ' || p_obj_name;
				END IF;
			END IF;
			IF p_obj_type = 'FUNCTION' THEN
				SELECT COUNT(*)
				INTO v_counter
				FROM User_Objects
				WHERE object_type = 'FUNCTION'
				AND OBJECT_NAME	 = upper(p_obj_name);
				IF v_counter > 0 THEN
					EXECUTE immediate 'DROP FUNCTION ' || p_obj_name;
				END IF;
			END IF;
			IF p_obj_type = 'TRIGGER' THEN
				SELECT COUNT(*)
				INTO v_counter
				FROM User_Triggers
				WHERE TRIGGER_NAME = upper(p_obj_name);
				IF v_counter> 0 THEN
					EXECUTE immediate 'DROP TRIGGER ' || p_obj_name;
				END IF;
			END IF;
			IF p_obj_type = 'VIEW' THEN
				SELECT COUNT(*)
				INTO v_counter
				FROM User_Views
				WHERE VIEW_NAME = upper(p_obj_name);
				IF v_counter > 0 THEN
					EXECUTE immediate 'DROP VIEW ' || p_obj_name;
				END IF;
			END IF;
			IF p_obj_type = 'SEQUENCE' THEN
				SELECT COUNT(*)
				INTO v_counter
				FROM user_sequences
				WHERE sequence_name = upper(p_obj_name);
				IF v_counter > 0 THEN
					EXECUTE immediate 'DROP SEQUENCE ' || p_obj_name;
				END IF;
			END IF;
		END qp_del_object;
END qp_common;

/
