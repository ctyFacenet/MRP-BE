package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.DetailHoldInMrpDTO;
import com.facenet.mrp.service.dto.mrp.MrpDTO;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.mrp.MrpResultDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.MrpOrderQuantityMapper;
import com.facenet.mrp.service.mapper.MrpProductionQuantityMapper;
import com.facenet.mrp.service.mapper.MrpRequiredQuantityMapper;
import com.facenet.mrp.service.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

@Service
public class HistoryMrpService {
    private final Logger log = LogManager.getLogger(ListSaleService.class);

    @Autowired
    private HistoryMrpRepository historyMrpRepository;

    @Autowired
    private MrpSubRepository mrpSubRepository;

    @Autowired
    private ResultMrpRepository resultMrpRepository;
    @Autowired
    private ItemHoldRepository itemHoldRepository;

    @Autowired
    private MrpProductionQuantityMapper mrpProductionQuantityMapper;

    @Autowired
    private MrpRequiredQuantityMapper mrpRequiredQuantityMapper;

    @Autowired
    private MrpProductionQuantityRepository mrpProductionQuantityRepository;

    @Autowired
    private MrpRequiredQuantityRepository mrpRequiredQuantityRepository;
    @Autowired
    private ProductOrderDetailRepository productOrderDetailRepository;
    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private MrpOrderQuantityMapper mrpOrderQuantityMapper;

    @Value("${file.file-path.absolute-path}")
    private String absolutePath;

    /**
     * hàm lấy ds MRP
     * @param pageable
     * @return
     */
    public PageResponse<?> getAllMrp(Pageable pageable) {
        List<MrpEntity> list = historyMrpRepository.getAllMrp();
        if (list == null) {
            return null;
        }
        List<ListMrpDTO> listMrpDTOS = new ArrayList<>();
        for (MrpEntity item : list
        ) {
            ListMrpDTO listMrpDTO = new ListMrpDTO();
            listMrpDTO.setMrpCode(item.getMrpCode());
            listMrpDTO.setMrpDescription(item.getMrpDescription());
            listMrpDTO.setSoCode(item.getMrpPoId());
            listMrpDTO.setLastAccess(item.getLastAccess());
            listMrpDTO.setTimeStart(item.getStartTime());
            listMrpDTO.setTimeEnd(item.getEndTime());
            listMrpDTO.setStatus(item.getStatus());
            listMrpDTO.setUpdateTime(item.getUpdatedAt());
            listMrpDTO.setUpdateBy(item.getUpdatedBy());
            listMrpDTOS.add(listMrpDTO);
        }
        Page<ListMrpDTO> pages = new PageImpl<ListMrpDTO>(listMrpDTOS, pageable, listMrpDTOS.size());
        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(listMrpDTOS.size())
            .data(pages);
    }

    public PageResponse getDetailHold(SyntheticMrpDTO request){
        List<DetailHoldInMrpDTO> result = new ArrayList<>();
        List<ItemSyntheticDTO> requestData = request.getResultData();
        List<String> items = new ArrayList<>();
        for (ItemSyntheticDTO itemSyntheticDTO:requestData){
            if(itemSyntheticDTO.getType().equals("NVL")){
                items.add(itemSyntheticDTO.getItemCode());
            }
        }
        List<ItemHoldDTO> itemHoldDTOS = itemHoldRepository.sumHoldItem(items);
        for (ItemSyntheticDTO itemSyntheticDTO: requestData){
            if(itemSyntheticDTO.getType().equals("NVL")){
                DetailHoldInMrpDTO detailHoldInMrpDTO = new DetailHoldInMrpDTO();
                detailHoldInMrpDTO.setItemCode(itemSyntheticDTO.getItemCode());
                detailHoldInMrpDTO.setItemName(itemSyntheticDTO.getItemName());
                detailHoldInMrpDTO.setRequestQuantity(itemSyntheticDTO.getRequestNumber());
                detailHoldInMrpDTO.setTotalHoldQuantity(0.0);
                detailHoldInMrpDTO.setRequestNumber(itemSyntheticDTO.getRequestNumber());
                detailHoldInMrpDTO.setPrNumber(itemSyntheticDTO.getPrNumber());
                detailHoldInMrpDTO.setReadyQuantity(itemSyntheticDTO.getReadyQuantity());
                detailHoldInMrpDTO.setViewBtpTp(itemSyntheticDTO.getViewBtpTp());
                detailHoldInMrpDTO.setRequiredQuantity(itemSyntheticDTO.getRequiredQuantity());
                detailHoldInMrpDTO.setBomVersion(itemSyntheticDTO.getBomVersion());
                detailHoldInMrpDTO.setType(itemSyntheticDTO.getType());
                detailHoldInMrpDTO.setAltItemCode(itemSyntheticDTO.getAltItemCode());
                for (ItemHoldDTO itemHoldDTO: itemHoldDTOS){
                    if(itemHoldDTO.getSoCode().equals(itemSyntheticDTO.getItemCode())){
                        detailHoldInMrpDTO.setTotalHoldQuantity(itemHoldDTO.getQuantity());
                        break;
                    }
                }
                detailHoldInMrpDTO.setDetailData(itemSyntheticDTO.getDetailData());
                result.add(detailHoldInMrpDTO);
            }
        }
        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(result.size())
            .data(result);
    }

