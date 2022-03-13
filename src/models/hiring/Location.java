package models.hiring;

public class Location {
   public Integer id;
   public Integer levelId;
   public String location;
   public Integer upperLocation;
    public Location() {

    }
    public Location(Integer id, Integer levelId, String location, Integer upperLocation) {
        this.id = id;
        this.levelId = levelId;
        this.location = location;
        this.upperLocation = upperLocation;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getUpperLocation() {
        return upperLocation;
    }

    public void setUpperLocation(Integer upperLocation) {
        this.upperLocation = upperLocation;
    }
}
