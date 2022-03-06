package models.hiring;

public class JobPosting {
    private Integer jobId;
    private Integer userId;
    private String jobTitle;
    private String jobDescription;
    private String jobRequirements;
    private Integer location;
    private String startDate;
    private String duration;
    private Integer salary;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    public JobPosting(Integer jobId, Integer userId, String jobTitle, String jobDescription, String jobRequirements, Integer location, String startDate, String duration, int salary) {
        this.jobId = jobId;
        this.userId = userId;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobRequirements = jobRequirements;
        this.location = location;
        this.startDate = startDate;
        this.duration = duration;
        this.salary = salary;
    }
    @Override
    public String toString() {
        return "JobPosting{" +
                "jobId=" + jobId +
                "userId=" + userId +
                ", jobTitle=" + jobTitle +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobRequirements=" + jobRequirements +
                ", location=" + location +
                ", startDate=" + startDate +
                ", duration=" + duration +
                ", salary=" + salary +
                '}';
    }
}
