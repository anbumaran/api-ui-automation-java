docker build -t aiauto .
docker run --name asapp aiauto
docker cp aiauto:/asapp/build/reports/tests ./test-reports
open ./test-reports/index.html