package turbulent.entity;

public class EntityException extends RuntimeException {

  private static final long serialVersionUID = -5284408380566467615L;

  public EntityException() {
    
  }

  public EntityException(String message) {
    super (message);
  }

  public EntityException(Throwable cause) {
    super (cause);
  }

  public EntityException(String message, Throwable cause) {
    super (message, cause);
  }
}
