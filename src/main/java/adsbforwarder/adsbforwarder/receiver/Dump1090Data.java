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
    private double latitude;
    private double longitude;
    private int verticalRate;
    private String squawk;
    private boolean alertFlag;
    private boolean emergencyFlag;
    private boolean spiFlag;
    private boolean groundFlag;
    private String rawMessage;
    private String location;

    // Constructors, getters, and setters

    public Dump1090Data() {
    }

    public Dump1090Data(String messageType, int transmissionType, int sessionId, int icao24Decimal, String hexIdent, String flightNumber, String dateMessageGenerated, String timeMessageGenerated,
                        String dateMessageLogged, String timeMessageLogged, String callsign,
                        int altitude, int groundSpeed, String track, double latitude, double longitude,
                        int verticalRate, String squawk, boolean alertFlag, boolean emergencyFlag, boolean spiFlag, boolean groundFlag, String rawMessage) {
        this.messageType = messageType;
        this.transmissionType = transmissionType;
        this.sessionId = sessionId;
        this.icao24Decimal = icao24Decimal;
        this.hexIdent = hexIdent;
        this.flightNumber = flightNumber;
        this.dateMessageGenerated = dateMessageGenerated;
        this.timeMessageGenerated = timeMessageGenerated;
        this.dateMessageLogged = dateMessageLogged;
        this.timeMessageLogged = timeMessageLogged;
        this.callsign = callsign;
        this.altitude = altitude;
        this.groundSpeed = groundSpeed;
        this.track = track;
        this.latitude = latitude;
        this.longitude = longitude;
        this.verticalRate = verticalRate;
        this.squawk = squawk;
        this.alertFlag = alertFlag;
        this.emergencyFlag = emergencyFlag;
        this.spiFlag = spiFlag;
        this.groundFlag = groundFlag;
        this.rawMessage = rawMessage;
    }
}