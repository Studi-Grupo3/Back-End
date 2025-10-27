package sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.ChartBarDTO;

import java.util.List;

public class OverviewDashDTO {
    private OverviewStatsDTO stats;
    private List<ChartLineDTO> monthlyRevenue;
    private List<ChartBarDTO> lessonsPerDay;
    private List<OverviewTableDTO> recentPayments;

    public OverviewDashDTO() {}

    public OverviewDashDTO(OverviewStatsDTO stats, List<ChartLineDTO> monthlyRevenue, List<ChartBarDTO> lessonsPerDay, List<OverviewTableDTO> recentPayments) {
        this.stats = stats;
        this.monthlyRevenue = monthlyRevenue;
        this.lessonsPerDay = lessonsPerDay;
        this.recentPayments = recentPayments;
    }

    public OverviewStatsDTO getStats() {
        return stats;
    }

    public void setStats(OverviewStatsDTO stats) {
        this.stats = stats;
    }

    public List<ChartLineDTO> getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(List<ChartLineDTO> monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public List<ChartBarDTO> getLessonsPerDay() {
        return lessonsPerDay;
    }

    public void setLessonsPerDay(List<ChartBarDTO> lessonsPerDay) {
        this.lessonsPerDay = lessonsPerDay;
    }

    public List<OverviewTableDTO> getRecentPayments() {
        return recentPayments;
    }

    public void setRecentPayments(List<OverviewTableDTO> recentPayments) {
        this.recentPayments = recentPayments;
    }
}
