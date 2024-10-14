package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.domain.sap.QOitmEntity;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.Itm2Repository;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.MqqPriceExcelModel;
import com.facenet.mrp.service.model.OitmFilter;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.RequestInput;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.CsvHandle;
import com.facenet.mrp.service.utils.XlsxExcelHandle;
import com.facenet.mrp.web.rest.OitmResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * service xử lý nghiệp vụ lấy chi tiết thông tin ncc
 */
@Service
public class DetailVendorService {
    private final Logger log = LogManager.getLogger(DetailVendorService.class);

    @Autowired
    @Qualifier("sapEntityManager")
    private EntityManager entityManagerSap;

    @Autowired
    private LeadTimeRepository leadTimeRepository;

    @Autowired
    private MqqPriceRepository mqqPriceRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private OcrdRepository ocrdRepository;

    @Autowired
    private OitmRepository oitmRepository;

    @Autowired
    private Itm2Repository itm2Repository;

    @Autowired
    private XlsxExcelHandle xlsxExcelHandle;

    @Autowired
    private CsvHandle csvHandle;
    @Autowired
    private VendorItemRepository vendorItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private RateExchangeService rateExchangeService;
    @Autowired
    private OitmResource oitmResource;

    //hàm lấy thông tin ncc chi tiết
    public PageResponse<?> getData(PageFilterInput<VendorDetailForm> input, String vendorCode, Pageable pageable) throws JsonProcessingException {
        PageResponse<List<DataItemInVendor>> data = getAllData(input, vendorCode, pageable);
        List<DataItemInVendor> list = data.getData();
        ContactPersonDTO contactPersonDTO = ocrdRepository.getContactPerson(vendorCode);
        DetailVendorDTO detailVendorDTO = new DetailVendorDTO();
        detailVendorDTO.setProductInfoList(list);
        if (contactPersonDTO != null) {
            detailVendorDTO.setId(contactPersonDTO.getAddId());
            detailVendorDTO.setAddress(contactPersonDTO.getAddress());
            detailVendorDTO.setDateOfBirth(contactPersonDTO.getBirthDay());
            detailVendorDTO.setEmail(contactPersonDTO.getEmail());
            detailVendorDTO.setFax(contactPersonDTO.getFax());
            detailVendorDTO.setGender(contactPersonDTO.getGender());
            detailVendorDTO.setName(contactPersonDTO.getName());
            detailVendorDTO.setNationality(contactPersonDTO.getNational());
            detailVendorDTO.setPosition(contactPersonDTO.getPosition());
            detailVendorDTO.setTitle(contactPersonDTO.getTitle());
            detailVendorDTO.setPhoneNumber(contactPersonDTO.getPhone());
        }
        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(data.getDataCount())
            .data(detailVendorDTO);
    }

