package models;

public enum  Protocol {
    HTTP("http"),
    HTTPS("https");

    private String protocolTypeString;

    Protocol(String protocolTypeString) {
        this.protocolTypeString = protocolTypeString;
    }

    public String getProtocolTypeString() {
        return protocolTypeString;
    }
}
