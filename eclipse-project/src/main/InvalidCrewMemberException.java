package main;

public class InvalidCrewMemberException extends IllegalArgumentException {

  public InvalidCrewMemberException() {
    super();
  }
  
  public InvalidCrewMemberException(String message) {
    super(message);
  }
}
