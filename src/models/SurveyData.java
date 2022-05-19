package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SurveyData {
    String surveyId;
    String surveyData;
    public SurveyData(ResultSet rs) throws SQLException {
        this.surveyId = rs.getString("surveyId");
        this.surveyData = rs.getString("surveyData");
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyData() {
        return surveyData;
    }

    public void setSurveyData(String surveyData) {
        this.surveyData = surveyData;
    }
}
