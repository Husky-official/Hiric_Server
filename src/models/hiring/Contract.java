package models;

/**
 * @author  Biziyaremye Henriette
 * @description contract model
 * */
public class Contract {

    private Integer ContractId ;
    private  Integer Field;
    private  String Status;
    private Integer SignatoryId;

    public Contract(){}

    public Contract(Integer contractId, Integer field, String status, Integer signatoryId){
        ContractId = contractId;
        Field = field;
        Status = status;
        SignatoryId = signatoryId;
    }


    public Integer getContractId() {
        return ContractId;
    }

    public void setContractId(Integer contractId) {
        ContractId = contractId;
    }

    public Integer getField() {
        return Field;
    }

    public void setField(Integer field) {
        Field = field;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getSignatoryId() {
        return SignatoryId;
    }

    public void setSignatoryId(Integer signatoryId) {
        SignatoryId = signatoryId;
    }
}




