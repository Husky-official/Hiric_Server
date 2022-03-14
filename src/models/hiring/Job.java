package models.hiring;

public class Job {
    private Integer id;
    private String jobTitle;

    public Job(Integer id, String jobTitle) {
        this.id = id;
        this.jobTitle = jobTitle;
    }

    public Job() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
