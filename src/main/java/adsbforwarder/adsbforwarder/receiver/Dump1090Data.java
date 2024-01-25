package adsbforwarder.adsbforwarder.receiver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dump1090Data {
    private Integer messageLogId;

    private String messageType;
    private int transmissionType;
    private int sessionId;
    private int icao24Decimal;
    private String hexIdent;
    private String flightNumber;
    private String dateMessageGenerated;
    private String timeMessageGenerated;
    private String dateMessageLogged;
    private String timeMessageLogged;
    private String callsign;
    private int altitude;
    private int groundSpeed;
    private String track;
    private double lat;
    private double lon;
    private int verticalRate;
    private String squawk;
    private boolean alertFlag;
    private boolean emergencyFlag;
    private boolean spiFlag;
    private boolean groundFlag;
    private String rawMessage;
    private String name;

    // Constructors, getters, and setters

    public Dump1090Data() {
    }
}