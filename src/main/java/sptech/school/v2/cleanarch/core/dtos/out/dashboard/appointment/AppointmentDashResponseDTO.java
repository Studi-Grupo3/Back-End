package sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment;

import sptech.school.domain.dto.response.dashboard.ChartBarDTO;
import sptech.school.domain.dto.response.dashboard.ChartPieDTO;
import sptech.school.domain.dto.response.dashboard.appointment.AppointmentTableDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.appoinment.AppointmentStatsDTO;


import java.util.List;

public record AppointmentDashResponseDTO(
        AppointmentStatsDTO stats,
        List<ChartBarDTO> weeklyChart,
        List<ChartPieDTO> statusPie,
        List<AppointmentTableDTO> table
) { }
