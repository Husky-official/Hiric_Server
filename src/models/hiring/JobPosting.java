package models.hiring;
import java.sql.*;

public class JobPosting {
    private String jobId;
    private String userId;
    private String jobTitle;
    private String jobDescription;
    private String jobRequirements;
    private String location;
    private String startDate;
    private String duration;
    private Double salary;

    public JobPosting() {
    }
    public Integer id;
    public String jobDesc;;
    public Time startTime;
    public String salaryType;
    public Integer workers;
    public Integer paymentStatus;
    public String status;
    public JobPosting(Integer id,Integer jobId, Integer userId, String jobDesc, String jobRequirements, Integer location, Date startDate, Time startime, String duration, Integer salary, String salaryType, Integer workers, Integer paymentStatus, String status) {
        this.id = id;
        this.jobId = String.valueOf(jobId);
        this.userId = String.valueOf(userId);
        this.jobDesc = jobDesc;
        this.jobRequirements = jobRequirements;
        this.location = String.valueOf(location);
        this.startDate = String.valueOf(startDate);
        this.startTime = startime;
        this.duration = duration;
        this.salary = Double.valueOf(salary);
        this.salaryType = salaryType;
        this.workers = workers;
        this.paymentStatus = paymentStatus;
        this.status = status;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = String.valueOf(jobId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = String.valueOf(userId);
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(String jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = String.valueOf(location);
    }

    public String getStartDate() {
        return startDate;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public Integer getWorkers() {
        return workers;
    }

    public void setWorkers(Integer workers) {
        this.workers = workers;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDate(Date startDate) {
        this.startDate = String.valueOf(startDate);
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
    public JobPosting(String jobId, String userId, String jobTitle, String jobDescription, String jobRequirements, String location, String startDate, String duration, Double salary) {
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
                ", jobDesc='" + jobDesc + '\'' +
                ", jobRequirements=" + jobRequirements +
                ", location=" + location +
                ", startDate=" + startDate +
                ", duration=" + duration +
                ", salary=" + salary +
                '}';
    }
    public void setSalary(Integer salary) {
        this.salary = Double.valueOf(salary);
    }
}