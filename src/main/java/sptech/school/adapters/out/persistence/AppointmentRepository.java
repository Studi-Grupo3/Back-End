package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.domain.dto.response.teacher.DisciplineStatsDTO;
import sptech.school.domain.dto.response.teacher.WeekdayStatsDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.enumerated.AppointmentStatus;
import sptech.school.domain.enumerated.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    // Verifica existência de conflito de horário
    boolean existsByStudentIdAndTeacherIdAndDateTime(Integer userStudentId, Integer userTeacherId, LocalDateTime dateTime);

    // Métodos genéricos que você pode manter conforme uso em outras partes:
    List<Appointment> findByDateTimeBetween(LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<Appointment> findByPaymentStatus(PaymentStatus paymentStatus);

    // === Substituir os antigos findByTeacherIdAndDateTimeAfter/Before por status-based ===

    /**
     * Próximas aulas (aulas agendadas ainda não realizadas): status = SCHEDULED.
     * Ordena por dateTime ascendente.
     */
    List<Appointment> findByTeacherIdAndStatusOrderByDateTime(Integer teacherId, AppointmentStatus status);

    /**
     * Histórico de aulas: status COMPLETED ou CANCELLED, ordenado por dateTime descendente.
     */
    List<Appointment> findByTeacherIdAndStatusInOrderByDateTimeDesc(Integer teacherId, List<AppointmentStatus> statuses);

    /**
     * Busca no histórico por nome do aluno (case-insensitive) e status em COMPLETED/CANCELLED.
     */
    @Query("SELECT a FROM Appointment a " +
            "WHERE a.teacher.id = :teacherId " +
            "  AND a.status IN :statuses " +
            "  AND LOWER(a.student.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "ORDER BY a.dateTime DESC")
    List<Appointment> searchHistoryByTeacherAndStudentNameAndStatusIn(
            @Param("teacherId") Integer teacherId,
            @Param("statuses") List<AppointmentStatus> statuses,
            @Param("search") String search);

    /**
     * Soma de duração (em minutos) para status COMPLETED, para stats.
     */
    @Query("SELECT SUM(a.lessonDuration) FROM Appointment a " +
            "WHERE a.teacher.id = :teacherId " +
            "  AND a.status = :statusCompleted")
    Double sumLessonDurationByTeacherIdAndStatus(
            @Param("teacherId") Integer teacherId,
            @Param("statusCompleted") AppointmentStatus statusCompleted);

    Long countByTeacherId(Integer teacherId);
    Long countByTeacherIdAndStatus(Integer teacherId, AppointmentStatus status);

    @Query("SELECT COALESCE(SUM(a.lessonDuration),0) " +
            "FROM Appointment a WHERE a.teacher.id = :teacherId")
    Double sumLessonDurationByTeacherId(Integer teacherId);

    // Estatística por disciplina
    @Query("SELECT new sptech.school.domain.dto.response.teacher.DisciplineStatsDTO(" +
            "  s, COUNT(a)) " +
            "FROM Appointment a JOIN a.teacher.subjects s " +
            "WHERE a.teacher.id = :teacherId " +
            "GROUP BY s")
    List<DisciplineStatsDTO> countByTeacherGroupBySubject(Integer teacherId);

    @Query("""
  SELECT new sptech.school.domain.dto.response.teacher.WeekdayStatsDTO(
    CAST(FUNCTION('DAYOFWEEK', a.dateTime) AS integer),
    CAST(COUNT(a)                       AS long)
  )
  FROM Appointment a
  WHERE a.teacher.id = :teacherId
  GROUP BY CAST(FUNCTION('DAYOFWEEK', a.dateTime) AS integer)
  ORDER BY CAST(FUNCTION('DAYOFWEEK', a.dateTime) AS integer)
""")
    List<WeekdayStatsDTO> countByTeacherGroupByWeekday(Integer teacherId);

    List<Appointment> findByTeacherId(Integer teacherId);
    List<Appointment> findByTeacherIdAndStatus(Integer teacherId, AppointmentStatus status);
}
