Camunda External Task Client Worker
#Contents
Spring boot Project to Run Camunda External Task Client 
Define baseUri, topicName, workerId, userName, password in Run Configurations

In resources 

Process External Task Topic and Run Configurations Topic should be Same 

This External Task Worker Expects an input from the process in following format 
```JSON

{
    "variables":{
        "sla":{"value":"PT20H", "type":"String"}

    }
}
```
and the output will be four variables and similar to following 
```JSON

{
    "processedOn": "Wed Apr 05 00:31:26 CDT 2023",
    "dueByDate": "2023-04-05T20:31:26-0500",
    "minutesOfSla": "1200",
    "processedBy": "shippingtaskworkerOne"
}
```

This worker is simply calculating the Due Date with the Given SLA in time



