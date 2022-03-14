package models;
import java.util.*;


/**
 * @author Biziyaremye Henriette
 * @desciprition Model of archived items in our application
 * */
public class Archive {
    private Integer ArchiveIntakeId;
    private Date ArchivedDate;
    private Integer ArchiveItemType;
    private String ArchivedContent;
    private String ArchivedMessageOriginId;
    private Date ArchivedsentAt;
    private Date ArchivedReceivedAt;



    public Archive(){}

    public Integer getArchiveIntakeId() {
        return ArchiveIntakeId;
    }

    public void setArchiveIntakeId(Integer archiveIntakeId) {
        ArchiveIntakeId = archiveIntakeId;
    }

    public Date getArchivedDate() {
        return ArchivedDate;
    }

    public void setArchivedDate(Date archivedDate) {
        ArchivedDate = archivedDate;
    }

    public Integer getArchiveItemType() {
        return ArchiveItemType;
    }

    public void setArchiveItemType(Integer archiveItemType) {
        ArchiveItemType = archiveItemType;
    }

    public String getArchivedContent() {
        return ArchivedContent;
    }

    public void setArchivedContent(String archivedContent) {
        ArchivedContent = archivedContent;
    }
}
