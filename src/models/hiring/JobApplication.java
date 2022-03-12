
package models.hiring;

public class JobApplication {

    private  Integer id;
    private  Integer userId;
    private  Integer jobPostId;
    private  String paymentMethod;
    private  Integer locationId;
    private String referenceName;
    private  String referencePhone;
    private  String resume;
    private  String certificate;
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String firstName;
    private String lastName;
    private String email;

    public void JobApplication(){};
    public JobApplication(int id,int jobId,int userId, String paymentMethod, int locationId, String referenceName, String referencePhone, String resume, String certificate,String status){
        this.userId=userId;
        this.id=id;
        this.jobPostId =jobId;
        this.paymentMethod=paymentMethod;
        this.referencePhone=referencePhone;
        this.referenceName=referenceName;
        this.locationId =locationId;
        this.resume=resume;
        this.certificate=certificate;
        this.status=status;
    }

    public JobApplication(int id,int jobId,int userId, String paymentMethod, int locationId, String referenceName, String referencePhone, String resume, String certificate, String firstName, String lastName, String email){
        this.userId=userId;
        this.id=id;
        this.jobPostId =jobId;
        this.paymentMethod=paymentMethod;
        this.referencePhone=referencePhone;
        this.referenceName=referenceName;
        this.locationId =locationId;
        this.resume=resume;
        this.certificate=certificate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public  Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public  String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public  String getReferencePhone() {
        return referencePhone;
    }

    public void setReferencePhone(String referencePhone) {
        this.referencePhone = referencePhone;
    }

    public  String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public  String getCertificate() {
        return certificate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("-----------JOB APPLICATION INFORMATION");
        sb.append("ID: "+getId());
        sb.append("Job post id "+getJobPostId());
        sb.append("user id" +getUserId());
        sb.append("locationId "+getLocationId());
        sb.append("payment method "+getPaymentMethod());
        sb.append("referenceName "+getReferenceName());
        sb.append("reference phone "+getReferencePhone());
        sb.append("resume "+getResume());
        sb.append("certificate "+getCertificate());
        return  sb.toString();
    }
}