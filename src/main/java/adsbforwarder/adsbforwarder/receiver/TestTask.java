package adsbforwarder.adsbforwarder.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {

    private static final Logger logger = LoggerFactory.getLogger(TestTask.class);

    @Scheduled(fixedRate = 1000)
    public void reportHealth() {
        logger.info("Alive");
    }
}
