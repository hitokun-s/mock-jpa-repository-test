package hitokun;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "person", path = "person")
public interface PersonRepository extends JpaRepository<Person, Integer> {

  Person findByName(String name);
  Person findByNickName(String name);

  Person findByNameAndAge(String name, Integer age);
  List<Person> findAllByAgeGreaterThanEqual(Integer age);
  List<Person> findAllByAgeGreaterThanOrderByAge(Integer age);
  List<Person> findAllByAgeGreaterThanOrderByAgeDesc(Integer age);

  Person findFirstByAgeGreaterThanOrderByAge(Integer age);

  List<Person> findAllByAgeIn(List<Integer> ages);

  List<Person> findAllByNameAndAgeIn(String name, List<Integer> ages);

  List<Person> findAllByAgeGreaterThanAndAgeLessThan(Integer age1, Integer age2);

  List<Person> findAllByBirthDateGreaterThan(LocalDate date);

//  List<ActualResult> findByDateGreaterThanEqualAndDateLessThan(
//      LocalDateTime from, LocalDateTime to);
//
//  List<ActualResult> findByDateLessThanAndStatus(LocalDateTime date, ActualResultStatus status);
//
//  List<ActualResult> findByProductIdInAndStatus(
//      List<Integer> productIds, ActualResultStatus status);
//
//  List<ActualResult> findByDateAndProductIdAndProductItemIdAndServiceIdAndServiceItemId(
//      LocalDateTime date, Integer productId, Integer productItemId, String serviceId,
//      String serviceItemId);

}
