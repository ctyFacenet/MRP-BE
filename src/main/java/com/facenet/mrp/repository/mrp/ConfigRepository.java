package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConfigRepository extends JpaRepository<ConfigEntity, Integer> {
    @Query("select c.value from ConfigEntity c where c.name = :name")
    Optional<String> getValueByName(@Param("name") String name);
}
