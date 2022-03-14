package models;

/**
 * @author : DABAGIRE Valens
 */

public class ResponseData {
    private String message;
    private String messageType;
    private String sentAt;
    private String userName;

    public ResponseData() {};

    public ResponseData(String message, String messageType, String sentAt, String userName){
        this.message = message;
        this.messageType = messageType;
        this.sentAt = sentAt;
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
