package sptech.school.v2.cleanarch.core.application.usecases.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherAvailabilitySlotDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherAvailabilityResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.entities.TeacherAvailability;
import sptech.school.v2.cleanarch.infra.persistence.repository.appointment.AppointmentJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherAvailabilityJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherJpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherAvailabilityUseCase {

    private final TeacherAvailabilityJpaRepository availabilityRepository;
    private final TeacherJpaRepository teacherRepository;
    private final AppointmentJpaRepository appointmentRepository;

    public TeacherAvailabilityUseCase(TeacherAvailabilityJpaRepository availabilityRepository,
                                      TeacherJpaRepository teacherRepository,
                                      AppointmentJpaRepository appointmentRepository) {
        this.availabilityRepository = availabilityRepository;
        this.teacherRepository = teacherRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<TeacherAvailabilityResponseDTO> getByTeacherId(Integer teacherId) {
        List<TeacherAvailability> slots = availabilityRepository.findByTeacherId(teacherId);
        return slots.stream()
                .map(s -> new TeacherAvailabilityResponseDTO(
                        s.getId(),
                        s.getDayOfWeek().name(),
                        s.getStartTime().toString(),
                        s.getEndTime().toString()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TeacherAvailabilityResponseDTO> replaceAll(Integer teacherId, List<TeacherAvailabilitySlotDTO> slots) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found."));

        availabilityRepository.deleteByTeacherId(teacherId);

        List<TeacherAvailability> entities = new ArrayList<>();
        for (TeacherAvailabilitySlotDTO slot : slots) {
            DayOfWeek day = DayOfWeek.valueOf(slot.dayOfWeek().toUpperCase());
            LocalTime start = LocalTime.parse(slot.startTime());
            LocalTime end = LocalTime.parse(slot.endTime());

            if (!end.isAfter(start)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "End time must be after start time: " + slot.startTime() + " - " + slot.endTime());
            }

            TeacherAvailability entity = new TeacherAvailability(teacher, day, start, end);
            entities.add(entity);
        }

        List<TeacherAvailability> saved = availabilityRepository.saveAll(entities);

        return saved.stream()
                .map(s -> new TeacherAvailabilityResponseDTO(
                        s.getId(),
                        s.getDayOfWeek().name(),
                        s.getStartTime().toString(),
                        s.getEndTime().toString()
                ))
                .collect(Collectors.toList());
    }

    public List<String> getAvailableSlotsForDate(Integer teacherId, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // Get teacher's availability windows for this day of week
        List<TeacherAvailability> windows = availabilityRepository.findByTeacherIdAndDayOfWeek(teacherId, dayOfWeek);

        if (windows.isEmpty()) {
            return List.of();
        }

        // Generate all possible 1-hour slots from availability windows
        List<LocalTime> allSlots = new ArrayList<>();
        for (TeacherAvailability window : windows) {
            LocalTime current = window.getStartTime();
            while (current.isBefore(window.getEndTime())) {
                allSlots.add(current);
                current = current.plusHours(1);
            }
        }

        // Get existing appointments for this teacher on this date
        int month = date.getMonthValue();
        int year = date.getYear();
        List<Appointment> monthAppointments = appointmentRepository.findByTeacherIdAndMonthAndYear(teacherId, month, year);

        // Filter out slots that conflict with existing appointments
        List<LocalTime> bookedTimes = monthAppointments.stream()
                .filter(a -> a.getDateTime().toLocalDate().equals(date))
                .map(a -> a.getDateTime().toLocalTime())
                .collect(Collectors.toList());

        List<String> available = allSlots.stream()
                .filter(slot -> !bookedTimes.contains(slot))
                .sorted()
                .distinct()
                .map(t -> t.toString().substring(0, 5)) // "HH:mm" format
                .collect(Collectors.toList());

        return available;
    }

    public boolean isTeacherAvailableAt(Integer teacherId, LocalDate date, LocalTime time) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<TeacherAvailability> windows = availabilityRepository.findByTeacherIdAndDayOfWeek(teacherId, dayOfWeek);

        if (windows.isEmpty()) {
            return true; // If no availability is configured, allow any time (backward compatible)
        }

        return windows.stream().anyMatch(w ->
                (time.equals(w.getStartTime()) || time.isAfter(w.getStartTime())) && time.isBefore(w.getEndTime())
        );
    }
}
