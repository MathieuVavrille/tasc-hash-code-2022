import java.util.List;

public class Project {

  public String name;
  public int duration;
  public int score;
  public int bestBefore;
  public List<Skill> roles;

  public Project(String name, int duration, int score, int bestBefore, List<Skill> roles) {
    this.name = name;
    this.duration = duration;
    this.score = score;
    this.bestBefore = bestBefore;
    this.roles = roles;
  }

  public List<Person> whoCanDoIt(List<Person> persons) {
    List<Person> canDoIt = new ArrayList<Person>();
    Set<Integer> usedPersons = new HashSet<Integer>();
    for (Skill skill : skills) {
      boolean done = false;
      for (int i = 0; i < persons.size(); i++) {
        if (!usedPersons.contains(i) && persons.get(i).canDo(skill)) {
          usedPersons.add(i);
          canDoIt.add(persons.get(i));
          i = persons.size();
          done = true;
        }
      }
      if (!done)
        return null;
    }
    return canDoIt;
  }
}
