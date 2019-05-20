package crew;

/**
 * unchecked exception to be thrown when an invalid crew member is used.
 */
public class InvalidCrewMemberException extends IllegalArgumentException {

  /**
   * creates an InvalidCrewMemberException.
   */
  public InvalidCrewMemberException() {
    super();
  }
  
  /**
   * creates an InvalidCrewMemberException with a custom message.
   * @param message custom message string
   */
  public InvalidCrewMemberException(String message) {
    super(message);
  }
}
