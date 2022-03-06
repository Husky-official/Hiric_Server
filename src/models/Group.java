package models;

import java.sql.Date;

/**
 * @author: DABAGIRE Valens
 * @description: Chatting group class to model the fields needed to create and read content
 *               from group messaging table
 **/

public class Group {

    private int id;
    private String groupName;
    private String groupDescription;
    private int groupCreatorID;
    private int numberOfParticipants;
    private Date createdAt;
    private Date updatedAt;

    public Group(){};

    public Group(String groupName, int groupCreatorID, Date createdAt, Date updatedAt){
        this.groupName = groupName;
        this.groupCreatorID = groupCreatorID;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Group(String groupName, String groupDescription, int groupCreatorID, Date createdAt, Date updatedAt){
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupCreatorID = groupCreatorID;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public int getGroupCreatorID() {
        return groupCreatorID;
    }

    public void setGroupCreatorID(int groupCreatorID) {
        this.groupCreatorID = groupCreatorID;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
