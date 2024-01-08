package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.AlertItemSoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertItemSoRepository extends JpaRepository<AlertItemSoEntity,Long> {
}
