package sptech.school.domain.dto.response.dashboard.appointment;

public class AppointmentStatsDTO {

    private final int totalAppointments;
    private final long confirmedCount;
    private final long activeStudents;
    private final double averageDuration;

    public AppointmentStatsDTO(int totalAppointments,
                               long confirmedCount,
                               long activeStudents,
                               double averageDuration) {
        this.totalAppointments = totalAppointments;
        this.confirmedCount   = confirmedCount;
        this.activeStudents   = activeStudents;
        this.averageDuration  = averageDuration;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public long getConfirmedCount() {
        return confirmedCount;
    }

    public long getActiveStudents() {
        return activeStudents;
    }

    public double getAverageDuration() {
        return averageDuration;
    }
}
