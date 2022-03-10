package models;


import java.util.Date;

public class InvoiceContent {
    private int invoiceId;
    private String invoiceNumber;
    private int jobId;
    private int userId;
    public InvoiceContent(){

    }
    public InvoiceContent(int invoiceId, String invoiceNumber, int jobId, int userId, Date createdAt, String jobTitle, String jobDescription, String jobRequirements, String location, String duration, Long salary, String firstname, String lastname, String email) {
        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.jobId = jobId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobRequirements = jobRequirements;
        this.location = location;
        this.duration = duration;
        this.salary = salary;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private Date createdAt;
    private String jobTitle;
    private String jobDescription;
    private String jobRequirements;
    private String location;
    private String duration;
    private Long salary;
    private String firstname;
    private String lastname;
    private String email;


}
