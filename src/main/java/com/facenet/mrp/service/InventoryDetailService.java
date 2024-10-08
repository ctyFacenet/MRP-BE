package com.facenet.mrp.service;

import com.facenet.mrp.repository.mrp.MqqPriceRepository;
import com.facenet.mrp.repository.mrp.VendorEntityRepository;
import com.facenet.mrp.repository.sap.InventoryDetailRepository;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.service.dto.InventoryDetailDTO;
import com.facenet.mrp.service.dto.InventorySupplierDTO;
import com.facenet.mrp.service.dto.mrp.VendorCodeForDetailReport;
import com.facenet.mrp.service.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * @author: trần đình thành
 * Service xử lý dữ liệu liên quan đến bảng kho,nvl trong sap
 */
@Service
public class InventoryDetailService {
    private final Logger log = LogManager.getLogger(CoittServiceImpl.class);

    @Autowired
    private MqqPriceRepository mqqPriceRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private OcrdRepository ocrdRepository;

    @Autowired
    private VendorEntityRepository vendorEntityRepository;

    @Autowired
    private InventoryDetailRepository inventoryDetailRepository;

    @Autowired
    private WarehouseChosenService warehouseChosenService;
    /**
     * lấy thông tin tồn kho chi tiết theo mã hàng hóa
     * @param itemCode
     * @return
     */
//    public List<InventoryDetailDTO> getInventoryDetail(String ItemCode){
//        log.info("------Start get inventory detail by itemcode-------");
//        if(StringUtils.isEmpty(ItemCode)){
//            log.error("tham số truyền vào không đúng!");
//            return null;
//        }
//        List<InventoryDetailDTO> list = inventoryDetailRepository.getInventoryDetailByItemCode(ItemCode);
//        log.info("------end get inventory detail by itemcode-------");
//        return list;
//    }
    public List<InventoryDetailDTO> getInventoryDetail(String itemCode) {
        log.info("------Start get inventory detail by item code-------");

        if (StringUtils.isEmpty(itemCode)) {
            log.error("Tham số truyền vào không đúng!");
            return Collections.emptyList();
        }
        List<String> listWarehouse = warehouseChosenService.getWarehouseCodes(1);
        // Initialize the list that will store all inventory details
        List<InventoryDetailDTO> finalList = new ArrayList<>();
        List<InventoryDetailDTO> list = inventoryDetailRepository.getInventoryDetailByItemCode(itemCode, listWarehouse);
        finalList.addAll(list);
        // Add inventory details for other warehouse
        if(listWarehouse.contains("KHA")){
            List<InventoryDetailDTO> otherWarehouseDetails = warehouseService.getInventoryDetails(itemCode, Constants.Warehouse.Hoa_an);
            finalList.addAll(otherWarehouseDetails);
        }

        if(listWarehouse.contains("KVTCT")){
            List<InventoryDetailDTO> otherWarehouseDetails = warehouseService.getInventoryDetails(itemCode, Constants.Warehouse.Cty);
            finalList.addAll(otherWarehouseDetails);
        }

        if(listWarehouse.contains("KTP")){
            List<InventoryDetailDTO> otherWarehouseDetails = warehouseService.getInventoryDetails(itemCode, Constants.Warehouse.Tp);
            finalList.addAll(otherWarehouseDetails);
        }
        log.info("------End get inventory detail by item code-------");
        return finalList;
    }
    /**
     * lấy thông tin nhà cung cấp theo mã hàng hóa
     * @param itemCode
     * @return
     */
    public List<InventorySupplierDTO> getInventorySupplier(String itemCode){
        log.info("------Start get inventory supplier by itemcode-------");
        if(StringUtils.isEmpty(itemCode)){
            log.error("tham số truyền vào không đúng!");
            return null;
        }
//        List<InventorySupplierDTO> list = new ArrayList();
//        List<Itm2Entity> listData = inventoryDetailRepository.getVendorByItemCode(ItemCode);
//        for (Itm2Entity itm:listData
//             ) {
//            QItm2Entity qItm2Entity = QItm2Entity.itm2Entity;
//            QOcrdEntity qOcrdEntity = QOcrdEntity.ocrdEntity;
//            QPdn1Entity qPdn1Entity = QPdn1Entity.pdn1Entity;
//            JPAQuery<InventorySupplierDTO> query = new JPAQueryFactory(entityManagerSap)
//                .select(
//                    new QInventorySupplierDTO(
//                        qOcrdEntity.cardCode,
//                        qOcrdEntity.cardName,
//                        qPdn1Entity.price,
//                        qPdn1Entity.currency
//                    )
//                )
//                .from(qItm2Entity)
//                .join(qOcrdEntity).on(qOcrdEntity.cardCode.eq(qItm2Entity.vendorCode))
//                .join(qPdn1Entity).on(qPdn1Entity.itemCode.eq(qItm2Entity.itemCode))
//                .orderBy(qPdn1Entity.docDate.desc())
//                .limit(1);
//            query.where(qItm2Entity.vendorCode.eq(itm.getVendorCode()));
//            List<InventorySupplierDTO> result =query.fetch();
//            if(result.size() > 0){
//                list.add(result.get(0));
//            }
//        }
//        log.info("------end get inventory supplier by itemcode-------");

        //**************************************************
        //lấy list vendor và giá MOQ theo itemCode trong MRP Db
        List<InventorySupplierDTO> inventorySupplierDTOList = mqqPriceRepository.getAllVendorAndPriceByItemCode(itemCode);
        Set<String> vendorCodeList = new LinkedHashSet<>();

        inventorySupplierDTOList.stream().forEach(item -> {
            vendorCodeList.add(item.getVendorId());
        });
        System.err.println("vendorCodeList: " + vendorCodeList.size());

        //Lấy thông tin vendor trong SAP
        List<VendorCodeForDetailReport> vendorInfos = vendorEntityRepository.getVendorList(vendorCodeList);
        if (vendorInfos != null && !vendorInfos.isEmpty()){
            for (InventorySupplierDTO result : inventorySupplierDTOList) {
                for (VendorCodeForDetailReport vendorInfo : vendorInfos){
                    if (result.getVendorId().equals(vendorInfo.getVendorCode())){
                        result.setVendorName(vendorInfo.getVendorName());
                        break;
                    }
                }
            }
        }

        return inventorySupplierDTOList;
    }
}
