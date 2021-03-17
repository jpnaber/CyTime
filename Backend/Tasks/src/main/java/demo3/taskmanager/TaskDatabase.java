package demo3.taskmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * This controller uses a Jpa Repository
 */
@Repository
public interface TaskDatabase extends JpaRepository<myTask, Integer> {
}
