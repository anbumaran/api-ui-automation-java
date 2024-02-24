cd %~dp0..\qa_auto_challenge_prj\
docker build ./src/api -t asapp-qa-challenge-api
docker build ./src/ui -t asapp-qa-challenge-ui
docker-compose up -d