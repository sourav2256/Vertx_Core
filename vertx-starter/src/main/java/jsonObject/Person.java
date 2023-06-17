package jsonObject;

public class Person {

  private Integer id;
  private String name;
  private boolean is_in_love;

  public Person() { // default contractor is required to deserialize
    //  deserialize from Object value
  }

  public Person(Integer id, String name, boolean is_in_love) {
    this.id = id;
    this.name = name;
    this.is_in_love = is_in_love;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isIs_in_love() {
    return is_in_love;
  }

  @Override
  public String toString() {
    return "Person{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", is_in_love=" + is_in_love +
      '}';
  }
}
