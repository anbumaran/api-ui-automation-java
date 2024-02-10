# ui-api-automation-java

# To install and intialize the DB
initdb.exe -D . -U postgres -W -E UTF8 -A scram-sha-256

# To Start DB 
Go to  - \pgsql\bin

pg_ctl -D . -l logfile start

pg_ctl -D . stop
