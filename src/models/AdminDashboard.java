package models;

import java.util.Date;

/**
 *@author : BWIZA Cyndy Nina - Admin dashboard
 *@description: : getters and setters of the elements
 */

public class AdminDashboard {

    private String adminName;
    private Date dateToday;
    private String messages;
    private String notifications;
    private String employerReviews;
    private String employeeReviews;
    private Date publishedDate;
    private int employeesRecentlyAdded;
    private String employeeEmail;
    private String employeePost;
    private String desiredSchedule;
    private int employersRecentlyAdded;
    private String employerEmail;
    private String employerDesiredPost;

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getDateToday() {
        return dateToday;
    }

    public void setDateToday(Date dateToday) {
        this.dateToday = dateToday;
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

    public String getEmployerReviews() {
        return employerReviews;
    }

    public void setEmployerReviews(String employerReviews) {
        this.employerReviews = employerReviews;
    }

    public String getEmployeeReviews() {
        return employeeReviews;
    }

    public void setEmployeeReviews(String employeeReviews) {
        this.employeeReviews = employeeReviews;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getEmployeesRecentlyAdded() {
        return employeesRecentlyAdded;
    }

    public void setEmployeesRecentlyAdded(int employeesRecentlyAdded) {
        this.employeesRecentlyAdded = employeesRecentlyAdded;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePost() {
        return employeePost;
    }

    public void setEmployeePost(String employeePost) {
        this.employeePost = employeePost;
    }

    public String getDesiredSchedule() {
        return desiredSchedule;
    }

    public void setDesiredSchedule(String desiredSchedule) {
        this.desiredSchedule = desiredSchedule;
    }

    public int getEmployersRecentlyAdded() {
        return employersRecentlyAdded;
    }

    public void setEmployersRecentlyAdded(int employersRecentlyAdded) {
        this.employersRecentlyAdded = employersRecentlyAdded;
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
}
