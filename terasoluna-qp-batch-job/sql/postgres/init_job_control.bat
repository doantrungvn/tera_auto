set path=C:\Program Files\PostgreSQL\9.2\bin\;%path%
psql -h 192.168.1.73 -U postgres -d terasolunaqp -f insert_job_control.sql

pause