    public PageResponse getDetailHoldV2(SyntheticMrpDTO request,AdvancedMrpDTO advancedMrpDTO){
        List<DetailHoldInMrpDTO> res = new ArrayList<>();
        List<ItemSyntheticDTO> requestData = request.getResultData();
        List<String> items = new ArrayList<>();
        for (ItemSyntheticDTO itemSyntheticDTO:requestData){
            if(itemSyntheticDTO.getType().equals("NVL")){
                items.add(itemSyntheticDTO.getItemCode());
            }
        }
        List<ItemHoldDTO> itemHoldDTOS = itemHoldRepository.sumHoldItem(items);
        List<DetailHoldInMrpDTO> result = dequy(advancedMrpDTO.getResultData(),itemHoldDTOS,res);
        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(result.size())
            .data(result);
    }

    private List<DetailHoldInMrpDTO> dequy(List<MrpDetailDTO> mrpDetailDTOS,List<ItemHoldDTO> itemHoldDTOS,List<DetailHoldInMrpDTO> response){
        Double sumRequest = 0.0;
        Integer check = 0;
        for (MrpDetailDTO mrpDetailDTO: mrpDetailDTOS){
            if(mrpDetailDTO.getGroupItem().equals("NVL")){
                DetailHoldInMrpDTO detailHoldInMrpDTO = new DetailHoldInMrpDTO();
                detailHoldInMrpDTO.setItemCode(mrpDetailDTO.getItemCode());
                detailHoldInMrpDTO.setItemName(mrpDetailDTO.getItemName());
                detailHoldInMrpDTO.setTotalHoldQuantity(0.0);
                List<MrpResultDTO> mrpResultDTOS= mrpDetailDTO.getDetailResult();
                for (MrpResultDTO mrpResultDTO: mrpResultDTOS){
                    if(check > 0){
                        sumRequest += mrpResultDTO.getTotalOriginQuantity();
                    }
                    check ++;
                }
                detailHoldInMrpDTO.setRequestQuantity(sumRequest);//TODO: sửa lại số lượng yc MRP ở màn hold
                sumRequest = 0.0;
                check = 0;
                detailHoldInMrpDTO.setRequiredQuantity(mrpDetailDTO.getRequiredQuantity());
                detailHoldInMrpDTO.setBomVersion(mrpDetailDTO.getBomVersion());
                detailHoldInMrpDTO.setType(mrpDetailDTO.getGroupItem());
                detailHoldInMrpDTO.setAltItemCode(mrpDetailDTO.getAltItemCode());
                for (ItemHoldDTO itemHoldDTO: itemHoldDTOS){
                    if(itemHoldDTO.getSoCode().equals(mrpDetailDTO.getItemCode())){
                        detailHoldInMrpDTO.setTotalHoldQuantity(itemHoldDTO.getQuantity());
                        break;
                    }
                }
                response.add(detailHoldInMrpDTO);
            }
            if (!mrpDetailDTO.getChildren().isEmpty()){
                dequy(mrpDetailDTO.getChildren(),itemHoldDTOS,response);
            }
        }

        return response;
    }

    /**
     * hàm lấy ds MRP sub code
     * @param mrpCode
     * @param pageable
     * @return
     */
    public PageResponse<?> getMrpSub(String mrpCode, Pageable pageable) {
        if (StringUtil.isEmpty(mrpCode)) {
            return null;
        }
        List<MrpSubEntity> list = historyMrpRepository.getMrpSub(mrpCode);
        if (list == null) {
            return null;
        }
        List<ListDetailMrpDTO> listDetailMrpDTOS = new ArrayList<>();
        for (MrpSubEntity item : list
        ) {
            ListDetailMrpDTO listDetailMrpDTO = new ListDetailMrpDTO();
            listDetailMrpDTO.setMrpSubCode(item.getMrpSubCode());
            listDetailMrpDTO.setAnalysisType(item.getAnalysisType());
            listDetailMrpDTO.setCreatedBy(item.getCreatedBy());
            listDetailMrpDTO.setCreatedTime(item.getCreatedAt());
            listDetailMrpDTO.setMrpCode(item.getMrpCode());
            listDetailMrpDTO.setMrpDescription(item.getMrpDescription());
            listDetailMrpDTO.setTimeEnd(item.getEndTime());
            listDetailMrpDTO.setStatus(item.getStatus());
            listDetailMrpDTO.setSortType(item.getSortType());
            listDetailMrpDTO.setTimeStart(item.getStartTime());
            listDetailMrpDTO.setMrpCode(item.getMrpCode());
            listDetailMrpDTO.setSoCode(item.getMrpPoId());
            listDetailMrpDTOS.add(listDetailMrpDTO);
        }
        Page<ListDetailMrpDTO> pages = new PageImpl<ListDetailMrpDTO>(listDetailMrpDTOS, pageable, listDetailMrpDTOS.size());
        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(listDetailMrpDTOS.size())
            .data(pages);
    }

