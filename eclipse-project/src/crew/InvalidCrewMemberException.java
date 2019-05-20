package crew;

public class InvalidCrewMemberException extends IllegalArgumentException {

  public InvalidCrewMemberException() {
    super();
  }
  
  public InvalidCrewMemberException(String message) {
    super(message);
  }
}
