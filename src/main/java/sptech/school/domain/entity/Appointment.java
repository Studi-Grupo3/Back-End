package sptech.school.domain.entity;

import jakarta.persistence.*;
import sptech.school.domain.enumerated.AppointmentStatus;
import sptech.school.domain.enumerated.PaymentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkStudent", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fkTeacher", nullable = false)
    private Teacher teacher;

    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime dateTime;

    private Double lessonDuration;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String location;

    @Column(nullable = false)
    private Double totalValue;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Appointment() {
    }

    public Appointment(Integer id, Student student, Teacher teacher, LocalDateTime dateTime, Double lessonDuration, AppointmentStatus status, String location, Double totalValue, PaymentStatus paymentStatus) {
        this.id = id;
        this.student = student;
        this.teacher = teacher;
        this.dateTime = dateTime;
        this.lessonDuration = lessonDuration;
        this.status = status;
        this.location = location;
        this.totalValue = totalValue;
        this.paymentStatus = paymentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getLessonDuration() {
        return lessonDuration;
    }

    public void setLessonDuration(Double lessonDuration) {
        this.lessonDuration = lessonDuration;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

}