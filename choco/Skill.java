import java.util.List;

public class Skill {

    public String name;
    public int level;

    public Skill(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public boolean hasMentor(List<Person> persons) {
        return persons.stream().anyMatch(p -> p.canDo(this));
    }
}
