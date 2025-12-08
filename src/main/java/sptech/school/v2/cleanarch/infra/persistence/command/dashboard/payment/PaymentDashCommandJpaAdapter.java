package sptech.school.v2.cleanarch.infra.persistence.command.dashboard.payment;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment.PaymentDashCommandGateway;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.appointment.AppointmentJpaRepository;

@Component
public class PaymentDashCommandJpaAdapter implements PaymentDashCommandGateway {

    private final AppointmentJpaRepository appointmentRepository;
    private final sptech.school.v2.cleanarch.infra.persistence.repository.payment.PaymentTeacherPeriodRepository paymentTeacherPeriodRepository;
    private final sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherRepository teacherRepository;

    public PaymentDashCommandJpaAdapter(AppointmentJpaRepository appointmentRepository,
            sptech.school.v2.cleanarch.infra.persistence.repository.payment.PaymentTeacherPeriodRepository paymentTeacherPeriodRepository,
            sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherRepository teacherRepository) {
        this.appointmentRepository = appointmentRepository;
        this.paymentTeacherPeriodRepository = paymentTeacherPeriodRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional
    public PaymentStatus togglePaymentStatus(Integer appointmentId, int month, int year) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Appointment não encontrado para id: " + appointmentId));

        if (appointment.getDateTime().getMonthValue() != month || appointment.getDateTime().getYear() != year) {
            throw new IllegalArgumentException("Appointment não está no período informado");
        }

        PaymentStatus current = appointment.getPaymentStatus();
        PaymentStatus next;
        if (current == PaymentStatus.PENDING) {
            next = PaymentStatus.PAID;
        } else if (current == PaymentStatus.PAID) {
            next = PaymentStatus.PENDING;
        } else {
            next = current;
        }
        appointment.setPaymentStatus(next);
        appointmentRepository.save(appointment);
        return next;
    }

    @Override
    @Transactional
    public Object togglePaymentsStatus(Integer teacherId, int month, int year) {
        var teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));

        var paymentPeriod = paymentTeacherPeriodRepository.findByTeacherIdAndMonthAndYear(teacherId, month, year)
                .orElse(new sptech.school.v2.cleanarch.domain.entities.PaymentTeacherPeriod(teacher, month, year,
                        PaymentStatus.PENDING));

        PaymentStatus current = paymentPeriod.getStatus();
        PaymentStatus next;
        if (current == PaymentStatus.PENDING) {
            next = PaymentStatus.PAID;
        } else if (current == PaymentStatus.PAID) {
            next = PaymentStatus.PENDING;
        } else {
            next = current;
        }
        paymentPeriod.setStatus(next);
        paymentTeacherPeriodRepository.save(paymentPeriod);

        return paymentPeriod;
    }
}
