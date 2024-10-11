package com.facenet.mrp.thread;

import com.facenet.mrp.cache.PrPoGrpoCache;
import com.facenet.mrp.domain.mrp.SapOnOrderSummary;
import com.facenet.mrp.repository.mrp.SapOnOrderSummaryRepository;
import com.facenet.mrp.repository.sap.PoRepository;
import com.facenet.mrp.service.GrpoService;
import com.facenet.mrp.service.PurchaseRequestService;
import com.facenet.mrp.service.dto.GrpoDTO;
import com.facenet.mrp.service.dto.PoDto;
import com.facenet.mrp.service.dto.PurchaseRequestDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.mapper.SapMaterialOnOrderMapper;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.PoFilter;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Profile("prod")
@Component
public class MaterialOnOrderSummaryJob {

    private final Logger logger = LogManager.getLogger(MaterialOnOrderSummaryJob.class);

    @Autowired
    private PoRepository poRepository;

    @Autowired
    private SapOnOrderSummaryRepository sapOnOrderSummaryRepository;

    @Autowired
    private SapMaterialOnOrderMapper mapper;

    @Autowired
    private PurchaseRequestService purchaseRequestService;

    @Autowired
    private GrpoService grpoService;

    @Autowired
    private PrPoGrpoCache prPoGrpoCache;

//    //@Scheduled(fixedDelay = 30 * 60 * 1000)
    public void poOnOrderSummary() {
        logger.info("------ start run PO_OnOrderSummary job ------");
        PoFilter poFilter = new PoFilter();
//        poFilter.setSapUpdatedAt(LocalDate.now());
        poFilter.setStatus("O");
        clonePo(poFilter);
    }

    public void clonePo(PoFilter poFilter) {
        Page<PoDto> poDtos = poRepository.getAllPoList(Pageable.unpaged(), poFilter);
        logger.info("po from SAP size {}", poDtos.getContent().size());
        List<SapOnOrderSummary> insertList = new ArrayList<>();
        List<SapOnOrderSummary> updateList = new ArrayList<>();

        Set<String> closedPo = new HashSet<>(prPoGrpoCache.getPoCache());

        if (!CollectionUtils.isEmpty(poDtos.getContent())) {
            logger.info("poDtos.getContent().size() {}", poDtos.getContent().size());
            poDtos.getContent().forEach(poDto -> {
                if (!prPoGrpoCache.isPoContained(poDto.getPoCode(), poDto.getLineNumber())) {
                    //TODO: not in DB -> insert
                    insertList.add( mapper.poToEntity(poDto));
                    prPoGrpoCache.addPo(poDto.getPoCode(), poDto.getLineNumber());
                }
                closedPo.remove(Utils.toItemKey(poDto.getPoCode(), String.valueOf(poDto.getLineNumber())));

//                SapOnOrderSummary entity = sapOnOrderSummaryRepository.findBySapCodeAndTypeAndLineNumber(
//                    poDto.getPoCode().trim(),
//                    Constants.PO_TYPE,
//                    poDto.getLineNumber()
//                );
//
//                if (entity != null) {
////                    logger.info("update record sapCode = {}, itemCode = {}",entity.getSapCode(),entity.getItemCode());
//                    entity.setStatus(poDto.getStatus());
//                    entity.setQuantity((double) poDto.getQuantity());
//                    entity.setDueDate(poDto.getReceivedDate() == null ? null : poDto.getReceivedDate());
//                    entity.setPoCode(poDto.getPoCode() == null ? null : poDto.getPoCode().trim());
//                    entity.setGrpoCode(poDto.getGrpoCode() == null ? null : poDto.getGrpoCode().trim());
//                    entity.setPrCode(poDto.getPrCode() == null ? null : poDto.getPrCode().trim());
//                    entity.setMrpCode(poDto.getMrpCode() == null ? null : poDto.getMrpCode().trim());
//                    entity.setSoCode(poDto.getSoCode() == null ? null : poDto.getSoCode().trim());
//                    entity.setProviderCode(poDto.getVendorCode() == null ? null : poDto.getVendorCode().trim());
//                    entity.setProviderName(poDto.getVendor() == null ? null : poDto.getVendor().trim());
//                    entity.setContractNumber(poDto.getContractCode() == null ? null : poDto.getContractCode().trim());
//                    entity.setPoCreateDate(poDto.getCreatedAt());
//                    entity.setCreatePoUser(poDto.getPersonInCharge() == null ? null : poDto.getPersonInCharge().trim());
////                    updateList.add(entity);
//                    sapOnOrderSummaryRepository.save(entity);
//                } else {
//                    logger.info("insert new PO record sapCode = {} itemCode = {}", poDto.getPoCode(), poDto.getProductCode());
//                    entity = mapper.poToEntity(poDto);
////                    insertList.add(entity);
//                    sapOnOrderSummaryRepository.save(entity);
//                }
            });
        }

        //TODO update close
        for (String closedKey : closedPo) {
            String[] splitKey = closedKey.split("-");
            String sapCode = splitKey[0];
            Integer lineNumber = Integer.valueOf(splitKey[1]);
            logger.info("Close PO {} {}", sapCode, lineNumber);
            SapOnOrderSummary entity = sapOnOrderSummaryRepository.findBySapCodeAndTypeAndLineNumber(
                sapCode,
                Constants.PO_TYPE,
                lineNumber
            );

            if (entity != null) {
                entity.setStatus("C");
                updateList.add(entity);
                // Closed => remove Open
                prPoGrpoCache.removePo(sapCode, lineNumber);
            }
        }
        sapOnOrderSummaryRepository.saveAll(updateList);
        sapOnOrderSummaryRepository.saveAll(insertList);
        System.err.println("PO Size: "+prPoGrpoCache.getPoCache().size());
//        logger.info("Update PO size {}, insert PO size {}", toUpdateList.size(), insertList.size());
        logger.info("------ finish run PO_OnOrderSummary job ------");
    }


