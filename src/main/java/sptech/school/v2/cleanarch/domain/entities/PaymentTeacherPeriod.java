package sptech.school.v2.cleanarch.domain.entities;

import jakarta.persistence.*;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;

@Entity
@Table(name = "tb_payment_teacher_period")
public class PaymentTeacherPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkTeacher", nullable = false)
    private Teacher teacher;

    @Column(name = "period_month", nullable = false)
    private int month;

    @Column(name = "period_year", nullable = false)
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public PaymentTeacherPeriod() {}

    public PaymentTeacherPeriod(Teacher teacher, int month, int year, PaymentStatus status) {
        this.teacher = teacher;
        this.month = month;
        this.year = year;
        this.status = status;
    }

    public Integer getId() { return id; }
    public Teacher getTeacher() { return teacher; }
    public int getMonth() { return month; }
    public int getYear() { return year; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}

