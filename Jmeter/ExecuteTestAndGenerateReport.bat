rmdir /s /q "TestResults"
del /s /q "testResults.csv"
jmeter -n -Jjmeter.reportgenerator.overall_granularity=2000 -t "./ASAPP-Perfomance.jmx" -l "./testResults.csv" -e -o "./TestResults"