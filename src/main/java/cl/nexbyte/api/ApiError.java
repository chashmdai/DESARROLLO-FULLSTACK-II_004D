package cl.nexbyte.api;

import java.time.OffsetDateTime;

public class ApiError {
  private OffsetDateTime timestamp;
  private String path;
  private int status;
  private String error;
  private String message;
  private Object details;

  public ApiError() {}
  public ApiError(OffsetDateTime timestamp, String path, int status, String error, String message, Object details) {
    this.timestamp = timestamp;
    this.path = path;
    this.status = status;
    this.error = error;
    this.message = message;
    this.details = details;
  }

  public OffsetDateTime getTimestamp() { return timestamp; }
  public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
  public String getPath() { return path; }
  public void setPath(String path) { this.path = path; }
  public int getStatus() { return status; }
  public void setStatus(int status) { this.status = status; }
  public String getError() { return error; }
  public void setError(String error) { this.error = error; }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
  public Object getDetails() { return details; }
  public void setDetails(Object details) { this.details = details; }
}