    //hàm lấy thông tin leadtime và mqq price theo item
    public PageResponse<List<DataItemInVendor>> getAllData(PageFilterInput<VendorDetailForm> input, String vendorCode, Pageable pageable) throws JsonProcessingException {
        List<DataItemInVendor> listData = new ArrayList<>();
        PageResponse<List<ItemInVendorDTO>> listVendor = getAllItemAlongVendor(input, vendorCode, pageable);
        List<ItemInVendorDTO> list = listVendor.getData();
//        List<LeadTimeEntity> leadTimeEntities = leadTimeRepository.getLeadTime(vendorCode);
        for (ItemInVendorDTO item : list) {
            DataItemInVendor dataItemInVendor = new DataItemInVendor();
            RequestInput<OitmFilter> requestInput = new RequestInput<>();
            OitmFilter filter = new OitmFilter();
            filter.setProductId(item.getItemCode());
            requestInput.setFilter(filter);
            requestInput.setPageNumber(0);
            requestInput.setPageSize(1);
            List<OitmDTO> oitmStockList = oitmResource.getOitmWithWarehouseStock(requestInput).getBody().getData();
            Map<String, Long> stockByProductId = oitmStockList.stream()
                .collect(Collectors.toMap(OitmDTO::getProductId, OitmDTO::getTotalInStock));

//            for (LeadTimeEntity leadTime:leadTimeEntities) {
//                System.out.println(item.getItemCode() + "---" +leadTime.getItemCode());
//                if(item.getItemCode().equals(leadTime.getItemCode())){
//                    MqqLeadTimeDTO mqqLeadTimeDTO = mqqPriceRepository.findMqqPriceAndLeadTime(vendorCode, item.getItemCode());
//                    dataItemInVendor.setPriceMQQ(leadTime.getMqqPriceMin());
//                    dataItemInVendor.setDueDate(mqqLeadTimeDTO.getDueDate());
//                    dataItemInVendor.setCurrency(leadTime.getCurrency());
//                    dataItemInVendor.setNote(mqqLeadTimeDTO.getNote());
            List<MoqDTO> moqDTOList = mqqPriceRepository.findMoqMinAndLeadTimeByItemCodeAndVendorCode(vendorCode, item.getItemCode());
            Map<String, Float> rateExchange = rateExchangeService.getRateExchange();
            // xét giá trị ban đầu cho dataItemInVendor.priceMQQ để so sánh tìm exchange
            if (moqDTOList.size() > 0) {
                dataItemInVendor.setLeadTime(moqDTOList.get(0).getLeadTime());
                dataItemInVendor.setPriceMQQ(moqDTOList.get(0).getPrice());
                dataItemInVendor.setCurrency(moqDTOList.get(0).getCurrency());
                dataItemInVendor.setNote(moqDTOList.get(0).getNote());
                dataItemInVendor.setDueDate(moqDTOList.get(0).getDueDate());
            }
            for (MoqDTO m : moqDTOList) {
                double exchange;
                exchange = rateExchange.get(m.getCurrency()) * m.getPrice();
                if (exchange < rateExchange.get(dataItemInVendor.getCurrency()) * dataItemInVendor.getPriceMQQ()) {
                    dataItemInVendor.setPriceMQQ(m.getPrice());
                    dataItemInVendor.setCurrency(m.getCurrency());
                    dataItemInVendor.setNote(m.getNote());
                    dataItemInVendor.setDueDate(m.getDueDate());
                }
            }
//                }
//            }
            dataItemInVendor.setProductCode(item.getItemCode());
            dataItemInVendor.setProductDescription(item.getItemName());
            dataItemInVendor.setProductTechName(item.getTechName());
            dataItemInVendor.setGroupName(item.getGroupName());
            if (item.getGroupType() == Constants.BTP) {
                dataItemInVendor.setGroup("BTP");
            } else if (item.getGroupType() == Constants.TP){
                dataItemInVendor.setGroup("TP");
            } else {
                dataItemInVendor.setGroup("NVL");
            }
            dataItemInVendor.setStatus(item.getStatus());
            dataItemInVendor.setProductType(item.getType());
//            dataItemInVendor.setTotalInventory(item.getOnHand());
            Long totalStock = stockByProductId.getOrDefault(item.getItemCode(), 0L);
            dataItemInVendor.setTotalInventory(totalStock);
            dataItemInVendor.setUnit(item.getUnit());
            dataItemInVendor.setSap(itemRepository.getSap(vendorCode, item.getItemCode()));
            listData.add(dataItemInVendor);
        }
        return new PageResponse<List<DataItemInVendor>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(listVendor.getDataCount())
            .data(listData);
    }

