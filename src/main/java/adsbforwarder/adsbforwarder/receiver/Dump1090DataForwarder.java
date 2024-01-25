package adsbforwarder.adsbforwarder.receiver;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Dump1090DataForwarder {

    private final Logger logger = LoggerFactory.getLogger(Dump1090DataForwarder.class);

    private final Dump1090DataService dataService;

    public Dump1090DataForwarder(Dump1090DataService dataService) {
        this.dataService = dataService;
    }

    @PostConstruct
    public void forwardDump1090Data() {
        while (true) {
            if (dataService.queueIsNotEmpty()) {
                Dump1090Data dataObject = dataService.getNextFromQueue();
                logger.info(dataObject.getHexIdent());
            }
        }
    }
}