package com.demo4.preferences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface preferenceDB extends JpaRepository<preferences, Integer> {
}
