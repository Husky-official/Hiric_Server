package models;

import java.util.List;

public class ResponseBody {
    private List<Object> response;

    public ResponseBody() {}
    public ResponseBody(List<Object> response){
        this.response = response;
    }

    public List<Object> getResponse() {
        return response;
    }

    public void setResponse(List<Object> response) {
        this.response = response;
    }
}
