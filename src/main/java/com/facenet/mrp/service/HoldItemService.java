package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ItemHoldEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.repository.mrp.ItemHoldRepository;
import com.facenet.mrp.repository.sap.OitwRepository;
import com.facenet.mrp.service.dto.DetailItemSyntheticDTO;
import com.facenet.mrp.service.dto.ItemHoldDTO;
import com.facenet.mrp.service.dto.ItemSyntheticDTO;
import com.facenet.mrp.service.dto.SyntheticMrpDTO;
import com.facenet.mrp.service.dto.mrp.CurrentInventory;
import com.facenet.mrp.service.dto.mrp.CurrentWarehouseInventory;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ItemHoldMapper;
import com.facenet.mrp.service.model.ItemHoldInput;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.Utils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HoldItemService {
    private static final Logger log = LoggerFactory.getLogger(HoldItemService.class);
    private final ItemHoldRepository itemHoldRepository;
    private final ItemHoldMapper itemHoldMapper;
    private final OitwRepository oitwRepository;

    public HoldItemService(ItemHoldRepository itemHoldRepository, ItemHoldMapper itemHoldMapper, OitwRepository oitwRepository) {
        this.itemHoldRepository = itemHoldRepository;
        this.itemHoldMapper = itemHoldMapper;
        this.oitwRepository = oitwRepository;
    }

    public List<ItemHoldDTO> getItemHold(String itemCode) {
        List<ItemHoldDTO> result = itemHoldRepository.getAllByItemCode(itemCode);
        if (CollectionUtils.isEmpty(result)) throw new CustomException("record.notfound");
        return result;
    }

    public List<ItemHoldDTO> getItemHold(String itemCode, ItemHoldInput input) {
        List<ItemHoldDTO> result = itemHoldRepository.getAllByItemCode(itemCode,
            DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH),
            Utils.toUTC(input.getTimeEnd()));
        List<CurrentInventory> inStockQuantity = oitwRepository.getInStockQuantityByItemCodeAndWhs(itemCode, input.getWarehouses());
        result.forEach(itemHoldDTO -> itemHoldDTO.setCurrentInventory(inStockQuantity.get(0).getCurrentQuantity()));
        return result;
    }

    public void saveHoldItem(SyntheticMrpDTO syntheticMrpDTO) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ItemHoldEntity> itemHoldEntities = new ArrayList<>();
        for (ItemSyntheticDTO item : syntheticMrpDTO.getResultData()) {
            int numberOfOutOfSpaceWarehouse = 0;

            for (int i = 1; i < item.getDetailData().size(); i++) {
                DetailItemSyntheticDTO itemQuantityDetail = item.getDetailData().get(i);
                // Khong co nhu cau san xuat
                if (itemQuantityDetail.getOriginQuantity() == 0) continue;

                // SL yêu cầu + SL cần mua(C) - SL đã len PR(B)
                double warehouseNeed = itemQuantityDetail.getOriginQuantity(); // need
//                    + itemQuantityDetail.getOriginalRequiredQuantity() // hold
//                    - itemQuantityDetail.getExpectedQuantity(); // pr
//                    - itemQuantityDetail.getSumPoAndDeliveringQuantity(); // pr
                System.err.println(itemQuantityDetail);
                System.err.println("Warehouse need " + warehouseNeed + " item " + item.getItemCode());
                log.info("Warehouse need {} item {}", warehouseNeed, item.getItemCode());
                if (item.getCurrentWarehouseInventoryList() == null || numberOfOutOfSpaceWarehouse == item.getCurrentWarehouseInventoryList().size()) {
                    // Không có tồn ở các kho
                    if (itemQuantityDetail.getRequiredQuantity() > 0) {
                        itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, null,
//                                itemQuantityDetail.getRequiredQuantity(),
                                warehouseNeed,
                                null,
                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark())
                            )
                        );
                        System.err.println("Whs out of stock hold need quantity " + itemQuantityDetail.getRequiredQuantity());
                        log.info("Whs out of stock hold need quantity {}", itemQuantityDetail.getRequiredQuantity());
                    }
                } else {
                    for (CurrentWarehouseInventory currentWarehouseInventory : item.getCurrentWarehouseInventoryList()) {
                        System.err.println((item.getDetailData().get(i).getLandMark()));
                        if (warehouseNeed == 0 || currentWarehouseInventory.getCurrentQuantity() == 0) continue;

//                         PR > Need + Hold
//                        if (warehouseNeed < 0) {
//                            warehouseNeed = itemQuantityDetail.getOriginQuantity();
//                            System.err.println("PR > Need + Hold => new warehouseNeed " + warehouseNeed);
//                            log.info("PR > Need + Hold => new warehouseNeed {}", warehouseNeed);
//                        }

                        // Lấy trong kho
                        if (warehouseNeed > currentWarehouseInventory.getCurrentQuantity()) {
                            warehouseNeed -= currentWarehouseInventory.getCurrentQuantity();
                            // Lấy tất cả ở kho đang xét
                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, null,
                                    currentWarehouseInventory.getCurrentQuantity(),
                                    currentWarehouseInventory.getWarehouseCode(),
                                    simpleDateFormat.parse(item.getDetailData().get(i).getLandMark())
                                )
                            );
                            System.err.println("Warehouse Need > Warehouse (warehouseNeed: " + warehouseNeed + ") (currentWarehouseQuantity: " + currentWarehouseInventory.getCurrentQuantity() + ")" + " (whsCode: " + currentWarehouseInventory.getWarehouseCode() + ")");
                            log.info("Warehouse Need > Warehouse (warehouseNeed: {}) (currentWarehouseQuantity: {}) (whsCode: {})", warehouseNeed, currentWarehouseInventory.getCurrentQuantity(), currentWarehouseInventory.getWarehouseCode());
                            currentWarehouseInventory.setCurrentQuantity(0.0);
                            numberOfOutOfSpaceWarehouse++;
                        } else {
                            currentWarehouseInventory.setCurrentQuantity(currentWarehouseInventory.getCurrentQuantity() - warehouseNeed);
                            // Lấy 1 phần trong kho đang xét
                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, null,
                                warehouseNeed,
                                currentWarehouseInventory.getWarehouseCode(),
                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark())
                            ));
                            System.err.println("Warehouse Need < Warehouse (warehouseNeed: " + warehouseNeed + ") (currentWarehouseQuantity: " + currentWarehouseInventory.getCurrentQuantity() + ")" + " (whsCode: " + currentWarehouseInventory.getWarehouseCode() + ")");
                            log.info("Warehouse Need < Warehouse (warehouseNeed: {}) (currentWarehouseQuantity: {}) (whsCode: {})", warehouseNeed, currentWarehouseInventory.getCurrentQuantity(), currentWarehouseInventory.getWarehouseCode());
                            warehouseNeed = 0;
                        }

                        // Hold tiếp số còn lại nếu kho cuối đã hết hàng
                        if (numberOfOutOfSpaceWarehouse == item.getCurrentWarehouseInventoryList().size()) {
                            System.err.println("All whs out of stock hold remaining " + warehouseNeed);
                            log.info("All whs out of stock hold remaining {}", warehouseNeed);
                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, null,
                                warehouseNeed,
//                                itemQuantityDetail.getRequiredQuantity(), // Hold tiếp phần cần phải mua
                                null,
                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark())
                            ));
                            break;
                        }
                    }
                }
            }
        }
        itemHoldEntities.forEach(holdEntity -> {
            System.err.println("Item " + holdEntity.getItemCode() + " quantity " + holdEntity.getQuantity() + " whs " + holdEntity.getWarehouseCode() + " date: " + simpleDateFormat.format(holdEntity.getHoldDate()));
            log.info("Item {} quantity {} whs {} date {}", holdEntity.getItemCode(), holdEntity.getQuantity(), holdEntity.getWarehouseCode(), simpleDateFormat.format(holdEntity.getHoldDate()));
        });
        itemHoldRepository.saveAll(itemHoldEntities);
    }
}
