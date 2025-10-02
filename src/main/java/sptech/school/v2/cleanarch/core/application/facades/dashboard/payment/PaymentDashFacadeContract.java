package sptech.school.v2.cleanarch.core.application.facades.dashboard.payment;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview.OverviewDashResponseDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;

public interface PaymentDashFacadeContract {

    PaymentDashResponseDTO getPaymentDashData();
}
