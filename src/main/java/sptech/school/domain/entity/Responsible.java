package sptech.school.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Responsible {
    private String responsibleName;
    private String kinship;
    private String responsibleCpf;
    private String responsibleCellphoneNumber;
    private String responsibleEmail;

    public Responsible() {
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getKinship() {
        return kinship;
    }

    public void setKinship(String kinship) {
        this.kinship = kinship;
    }

    public String getResponsibleCpf() {
        return responsibleCpf;
    }

    public void setResponsibleCpf(String responsibleCpf) {
        this.responsibleCpf = responsibleCpf;
    }

    public String getResponsibleCellphoneNumber() {
        return responsibleCellphoneNumber;
    }

    public void setResponsibleCellphoneNumber(String responsibleCellphoneNumber) {
        this.responsibleCellphoneNumber = responsibleCellphoneNumber;
    }

    public String getResponsibleEmail() {
        return responsibleEmail;
    }

    public void setResponsibleEmail(String responsibleEmail) {
        this.responsibleEmail = responsibleEmail;
    }
}