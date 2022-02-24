import java.util.*;

public class Person {
  public String name = "";
  public List<Skill> skills;

  public Person(String name, List<Skill> skills) {
    this.name = name;
    this.skills = skills;
  }
}
