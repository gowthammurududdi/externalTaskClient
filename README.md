Camunda External Task Client Worker

Spring boot Project to Run Camunda External Task Client 

Define the following variables in Run Configurations

| Variables| 
| -------- |
| baseUri  |
| topicName|
| workerId |
| userName |
| password |

BPMN Process is provided in resources

 External Task Topic in BPMN 
 
 ![image](https://user-images.githubusercontent.com/129924361/229994997-8a3424c9-9ff3-493c-8681-d44802ba4dff.png)

 
 and Run Configurations Topic should be Same 
 
 ```java
         String topicName = System.getenv("topicName");
```
 

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



