package sptech.school.domain.dto.response.dashboard.overview;

public class OverviewStatsDTO {
    private double totalRevenue;
    private long totalTeachers;
    private double pendingAmount;
    private int totalAppointments;

    public OverviewStatsDTO() {}

    public OverviewStatsDTO(double totalRevenue, long totalTeachers, double pendingAmount, int totalAppointments) {
        this.totalRevenue = totalRevenue;
        this.totalTeachers = totalTeachers;
        this.pendingAmount = pendingAmount;
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

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }
}
