package demo3.settings_and_preferences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceDatabase extends JpaRepository<preferences, Integer> {
}
