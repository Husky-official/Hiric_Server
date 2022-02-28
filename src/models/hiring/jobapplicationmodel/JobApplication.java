package models.hiring.jobapplicationmodel;

public class JobApplication {

    private Integer id;
    private Integer userId;
    private Integer jobId;
    private String currentAddress;
    private String positionAppliedFor;
    private String availableDate;
    private String salaryDesired;
    private String paymentMethod;
    private String prevEmployer;
    private String prevEmpPhone;
    private String prevEmpEmail;
    private String prevPosition;
    private String reason;
    private String referenceName;
    private String referencePhone;
    private String resume;
    private String certificate;

    public void JobApplication(){};
    public void JobApplication(int id,int jobId,int userId,String currentAddress, String positionAppliedFor, String availableDate, String salaryDesired, String
            paymentMethod, String prevEmpPhone, String prevEmpEmail, String
                                       prevPosition, String reason, String referenceName, String referencePhone, String resume,
                               String certificate){
        this.userId=userId;
        this.id=id;
        this.jobId=jobId;
        this.currentAddress=currentAddress;
        this.positionAppliedFor= positionAppliedFor;
        this.availableDate=availableDate;
        this.salaryDesired=salaryDesired;
        this.paymentMethod=paymentMethod;
        this.referencePhone=referencePhone;
        this.prevEmpEmail= prevEmpEmail;
        this.referenceName=referenceName;

        this.prevPosition= prevPosition;
        this.reason=reason;
        this.resume=resume;
        this.certificate=certificate;
        this.prevEmpPhone= prevEmpPhone;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPositionAppliedFor() {
        return positionAppliedFor;
    }

    public void setPositionAppliedFor(String positionAppliedFor) {
        this.positionAppliedFor = positionAppliedFor;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getSalaryDesired() {
        return salaryDesired;
    }

    public void setSalaryDesired(String salaryDesired) {
        this.salaryDesired = salaryDesired;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPrevEmployer() {
        return prevEmployer;
    }

    public void setPrevEmployer(String prevEmployer) {
        this.prevEmployer = prevEmployer;
    }

    public String getPrevEmpPhone() {
        return prevEmpPhone;
    }

    public void setPrevEmpPhone(String prevEmpPhone) {
        this.prevEmpPhone = prevEmpPhone;
    }

    public String getPrevEmpEmail() {
        return prevEmpEmail;
    }

    public void setPrevEmpEmail(String prevEmpEmail) {
        this.prevEmpEmail = prevEmpEmail;
    }

    public String getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition(String prevPosition) {
        this.prevPosition = prevPosition;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getReferencePhone() {
        return referencePhone;
    }

    public void setReferencePhone(String referencePhone) {
        this.referencePhone = referencePhone;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}

