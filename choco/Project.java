import java.util.*;

public class Project implements Comparable<Project> {

  public String name;
  public int duration;
  public int score;
  public int bestBefore;
  public List<Skill> skills;

  public Project(String name, int duration, int score, int bestBefore, List<Skill> skills) {
    this.name = name;
    this.duration = duration;
    this.score = score;
    this.bestBefore = bestBefore;
    this.skills = skills;
  }

  public List<Person> whoCanDoIt(List<Person> people) {
    List<Person> canDoIt = new ArrayList<Person>();
    Set<Person> usedPeople = new HashSet<Person>();
    for (Skill skill : skills) {
      Person fastestPerson = null;
      int fastestTime = Integer.MAX_VALUE;
      for (Person p : people) {
        if (!usedPeople.contains(p)) {
          if (p.canDo(skill) || p.canAlmostDo(skill) && skill.hasMentor(canDoIt)) {
            if (p.nextTimeAvailable < fastestTime) {
              fastestTime = p.nextTimeAvailable;
              fastestPerson = p;
            }
          }
        }
      }
      if (fastestPerson == null) {
        return null;
      }
      usedPeople.add(fastestPerson);
      canDoIt.add(fastestPerson);
    }
    return canDoIt;
  }

  public double getRank() {
    return (double) score / duration;
  }

  @Override
  public int compareTo(Project p) {
    if (getRank() < p.getRank()) return -1;
    if (getRank() > p.getRank()) return 1;
    return 0;
  }
}
