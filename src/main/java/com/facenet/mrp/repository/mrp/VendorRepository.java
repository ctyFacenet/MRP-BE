package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface VendorRepository extends JpaRepository<VendorEntity, Integer> {
    List<VendorEntity> getAllByVendorCodeIn(Collection<String> itemList);

    default Map<String, VendorEntity> getAllByVendorCodeInMap(Collection<String> itemList) {
        return getAllByVendorCodeIn(itemList).stream()
            .collect(Collectors.toMap(VendorEntity::getVendorCode, Function.identity()));
    }
    Boolean existsAllByVendorCode(String vendorCode);
}
