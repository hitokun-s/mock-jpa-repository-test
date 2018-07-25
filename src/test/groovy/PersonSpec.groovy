import hitokun.Person
import hitokun.PersonRepository
import spock.lang.Specification

import java.time.LocalDate

class PersonSpec extends Specification {

  PersonRepository mockRepository

  def setup(){
    mockRepository = new RepoProxy() as PersonRepository
    mockRepository.deleteAll()
  }

  def "saveAll"(){
    when:
    def res = mockRepository.saveAll([
        new Person(name: "toshi"),
        new Person(name: "toshi2")
    ])
    then:
    res.size() == 2
  }

  def "findByName"(){
    given:
    assert mockRepository.findByName("toshi") == null
    when:
    mockRepository.save(new Person(name: "toshi"))
    then:
    mockRepository.findByName("toshi").id == 1
  }

  def "findByNickName"(){
    when:
    mockRepository.save(new Person(name: "Hitoshi Wada", nickName: "toshi"))
    then:
    mockRepository.findByNickName("toshi")
  }

  def "findByNameAndAge"(){
    when:
    mockRepository.save(new Person(name: "toshi", age: 35))
    then:
    mockRepository.findByNameAndAge("toshi", 35)
  }

  def "findAllByAgeGreaterThanEqual"(){
    when:
    mockRepository.save(new Person(name: "toshi1", age: 35))
    mockRepository.save(new Person(name: "toshi2", age: 36))
    then:
    mockRepository.findAllByAgeGreaterThanEqual(35).size() == 2
  }

  def "findAllByAgeGreaterThanOrderByAge"(){
    when:
    mockRepository.save(new Person(name: "toshi1", age: 35))
    mockRepository.save(new Person(name: "toshi2", age: 36))
    mockRepository.save(new Person(name: "toshi3", age: 37))
    then:
    mockRepository.findAllByAgeGreaterThanOrderByAge(35).collect{it.age} == [36,37]
  }

  def "findAllByAgeGreaterThanOrderByAgeDesc"(){
    when:
    mockRepository.save(new Person(name: "toshi1", age: 35))
    mockRepository.save(new Person(name: "toshi2", age: 36))
    mockRepository.save(new Person(name: "toshi3", age: 37))
    then:
    mockRepository.findAllByAgeGreaterThanOrderByAgeDesc(35).collect{it.age} == [37, 36]
  }

  def "findFirstByAgeGreaterThanOrderByAge"(){
    when:
    mockRepository.save(new Person(name: "toshi1", age: 35))
    mockRepository.save(new Person(name: "toshi2", age: 36))
    mockRepository.save(new Person(name: "toshi3", age: 37))
    then:
    mockRepository.findFirstByAgeGreaterThanOrderByAge(35).age == 36
  }

  def "findAllByAgeIn"(){
    when:
    mockRepository.save(new Person(name: "toshi1", age: 35))
    mockRepository.save(new Person(name: "toshi2", age: 40))
    mockRepository.save(new Person(name: "toshi3", age: 45))
    then:
    mockRepository.findAllByAgeIn([35, 40]).size() == 2
  }

  def "findAllByNameAndAgeIn"(){
    when:
    mockRepository.save(new Person(name: "toshi1", age: 35))
    mockRepository.save(new Person(name: "toshi2", age: 40))
    mockRepository.save(new Person(name: "toshi3", age: 45))
    then:
    mockRepository.findAllByNameAndAgeIn("toshi1", [35, 40]).size() == 1
  }

  def "findAllByAgeGreaterThanAndAgeLessThan"(){
    when:
    mockRepository.save(new Person(name: "toshi1", age: 30))
    mockRepository.save(new Person(name: "toshi2", age: 31))
    mockRepository.save(new Person(name: "toshi3", age: 32))
    then:
    mockRepository.findAllByAgeGreaterThanAndAgeLessThan(29, 33).size() == 3
    mockRepository.findAllByAgeGreaterThanAndAgeLessThan(30, 32).size() == 1
  }

  def "findAllByBirthDateGreaterThan"(){
    when:
    mockRepository.save(new Person(name: "toshi1", birthDate: LocalDate.parse("1980-01-01")))
    mockRepository.save(new Person(name: "toshi2", birthDate: LocalDate.parse("1980-01-15")))
    mockRepository.save(new Person(name: "toshi3", birthDate: LocalDate.parse("1980-02-01")))
    then:
    mockRepository.findAllByBirthDateGreaterThan(LocalDate.parse("1980-01-01")).size() == 2
  }


}