    //hàm lấy ds item thuộc về ncc
    public PageResponse<List<ItemInVendorDTO>> getAllItemAlongVendor(PageFilterInput<VendorDetailForm> input, String vendorCode, Pageable pageable) {
        VendorDetailForm filter = input.getFilter();
        Page<String> itemCodeList = itemRepository.getAllByVendorCode(
            vendorCode,
            StringUtils.isEmpty(filter.getItemCode()) ? null : filter.getItemCode(),
            StringUtils.isEmpty(filter.getItemName()) ? null : filter.getItemName(),
            pageable
        );
        log.info("Item list belong to vendor {}: {}", vendorCode, itemCodeList.getContent());

//        ITM2 Table
        QOitmEntity qOitmEntity = QOitmEntity.oitmEntity;
//        QItm2Entity qItm2Entity = QItm2Entity.itm2Entity;
        JPAQuery<ItemInVendorDTO> query = new JPAQueryFactory(entityManagerSap)
            .select(
                new QItemInVendorDTO(
                    qOitmEntity.cardCode,
                    qOitmEntity.itemCode,
                    qOitmEntity.itemName,
                    qOitmEntity.uTechName,
                    qOitmEntity.itmsGrpCod.itmsGrpCode,
                    qOitmEntity.itmsGrpCod.itmsGrpName,
                    qOitmEntity.onHand,
                    qOitmEntity.cntUnitMsr,
                    qOitmEntity.uIGroupName,
                    qOitmEntity.asstStatus
                )
            )
            .from(qOitmEntity);
//            .join(qItm2Entity).on(qOitmEntity.itemCode.eq(qItm2Entity.itemCode))
//            .limit(pageable.getPageSize())
//            .offset(pageable.getOffset());
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        booleanBuilder.and(qItm2Entity.vendorCode.eq(vendorCode));
//        booleanBuilder.and(qOitmEntity.itemCode.in(itemCodeList.getContent()));
//        if (!StringUtils.isEmpty(filter.getItemCode())) {
//            booleanBuilder.and(qOitmEntity.itemCode.containsIgnoreCase(filter.getItemCode()));
//        }
//        if (!StringUtils.isEmpty(filter.getItemName())) {
//            booleanBuilder.and(qOitmEntity.itemName.containsIgnoreCase(filter.getItemName()));
//        }
//        query.where(booleanBuilder);
        query.where(qOitmEntity.itemCode.in(itemCodeList.getContent()));
        List<ItemInVendorDTO> result = query.fetch();
        List<String> itemCode = new ArrayList<>();
        for (ItemInVendorDTO i : result) {
            itemCode.add(i.getItemCode());
        }
        for (String i : itemCodeList.getContent()) {
            if (!itemCode.contains(i)) {
                result.add(
                    new ItemInVendorDTO(
                        result.get(0).getVendorCode(),
                        i,
                        itemRepository.findByItemCode(i).getItemName(),
                        null,
                        -1,
                        null,
                        -1,
                        null,
                        null,
                        null)
                );
            }
        }
//        long count = query.fetchCount();
        return new PageResponse<List<ItemInVendorDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(itemCodeList.getTotalElements())
            .data(result);
    }

    public ItemInVendorDTO getDetailItem(String itemCode) {
        QOitmEntity qOitmEntity = QOitmEntity.oitmEntity;
        JPAQuery<ItemInVendorDTO> query = new JPAQueryFactory(entityManagerSap)
            .select(
                new QItemInVendorDTO(
                    qOitmEntity.cardCode,
                    qOitmEntity.itemCode,
                    qOitmEntity.itemName,
                    qOitmEntity.uTechName,
                    qOitmEntity.itmsGrpCod.itmsGrpCode,
                    qOitmEntity.itmsGrpCod.itmsGrpName,
                    qOitmEntity.onHand,
                    qOitmEntity.cntUnitMsr,
                    qOitmEntity.uIGroupName,
                    qOitmEntity.asstStatus
                )
            )
            .from(qOitmEntity);
        query.where(qOitmEntity.itemCode.eq(itemCode));
        List<ItemInVendorDTO> result = query.fetch();
        return result.get(0);
    }

    @Transactional
    public CommonResponse addItemForVendorMulti(List<ItemInVendorDTO> itemInVendorDTOs) {
        for (ItemInVendorDTO itemInVendorDTO: itemInVendorDTOs){
            if (!oitmRepository.existsByItemCode(itemInVendorDTO.getItemCode()))
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.item", itemInVendorDTO.getItemCode());

            if (!itemRepository.existsAllByItemCode(itemInVendorDTO.getItemCode())) {
                ItemEntity itemEntity = new ItemEntity();
                itemEntity.setItemCode(itemInVendorDTO.getItemCode());
                itemEntity.setItemName(oitmRepository.getByItemCode(itemInVendorDTO.getItemCode()).getItemName());
                itemRepository.save(itemEntity);
            }

//            if (!ocrdRepository.existsByCardCode(itemInVendorDTO.getVendorCode()))
//                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.vendor", itemInVendorDTO.getVendorCode());

            if (!vendorRepository.existsAllByVendorCode(itemInVendorDTO.getVendorCode())) {
                VendorEntity vendorEntity = new VendorEntity();
                vendorEntity.setVendorCode(itemInVendorDTO.getVendorCode());
                vendorEntity.setVendorName(ocrdRepository.getVendor(itemInVendorDTO.getVendorCode()).getCardName());
                vendorRepository.save(vendorEntity);
            }

            if (vendorItemRepository.existsByItemCodeAndVendorCode(itemInVendorDTO.getItemCode(), itemInVendorDTO.getVendorCode()))
                throw new CustomException(HttpStatus.BAD_REQUEST, "exist.item.vendor", vendorItemRepository.findByItemCodeAndVendorCode(itemInVendorDTO.getItemCode(), itemInVendorDTO.getVendorCode()).getItemCode(), vendorItemRepository.findByItemCodeAndVendorCode(itemInVendorDTO.getItemCode(), itemInVendorDTO.getVendorCode()).getVendorCode());

            VendorItemEntity vendorItemEntity = new VendorItemEntity(itemInVendorDTO.getVendorCode(), itemInVendorDTO.getItemCode());
            vendorItemRepository.save(vendorItemEntity);

        }
        return new CommonResponse().success();
    }

