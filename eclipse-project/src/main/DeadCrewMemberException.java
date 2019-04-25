package main;

public class DeadCrewMemberException extends IllegalArgumentException {

  public DeadCrewMemberException() {
    super();
  }
  
  public DeadCrewMemberException(String message) {
    super(message);
  }
}
