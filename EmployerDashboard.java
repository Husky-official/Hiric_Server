package models;

public class EmployerDashboard {
    private String employerName;
    private String jobStatus;
    private int jobsPosted;
    private int newApplicants;
    private String scheduledInterviews;
    private String rating;
    private String messages;
    private String notifications;
    private String employerEmail;
    private String employerDesiredPost;

    public String getEmployerName() {
        return employerName;
    }
    public void setEmployerName(String adminName) {
        this.employerName = employerName;
    }
    public String getMessages() {
        return messages;
    }
    public void setMessages(String messages) {
        this.messages = messages;
    }
    public String getNotifications() {
        return notifications;
    }
    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }
    public String getJobStatus() {
        return jobStatus;
    }
    public void setJobStatus(String employerReviews) {
        this.jobStatus = jobStatus;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String employeeReviews) {
        this.rating = rating;
    }
    public int getJobsPosted() {
        return jobsPosted;
    }
    public void setJobsPosted(int employeesRecentlyAdded) {
        this.jobsPosted = jobsPosted;
    }
    public String getScheduledInterviews() {
        return scheduledInterviews;
    }
    public void setScheduledInterviews(String desiredSchedule) {
        this.scheduledInterviews = scheduledInterviews;
    }
    public int getNewApplicants() {
        return newApplicants;
    }
    public void setNewApplicants(int newApplicants) {
        this.newApplicants = newApplicants;
    }
    public String getEmployerEmail() {
        return employerEmail;
    }
    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }
    public String getEmployerDesiredPost() {
        return employerDesiredPost;
    }
    public void setEmployerDesiredPost(String employerDesiredPost) {
        this.employerDesiredPost = employerDesiredPost;
    }
    public static void main(String[] args) {
        System.out.println("Employer Dashboard");
    }
}
