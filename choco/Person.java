import java.util.*;

public class Person implements Comparable<Person> {

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

  @Override
  public int compareTo(Person p) {
    int sumLevel = skills.stream().mapToInt(s -> s.level).sum();
    int otherSumLevel = p.skills.stream().mapToInt(s -> s.level).sum();
    return Integer.compare(sumLevel, otherSumLevel);
  }
}