    //@Scheduled(fixedDelay = 30 * 60 * 1000)
    public void prOnOrderSummary() {
        logger.info("------ start run PR_OnOrderSummary job ------");
        PageFilterInput<PurchaseRequestDTO> input = new PageFilterInput<>();
        input.setPageSize(0);
        input.setPageNumber(0);
        PurchaseRequestDTO filter = new PurchaseRequestDTO();
//        filter.setSapUpdatedAt(LocalDate.now());
        filter.setStatus("O");
        input.setFilter(filter);
        clonePr(input);
    }

    public void clonePr(PageFilterInput<PurchaseRequestDTO> input) {
        PageResponse<List<PurchaseRequestDTO>> pageResponse = purchaseRequestService.getPurchaseRequestsWithPaging(input);

        List<PurchaseRequestDTO> purchaseRequestDTOS = pageResponse.getData();
        logger.info("prDtos size {}", purchaseRequestDTOS.size());
        List<SapOnOrderSummary> insertList = new ArrayList<>();
        List<SapOnOrderSummary> updateList = new ArrayList<>();

        // Init closedPr set equal to opening PR in MRP
        Set<String> closedPr = new HashSet<>(prPoGrpoCache.getPrCache());

        if (!CollectionUtils.isEmpty(purchaseRequestDTOS)) {
            logger.info("PR from SAP size {}", purchaseRequestDTOS.size());
            purchaseRequestDTOS.forEach(purchaseRequestDTO -> {
                if (!prPoGrpoCache.isPrContained(purchaseRequestDTO.getPrId(), purchaseRequestDTO.getLineNumber())) {
                    //TODO not in MRP -> insert
                    insertList.add(mapper.prToEntity(purchaseRequestDTO));
                    prPoGrpoCache.addPr(purchaseRequestDTO.getPrId(), purchaseRequestDTO.getLineNumber());
                }
                // Remove current item -> leave only closed pr left
                closedPr.remove(Utils.toItemKey(purchaseRequestDTO.getPrId(), String.valueOf(purchaseRequestDTO.getLineNumber())));
//                SapOnOrderSummary entity = sapOnOrderSummaryRepository.findBySapCodeAndTypeAndLineNumber(
//                    purchaseRequestDTO.getPrId().trim(),
//                    Constants.PR_TYPE,
//                    purchaseRequestDTO.getLineNumber()
//                );
//                if (entity != null) {
////                    logger.info("update record sapCode = {}, itemCode = {}",entity.getSapCode(),entity.getItemCode());
//                    entity.setStatus(purchaseRequestDTO.getStatus());
//                    entity.setQuantity(Double.valueOf(purchaseRequestDTO.getQuantity()));
//                    entity.setDueDate(purchaseRequestDTO.getDeliverAt() == null ? null : purchaseRequestDTO.getDeliverAt());
//                    entity.setPoCode(purchaseRequestDTO.getPoId() == null ? null : purchaseRequestDTO.getPoId().trim());
//                    entity.setPrCode(purchaseRequestDTO.getPrId() == null ? null : purchaseRequestDTO.getPrId().trim());
//                    entity.setGrpoCode(purchaseRequestDTO.getGrpoId() == null ? null : purchaseRequestDTO.getGrpoId().trim());
//                    entity.setProviderCode(purchaseRequestDTO.getVendorCode() == null ? null : purchaseRequestDTO.getVendorCode().trim());
//                    entity.setProviderName(purchaseRequestDTO.getVendor() == null ? null : purchaseRequestDTO.getVendor().trim());
//                    entity.setMrpCode(purchaseRequestDTO.getMrpId() == null ? null : purchaseRequestDTO.getMrpId().trim());
//                    entity.setSoCode(purchaseRequestDTO.getSoCode() == null ? null : purchaseRequestDTO.getSoCode().trim());
//                    entity.setPoCreateDate(purchaseRequestDTO.getCreatedAt());
//                    entity.setCreatePoUser(purchaseRequestDTO.getCreatedBy());
////                    updateList.add(entity);
//                    sapOnOrderSummaryRepository.save(entity);
//                } else {
//                    logger.info("insert new PR record sapCode = {} itemCode = {}", purchaseRequestDTO.getPrId(), purchaseRequestDTO.getProductId());
//                    entity = mapper.prToEntity(purchaseRequestDTO);
////                    insertList.add(entity);
//                    sapOnOrderSummaryRepository.save(entity);
//                }
            });
        }

        //TODO update close
        for (String closedKey : closedPr) {
            String[] splitKey = closedKey.split("-");
            String sapCode = splitKey[0];
            Integer lineNumber = Integer.valueOf(splitKey[1]);
            logger.info("Close PR {} {}", sapCode, lineNumber);
            SapOnOrderSummary entity = sapOnOrderSummaryRepository.findBySapCodeAndTypeAndLineNumber(
                sapCode,
                Constants.PR_TYPE,
                lineNumber
            );
            if (entity != null) {
                entity.setStatus("C");
                updateList.add(entity);
                // Closed => remove Open
                prPoGrpoCache.removePr(sapCode, lineNumber);
            }


        }

        sapOnOrderSummaryRepository.saveAll(updateList);
        sapOnOrderSummaryRepository.saveAll(insertList);
        logger.info("------ finish run PR_OnOrderSummary job ------");
    }

