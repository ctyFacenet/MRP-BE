package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ItemHoldEntity;
import com.facenet.mrp.domain.mrp.PurchaseRecommendationEntity;
import com.facenet.mrp.repository.mrp.ItemHoldRepository;
import com.facenet.mrp.repository.mrp.WarehouseEntityRepository;
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
import org.keycloak.crypto.KeyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HoldItemService {
    private static final Logger log = LoggerFactory.getLogger(HoldItemService.class);
    private final ItemHoldRepository itemHoldRepository;
    private final ItemHoldMapper itemHoldMapper;
    private final OitwRepository oitwRepository;
    private final WarehouseEntityRepository warehouseEntityRepository;

    public HoldItemService(ItemHoldRepository itemHoldRepository, ItemHoldMapper itemHoldMapper, OitwRepository oitwRepository,
                           WarehouseEntityRepository warehouseEntityRepository) {
        this.itemHoldRepository = itemHoldRepository;
        this.itemHoldMapper = itemHoldMapper;
        this.oitwRepository = oitwRepository;
        this.warehouseEntityRepository = warehouseEntityRepository;
    }

    public List<ItemHoldDTO> getItemHold(String itemCode) {
        List<ItemHoldDTO> result = itemHoldRepository.getAllByItemCode(itemCode);
        if (CollectionUtils.isEmpty(result)) throw new CustomException("record.notfound");
        return result;
    }

    public List<ItemHoldDTO> getItemHold(String itemCode, ItemHoldInput input) {
        boolean isFirstTime = input.getMrpSubCode().split("\\.")[1].equals("1");
        List<ItemHoldDTO> result;
        if (isFirstTime) {
            result = itemHoldRepository.getAllByItemCode(itemCode,
                DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH),
                Utils.toUTC(input.getTimeEnd()));
        } else {
            result = itemHoldRepository.getAllByItemCodeExceptMrpCode(itemCode,
                DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH),
                Utils.toUTC(input.getTimeEnd()),
                input.getMrpSubCode().split("\\.")[0] + "."
            );
        }

        List<CurrentInventory> inStockQuantity = oitwRepository.getInStockQuantityByItemCodeAndWhs(itemCode, input.getWarehouses());
        List<CurrentInventory> inStockQuantity2 = warehouseEntityRepository.getInStockQuantityByItemCodeAndWhs(itemCode, input.getWarehouses());
        // Kết hợp hai danh sách
        List<CurrentInventory> combinedInventory = new ArrayList<>(inStockQuantity);
        combinedInventory.addAll(inStockQuantity2);

        // Nhóm theo itemCode và cộng tổng số lượng
        Map<String, Double> summedInventoryMap = combinedInventory.stream()
            .collect(Collectors.groupingBy(
                CurrentInventory::getItemCode,
                Collectors.summingDouble(CurrentInventory::getCurrentQuantity)
            ));

        // Chuyển đổi kết quả về dạng List<CurrentInventory>
        List<CurrentInventory> finalInventoryList = summedInventoryMap.entrySet().stream()
            .map(entry -> new CurrentInventory(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
        result.forEach(itemHoldDTO -> itemHoldDTO.setCurrentInventory(finalInventoryList.get(0).getCurrentQuantity()));
        return result;
    }

    public List<ItemHoldEntity> saveHoldItem(SyntheticMrpDTO syntheticMrpDTO) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ItemHoldEntity> itemHoldEntities = new ArrayList<>();
        for (ItemSyntheticDTO item : syntheticMrpDTO.getResultData()) {
            int numberOfOutOfSpaceWarehouse = 0;
            System.err.println("Item " + item.getItemCode());
            for (int i = 1; i < item.getDetailData().size(); i++) {
                DetailItemSyntheticDTO itemQuantityDetail = item.getDetailData().get(i);
                // Khong co nhu cau san xuat
                if (itemQuantityDetail.getOriginQuantity() == 0) continue;
                itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
//                                itemQuantityDetail.getRequiredQuantity(),
                        itemQuantityDetail.getOriginQuantity(),
                        null,
                        simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
                        Constants.ItemHold.ACTIVE
                    )
                );
                // SL yêu cầu + SL cần mua(C) - SL đã len PR(B)
//                double warehouseNeed = itemQuantityDetail.getOriginQuantity(); // need
////                    + itemQuantityDetail.getOriginalRequiredQuantity() // hold
////                    - itemQuantityDetail.getExpectedQuantity(); // pr
////                    - itemQuantityDetail.getSumPoAndDeliveringQuantity(); // pr
//                System.err.println(itemQuantityDetail);
//                System.err.println("Warehouse need " + warehouseNeed + " item " + item.getItemCode());
//                log.info("Warehouse need {} item {}", warehouseNeed, item.getItemCode());
//                if (item.getCurrentWarehouseInventoryList() == null || numberOfOutOfSpaceWarehouse == item.getCurrentWarehouseInventoryList().size()) {
//                    // Không có tồn ở các kho
//                    if (itemQuantityDetail.getRequiredQuantity() > 0) {
//                        itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
////                                itemQuantityDetail.getRequiredQuantity(),
//                                warehouseNeed,
//                                null,
//                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
//                                Constants.ItemHold.ACTIVE
//                            )
//                        );
//                        System.err.println("Whs out of stock hold need quantity " + itemQuantityDetail.getRequiredQuantity());
//                        log.info("Whs out of stock hold need quantity {}", itemQuantityDetail.getRequiredQuantity());
//                    }
//                } else {
//                    for (CurrentWarehouseInventory currentWarehouseInventory : item.getCurrentWarehouseInventoryList()) {
//                        System.err.println((item.getDetailData().get(i).getLandMark()));
//                        if (warehouseNeed == 0 || currentWarehouseInventory.getCurrentQuantity() == 0) continue;
//
////                         PR > Need + Hold
////                        if (warehouseNeed < 0) {
////                            warehouseNeed = itemQuantityDetail.getOriginQuantity();
////                            System.err.println("PR > Need + Hold => new warehouseNeed " + warehouseNeed);
////                            log.info("PR > Need + Hold => new warehouseNeed {}", warehouseNeed);
////                        }
//
//                        // Lấy trong kho
//                        if (warehouseNeed > currentWarehouseInventory.getCurrentQuantity()) {
//                            warehouseNeed -= currentWarehouseInventory.getCurrentQuantity();
//                            // Lấy tất cả ở kho đang xét
//                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
//                                    currentWarehouseInventory.getCurrentQuantity(),
//                                    currentWarehouseInventory.getWarehouseCode(),
//                                    simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
//                                    Constants.ItemHold.ACTIVE
//                                )
//                            );
//                            System.err.println("Warehouse Need > Warehouse (warehouseNeed: " + warehouseNeed + ") (currentWarehouseQuantity: " + currentWarehouseInventory.getCurrentQuantity() + ")" + " (whsCode: " + currentWarehouseInventory.getWarehouseCode() + ")");
//                            log.info("Warehouse Need > Warehouse (warehouseNeed: {}) (currentWarehouseQuantity: {}) (whsCode: {})", warehouseNeed, currentWarehouseInventory.getCurrentQuantity(), currentWarehouseInventory.getWarehouseCode());
//                            currentWarehouseInventory.setCurrentQuantity(0.0);
//                            numberOfOutOfSpaceWarehouse++;
//                        } else {
//                            currentWarehouseInventory.setCurrentQuantity(currentWarehouseInventory.getCurrentQuantity() - warehouseNeed);
//                            // Lấy 1 phần trong kho đang xét
//                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
//                                warehouseNeed,
//                                currentWarehouseInventory.getWarehouseCode(),
//                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
//                                Constants.ItemHold.ACTIVE
//                            ));
//                            System.err.println("Warehouse Need < Warehouse (warehouseNeed: " + warehouseNeed + ") (currentWarehouseQuantity: " + currentWarehouseInventory.getCurrentQuantity() + ")" + " (whsCode: " + currentWarehouseInventory.getWarehouseCode() + ")");
//                            log.info("Warehouse Need < Warehouse (warehouseNeed: {}) (currentWarehouseQuantity: {}) (whsCode: {})", warehouseNeed, currentWarehouseInventory.getCurrentQuantity(), currentWarehouseInventory.getWarehouseCode());
//                            warehouseNeed = 0;
//                        }
//
//                        // Hold tiếp số còn lại nếu kho cuối đã hết hàng
//                        if (numberOfOutOfSpaceWarehouse == item.getCurrentWarehouseInventoryList().size()) {
//                            System.err.println("All whs out of stock hold remaining " + warehouseNeed);
//                            log.info("All whs out of stock hold remaining {}", warehouseNeed);
//                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
//                                warehouseNeed,
////                                itemQuantityDetail.getRequiredQuantity(), // Hold tiếp phần cần phải mua
//                                null,
//                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
//                                Constants.ItemHold.ACTIVE
//                            ));
//                            break;
//                        }
//                    }
//                }
            }
        }
        itemHoldEntities.forEach(holdEntity -> {
            System.err.println("Item " + holdEntity.getItemCode() + " quantity " + holdEntity.getQuantity() + " whs " + holdEntity.getWarehouseCode() + " date: " + simpleDateFormat.format(holdEntity.getHoldDate()));
            log.info("Item {} quantity {} whs {} date {}", holdEntity.getItemCode(), holdEntity.getQuantity(), holdEntity.getWarehouseCode(), simpleDateFormat.format(holdEntity.getHoldDate()));
        });
        // Un-hold last mrpSubCode (mrpSubCode.split[1] - 1)
        itemHoldRepository.unHoldItemsOfMrpStartWith(
            syntheticMrpDTO.getMrpCode() + "."
                // Get last mrpSubCode = current mrpSubCode last element - 1
//                + (Integer.parseInt(syntheticMrpDTO.getMrpSubCode().split("\\.")[1]) - 1)
        );
        return itemHoldRepository.saveAll(itemHoldEntities);
    }

    public List<ItemHoldEntity> saveHoldItemV2(SyntheticMrpDTO syntheticMrpDTO, Map<String,Double> listHold) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> listItem = new ArrayList<>(listHold.keySet());
        List<ItemHoldEntity> itemHoldEntities = new ArrayList<>();
        for (ItemSyntheticDTO item : syntheticMrpDTO.getResultData()) {
            int numberOfOutOfSpaceWarehouse = 0;
            System.err.println("Item " + item.getItemCode());
            for (int i = 1; i < item.getDetailData().size(); i++) {
                if (!listItem.contains(Utils.toItemKey(item.getItemCode(), item.getBomVersion()))) {
                    continue;
                }
                DetailItemSyntheticDTO itemQuantityDetail = item.getDetailData().get(i);
                // Khong co nhu cau san xuat
                if (itemQuantityDetail.getOriginQuantity() == 0) continue;
                itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
                        itemQuantityDetail.getOriginQuantity(),
                        null,
                        simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
                        Constants.ItemHold.ACTIVE
                    )
                );

                // SL yêu cầu + SL cần mua(C) - SL đã len PR(B)
