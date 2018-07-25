package hitokun;

public class PersonService {

  public PersonRepository personRepository;

  public Person findByName(String name){
    return personRepository.findByName(name);
  }

}
