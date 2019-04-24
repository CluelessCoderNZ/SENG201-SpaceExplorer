package main;

public class DeadCrewMemberException extends Exception {

  public DeadCrewMemberException() {
    super();
  }
  
  public DeadCrewMemberException(String message) {
    super(message);
  }
}