    /**
     * hàm lấy lịch sử chạy chi tiết mrp
     * @param mrpSubCode
     * @return
     * @throws JsonProcessingException
     */
    public ResponseEntity<FileSystemResource> getMrpResult(String mrpSubCode) throws JsonProcessingException {
        ResultMrpJsonEntity resultMrpJsonEntity;
//        // Lấy MRP theo mã chính xác
        log.info("View MRP {}", mrpSubCode);
        if (mrpSubCode.contains("."))
            resultMrpJsonEntity = resultMrpRepository.getResultMrpJsonByMrpSubCode(mrpSubCode);
        else
            // Lấy MRP mới nhất
            resultMrpJsonEntity = resultMrpRepository.getResultMrpJson(mrpSubCode);
//
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> map = mapper.readValue(resultMrpJsonEntity.getDataResultJson(), Map.class);

        String filePath = resultMrpJsonEntity.getDataResultJson();
        File file = new File(filePath);
        log.info("File kết quả: {}", file);
        if (!file.exists()) {
            // Handle the case if the file does not exist
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        }

        // Set the appropriate Content-Disposition header to trigger download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(mrpSubCode).build());
        headers.set(HttpHeaders.CONTENT_ENCODING, "gzip");
        // Set the Content-Type based on the file type, or you can set it to "application/octet-stream" for generic binary data.
        headers.setContentType(MediaType.APPLICATION_JSON);

//
        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }

