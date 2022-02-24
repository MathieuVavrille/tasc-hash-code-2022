import java.util.*;

public class Person implements Comparable<Person> {

  public String name = "";
  public int nextTimeAvailable = 0;
  public int initialSkillsLength;
  public List<Skill> skills;

  public Person(String name, List<Skill> skills) {
    this.name = name;
    initialSkillsLength = skills.size();
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

  public boolean canAlmostDo(Skill requiredSkill) {
    return requiredSkill.level == 1 || skills.stream().anyMatch(skill -> skill.name.equals(requiredSkill.name) && skill.level == requiredSkill.level - 1);
  }

  public void increaseSkill(String name) {
    for (Skill skill : skills) {
      if (skill.name.equals(name)) {
        skill.level += 1;
        return;
      }
    }
    skills.add(new Skill(name, 1));
  }

  public int getSkillLevel(String name) {
    for (Skill skill : skills) {
      if (skill.name.equals(name))
        return skill.level;
    }
    return 0;
  }

  public void doImproveSkillOrNot(Skill skill) {
    if (getSkillLevel(skill.name) == skill.level-1) {
      increaseSkill(skill.name);
    }
  }
}
