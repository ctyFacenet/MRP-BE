package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    @Query("select i.itemCode from ItemEntity i join VendorItemEntity v on i.itemCode = v.itemCode " +
        "where v.vendorCode = :vendorCode " +
        "and (:itemCode is null or i.itemCode like '%' + :itemCode + '%') " +
        "and (:itemName is null or i.itemName like '%' + :itemName + '%')")
    Page<String> getAllByVendorCode(@Param("vendorCode") String vendorCode,
                                            @Param("itemCode") String itemCode,
                                            @Param("itemName") String itemName,
                                            Pageable pageable);

    List<ItemEntity> getAllByItemCodeIn(Collection<String> itemList);

    default Map<String, ItemEntity> getAllByItemCodeInMap(Collection<String> itemList) {
        return getAllByItemCodeIn(itemList).stream().collect(Collectors.toMap(ItemEntity::getItemCode, Function.identity()));
    }

    Boolean existsAllByItemCode(String itemCode);

    ItemEntity findByItemCode(String itemCode);
}