    @Transactional
    public void saveMrpResult(AdvancedMrpDTO mrpDTO, boolean isHold) throws JsonProcessingException, ParseException {

        // Define the file path
        String filePath = absolutePath + File.separator + mrpDTO.getMrpSubCode();
        log.info("File save in {}", filePath);

        try {
            // Ensure the directory exists
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                if (parentDir.mkdirs()) {
                    log.info("Created directory: {}", parentDir.getAbsolutePath());
                } else {
                    log.error("Failed to create directory: {}", parentDir.getAbsolutePath());
                }
            }

            // Convert the Data object to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(new CommonResponse<>().success().data(mrpDTO));

            // Write JSON data to a Gzip file
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                 GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
                 Writer writer = new OutputStreamWriter(gzipOutputStream, StandardCharsets.UTF_8)) {

                // Write the JSON data in UTF-8 encoding to Gzip output stream
                writer.write(jsonData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }


        ObjectMapper objectMapper = new ObjectMapper();
        MrpEntity mrp = historyMrpRepository.getMrp(mrpDTO.getSoCode(), mrpDTO.getMrpCode());
        if (mrp == null) {
            mrp = new MrpEntity();
        }
        mrp.setMrpId(mrp.getMrpId());
        mrp.setLastAccess(mrpDTO.getAnalysisDate());
        mrp.setMrpCode(mrpDTO.getMrpCode());
        mrp.setMrpDescription(mrpDTO.getMrpDescription());
        mrp.setMrpPoId(mrpDTO.getSoCode());
        mrp.setStartTime(mrpDTO.getTimeStart());
        mrp.setEndTime(mrpDTO.getTimeEnd());
        mrp.setStatus(isHold ? Constants.Mrp.NEW_WITH_HOLD : Constants.Mrp.NEW);
        mrp.setIsActive((byte) 1);
        mrp.setLastAccess(mrpDTO.getAnalysisDate());
        historyMrpRepository.save(mrp);
//
        MrpSubEntity mrpSubEntity = historyMrpRepository.getAllMrpSubCode(mrpDTO.getMrpCode(), mrpDTO.getMrpSubCode());
        if (mrpSubEntity == null) {
            mrpSubEntity = new MrpSubEntity();
        }
        mrpSubEntity.setMrpId(mrpSubEntity.getMrpId());
        mrpSubEntity.setMrpPoId(mrpDTO.getSoCode());
        mrpSubEntity.setMrpCode(mrpDTO.getMrpCode());
        mrpSubEntity.setMrpSubCode(mrpDTO.getMrpSubCode());
        mrpSubEntity.setMrpDescription(mrpDTO.getMrpDescription());
        mrpSubEntity.setAnalysisSource(String.valueOf(mrpDTO.getAnalysisWhs()));
        // Item list
        mrpSubEntity.setItems(objectMapper.writeValueAsString(mrpDTO.getListItemCode()));
        mrpSubEntity.setAnalysisType(mrpDTO.getAnalysisPeriod());
        mrpSubEntity.setAnalysisKind(mrpDTO.getAnalysisType());
        mrpSubEntity.setStartTime(mrpDTO.getTimeStart());
        mrpSubEntity.setEndTime(mrpDTO.getTimeEnd());
        mrpSubEntity.setSortType(mrpDTO.getSortType());
        mrpSubEntity.setStatus(Constants.MrpSub.NEW);
        mrpSubEntity.setIsActive((byte) 1);
        mrpSubEntity.setWarehouseAnalysis(objectMapper.writeValueAsString(mrpDTO.getWarehouseAnalysis()));
        mrpSubEntity.setAnalysisType(mrpDTO.getAnalysisPeriod());
        mrpSubEntity.setAnalysisMode(mrpDTO.getAnalysisMode());
        mrpSubRepository.save(mrpSubEntity);
        ResultMrpJsonEntity resultMrpJson = historyMrpRepository.getMrpResult(mrpDTO.getMrpSubCode());
        if (resultMrpJson == null) resultMrpJson = new ResultMrpJsonEntity();
        resultMrpJson.setMrpCode(mrpDTO.getMrpCode());
        resultMrpJson.setMrpSubCode(mrpDTO.getMrpSubCode());
        resultMrpJson.setMrpPoCode(mrpDTO.getSoCode());
        resultMrpJson.setIsActive((byte) 1);
        resultMrpJson.setDataResultJson(filePath);
        resultMrpRepository.save(resultMrpJson);

        // Save production quantity
        List<MrpProductionQuantityEntity> itemProductionQuantityList = new ArrayList<>();
        List<MrpRequiredQuantityEntity> itemRequiredQuantityList = new ArrayList<>();
        for (MrpDetailDTO item : mrpDTO.getResultData()) {
            // Skip hien trang
            for (int i = 1; i < item.getDetailResult().size(); i++) {
                if (item.getDetailResult().get(i).getOriginQuantity() > 0) {
                    itemProductionQuantityList.add(
                        mrpProductionQuantityMapper.toEntity(mrpDTO.getSoCode(), mrpDTO.getMrpSubCode(), item, item.getDetailResult().get(i))
                    );
                }

                if (item.getRequiredQuantity() > 0) {
                    itemRequiredQuantityList.add(
                        mrpRequiredQuantityMapper.toEntity(mrpDTO.getSoCode(), mrpDTO.getMrpSubCode(), item)
                    );
                }
            }
        }
        SyntheticMrpDTO syntheticData = viewSynthetic(mrpDTO).getData();
        for (ItemSyntheticDTO item : syntheticData.getResultData()) {
            for (int i = 1; i < item.getDetailData().size(); i++) {
                if (item.getDetailData().get(i).getOriginQuantity() > 0) {
                    itemProductionQuantityList.add(
                        mrpProductionQuantityMapper.toEntity(mrpDTO.getSoCode(), mrpDTO.getMrpSubCode(), item, item.getDetailData().get(i))
                    );
                }

                if (item.getRequiredQuantity() > 0) {
                    itemRequiredQuantityList.add(
                        mrpRequiredQuantityMapper.toEntity(mrpDTO.getSoCode(), mrpDTO.getMrpSubCode(), item)
                    );
                }

            }
        }
        mrpProductionQuantityRepository.saveAll(itemProductionQuantityList);
        mrpRequiredQuantityRepository.saveAll(itemRequiredQuantityList);

        // update item status
        List<String> itemCode = mrpDTO.getResultData().stream().map(MrpDetailDTO::getItemCode).collect(Collectors.toList());
        productOrderDetailRepository.updateStatusProductInPO((byte) Constants.ProductOrder.STATUS_ORDER_ANALYTICS_FULL, itemCode, mrpDTO.getSoCode());

        // Update SO status if new item count = 0
        int newItemCount = productOrderDetailRepository.countByItemOfSOAndStatusIn(
            mrpDTO.getSoCode(),
            List.of(Constants.ProductOrder.STATUS_NEW, Constants.ProductOrder.STATUS_ORDER_ANALYTICS)
        );
        if (newItemCount == 0) productOrderRepository.updateStatusPO(Constants.ProductOrder.STATUS_ORDER_ANALYTICS_FULL, mrpDTO.getSoCode(), SecurityUtils.getCurrentUserLogin().orElse("system"));
    }

