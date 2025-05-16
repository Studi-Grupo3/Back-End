package sptech.school.domain.dto.response.dashboard.payment;

public class PaymentTableDTO {
    private Integer id;
    private String name;
    private String subject;
    private double valuePerHour;
    private int hours;
    private double total;
    private String status;

    public PaymentTableDTO() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public double getValuePerHour() { return valuePerHour; }
    public void setValuePerHour(double valuePerHour) { this.valuePerHour = valuePerHour; }

    public int getHours() { return hours; }
    public void setHours(int hours) { this.hours = hours; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
