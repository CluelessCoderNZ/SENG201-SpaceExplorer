package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import crew.CrewMemberFactory;
import crew.CrewMemberType;
import crew.Engineer;
import crew.Investor;
import crew.Medic;
import crew.Robot;
import crew.Scientist;
import crew.Student;

class CrewMemberFactoryTest {

  @Test
  void testEnumCreator() {
    assertTrue(
        CrewMemberFactory.createCrewMember(CrewMemberType.INVESTOR, "")
        instanceof Investor);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(CrewMemberType.MEDIC, "")
        instanceof Medic);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(CrewMemberType.ENGINEER, "")
        instanceof Engineer);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(CrewMemberType.STUDENT, "")
        instanceof Student);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(CrewMemberType.SCIENTIST, "")
        instanceof Scientist);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(CrewMemberType.ROBOT, "")
        instanceof Robot);
  }
  
  @Test
  void testIntCreator() {
    assertTrue(
        CrewMemberFactory.createCrewMember(1, "")
        instanceof Investor);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(2, "")
        instanceof Medic);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(3, "")
        instanceof Engineer);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(4, "")
        instanceof Student);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(5, "")
        instanceof Scientist);
    
    assertTrue(
        CrewMemberFactory.createCrewMember(6, "")
        instanceof Robot);
    
    assertThrows(RuntimeException.class, () -> {
      CrewMemberFactory.createCrewMember(0, "");
    });
  }

}