    //@Scheduled(fixedDelay = 30 * 60 * 1000)
    public void grpoOnOrderSummary() {
        logger.info("------ start run GRPO_OnOrderSummary job ------");
        PageFilterInput<GrpoDTO> input = new PageFilterInput<>();
        input.setPageSize(0);
        input.setPageNumber(0);
        GrpoDTO filter = new GrpoDTO();
//        filter.setSapUpdatedAt(LocalDate.now());
        filter.setStatus("O");
        input.setFilter(filter);
        cloneGrpo(input);
    }

    public void cloneGrpo(PageFilterInput<GrpoDTO> input) {
        PageResponse<List<GrpoDTO>> pageResponse = grpoService.getAllGrpo(input, Pageable.unpaged());
        List<GrpoDTO> grpoDTOList = pageResponse.getData();
        logger.info("grpo from SAP size {}", grpoDTOList.size());

        List<SapOnOrderSummary> insertList = new ArrayList<>();
        List<SapOnOrderSummary> updateList = new ArrayList<>();

        Set<String> closedGrpo = new HashSet<>(prPoGrpoCache.getGrpoCache());

        if (!CollectionUtils.isEmpty(grpoDTOList)) {
            logger.info("GRPO from SAP size {}", grpoDTOList.size());
            grpoDTOList.forEach(grpoDTO -> {
                if (!prPoGrpoCache.isGrpoContained(grpoDTO.getGrpoId(), grpoDTO.getLineNumber())) {
                    insertList.add(mapper.grpoToEntity(grpoDTO));
                    prPoGrpoCache.addGrpo(grpoDTO.getGrpoId(), grpoDTO.getLineNumber());
                }
                closedGrpo.remove(Utils.toItemKey(grpoDTO.getGrpoId(), String.valueOf(grpoDTO.getLineNumber())));
//                SapOnOrderSummary entity = sapOnOrderSummaryRepository.findBySapCodeAndTypeAndLineNumber(
//                    grpoDTO.getGrpoId().trim(),
//                    Constants.GRPO_TYPE,
//                    grpoDTO.getLineNumber()
//                );
//                if (entity != null) {
////                    logger.info("update record sapCode = {}, itemCode = {}",entity.getSapCode(),entity.getItemCode());
//                    entity.setStatus(grpoDTO.getStatus());
//                    entity.setQuantity((double) grpoDTO.getDeliveredNum());
//                    entity.setDueDate(grpoDTO.getWareHouseDate());
//                    entity.setPoCode(grpoDTO.getPoId() == null ? null : grpoDTO.getPoId().trim());
//                    entity.setMrpCode(grpoDTO.getMrpCode() == null ? null : grpoDTO.getMrpCode().trim());
//                    entity.setSoCode(grpoDTO.getSoId() == null ? null : grpoDTO.getSoId().trim());
//                    entity.setPrCode(grpoDTO.getPrId() == null ? null : grpoDTO.getPrId().trim());
//                    entity.setGrpoCode(grpoDTO.getGrpoId() == null ? null : grpoDTO.getGrpoId().trim());
//                    entity.setProviderCode(grpoDTO.getVendorCode());
//                    entity.setProviderName(grpoDTO.getVendor());
//                    entity.setPoCreateDate(grpoDTO.getCreatedAt());
//                    entity.setCreatePoUser(grpoDTO.getWareHouser());
//                    sapOnOrderSummaryRepository.save(entity);
////                    updateList.add(entity);
//                } else {
//                    logger.info("insert new GRPO record sapCode = {} itemCode = {}", grpoDTO.getGrpoId(), grpoDTO.getProductId());
//                    entity = mapper.grpoToEntity(grpoDTO);
////                    insertList.add(entity);
//                    sapOnOrderSummaryRepository.save(entity);
//                }
            });
        }

        //TODO update close
        for (String closedKey : closedGrpo) {
            String[] splitKey = closedKey.split("-");
            String sapCode = splitKey[0];
            Integer lineNumber = Integer.valueOf(splitKey[1]);
            logger.info("Close GRPO {} {}", sapCode, lineNumber);
            SapOnOrderSummary entity = sapOnOrderSummaryRepository.findBySapCodeAndTypeAndLineNumber(
                sapCode,
                Constants.GRPO_TYPE,
                lineNumber
            );

            if (entity != null) {
                entity.setStatus("C");
                updateList.add(entity);
                // Closed => remove Open
                prPoGrpoCache.removeGrpo(sapCode, lineNumber);
            }
        }
        sapOnOrderSummaryRepository.saveAll(updateList);
        sapOnOrderSummaryRepository.saveAll(insertList);
//        logger.info("Update GRPO size {}, insert GRPO size {}", toUpdateList.size(), insertList.size());
        logger.info("------ finish run GRPO_OnOrderSummary job ------");
    }
}
