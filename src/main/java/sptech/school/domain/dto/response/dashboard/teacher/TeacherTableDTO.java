package sptech.school.domain.dto.response.dashboard.teacher;

public class TeacherTableDTO {
    private String name;
    private String subject;
    private double hoursWorked;
    private String hourlyRate;
    private double rating;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double horasTrabalhadas) {
        this.hoursWorked = horasTrabalhadas;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
