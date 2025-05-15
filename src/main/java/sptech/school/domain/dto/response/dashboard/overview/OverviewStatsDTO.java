package sptech.school.domain.dto.response.dashboard.overview;

public class OverviewStatsDTO {
    private double totalRevenue;
    private long totalTeachers;
    private double totalHours;
    private int totalAppointments;

    public OverviewStatsDTO() {}

    public OverviewStatsDTO(double totalRevenue, long totalTeachers, double totalHours, int totalAppointments) {
        this.totalRevenue = totalRevenue;
        this.totalTeachers = totalTeachers;
        this.totalHours = totalHours;
        this.totalAppointments = totalAppointments;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(long totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }
}
