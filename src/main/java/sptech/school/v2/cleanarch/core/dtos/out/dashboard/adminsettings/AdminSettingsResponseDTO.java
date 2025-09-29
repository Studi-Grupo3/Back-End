package sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings;

public record AdminSettingsResponseDTO(
        String email,
        Boolean notifyPayments,
        Boolean notifyAppointments,
        Boolean notifyCancellations
) {}
