import hitokun.Person
import hitokun.PersonRepository
import hitokun.PersonService
import spock.lang.Specification

class PersonServiceSpec extends Specification {

  PersonRepository mockRepository
  PersonService service

  def setup(){
    mockRepository = new RepoProxy() as PersonRepository
    mockRepository.deleteAll()

    service = new PersonService(
        personRepository: mockRepository
    )
  }

  def "findByName"(){
    given:
    def saved = mockRepository.save(new Person(name: "toshi"))
    when:
    def res = service.findByName("toshi")
    then:
    res.id == saved.id
  }


}