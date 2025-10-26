package sptech.school.v2.cleanarch.core.dtos.out.dashboard;

public class ChartBarDTO {
    private String label;
    private double value;

    public ChartBarDTO() {
    }

    public ChartBarDTO(String label, double value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
