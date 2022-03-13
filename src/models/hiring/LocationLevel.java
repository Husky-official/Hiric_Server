package models.hiring;

public class LocationLevel {
    public Integer id;
    public String level;
    public LocationLevel() {

    }
    public LocationLevel(Integer id, String level) {
        this.id = id;
        this.level = level;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
