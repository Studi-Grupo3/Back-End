package sptech.school.domain.dto.response.dashboard;

public class ChartPieDTO {
    private String label;
    private double percentage;

    public ChartPieDTO() {
    }

    public ChartPieDTO(String label, double percentage) {
        this.label = label;
        this.percentage = percentage;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}