    @Transactional
    public CommonResponse deleteHistoryMrp(String mrpCode) {
        log.info("Xóa mrpCode {}", mrpCode);
        if (!historyMrpRepository.existsByMrpCodeAndIsActive(mrpCode, (byte) 1))
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        if (historyMrpRepository.deleteMrpHistory(mrpCode) != 1) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        }
        log.info("Xóa mrpCode {} thành công", mrpCode);
        int numberOfSubMrp = mrpSubRepository.deleteAllSubMrpHistoryOfMrpCode(mrpCode);
        List<String> mrpSubCodeList = mrpSubRepository.getAllMrpSubCodeByMrpCode(mrpCode);
        itemHoldRepository.unHoldItemsOf(mrpSubCodeList);
        log.info("Xóa {} subMrp của mrpCode {} thành công", numberOfSubMrp, mrpCode);
        int deletedProductionItem = mrpProductionQuantityRepository.deleteByMrpSubCode(mrpSubCodeList);
        int deletedRequiredItem = mrpRequiredQuantityRepository.deleteByMrpSubCode(mrpSubCodeList);
        log.info("Xoá {} production item, {} required item ", deletedProductionItem, deletedRequiredItem);
        return new CommonResponse<>().success("Xóa mã MRP " + mrpCode + " thành công");
    }

    @Transactional
    public CommonResponse deleteHistorySubMrp(String mrpCode, String mrpSubCode) {
        log.info("Xóa subMrpCode {} của mrpCode {}", mrpSubCode, mrpCode);
        if (!historyMrpRepository.existsByMrpCodeAndIsActive(mrpCode, (byte) 1) || !mrpSubRepository.existsByMrpSubCodeAndIsActive(mrpSubCode, (byte) 1))
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        if (mrpSubRepository.deleteSubMrpHistory(mrpSubCode) != 1) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        }
        itemHoldRepository.unHoldItemsOf(mrpSubCode);
        log.info("Xóa mã subMrp {} của mrpCode {} thành công", mrpSubCode, mrpCode);
        int deletedProductionItem = mrpProductionQuantityRepository.deleteByMrpSubCode(mrpSubCode);
        int deletedRequiredItem = mrpRequiredQuantityRepository.deleteByMrpSubCode(mrpSubCode);
        log.info("Xoá {} production item, {} required item ", deletedProductionItem, deletedRequiredItem);
        return new CommonResponse<>().success("Xóa mã MRP " + mrpSubCode + " thành công");
    }

    /**
     * hàm tổng hợp sau khi chạy mrp và xem btp ,tp của từng item
     * @param mrpDTO
     * @return
     */
    public PageResponse<SyntheticMrpDTO> viewSynthetic(MrpDTO mrpDTO) {
        List<MrpDetailDTO> items = mrpDTO.getResultData();
        List<ItemSyntheticDTO> itemSyntheticDTOList = new ArrayList<>();//danh sách tổng hợp nvl
        Set<String> itemCodeSet = new HashSet<>();

        for (MrpDetailDTO item : items) {
            List<MrpDetailDTO> mrpDetailDTOList = item.getChildren();//danh sách item trong sản phẩm
            List<ItemSyntheticDTO> itemSyntheticDTOs = new ArrayList<>();
            List<ItemSyntheticDTO> listResult = dfsSynthetic(itemSyntheticDTOs, mrpDetailDTOList, itemCodeSet, item.getItemCode(), item.getItemName(), item.getBomVersion(), "", "", "");
            for (ItemSyntheticDTO list : listResult) {
                Integer check = checkExist(itemSyntheticDTOList, list.getItemCode());
                if (check == null) {
                    itemSyntheticDTOList.add(list);
                } else {
                    ItemSyntheticDTO itemSyntheticDTO = itemSyntheticDTOList.get(check);
                    itemSyntheticDTO.setRequestNumber(itemSyntheticDTO.getRequestNumber() + list.getRequestNumber());
//                    itemSyntheticDTO.setPrNumber(itemSyntheticDTO.getPrNumber() + list.getPrNumber());
                    if (!itemSyntheticDTOList.isEmpty()) {
                        for (ViewBtpTpDTO vb : list.getViewBtpTp()) {
                            itemSyntheticDTO.getViewBtpTp().add(vb);
                        }
                    }
                    List<DetailItemSyntheticDTO> list1 = itemSyntheticDTO.getDetailData();
                    List<DetailItemSyntheticDTO> list2 = list.getDetailData();
                    // skip Hiện trạng
                    for (int i = 1; i < list1.size(); i++) {
                        list1.get(i).setRequiredQuantity(list1.get(i).getRequiredQuantity() + list2.get(i).getRequiredQuantity());
                        list1.get(i).setOriginQuantity(list1.get(i).getOriginQuantity() + list2.get(i).getOriginQuantity());
//                        list1.get(i).setExpectedQuantity(list1.get(i).getExpectedQuantity() + list2.get(i).getExpectedQuantity());
                    }
                    itemSyntheticDTO.setDetailData(list1);
                    itemSyntheticDTO.setRequiredQuantity(itemSyntheticDTO.getRequiredQuantity() + list.getRequiredQuantity());
                }
            }
        }

        SyntheticMrpDTO syntheticMrpDTO = new SyntheticMrpDTO();
        syntheticMrpDTO.setMrpCode(mrpDTO.getMrpCode());
        syntheticMrpDTO.setMrpSubCode(mrpDTO.getMrpSubCode());
        syntheticMrpDTO.setMrpDescription(mrpDTO.getMrpDescription());
        syntheticMrpDTO.setAnalysisPeriod(mrpDTO.getAnalysisPeriod());
        syntheticMrpDTO.setTimeStart(mrpDTO.getTimeStart());
        syntheticMrpDTO.setTimeEnd(mrpDTO.getTimeEnd());
        syntheticMrpDTO.setCreatedAt(mrpDTO.getAnalysisDate());
        syntheticMrpDTO.setSoCode(mrpDTO.getSoCode());
        syntheticMrpDTO.setResultData(itemSyntheticDTOList);

        //TODO: Optimize
        for (ItemSyntheticDTO itemSyntheticDTO : syntheticMrpDTO.getResultData()) {
            double totalRequiredQuantity = 0;
            // Số lượng trên kho SAP
            double inStockQuantity = itemSyntheticDTO.getDetailData().get(0).getOriginQuantity();

            // Old code
            // SL Sẵn sàng
//            double readyQuantity = itemSyntheticDTO.getReadyQuantity();

            for (int i = 1; i < itemSyntheticDTO.getDetailData().size(); i++) {
                DetailItemSyntheticDTO resultDTO = itemSyntheticDTO.getDetailData().get(i);
                // Không có nhu cầu sản xuất
                if (resultDTO.getOriginQuantity() == 0.0) continue;
                log.info("Synthetic item {}", itemSyntheticDTO.getItemCode());

                // SL sẵn sàng = SL trên kho SAP + SL PR - SL Hold
                double readyQuantity =
                    inStockQuantity // SL trên SAP
//                    + resultDTO.getExpectedQuantity()
                    + resultDTO.getSumPoAndDeliveringQuantity() // SL PR
                    - resultDTO.getOriginalRequiredQuantity(); // SL Hold
                log.info("ready quantity {}, inStock {}, PR {}, Hold {}", readyQuantity, inStockQuantity, resultDTO.getSumPoAndDeliveringQuantity(), resultDTO.getOriginalRequiredQuantity());
//                if (readyQuantity > 0) {
                    if (readyQuantity > resultDTO.getOriginQuantity()) {
                        // Lấy trong kho -> SL cần mua = 0
                        inStockQuantity = readyQuantity - resultDTO.getOriginQuantity();
                        resultDTO.setRequiredQuantity(0.0);
                        log.info("readyQuantity > needQuantity (lấy trong kho) sl kho {}, Sl cần mua = 0", inStockQuantity);
                    } else {
                        // Lấy hết trong kho
                        // Mua 1 phần = SL cần sản xuất - SL sẵn sàng >= 0
                        resultDTO.setRequiredQuantity(
                            Math.max(resultDTO.getOriginQuantity() - readyQuantity, 0)
                        );
                        inStockQuantity = 0;
                        log.info("readyQuantity < needQuantity (mua 1 phần) sl kho {}, Sl cần mua = {}", inStockQuantity, resultDTO.getRequiredQuantity());
                    }
//                } else {
//                    inStockQuantity = 0;
//                    log.info("readyQuantity < 0 Sl cần mua = {}", resultDTO.getRequiredQuantity());
//                }

                // Old code
//                readyQuantity = readyQuantity
//                    - resultDTO.getOriginalRequiredQuantity()
//                    + resultDTO.getExpectedQuantity();
//                double requiredQuantity = resultDTO.getRequiredQuantity();
//                System.err.println(itemSyntheticDTO.getItemCode() + " " + resultDTO.getLandMark());
//                System.err.println("Sl sẵn sàng " + readyQuantity + " sl yeu cau " + requiredQuantity);
//                // Sl sẵn sàng > sl yêu cầu (ko cần mua thêm)
//                if (readyQuantity > 0 && requiredQuantity > 0) {
//                    if (readyQuantity > resultDTO.getRequiredQuantity()) {
//                        readyQuantity -= requiredQuantity;
//                        requiredQuantity = 0;
//                        System.err.println("Sl sẵn sàng > sl yêu cầu " + readyQuantity + " " + requiredQuantity);
//                    } else {
//                        // Lấy hết sl sẵn sàng còn lại
//                        requiredQuantity -= readyQuantity;
//                        readyQuantity = 0;
//                        System.err.println("Sl sẵn sàng < sl yêu cầu " + readyQuantity + " " + requiredQuantity);
//                    }
//                }

//                itemSyntheticDTO.getDetailData().get(i).setRequiredQuantity(requiredQuantity);
                totalRequiredQuantity += resultDTO.getRequiredQuantity();
            }

            itemSyntheticDTO.setRequestNumber(
                // Tong tat ca yeu cau
                Math.max(0, totalRequiredQuantity)
            );
        }
        return new PageResponse<SyntheticMrpDTO>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .data(syntheticMrpDTO);
    }

    //check trùng
    private Integer checkExist(List<ItemSyntheticDTO> items, String itemCode) {
        int index = 0;
        for (ItemSyntheticDTO item : items) {
            if (item.getItemCode().equals(itemCode)) {
                return index;
            }
            index++;
        }
        return null;
    }

    /**
     * đệ quy lấy các nvl trong tp
     *
     * @param itemSyntheticDTOList
     * @param items
     * @param itemCodeSet
     * @param productCode
     * @param productName
     * @param proBomVer
     * @param itemCode
     * @param itemName
     * @param bomVersion
     * @return
     */
    private List<ItemSyntheticDTO> dfsSynthetic(List<ItemSyntheticDTO> itemSyntheticDTOList, List<MrpDetailDTO> items, Set<String> itemCodeSet, String productCode, String productName, String proBomVer, String itemCode, String itemName, String bomVersion) {
        for (MrpDetailDTO mrpDetailDTO : items) {
            if (mrpDetailDTO.getGroupItem().equals("NVL")) {
                ArrayList<ViewBtpTpDTO> listViewBtpTp = new ArrayList<>();
                ViewBtpTpDTO viewBtpTpDTO = new ViewBtpTpDTO();
                viewBtpTpDTO.setProductCode(productCode);
                viewBtpTpDTO.setProductName(productName);
                viewBtpTpDTO.setProductBomVer("1.0");
                viewBtpTpDTO.setSemiProductCode(itemCode);
                viewBtpTpDTO.setSemiProductName(itemName);
                viewBtpTpDTO.setSemiProductBomVer("1.0");
                viewBtpTpDTO.setLevel(mrpDetailDTO.getLevel());
                listViewBtpTp.add(viewBtpTpDTO);
                ItemSyntheticDTO itemSyntheticDTO = new ItemSyntheticDTO();
                itemSyntheticDTO.setViewBtpTp(listViewBtpTp);
                itemSyntheticDTO.setItemCode(mrpDetailDTO.getItemCode());
                itemSyntheticDTO.setItemName(mrpDetailDTO.getItemName());
                itemSyntheticDTO.setAltItemCode(mrpDetailDTO.getAltItemCode());
                itemSyntheticDTO.setType("NVL");
                itemSyntheticDTO.setBomVersion("1.0");

                if (mrpDetailDTO.getRequiredQuantity() != null) {
                    itemSyntheticDTO.setRequestNumber(mrpDetailDTO.getRequiredQuantity());
                }
                List<DetailItemSyntheticDTO> detailItemList = new ArrayList<>();
                double prNumber = 0.0;

                for (int i = 0; i < mrpDetailDTO.getDetailResult().size(); i++) {
                    MrpResultDTO resultDTO = mrpDetailDTO.getDetailResult().get(i);
                    DetailItemSyntheticDTO detailItemSyntheticDTO = new DetailItemSyntheticDTO();
                    detailItemSyntheticDTO.setLandMark(resultDTO.getLandmark());
                    detailItemSyntheticDTO.setExpectedQuantity(resultDTO.getExpectedQuantity());

                    // Not the first (top-most) result => ignore PR/PO/GRPO
                    if (itemCodeSet.contains(mrpDetailDTO.getItemCode()))
                        itemCodeSet.add(mrpDetailDTO.getItemCode());
                    else
                        detailItemSyntheticDTO.setSumPoAndDeliveringQuantity(resultDTO.getSumPoAndDeliveringQuantity());

                    detailItemSyntheticDTO.setOriginQuantity(resultDTO.getOriginQuantity());
                    detailItemSyntheticDTO.setOriginalRequiredQuantity(resultDTO.getRequiredQuantity());
                    detailItemSyntheticDTO.setRequiredQuantity(resultDTO.getOriginQuantity());
                    detailItemSyntheticDTO.setOrderQuantity(resultDTO.getOrderQuantity());
//                    detailItemSyntheticDTO.setRequiredQuantity(resultDTO.getOrderQuantity());
//                    prNumber += detailItemSyntheticDTO.getExpectedQuantity();
                    prNumber += detailItemSyntheticDTO.getSumPoAndDeliveringQuantity();
                    detailItemList.add(detailItemSyntheticDTO);
                }
                itemSyntheticDTO.setPrNumber(prNumber);
                itemSyntheticDTO.setReadyQuantity(mrpDetailDTO.getDetailResult().get(0).getReadyQuantity());
                itemSyntheticDTO.setRequiredQuantity(mrpDetailDTO.getRequiredQuantity());
                itemSyntheticDTO.setDetailData(detailItemList);
                itemSyntheticDTO.setCurrentWarehouseInventoryList(mrpDetailDTO.getCurrentWarehouseInventoryList());
                if (itemSyntheticDTO.getItemCode() != null && !itemSyntheticDTO.getItemCode().equals("")) {
                    itemSyntheticDTOList.add(itemSyntheticDTO);
                }
            } else {
                ArrayList<ViewBtpTpDTO> listViewBtpTp = new ArrayList<>();
                ViewBtpTpDTO viewBtpTpDTO = new ViewBtpTpDTO();
                viewBtpTpDTO.setProductCode(productCode);
                viewBtpTpDTO.setProductName(productName);
                viewBtpTpDTO.setProductBomVer("1.0");
                viewBtpTpDTO.setSemiProductCode(itemCode);
                viewBtpTpDTO.setSemiProductName(itemName);
                viewBtpTpDTO.setSemiProductBomVer("1.0");
                viewBtpTpDTO.setLevel(mrpDetailDTO.getLevel());
                listViewBtpTp.add(viewBtpTpDTO);
                ItemSyntheticDTO itemSyntheticDTO = new ItemSyntheticDTO();
                itemSyntheticDTO.setViewBtpTp(listViewBtpTp);
                itemSyntheticDTO.setItemCode(mrpDetailDTO.getItemCode());
                itemSyntheticDTO.setItemName(mrpDetailDTO.getItemName());
                itemSyntheticDTO.setAltItemCode(mrpDetailDTO.getAltItemCode());
                itemSyntheticDTO.setType("BTP");
                itemSyntheticDTO.setBomVersion("1.0");

                if (mrpDetailDTO.getRequiredQuantity() != null) {
                    itemSyntheticDTO.setRequestNumber(mrpDetailDTO.getRequiredQuantity());
                } else {
                    itemSyntheticDTO.setRequestNumber(0.0);
                }
                List<DetailItemSyntheticDTO> detailItemList = new ArrayList<>();
                double prNumber = 0.0;

                for (int i = 0; i < mrpDetailDTO.getDetailResult().size(); i++) {
                    MrpResultDTO resultDTO = mrpDetailDTO.getDetailResult().get(i);
                    DetailItemSyntheticDTO detailItemSyntheticDTO = new DetailItemSyntheticDTO();
                    detailItemSyntheticDTO.setLandMark(resultDTO.getLandmark());
                    detailItemSyntheticDTO.setExpectedQuantity(resultDTO.getExpectedQuantity());
                    detailItemSyntheticDTO.setSumPoAndDeliveringQuantity(resultDTO.getSumPoAndDeliveringQuantity());
                    detailItemSyntheticDTO.setOriginQuantity(resultDTO.getOriginQuantity());
                    detailItemSyntheticDTO.setOriginalRequiredQuantity(resultDTO.getRequiredQuantity());
                    detailItemSyntheticDTO.setRequiredQuantity(resultDTO.getOriginQuantity());
                    detailItemSyntheticDTO.setOrderQuantity(resultDTO.getOrderQuantity());
//                    detailItemSyntheticDTO.setRequiredQuantity(resultDTO.getOrderQuantity());
//                    prNumber += detailItemSyntheticDTO.getExpectedQuantity();
                    prNumber += detailItemSyntheticDTO.getSumPoAndDeliveringQuantity();
                    detailItemList.add(detailItemSyntheticDTO);
                }
                itemSyntheticDTO.setPrNumber(prNumber);
                itemSyntheticDTO.setReadyQuantity(mrpDetailDTO.getDetailResult().get(0).getReadyQuantity());
                itemSyntheticDTO.setRequiredQuantity(mrpDetailDTO.getRequiredQuantity());
                itemSyntheticDTO.setDetailData(detailItemList);
                itemSyntheticDTO.setCurrentWarehouseInventoryList(mrpDetailDTO.getCurrentWarehouseInventoryList());
                if (!StringUtils.isEmpty(itemSyntheticDTO.getItemCode())) {
                    itemSyntheticDTOList.add(itemSyntheticDTO);
                }
                List<MrpDetailDTO> mrpDetailDTOList = mrpDetailDTO.getChildren();
                if (mrpDetailDTOList != null)
                    dfsSynthetic(itemSyntheticDTOList, mrpDetailDTOList, itemCodeSet, productCode, productName, proBomVer, mrpDetailDTO.getItemCode(), mrpDetailDTO.getItemName(), mrpDetailDTO.getBomVersion());
            }
        }
        return itemSyntheticDTOList;
    }

    /**
     * hàm lấy ds những nvl đã đc khuyến nghị
     * @param mrpSubCode
     * @return
     * @throws JsonProcessingException
     */
    public PageResponse<?> getMrpResultByCode(String mrpSubCode) throws JsonProcessingException {
        ResultMrpJsonEntity resultMrpJsonEntity = historyMrpRepository.getMrpResult(mrpSubCode);
        if (resultMrpJsonEntity == null) {
            return new PageResponse<>()
                .errorCode("400")
                .message("yêu cầu không hợp lệ")
                .isOk(false);
        }
        String data = resultMrpJsonEntity.getDataResultJson();
        ObjectMapper objectMapper = new ObjectMapper();
        MrpDTO mrpDTO = objectMapper.readValue(data, MrpDTO.class);

        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .data(mrpDTO);
    }

    public CommonResponse getAnalyzedMRPOfOrder(String productOrderCode) {
        List<ListDetailMrpDTO> analyzedMRP = mrpSubRepository.getAllAnalyzedMrpOf(productOrderCode);
        if (CollectionUtils.isEmpty(analyzedMRP)) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        return new CommonResponse<>().success().data(analyzedMRP);
    }
}
