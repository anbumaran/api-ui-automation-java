<html>
<style>
table {
        font-family: Calibri, Arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
        }
        td, th {
        border: 2px solid sky-blue;
        padding: 8px;
        color:#6400aa;
        text-align: center;
        }

        tr:nth-child(even){background-color: #f2f2f2;}

        tr:hover {background-color: #ddd;}

        th {
        padding-top: 12px;
        padding-bottom: 12px;
        text-align: center;
        background-color: #8e8a94;
        color: white;
        }
        body {background-color: #fffff;font-family: Calibri, Arial, sans-serif;}
        h1   {color: blue;}
        p    {color: red;}
        div.text {
        margin: 0;
        padding: 0;
        padding-bottom: 1.25em;
        }
        </style>
<body>
<!--<br/>&nbsp;&nbsp;Hi,<br/><br/>&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;  Please find My Quote Automation Test Report below also refer attached file for the details<br/> <br/>&nbsp;&nbsp;Thank you<br/> <br/> <br/> -->
<div >
<table border="1" >
    <tr>
        <th style="font-size:18px;font-weight:bold;background-color:#5514b4; text-align:center;padding:20px;padding-top: 6px;
        padding-bottom: 6px;">${appName} - Automation Report - ${env}</th>
        </td>
    </tr>
 </table>
<div>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!--<table border="1">
    <tr>
        <th style="font-size:15px;font-weight:bold">Test Summary</th>
     </tr>
</table> -->
<table border="1">
    <tr style="font-size:15px;font-weight:bold">
        <th style="width:10%">Total Features</th>
        <th style="width:10%">Passed Features</th>
        <th style="width:10%">Failed Features</th>
        <th style="width:10%">Total Test Scenarios</th>
        <th style="width:10%">Passed Scenarios</th>
        <th style="width:10%">Failed Scenarios</th>
<th style="width:11%">Total Duration (hh:mm:ss)</th>
    </tr>
    <tr style="font-size:15px;font-weight:bold">
        <td> ${totalEndPoints}</td>
        <td style="color:green"> ${passedEndPoints}</td>
        <td style="color:red"> ${failedEndPoints}</td>
        <td> ${totalTestCases}</td>
        <td style="color:green"> ${passedTestCases}</td>
        <td style="color:red"> ${failedTestCases}</td>
<td> ${testDuration}</td>
    </tr>
 </table>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<table border="1">
    <tr>
         <th style="font-size:18px;font-weight:bold; text-align:center;
         padding:20px;padding-top: 6px;padding-bottom: 6px;">Test Results</th>
    </tr>
</table>
    <table border="1">
     <tr style="font-size:15px;font-weight:bold">
        <th style="width:5%">S.No</th>
        <th style="width:20%">Test Name</th>
        <th style="width:10%">Duration<br>(hh:mm:ss)</th>
        <th style="width:10%">Test Scenarios</th>
        <th style="width:10%">Passed</th>
        <th style="width:10%">Failed</th>
        <th style="width:10%">Retry Failed Tests<br>(Max Retry - 3x)</th>
    </tr>
    <#list testResultsList as testResults>
    <tr style="color:blue;font-size:15px;font-weight:bold">
        <td>${testResults.order}</td>
        <#if ("${testResults.displayName}" != '')><td>${testResults.displayName}</td>
        <#else><td style="color:blue">${testResults.name}</td>
        </#if>
          <td>${testResults.readableDuration}</td>
          <td>${testResults.tests}</td>
          <td style="color:green">${testResults.passed}</td>
            <#if ("${testResults.failed}" != '0')><td style="color:red">${testResults.failed}</td>
            <#else><td>${testResults.failed}</td>
            </#if>
          <td>${testResults.ignored}</td>
         </tr>
    </#list>
</table>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<table border="1">
    <th style="font-size:18px;font-weight:bold; text-align:center;
    padding:20px;padding-top: 6px;padding-bottom: 6px;">Failed Tests</th>
</table>

<table border="1">
     <tr style="font-size:15px;font-weight:bold">

        <th style="width:10%">Name</th>
        <th style="width:20%">Message</th>
    </tr>
    <#list failedTestCaseList as failedTest>
    <tr style="font-size:15px;font-weight:bold">

          <td>${failedTest.name}</td>
          <td>${failedTest.failedShortMessage}</td>
         </tr>
    </#list>
</table>

<br/>Thank you,
<br>
<br><b style="color:blue">ASAPP</b>
<br><i style="color:rgb(68,225,68);">World Trade Center, One, 80th Floor,
<br>New York, NY 10007, United States.</i>

</body>
</html>