package sptech.school.v2.cleanarch.core.dtos.out.teacher;

public class WeekdayStatsDTO {

    private Integer weekday;
    private Long count;

    public WeekdayStatsDTO(Integer weekday, Long count) {
        this.weekday = weekday;
        this.count = count;
    }

    // ← ADICIONE este construtor para primitivos
    public WeekdayStatsDTO(int weekday, long count) {
        this.weekday = weekday;
        this.count = count;
    }

    public WeekdayStatsDTO() {
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
