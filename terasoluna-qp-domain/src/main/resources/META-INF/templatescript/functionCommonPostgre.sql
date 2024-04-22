-- Function: "qp_intersectStr"(character varying, character varying, character varying)
-- DROP FUNCTION "qp_intersectStr"(character varying, character varying, character varying);
CREATE OR REPLACE FUNCTION qp_intersect_str(
	character varying,
	character varying,
	character varying) RETURNS integer AS
$BODY$
	SELECT ( string_to_array($1, $3) && string_to_array($2, $3) ::text[])::integer;
$BODY$
	LANGUAGE sql VOLATILE
	COST 100;
