package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.MrpOrderQuantityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MrpOrderQuantityRepository extends JpaRepository<MrpOrderQuantityEntity, Long> {
    List<MrpOrderQuantityEntity> findByMrpSubCode(String mrpSubCode);
}
