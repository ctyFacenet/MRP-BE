package com.facenet.mrp.repository.sap;

import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.service.dto.ItemWithTypeDTO;
import com.facenet.mrp.service.dto.mrp.ItemEntityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface OitmEntityRepository extends JpaRepository<OitmEntity, String> {

    @Query("select new com.facenet.mrp.service.dto.ItemWithTypeDTO(o.itemCode, o.itemName, o.itmsGrpCod.itmsGrpName, o.buyUnitMsr) from OitmEntity o")
    List<ItemWithTypeDTO> getAllByItemCodeIn(List<String> itemList);

    default Map<String, ItemWithTypeDTO> itemMap(List<String> itemList) {
        return getAllByItemCodeIn(itemList).stream().collect(
            Collectors.toMap(ItemWithTypeDTO::getItemCode, Function.identity())
        );
    }

    @Query("select new com.facenet.mrp.service.dto.mrp.ItemEntityDto(o.itemCode, o.itemName) from OitmEntity o")
    List<ItemEntityDto> findAllItemsFromSap();
}
