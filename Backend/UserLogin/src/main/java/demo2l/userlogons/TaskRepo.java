package demo2l.userlogons;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends CrudRepository<Task, Integer> {

    Task findTaskById(Integer id);

    Iterable<Task> findAllByUserName(String userName);
}
