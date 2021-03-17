package demo4.mastercontroller;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface userDB extends JpaRepository<userLogin, Integer> {
}
