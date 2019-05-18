package main;

public class Student extends CrewMember {

  public Student(String name) {
    super(name, "Student", 50, 50, 50, 3);
    addEffect(CrewMemberEffect.STRESSED);
  }
  
  public static String getClassDescription() {
    return String.format("Has 3 AP but has low stats");
  }

}
