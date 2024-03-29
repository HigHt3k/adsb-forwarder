package adsbforwarder.adsbforwarder.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Dump1090DataService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private long skippedMessages = 0;
    private long sentMessages = 0;

    private final Logger logger = LoggerFactory.getLogger(Dump1090DataService.class);

    @Autowired
    public Dump1090DataService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public void parseDump1090Data(String rawData) {
        String[] dataParts = new String[0];
        try {
            dataParts = rawData.split(",", -1);

            // default
            String messageType = dataParts[0];
            String transmissionType = dataParts[1];
            String sessionId = dataParts[2];
            String icao24Decimal = dataParts[3];
            String hexIdent = dataParts[4];
            String flightNumber = dataParts[5];
            String dateMessageGenerated = dataParts[6];
            String timeMessageGenerated = dataParts[7];
            String dateMessageLogged = dataParts[8];
            String timeMessageLogged = dataParts[9];

            // specific aircraft information
            String callsign = dataParts[10];
            String altitude = dataParts[11];
            String groundSpeed = dataParts[12];
            String track = dataParts[13];
            String latitude = dataParts[14];
            String longitude = dataParts[15];
            String verticalRate = dataParts[16];
            String squawk = dataParts[17];
            String alertFlag = dataParts[18];
            String emergencyFlag = dataParts[19];
            String spiFlag = dataParts[20];
            String groundFlag = dataParts[21];

            Dump1090Data dump1090Data = new Dump1090Data();
            dump1090Data.setMessageType(messageType);
            if (!transmissionType.isEmpty())
                dump1090Data.setTransmissionType(Integer.parseInt(transmissionType));
            if (!sessionId.isEmpty())
                dump1090Data.setSessionId(Integer.parseInt(sessionId));
            if (!hexIdent.isEmpty())
                dump1090Data.setIcao24Decimal(Integer.parseInt(hexIdent, 16));
            dump1090Data.setHexIdent(hexIdent);
            dump1090Data.setName(hexIdent);
            dump1090Data.setFlightNumber(flightNumber);
            dump1090Data.setDateMessageGenerated(dateMessageGenerated);
            dump1090Data.setTimeMessageGenerated(timeMessageGenerated);
            dump1090Data.setTimeMessageLogged(timeMessageLogged);
            dump1090Data.setDateMessageLogged(dateMessageLogged);
            dump1090Data.setCallsign(callsign);
            if (!altitude.isEmpty())
                dump1090Data.setAltitude(Integer.parseInt(altitude));
            if (!groundSpeed.isEmpty())
                dump1090Data.setGroundSpeed(Integer.parseInt(groundSpeed));
            dump1090Data.setTrack(track);
            if (!latitude.isEmpty())
                dump1090Data.setLat(Double.parseDouble(latitude));
            if (!longitude.isEmpty())
                dump1090Data.setLon(Double.parseDouble(longitude));
            if (!verticalRate.isEmpty())
                dump1090Data.setVerticalRate(Integer.parseInt(verticalRate));
            dump1090Data.setSquawk(squawk);
            if (!alertFlag.isEmpty())
                dump1090Data.setAlertFlag(Boolean.parseBoolean(alertFlag));
            if (!emergencyFlag.isEmpty())
                dump1090Data.setEmergencyFlag(Boolean.parseBoolean(emergencyFlag));
            if (!spiFlag.isEmpty())
                dump1090Data.setSpiFlag(Boolean.parseBoolean(spiFlag));
            if (!groundFlag.isEmpty())
                dump1090Data.setGroundFlag(Boolean.parseBoolean(groundFlag));

            dump1090Data.setRawMessage(rawData);

            if(!squawk.equals("7777") && !longitude.isEmpty() && !latitude.isEmpty()) {
                sendData(dump1090Data);
            } else {
                skipSending();
            }


        } catch(NumberFormatException e) {
            logger.warn("Not parsing line due to number format exception: {}", rawData);
        } catch(ArrayIndexOutOfBoundsException e) {
            logger.warn("Received message with different amount of fields [{}]: {}", dataParts.length, rawData);
        } catch(Exception e) {
            logger.error("Unexpected error: ", e);
        }
    }

    public void skipSending() {
        skippedMessages++;
    }

    public void sendData(Dump1090Data dump1090Data) {
        String endpointUrl = "http://34.28.80.116:1880/adsb";

        String requestData = convertToJson(dump1090Data);

        logger.info("SENDING DATA: {}", requestData);
        logger.info("Total amount of sent messages: {}", sentMessages);
        logger.info("Total amount of skipped messages: {}", skippedMessages);
        sentMessages++;

        restTemplate.put(endpointUrl, requestData);
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
