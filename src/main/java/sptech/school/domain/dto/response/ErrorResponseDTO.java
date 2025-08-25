package sptech.school.domain.dto.response;

public class ErrorResponseDTO {
    private int status;
    private String error;
    private String message;
    private String trace;

    public ErrorResponseDTO(int status, String error, String message, String trace) {
        this.status  = status;
        this.error   = error;
        this.message = message;
        this.trace   = trace;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

}