//                double warehouseNeed = itemQuantityDetail.getOriginQuantity(); // need
////                    + itemQuantityDetail.getOriginalRequiredQuantity() // hold
////                    - itemQuantityDetail.getExpectedQuantity(); // pr
////                    - itemQuantityDetail.getSumPoAndDeliveringQuantity(); // pr
//                System.err.println(itemQuantityDetail);
//                System.err.println("Warehouse need " + warehouseNeed + " item " + item.getItemCode());
//                log.info("Warehouse need {} item {}", warehouseNeed, item.getItemCode());
//                if (item.getCurrentWarehouseInventoryList() == null || numberOfOutOfSpaceWarehouse == item.getCurrentWarehouseInventoryList().size()) {
//                    // Không có tồn ở các kho
//                    if (itemQuantityDetail.getRequiredQuantity() > 0) {
//                        itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
////                                itemQuantityDetail.getRequiredQuantity(),
//                                warehouseNeed,
//                                null,
//                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
//                                Constants.ItemHold.ACTIVE
//                            )
//                        );
//                        System.err.println("Whs out of stock hold need quantity " + itemQuantityDetail.getRequiredQuantity());
//                        log.info("Whs out of stock hold need quantity {}", itemQuantityDetail.getRequiredQuantity());
//                    }
//                } else {
//                    for (CurrentWarehouseInventory currentWarehouseInventory : item.getCurrentWarehouseInventoryList()) {
//                        System.err.println((item.getDetailData().get(i).getLandMark()));
//                        if (warehouseNeed == 0 || currentWarehouseInventory.getCurrentQuantity() == 0) continue;
//
////                         PR > Need + Hold
////                        if (warehouseNeed < 0) {
////                            warehouseNeed = itemQuantityDetail.getOriginQuantity();
////                            System.err.println("PR > Need + Hold => new warehouseNeed " + warehouseNeed);
////                            log.info("PR > Need + Hold => new warehouseNeed {}", warehouseNeed);
////                        }
//
//                        // Lấy trong kho
//                        if (warehouseNeed > currentWarehouseInventory.getCurrentQuantity()) {
//                            warehouseNeed -= currentWarehouseInventory.getCurrentQuantity();
//                            // Lấy tất cả ở kho đang xét
//                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
//                                    currentWarehouseInventory.getCurrentQuantity(),
//                                    currentWarehouseInventory.getWarehouseCode(),
//                                    simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
//                                    Constants.ItemHold.ACTIVE
//                                )
//                            );
//                            System.err.println("Warehouse Need > Warehouse (warehouseNeed: " + warehouseNeed + ") (currentWarehouseQuantity: " + currentWarehouseInventory.getCurrentQuantity() + ")" + " (whsCode: " + currentWarehouseInventory.getWarehouseCode() + ")");
//                            log.info("Warehouse Need > Warehouse (warehouseNeed: {}) (currentWarehouseQuantity: {}) (whsCode: {})", warehouseNeed, currentWarehouseInventory.getCurrentQuantity(), currentWarehouseInventory.getWarehouseCode());
//                            currentWarehouseInventory.setCurrentQuantity(0.0);
//                            numberOfOutOfSpaceWarehouse++;
//                        } else {
//                            currentWarehouseInventory.setCurrentQuantity(currentWarehouseInventory.getCurrentQuantity() - warehouseNeed);
//                            // Lấy 1 phần trong kho đang xét
//                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
//                                warehouseNeed,
//                                currentWarehouseInventory.getWarehouseCode(),
//                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
//                                Constants.ItemHold.ACTIVE
//                            ));
//                            System.err.println("Warehouse Need < Warehouse (warehouseNeed: " + warehouseNeed + ") (currentWarehouseQuantity: " + currentWarehouseInventory.getCurrentQuantity() + ")" + " (whsCode: " + currentWarehouseInventory.getWarehouseCode() + ")");
//                            log.info("Warehouse Need < Warehouse (warehouseNeed: {}) (currentWarehouseQuantity: {}) (whsCode: {})", warehouseNeed, currentWarehouseInventory.getCurrentQuantity(), currentWarehouseInventory.getWarehouseCode());
//                            warehouseNeed = 0;
//                        }
//
//                        // Hold tiếp số còn lại nếu kho cuối đã hết hàng
//                        if (numberOfOutOfSpaceWarehouse == item.getCurrentWarehouseInventoryList().size()) {
//                            System.err.println("All whs out of stock hold remaining " + warehouseNeed);
//                            log.info("All whs out of stock hold remaining {}", warehouseNeed);
//                            itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, syntheticMrpDTO,
//                                warehouseNeed,
////                                itemQuantityDetail.getRequiredQuantity(), // Hold tiếp phần cần phải mua
//                                null,
//                                simpleDateFormat.parse(item.getDetailData().get(i).getLandMark()),
//                                Constants.ItemHold.ACTIVE
//                            ));
//                            break;
//                        }
//                    }
//                }
            }
        }
        itemHoldEntities.forEach(holdEntity -> {
            System.err.println("Item " + holdEntity.getItemCode() + " quantity " + holdEntity.getQuantity() + " whs " + holdEntity.getWarehouseCode() + " date: " + simpleDateFormat.format(holdEntity.getHoldDate()));
            log.info("Item {} quantity {} whs {} date {}", holdEntity.getItemCode(), holdEntity.getQuantity(), holdEntity.getWarehouseCode(), simpleDateFormat.format(holdEntity.getHoldDate()));
        });
        // Un-hold last mrpSubCode (mrpSubCode.split[1] - 1)
        itemHoldRepository.unHoldItemsOfMrpStartWith(
            syntheticMrpDTO.getMrpCode() + "."
            // Get last mrpSubCode = current mrpSubCode last element - 1
//                + (Integer.parseInt(syntheticMrpDTO.getMrpSubCode().split("\\.")[1]) - 1)
        );
        return itemHoldRepository.saveAll(itemHoldEntities);
    }

    public void unHoldMrpCode(String mrpCode) {
        List<ItemHoldEntity> holdItems = itemHoldRepository.findByMrpSubCodeStartsWith(mrpCode + ".");
        if (CollectionUtils.isEmpty(holdItems)) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        holdItems.forEach(holdEntity -> holdEntity.setActive(false));
        itemHoldRepository.saveAll(holdItems);
    }

    public void unHoldItemOfMrpCode(String mrpCode, String itemCode) {
        List<ItemHoldEntity> holdItems = itemHoldRepository.findByMrpSubCodeAndItemCode(mrpCode, itemCode);
        if (CollectionUtils.isEmpty(holdItems)) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        holdItems.forEach(item -> item.setActive(false));
        itemHoldRepository.saveAll(holdItems);
    }

    public void unHoldItem(String itemCode) {
        List<ItemHoldEntity> holdItems = itemHoldRepository.findByItemCode(itemCode);
        if (CollectionUtils.isEmpty(holdItems)) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        holdItems.forEach(item -> item.setActive(false));
        itemHoldRepository.saveAll(holdItems);
    }

    public List<String> getItemHoldOfMrpSub(String mrpSubCode) {
        return itemHoldRepository.findAllByMrpSubCode(mrpSubCode);
    }
}
