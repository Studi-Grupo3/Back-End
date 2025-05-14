package sptech.school.domain.dto.response.dashboard.appointment;

import sptech.school.domain.dto.response.dashboard.ChartBarDTO;
import sptech.school.domain.dto.response.dashboard.ChartPieDTO;

import java.util.List;

public class AppointmentDashDTO {
    private AppointmentStatsDTO stats;
    private List<ChartBarDTO> weeklyChart;
    private List<ChartPieDTO> statusPie;
    private List<AppointmentTableDTO> table;

    public AppointmentDashDTO() {
    }

    public AppointmentDashDTO(AppointmentStatsDTO stats, List<ChartBarDTO> weeklyChart, List<ChartPieDTO> statusPie, List<AppointmentTableDTO> table) {
        this.stats = stats;
        this.weeklyChart = weeklyChart;
        this.statusPie = statusPie;
        this.table = table;
    }

    public AppointmentStatsDTO getStats() {
        return stats;
    }

    public void setStats(AppointmentStatsDTO stats) {
        this.stats = stats;
    }

    public List<ChartBarDTO> getWeeklyChart() {
        return weeklyChart;
    }

    public void setWeeklyChart(List<ChartBarDTO> weeklyChart) {
        this.weeklyChart = weeklyChart;
    }

    public List<ChartPieDTO> getStatusPie() {
        return statusPie;
    }

    public void setStatusPie(List<ChartPieDTO> statusPie) {
        this.statusPie = statusPie;
    }

    public List<AppointmentTableDTO> getTable() {
        return table;
    }

    public void setTable(List<AppointmentTableDTO> table) {
        this.table = table;
    }
}
