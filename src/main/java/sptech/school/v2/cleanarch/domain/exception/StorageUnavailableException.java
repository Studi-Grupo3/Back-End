package sptech.school.v2.cleanarch.domain.exception;

public class StorageUnavailableException extends RuntimeException {
  public StorageUnavailableException(String message) {
    super(message);
  }
}
