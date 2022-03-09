package models.hiring;
import java.sql.*;

public class JobPosting {
    public Integer id;
    public Integer jobId;
    public Integer userId;
    public String jobDesc;
    public String jobRequirements;
    public Integer location;
    public Date startDate;
    public Time startTime;
    public String duration;
    public Integer salary;
    public String salaryType;
    public Integer workers;
    public Integer paymentStatus;
    public String status;

    public JobPosting(Integer id,Integer jobId, Integer userId, String jobDesc, String jobRequirements, Integer location, Date startDate, Time startime, String duration, Integer salary, String salaryType, Integer workers, Integer paymentStatus, String status) {
        this.id = id;
        this.jobId = jobId;
        this.userId = userId;
        this.jobDesc = jobDesc;
        this.jobRequirements = jobRequirements;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startime;
        this.duration = duration;
        this.salary = salary;
        this.salaryType = salaryType;
        this.workers = workers;
        this.paymentStatus = paymentStatus;
        this.status = status;
    }

    public JobPosting() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Date getStartDate() {
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
        this.startDate = startDate;
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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}