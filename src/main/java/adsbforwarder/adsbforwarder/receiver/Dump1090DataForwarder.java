package adsbforwarder.adsbforwarder.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Dump1090DataForwarder {

    private final Logger logger = LoggerFactory.getLogger(Dump1090DataForwarder.class);

    private final Dump1090DataService dataService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public Dump1090DataForwarder(Dump1090DataService dataService, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.dataService = dataService;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedRate = 1000)
    public void sendData() {
        String endpointUrl = "http://34.30.213.233:1880/adsb";

        if(dataService.queueIsNotEmpty()) {
            Dump1090Data dataObject = dataService.getNextFromQueue();

            Object requestData = convertToJson(dataObject);

            restTemplate.postForObject(endpointUrl, requestData, String.class);
        }
    }

    private String convertToJson(Dump1090Data data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            // Handle or log the exception as needed
            e.printStackTrace();
            return null;
        }
    }
}