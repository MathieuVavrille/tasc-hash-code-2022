import java.util.*;

public class Person {
  public String name = "";
  public List<Skill> skills;

  public Person(String name, List<Skill> skills) {
    this.name = name;
    this.skills = skills;
  }

  public boolean canDo(Skill requiredSkill) {
    for (Skill s : skills) {
      if (s.name.equals(requiredSkill.name) && s.level >= requiredSkill.level) return true;
    }
    return false;
  }
}
