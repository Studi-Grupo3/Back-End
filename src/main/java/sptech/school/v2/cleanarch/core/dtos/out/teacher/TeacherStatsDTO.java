package sptech.school.v2.cleanarch.core.dtos.out.teacher;

public class TeacherStatsDTO {
    private Long aulasHoje;
    private String aulasHojeSubtitle;
    private Long aulasSemana;
    private String aulasSemanaSubtitle;
    private String horasMinistradas;         // ex.: "2h 30m" ou "45m"
    private String horasMinistradasSubtitle; // ex.: "Concluídas"

    public TeacherStatsDTO() { }

    public TeacherStatsDTO(Long aulasHoje, String aulasHojeSubtitle,
                           Long aulasSemana, String aulasSemanaSubtitle,
                           String horasMinistradas, String horasMinistradasSubtitle) {
        this.aulasHoje = aulasHoje;
        this.aulasHojeSubtitle = aulasHojeSubtitle;
        this.aulasSemana = aulasSemana;
        this.aulasSemanaSubtitle = aulasSemanaSubtitle;
        this.horasMinistradas = horasMinistradas;
        this.horasMinistradasSubtitle = horasMinistradasSubtitle;
    }

    // Getters e setters

    public Long getAulasHoje() {
        return aulasHoje;
    }
    public void setAulasHoje(Long aulasHoje) {
        this.aulasHoje = aulasHoje;
    }
    public String getAulasHojeSubtitle() {
        return aulasHojeSubtitle;
    }
    public void setAulasHojeSubtitle(String aulasHojeSubtitle) {
        this.aulasHojeSubtitle = aulasHojeSubtitle;
    }
    public Long getAulasSemana() {
        return aulasSemana;
    }
    public void setAulasSemana(Long aulasSemana) {
        this.aulasSemana = aulasSemana;
    }
    public String getAulasSemanaSubtitle() {
        return aulasSemanaSubtitle;
    }
    public void setAulasSemanaSubtitle(String aulasSemanaSubtitle) {
        this.aulasSemanaSubtitle = aulasSemanaSubtitle;
    }
    public String getHorasMinistradas() {
        return horasMinistradas;
    }
    public void setHorasMinistradas(String horasMinistradas) {
        this.horasMinistradas = horasMinistradas;
    }
    public String getHorasMinistradasSubtitle() {
        return horasMinistradasSubtitle;
    }
    public void setHorasMinistradasSubtitle(String horasMinistradasSubtitle) {
        this.horasMinistradasSubtitle = horasMinistradasSubtitle;
    }
}
