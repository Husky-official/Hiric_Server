package models;

import java.io.Serializable;

/**
 *@author: DABAGIRE Valens
 * @description : this is a class to manage the response from server
 * */

public class ResponseStatus implements Serializable {
    private Integer status;
    private String message;
    private Object object;
    private String actionToDo;

    public ResponseStatus(){};

    public ResponseStatus(Integer status, String message, String actionToDo) {
        this.status = status;
        this.message = message;
        this.actionToDo = actionToDo;
    }

    public ResponseStatus(Integer status, String message, Object object, String actionToDo) {
        this.status = status;
        this.message = message;
        this.object = object;
        this.actionToDo = actionToDo;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getActionToDo() {
        return actionToDo;
    }

    public void setActionToDo(String actionToDo) {
        this.actionToDo = actionToDo;
    }
}