    @Transactional
    public CommonResponse addItemForVendor(ItemInVendorDTO itemInVendorDTO) {
        if (!oitmRepository.existsByItemCode(itemInVendorDTO.getItemCode()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.item", itemInVendorDTO.getItemCode());

        if (!itemRepository.existsAllByItemCode(itemInVendorDTO.getItemCode())) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItemCode(itemInVendorDTO.getItemCode());
            itemEntity.setItemName(oitmRepository.getByItemCode(itemInVendorDTO.getItemCode()).getItemName());
            itemRepository.save(itemEntity);
        }

//        if (!ocrdRepository.existsByCardCode(itemInVendorDTO.getVendorCode()))
//            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.vendor", itemInVendorDTO.getVendorCode());

        if (!vendorRepository.existsAllByVendorCode(itemInVendorDTO.getVendorCode())) {
            VendorEntity vendorEntity = new VendorEntity();
            vendorEntity.setVendorCode(itemInVendorDTO.getVendorCode());
            vendorEntity.setVendorName(ocrdRepository.getVendor(itemInVendorDTO.getVendorCode()).getCardName());
            vendorRepository.save(vendorEntity);
        }

        if (vendorItemRepository.existsByItemCodeAndVendorCode(itemInVendorDTO.getItemCode(), itemInVendorDTO.getVendorCode()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "exist.item.vendor", vendorItemRepository.findByItemCodeAndVendorCode(itemInVendorDTO.getItemCode(), itemInVendorDTO.getVendorCode()).getItemCode(), vendorItemRepository.findByItemCodeAndVendorCode(itemInVendorDTO.getItemCode(), itemInVendorDTO.getVendorCode()).getVendorCode());

        VendorItemEntity vendorItemEntity = new VendorItemEntity(itemInVendorDTO.getVendorCode(), itemInVendorDTO.getItemCode());
        vendorItemRepository.save(vendorItemEntity);

        return new CommonResponse().success();
    }

    @Transactional
    public CommonResponse importPrice(MultipartFile file) throws IOException, ParseException {
        MqqPriceExcelModel model = xlsxExcelHandle.readPriceExcel(file.getInputStream());

        List<VendorItemEntity> vendorItemEntities = vendorItemRepository.findAllItemOfVendorList(model.getVendorCodes());
        validateItemVendor(model, buildItemVendorMap(vendorItemEntities));
        leadTimeRepository.saveAll(model.getLeadTimeEntities());
        mqqPriceRepository.saveAll(model.getMqqPriceEntities());
        // Insert or update item entity
        Map<String, ItemEntity> itemEntityMap = itemRepository.getAllByItemCodeInMap(model.getItemEntities().stream().map(ItemEntity::getItemCode).collect(Collectors.toList()));
        for (ItemEntity itemEntity : model.getItemEntities()) {
            if (itemEntityMap.containsKey(itemEntity.getItemCode())) {
                itemEntity.setItemId(itemEntityMap.get(itemEntity.getItemCode()).getItemId());
            }
        }
        itemRepository.saveAll(model.getItemEntities());

        // Insert or update vendor entity
        List<String> vendorList = model.getVendorEntities().stream().map(VendorEntity::getVendorCode).collect(Collectors.toList());
        Map<String, VendorEntity> vendorEntityMap = vendorRepository.getAllByVendorCodeInMap(vendorList);
        Map<String, String> sapVendorMap = ocrdRepository.vendorMap(vendorList);
        for (VendorEntity vendorEntity : model.getVendorEntities()) {
            if (vendorEntityMap.containsKey(vendorEntity.getVendorCode())) {
                vendorEntity.setVendorId(vendorEntityMap.get(vendorEntity.getVendorCode()).getVendorId());
            }
            if (!sapVendorMap.containsKey(vendorEntity.getVendorCode())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.vendor", vendorEntity.getVendorCode());
            }
        }
        vendorRepository.saveAll(model.getVendorEntities());

        model.getSaleEntityMap().forEach((vendorCode, saleEntity) -> {
            SaleEntity sale = saleRepository.getAllSaleVendor(vendorCode);
            if (sale != null) saleEntity.setSaleId(sale.getSaleId());
            saleRepository.save(saleEntity);
        });
        return new CommonResponse().success();
    }

    @Transactional
    public CommonResponse importPriceCsv(MultipartFile file) throws IOException, ParseException {
        MqqPriceExcelModel model = csvHandle.readPriceCsv(file.getInputStream());

        List<VendorItemEntity> vendorItemEntities = vendorItemRepository.findAllItemOfVendorList(model.getVendorCodes());
        validateItemVendor(model, buildItemVendorMap(vendorItemEntities));
        leadTimeRepository.saveAll(model.getLeadTimeEntities());
        mqqPriceRepository.saveAll(model.getMqqPriceEntities());

        // Insert or update item entity
        Map<String, ItemEntity> itemEntityMap = itemRepository.getAllByItemCodeInMap(model.getItemEntities().stream().map(ItemEntity::getItemCode).collect(Collectors.toList()));
        for (ItemEntity itemEntity : model.getItemEntities()) {
            if (itemEntityMap.containsKey(itemEntity.getItemCode())) {
                itemEntity.setItemId(itemEntityMap.get(itemEntity.getItemCode()).getItemId());
            }
        }
        itemRepository.saveAll(model.getItemEntities());

        // Insert or update vendor entity
        List<String> vendorList = model.getVendorEntities().stream().map(VendorEntity::getVendorCode).collect(Collectors.toList());
        Map<String, VendorEntity> vendorEntityMap = vendorRepository.getAllByVendorCodeInMap(vendorList);
        Map<String, String> sapVendorMap = ocrdRepository.vendorMap(vendorList);
        for (VendorEntity vendorEntity : model.getVendorEntities()) {
            if (vendorEntityMap.containsKey(vendorEntity.getVendorCode())) {
                vendorEntity.setVendorId(vendorEntityMap.get(vendorEntity.getVendorCode()).getVendorId());
            }
            if (!sapVendorMap.containsKey(vendorEntity.getVendorCode())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.vendor", vendorEntity.getVendorCode());
            }
        }
        vendorRepository.saveAll(model.getVendorEntities());

        model.getSaleEntityMap().forEach((vendorCode, saleEntity) -> {
            SaleEntity sale = saleRepository.getAllSaleVendor(vendorCode);
            if (sale != null) saleEntity.setSaleId(sale.getSaleId());
            saleRepository.save(saleEntity);
        });
        return new CommonResponse().success();
    }

    private MultiValuedMap<String, String> buildItemVendorMap(List<VendorItemEntity> vendorItemEntities) {
        MultiValuedMap<String, String> itemVendorMap = new HashSetValuedHashMap<>();
        for (VendorItemEntity entity : vendorItemEntities) {
            itemVendorMap.put(entity.getItemCode(), entity.getVendorCode());
        }
        return itemVendorMap;
    }

    private void validateItemVendor(MqqPriceExcelModel model, MultiValuedMap<String, String> itemVendorMap) {
        List<VendorItemEntity> newVendorItemMapping = new ArrayList<>();
        for (MqqPriceEntity entity : model.getMqqPriceEntities()) {
            if (!itemVendorMap.get(entity.getItemCode()).contains(entity.getVendorCode())) {
//                throw new CustomException(HttpStatus.BAD_REQUEST, "item.does.not.belong.to.vendor", entity.getItemCode(), entity.getVendorCode());
                newVendorItemMapping.add(new VendorItemEntity(entity.getVendorCode(), entity.getItemCode()));
            }
        }
        if (!newVendorItemMapping.isEmpty())
            vendorItemRepository.saveAll(newVendorItemMapping);
    }
}
