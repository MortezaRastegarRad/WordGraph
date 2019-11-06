package cs.sbu.crawl;

public class User {
    private String name;
    private int age;
    public User() {
    }
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }
    @Override public String toString() {
        return "User(" + name + ", " + age + ")";
    }
}