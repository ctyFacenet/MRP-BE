package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MrpDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MrpDetailRepository extends JpaRepository<MrpDetailEntity,Long> {
}
