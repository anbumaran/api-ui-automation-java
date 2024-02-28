cd %~dp0..\..\Database
start "" /B pg_ctl -D . -l logfile start
