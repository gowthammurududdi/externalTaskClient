package com.gowthamm.externalTaskClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExternalTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalTaskApplication.class, args);

        String baseUri = System.getenv("baseUri");
        String topicName = System.getenv("topicName");
        String workerId = System.getenv("workerId");
        String userName = System.getenv("username");
        String password = System.getenv("password");

        // Initialize the external task client
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(baseUri)
                .asyncResponseTimeout(10000)
                .addInterceptor(new BasicAuthInterceptor(userName, password))
                .build();

        // Subscribe to the external task topic
        client.subscribe(topicName)
                .lockDuration(1000)
                .handler((task, service) -> {
                    // Put your business logic here - Simply Setting a variable
                    Map<String, Object> variables = task.getAllVariables();
                    String slaInTime = (String) variables.get("sla");
                    
                    Duration conversionRate  = Duration.parse(slaInTime);
                    
                    int minutesOfSla = (int) (conversionRate.getSeconds()/60);
                    

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                    
                    Date d = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    cal.add(Calendar.MINUTE, minutesOfSla);
                    String newTime = sdf.format(cal.getTime());
                    
                    

                	HashMap<String, Object> taskMetaData =  new HashMap<>();
                	
                	taskMetaData.put("processedOn", d);
                	taskMetaData.put("processedBy", workerId);
                	taskMetaData.put("minutesOfSla", minutesOfSla);
                	taskMetaData.put("dueByDate", newTime);

                	System.out.println(taskMetaData);
                	
                	
                    System.out.println("Processing external task: " + task.getId());

                    
                    // Complete the external task
                    service.complete(task, taskMetaData);
                })
                .open();
    }
}
