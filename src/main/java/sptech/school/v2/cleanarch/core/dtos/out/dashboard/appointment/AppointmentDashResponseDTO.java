package sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.ChartBarDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.ChartPieDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.appoinment.AppointmentStatsDTO;


import java.util.List;

public record AppointmentDashResponseDTO(
        AppointmentStatsDTO stats,
        List<ChartBarDTO> weeklyChart,
        List<ChartPieDTO> statusPie,
        List<AppointmentTableDTO> table
) { }
