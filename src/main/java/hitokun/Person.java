package hitokun;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class Person {
  Integer id;
  String name;
  Integer age;
  String nickName;
  LocalDate birthDate;

  public Integer getId(){
    return this.id;
  }

  public Integer getAge(){
    return this.age;
  }
}
