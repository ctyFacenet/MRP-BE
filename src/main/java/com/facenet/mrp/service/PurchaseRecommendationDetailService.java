package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.OcrdRepository;
import com.facenet.mrp.repository.sap.OitmRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.SendApprovalRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.dto.sap.PurchaseRequestDetailApiDTO;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ItemHoldMapper;
import com.facenet.mrp.service.mapper.PurchaseRequestApiMapper;
import com.facenet.mrp.service.mapper.RecommendationPlanMapper;
import com.facenet.mrp.service.model.ApproveInput;
import com.facenet.mrp.service.model.ItemFilter;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.UpdatePurchaseRecommendationForm;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseRecommendationDetailService {
    private static final Logger logger = LogManager.getLogger(PurchaseRecommendationDetailService.class);
    private final PurchaseRecommendationDetailRepository purchaseRecommendationDetailRepository;
    private final PurchaseRecommendationRepository purchaseRecommendationRepository;
    private final MqqPriceRepository mqqPriceRepository;
    private final ItemHoldRepository itemHoldRepository;
    private final EntityManager entityManager;
    private final PurchaseRecommendationPlanRepository planRepository;
    private final RecommendationPlanMapper planMapper;
    private final PurchaseRequestApiMapper purchaseRequestApiMapper;
    private final PurchaseRecommendationBatchRepository purchaseRecommendationBatchRepository;
    private final PurchaseHasRecommendationRepository purchaseHasRecommendationRepository;
    private final ConfigRepository configRepository;
    private final ApprovalUserAuthorizationRepository approvalUserAuthorizationRepository;
    private final MrpSubRepository mrpSubRepository;
    private final PurchaseRequestService purchaseRequestService;
    private final OitmRepository oitmRepository;
    private final KeycloakService keycloakService;

    public PurchaseRecommendationDetailService(PurchaseHasRecommendationRepository purchaseHasRecommendationRepository, PurchaseRecommendationBatchRepository purchaseRecommendationBatchRepository, PurchaseRecommendationDetailRepository purchaseRecommendationDetailRepository, PurchaseRecommendationRepository purchaseRecommendationRepository, OcrdRepository ocrdRepository, MqqPriceRepository mqqPriceRepository, ItemHoldRepository itemHoldRepository, ItemHoldMapper itemHoldMapper, @Qualifier("mrpEntityManager") EntityManager entityManager, PurchaseRecommendationPlanRepository planRepository, RateExchangeService rateExchangeService, RecommendationPlanMapper planMapper, PurchaseRequestApiMapper purchaseRequestApiMapper,
                                               ConfigRepository configRepository,
                                               ApprovalUserAuthorizationRepository approvalUserAuthorizationRepository,
                                               MrpSubRepository mrpSubRepository, PurchaseRequestService purchaseRequestService,
                                               OitmRepository oitmRepository, KeycloakService keycloakService) {
        this.purchaseHasRecommendationRepository = purchaseHasRecommendationRepository;
        this.purchaseRecommendationBatchRepository = purchaseRecommendationBatchRepository;
        this.purchaseRecommendationDetailRepository = purchaseRecommendationDetailRepository;
        this.purchaseRecommendationRepository = purchaseRecommendationRepository;
        this.mqqPriceRepository = mqqPriceRepository;
        this.itemHoldRepository = itemHoldRepository;
        this.entityManager = entityManager;
        this.planRepository = planRepository;
        this.planMapper = planMapper;
        this.purchaseRequestApiMapper = purchaseRequestApiMapper;
        this.configRepository = configRepository;
        this.approvalUserAuthorizationRepository = approvalUserAuthorizationRepository;
        this.mrpSubRepository = mrpSubRepository;
        this.purchaseRequestService = purchaseRequestService;
        this.oitmRepository = oitmRepository;
        this.keycloakService = keycloakService;
    }

    /**
     * hàm lấy tất cả item đã vào ds khuyến nghị
     *
     * @param purchaseRecommendationId
     * @param input
     * @return
     */
    public PageResponse<List<PurchaseRecommendationDetailDTO>> getAllItems(Integer purchaseRecommendationId, PageFilterInput<ItemFilter> input) {
        if (purchaseRecommendationRepository.findByPurchaseRecommendationIdAndIsActiveTrue(purchaseRecommendationId) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        }
        ItemFilter filter = input.getFilter();
        JPAQuery<PurchaseRecommendationDetailDTO> query = buildQuery(purchaseRecommendationId, filter);
        List<PurchaseRecommendationDetailDTO> resultList = query.fetch();
//        for(PurchaseRecommendationDetailDTO dto : resultList){
//            ItemInVendorDTO itemInVendorDTO = detailVendorService.getDetailItem(dto.getItemCode());
//            dto.setTechName(itemInVendorDTO.getTechName());
//            dto.setUnit(itemInVendorDTO.getUnit());
//            dto.setGroupName(itemInVendorDTO.getGroupName());
//            if (itemInVendorDTO.getGroupType() == 101){
//                dto.setGroup("BTP");
//            } else {
//                dto.setGroup("NVL");
//            }
//        }
        long count = query.fetchCount();
        return new PageResponse<List<PurchaseRecommendationDetailDTO>>()
            .result("00", "Thành công", true)
            .data(resultList)
            .dataCount(count);
    }

    /**
     * hàm lấy tất cả item đã gửi khuyến nghị
     *
     * @param purchaseRecommendationId
     * @param batch
     * @param input
     * @return
     */
    public PageResponse<List<PurchaseRecommendationDetailDTO>> getAllItemsHasRecommendation(Integer purchaseRecommendationId, Integer batch, PageFilterInput<ItemFilter> input) {
        if (purchaseRecommendationRepository.findByPurchaseRecommendationIdAndIsActiveTrue(purchaseRecommendationId) == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        }
        ItemFilter filter = input.getFilter();
        JPAQuery<PurchaseRecommendationDetailDTO> query = buildQueryGetItemHasRecommendation(purchaseRecommendationId, batch, filter);
        List<PurchaseRecommendationDetailDTO> resultList = query.fetch();
        long count = query.fetchCount();

        // Query set tên NCC
        //TODO: need OPTIMIZE
//        Map<String, String> vendorMap = new HashMap<>();
        for (PurchaseRecommendationDetailDTO result : resultList) {
//            if (!vendorMap.containsKey(result.getVendorCode())) {
//                OcrdEntity ocrdEntity = ocrdRepository.getVendor(result.getVendorCode());
//                if (ocrdEntity != null)
//                    vendorMap.put(ocrdEntity.getCardCode(), ocrdEntity.getCardName());
//            }
            result.setQuantity(planRepository.sumQuantity(result.getItemCode(), purchaseRecommendationId, batch));
            result.setSumPrQuantity(planRepository.sumPrQuantity(result.getItemCode(), purchaseRecommendationId));
            result.setSumRequestQuantity(planRepository.sumRequestQuantity(result.getItemCode(), purchaseRecommendationId));
//            result.setVendorName(vendorMap.get(result.getVendorCode()));
        }

        return new PageResponse<List<PurchaseRecommendationDetailDTO>>()
            .result("00", "Thành công", true)
            .data(resultList)
            .dataCount(count);
    }

    public PageResponse<List<String>> getItemsHasRecommendation(String purchaseRecommendationId) {
        return new PageResponse<List<String>>()
            .result("00", "Thành công", true)
            .data(purchaseRecommendationBatchRepository.getAllItemHasSend(purchaseRecommendationId));
    }

    public PageResponse<ItemPriceOfVendorListDTO> getAllPriceOfItem(String itemCode, Integer purchaseRecommendationId, PageFilterInput<Double> input) throws JsonProcessingException {
        PurchaseRecommendationDetailEntity purchaseRecommendationDetailEntity = purchaseRecommendationDetailRepository.findByPRIdAndItemCode(purchaseRecommendationId, itemCode);
        if (purchaseRecommendationDetailEntity == null)
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
//        Map<String, Float> rateExchange = rateExchangeService.getRateExchange();
//
//        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        List<ItemPriceOfVendorDTO> priceList = purchaseRecommendationDetailRepository.getAllItemPrice(itemCode);
//        priceList.sort(Comparator.comparing(ItemPriceOfVendorDTO::getPromotion).reversed()
//            .thenComparing(ItemPriceOfVendorDTO::getActualPrice));

        ItemPriceOfVendorListDTO result = new ItemPriceOfVendorListDTO();
        result.setPriceList(priceList);
        result.setCurrentSelection(purchaseRecommendationDetailEntity.getMoqPriceId());
        return new PageResponse<ItemPriceOfVendorListDTO>()
            .result("00", "Thành công", true)
            .dataCount(priceList.size())
            .data(result);
    }

    /**
     * hàm gửi duyệt khuyến nghị
     *
     * @param purchaseRecommendationId
     * @param approvalRequest
     * @return
     */
    @Transactional
    public CommonResponse sendApproval(Integer purchaseRecommendationId, SendApprovalRequest approvalRequest) {
        if (Utils.hasDuplicate(approvalRequest.getItems()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "duplicate.param");

        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationRepository.findByPurchaseRecommendationIdAndIsActiveTrue(purchaseRecommendationId);
        if (purchaseRecommendationEntity == null) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        List<PurchaseRecommendationDetailEntity> recommendationDetailEntities = purchaseRecommendationDetailRepository.getListPurchaseRecommendationDetail(purchaseRecommendationEntity.getPurchaseRecommendationId(), approvalRequest.getItems());
        if (recommendationDetailEntities.size() == 0)
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");

        List<Integer> purchaseRecommendationDetailIdList = recommendationDetailEntities.stream().map(PurchaseRecommendationDetailEntity::getPurchaseRecommendationDetailId).collect(Collectors.toList());
        StringBuilder mess = new StringBuilder();
        Set<String> checkSumDTOList = planRepository.checkByStatus(purchaseRecommendationDetailIdList, Constants.PurchaseRecommendationPlan.CHECKED);
        if (checkSumDTOList.size() == 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "choose.notfound", String.join(", ", approvalRequest.getItems()));
        } else if (checkSumDTOList.size() < approvalRequest.getItems().size()) {
            for (String item : approvalRequest.getItems()) {
                if (!checkSumDTOList.contains(item))
                    mess.append(item).append(",");
            }
            if (!StringUtils.isEmpty(mess.toString())) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "choose.notfound", mess.toString());
            }
        }

        Integer purchaseRecommendationBatch = purchaseRecommendationBatchRepository.maxBatch(purchaseRecommendationEntity.getPurchaseRecommendationId());
        int batch = Objects.requireNonNullElse(purchaseRecommendationBatch, 0);

        PurchaseHasRecommendationEntity purchaseHasRecommendationEntity = new PurchaseHasRecommendationEntity();
        purchaseHasRecommendationEntity.setPurchaseRecommendationId(purchaseRecommendationEntity.getPurchaseRecommendationId());
        purchaseHasRecommendationEntity.setMrpPoId(purchaseRecommendationEntity.getMrpPoId());
        purchaseHasRecommendationEntity.setMrpSubCode(purchaseRecommendationEntity.getMrpSubCode());
        purchaseHasRecommendationEntity.setNote(approvalRequest.getNote());
        purchaseHasRecommendationEntity.setTotalItems(approvalRequest.getItems().size());
        purchaseHasRecommendationEntity.setStartTime(purchaseRecommendationEntity.getStartTime());
        purchaseHasRecommendationEntity.setEndTime(purchaseRecommendationEntity.getEndTime());
        purchaseHasRecommendationEntity.setStatus(Constants.PurchaseRecommendationDetail.SEND_APPROVAL);
        purchaseHasRecommendationEntity.setActive(purchaseRecommendationEntity.getActive());
        purchaseHasRecommendationEntity.setCreatedAt(purchaseRecommendationEntity.getCreatedAt());
        purchaseHasRecommendationEntity.setCreatedBy(purchaseRecommendationEntity.getCreatedBy());
        purchaseHasRecommendationEntity.setUpdatedAt(purchaseRecommendationEntity.getUpdatedAt());
        purchaseHasRecommendationEntity.setUpdatedBy(purchaseRecommendationEntity.getUpdatedBy());
        purchaseHasRecommendationEntity.setDueDate(approvalRequest.getDueDate());
        purchaseHasRecommendationEntity.setBatch(batch + 1);
        purchaseHasRecommendationRepository.save(purchaseHasRecommendationEntity);
        List<Integer> list = new ArrayList<>();
        List<PurchaseRecommendationBatch> recommendationBatchList = new ArrayList<>();
        for (PurchaseRecommendationDetailEntity item : recommendationDetailEntities) {
            PurchaseRecommendationBatch prb = new PurchaseRecommendationBatch();
            prb.setItemCode(item.getItemCode());
            prb.setItemDescription(item.getItemDescription());
            prb.setPurchaseRecommendationDetailId(item.getPurchaseRecommendationDetailId());
            prb.setAnalysisResult(item.getAnalysisResult());
            prb.setActive(item.getActive());
            prb.setMoqPriceId(item.getMoqPriceId());
            prb.setStatus(Constants.PurchaseRecommendationDetail.SEND_APPROVAL);
            prb.setPrice(item.getPrice());
            prb.setQuantity(item.getQuantity());
            prb.setReceiveDate(item.getReceiveDate());
            prb.setNote(item.getNote());
            prb.setPlanNote(item.getPlanNote());
            prb.setBatch(batch + 1);
            recommendationBatchList.add(prb);
            if (!list.contains(item.getPurchaseRecommendationDetailId())) {
                list.add(item.getPurchaseRecommendationDetailId());
            }
        }
        purchaseRecommendationBatchRepository.saveAll(recommendationBatchList);
        int numberOfAffectedRecords = purchaseRecommendationDetailRepository.changeStatusRecommendation(
            purchaseRecommendationId,
            approvalRequest.getItems(),
            Constants.PurchaseRecommendationDetail.SEND_APPROVAL
        );

        if (purchaseRecommendationEntity.getStatus() == Constants.PurchaseRecommendation.STATUS_NEW) {
            purchaseRecommendationEntity.setStatus(Constants.PurchaseRecommendation.SEND_APPROVAL);
            purchaseRecommendationRepository.save(purchaseRecommendationEntity);
        }
        for (Integer item : list) {
            planRepository.setBatchPurchasePlan(item, batch + 1);
        }

        // Lưu user quyền phê duyệt
        List<ApprovalUserAuthorizationEntity> users = new ArrayList<>();
        for (String user : approvalRequest.getUsers()) {
            ApprovalUserAuthorizationEntity userEntity = new ApprovalUserAuthorizationEntity();
            userEntity.setUsername(user);
            userEntity.setPurchaseRecommendationId(purchaseRecommendationId);
            userEntity.setBatch(purchaseHasRecommendationEntity.getBatch());
            users.add(userEntity);
        }
        approvalUserAuthorizationRepository.saveAll(users);

        return new CommonResponse().success("Gửi phê duyệt thành công");
    }

    /**
     * hàm phê duyệt khuyến nghị
     *
     * @param purchaseRecommendationId
     * @param batch
     * @param input
     * @return
     */
    @Transactional(rollbackFor = CustomException.class)
    public CommonResponse approveRecommendation(Integer purchaseRecommendationId, Integer batch, ApproveInput input) {
        if (Utils.hasDuplicate(input.getItems())) throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        if (!input.getIsApproval() && StringUtils.isEmpty(input.getNote()))
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationRepository.findByPurchaseRecommendationIdAndIsActiveTrue(purchaseRecommendationId);
        if (purchaseRecommendationEntity == null) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");

        List<ApprovalUserAuthorizationEntity> userApproval = approvalUserAuthorizationRepository.findByPurchaseRecommendationIdAndBatch(purchaseRecommendationId, batch);
        if (SecurityUtils.getCurrentUsername().isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        }
        String username = SecurityUtils.getCurrentUsername().get();
        int numberOfAccepted = 0;
        for (ApprovalUserAuthorizationEntity entity : userApproval) {
            if (entity.getUsername().equals(username)) {
                entity.setAccepted(true);
                approvalUserAuthorizationRepository.save(entity);
            }
            if (entity.getAccepted()) numberOfAccepted++;
        }
        logger.info("Number of accepted {}, required accepted number {}", numberOfAccepted, userApproval.size());
        // Chưa đủ lượng đồng ý
//        if (numberOfAccepted != userApproval.size())
//            return new CommonResponse<>().success(
//                (input.getIsApproval() ? "Phê duyệt " : "Từ chối ") + "thành công "
//            );

        logger.info("Approval process (isApproval,{}) for items({})", input.getIsApproval(), input.getItems());
        int newStatus = input.getIsApproval() ? Constants.PurchaseRecommendationDetail.ACCEPTED : Constants.PurchaseRecommendationDetail.REJECTED;
        planRepository.approveRecommendationPlan(
            purchaseRecommendationEntity,
            newStatus,
            input.getItems(),
            batch
        );

        // Hold Item when accepting PR
        if (input.getIsApproval()) {
            logger.info("IF Create PR");
            PurchaseHasRecommendationEntity purchaseHasRecommendationEntity = purchaseHasRecommendationRepository.getPurchaseHasRecommendationBatch(purchaseRecommendationId, batch);
            List<PurchaseRequestDetailApiDTO> purchaseRequestDetailApiList = planRepository.getAllApprovedByItems(purchaseRecommendationEntity, Constants.PurchaseRecommendationPlan.ACCEPTED, input.getItems(), batch);
            purchaseRequestDetailApiList.removeIf(detailApiDTO -> detailApiDTO.getRequiredQuantity() <= 0.0);
            if (!purchaseRequestDetailApiList.isEmpty()) {

//                RestTemplate restTemplate = new RestTemplate();
//                PurchaseRequestApiDTO purchaseRequestDTO = purchaseRequestApiMapper.toDTO(purchaseRecommendationEntity, purchaseRequestDetailApiList, purchaseHasRecommendationEntity);
//                HttpEntity<PurchaseRequestApiDTO> httpEntity = new HttpEntity<>(purchaseRequestDTO);
//                String sapApiUrl = configRepository.getValueByName("SAP_PR_API").orElseThrow(RuntimeException::new);
                try {
//                    restTemplate.exchange(
//                        sapApiUrl,
//                        HttpMethod.POST,
//                        httpEntity, String.class
//                    );
                    logger.info("Creating PR ------");
                    purchaseRequestService.addPurchaseRequest(input.getPurchaseRequestEntityDto());
                } catch (Exception e) {
                    logger.error("Create PR failed ------", e);
                    planRepository.approveRecommendationPlan(
                        purchaseRecommendationEntity,
                        Constants.PurchaseRecommendationPlan.SEND_APPROVAL,
                        input.getItems(),
                        batch
                    );
                    throw new CustomException( "create.pr.failed");
                }
            }

            List<ItemHoldEntity> holdingItemList = itemHoldRepository.getAllSourceHoldingItem(purchaseRecommendationEntity.getPurchaseRecommendationId(), input.getItems());
            holdingItemList.forEach(holdEntity -> holdEntity.setStatus(Constants.ItemHold.ACTIVE));
            itemHoldRepository.saveAll(holdingItemList);
        }

//        purchaseRecommendationDetailRepository.approveRecommendation(
//            purchaseRecommendationEntity,
//            newStatus,
//            input.getItems(),
//            input.getNote()
//        );

        // Change status PRecommendation => APPROVED or REJECTED
        logger.info("Change status PRecommendation => APPROVED or REJECTED");
        List<PurchaseRecommendationDetailEntity> purchaseRecommendationDetailEntities = purchaseRecommendationDetailRepository.getAllWaitingForApprove(
            purchaseRecommendationEntity,
            List.of(Constants.PurchaseRecommendationDetail.SEND_APPROVAL, Constants.PurchaseRecommendationDetail.REJECTED),
            input.getItems()
        );
        for (PurchaseRecommendationDetailEntity purchaseRecommendationDetailEntity : purchaseRecommendationDetailEntities) {
            purchaseRecommendationDetailEntity.setStatus(newStatus);
            purchaseRecommendationDetailEntity.setNote(input.getNote());

            if (input.getIsApproval()
                && purchaseRecommendationDetailEntity.getMoqPriceEntity() != null
                && purchaseRecommendationDetailEntity.getMoqPriceEntity().getVendorItemEntity() != null) {

                // Increment the time used
                purchaseRecommendationDetailEntity.getMoqPriceEntity().getVendorItemEntity().incrementTimeUsed();
            } else {
                logger.warn("VendorItemEntity is null for purchase recommendation detail: {}", purchaseRecommendationDetailEntity.getPurchaseRecommendationDetailId());
            }
        }

        purchaseRecommendationDetailRepository.saveAll(purchaseRecommendationDetailEntities);

        purchaseRecommendationBatchRepository.approveRecommendation(
            purchaseRecommendationEntity,
            newStatus,
            input.getItems(),
            input.getNote(),
            batch
        );


        Integer countBatch = purchaseRecommendationBatchRepository.countBatchNotApproval(purchaseRecommendationId, batch);
        if (countBatch == 0) {
            purchaseHasRecommendationRepository.updateStatusPurchaseHasRecomment(purchaseRecommendationId, batch);
        }

        Integer count = purchaseRecommendationRepository.count(purchaseRecommendationId);
        if (count == 0) {
            purchaseRecommendationRepository.changeStatus(purchaseRecommendationId, 3);
        }

        MrpSubEntity mrpSubEntity = mrpSubRepository.getByMrpSubCodeAndStatus(purchaseRecommendationEntity.getMrpSubCode(), Constants.MrpSub.SENT_PURCHASE_RECOMMENDATION);
        // if not equal sent pr po status
        if (mrpSubEntity != null) {
            mrpSubEntity.setStatus(Constants.MrpSub.SENT_PR_PO);
            mrpSubRepository.save(mrpSubEntity);
        }

        return new CommonResponse<>().success(
            (input.getIsApproval() ? "Phê duyệt " : "Từ chối ") + "thành công "
        );
    }

    @Transactional
    public CommonResponse updatePurchaseRecommendation(Integer purchaseRecommendationId, List<UpdatePurchaseRecommendationForm> updateForm) throws CustomException {
        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationRepository.findByPurchaseRecommendationIdAndIsActiveTrue(purchaseRecommendationId);
        logger.info("Update purchaseRecommendationEntity with id {}", purchaseRecommendationEntity.getPurchaseRecommendationId());

        // Map itemCode -> updateForm index for indexed array access
        Map<String, Integer> itemIndex = new HashMap<>();
        for (int i = 0; i < updateForm.size(); i++) {
            // Check itemCode duplicate
            if (itemIndex.containsKey(updateForm.get(i).getItemCode()))
                throw new CustomException(HttpStatus.BAD_REQUEST, "item.code.duplicate", updateForm.get(i).getItemCode());
            itemIndex.put(updateForm.get(i).getItemCode(), i);
        }

        List<PurchaseRecommendationDetailEntity> entities = purchaseRecommendationDetailRepository.getAllByInItemList(purchaseRecommendationEntity, itemIndex.keySet());
        if (entities.size() != updateForm.size())
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");

        // Quantity Diff between new and old
//        Map<String, Double> itemQuantityDifference = new HashMap<>();

        for (PurchaseRecommendationDetailEntity entity : entities) {
            double newQuantity = updateForm.get(itemIndex.get(entity.getItemCode())).getQuantity();
            entity.setQuantity(newQuantity);
            entity.setAssignedUser(updateForm.get(itemIndex.get(entity.getItemCode())).getAssignedUser());
            entity.setReceiveDate(updateForm.get(itemIndex.get(entity.getItemCode())).getReceiveDate());
        }
        purchaseRecommendationDetailRepository.saveAll(entities);


        logger.info("Done update purchaseRecommendationEntity(id,{}),(mrp,{})", purchaseRecommendationEntity.getPurchaseRecommendationId(), purchaseRecommendationEntity.getMrpSubCode());
        return new CommonResponse<>().success("Cập nhật thành công");
    }

    public CommonResponse updateMoqPriceId(String itemCode, Integer purchaseRecommendationId, Integer moqPriceId) {
        PurchaseRecommendationDetailEntity entity = purchaseRecommendationDetailRepository.findByPRIdAndItemCode(purchaseRecommendationId, itemCode);
        if (entity == null || mqqPriceRepository.findById(moqPriceId).isEmpty())
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");

        entity.setMoqPriceId(moqPriceId);
        purchaseRecommendationDetailRepository.save(entity);
        return new CommonResponse().success();
    }

    @Transactional
    public CommonResponse getAllItemsForExport(Integer purchaseRecommendationId) {
        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationRepository.findByPurchaseRecommendationIdAndIsActiveTrue(purchaseRecommendationId);
        if (purchaseRecommendationEntity == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        }
        ItemFilter filter = new ItemFilter();
        // Only accepted Purchase Recommendation
        filter.setStatus(List.of(Constants.PurchaseRecommendationDetail.ACCEPTED));
        JPAQuery<PurchaseRecommendationDetailDTO> query = buildQuery(purchaseRecommendationId, filter);
        List<PurchaseRecommendationDetailDTO> resultList = query.fetch();

        //TODO: Optimize
//        Map<String, String> vendorMap = new HashMap<>();
//        for (PurchaseRecommendationDetailDTO result : resultList) {
//            if (!vendorMap.containsKey(result.getVendorCode())) {
//                OcrdEntity ocrdEntity = ocrdRepository.getVendor(result.getVendorCode());
//                if (ocrdEntity != null)
//                    vendorMap.put(ocrdEntity.getCardCode(), ocrdEntity.getCardName());
//            }
//            result.setVendorName(vendorMap.get(result.getVendorCode()));
//        }

//        purchaseRecommendationDetailRepository.changeStatus(purchaseRecommendationEntity, Constants.PurchaseRecommendationDetail.IN_PROGRESS, Constants.PurchaseRecommendationDetail.ACCEPTED);
        return new CommonResponse()
            .success()
            .data(resultList);
    }

    private JPAQuery<PurchaseRecommendationDetailDTO> buildQuery(Integer purchaseRecommendationId, ItemFilter filter) {
        QPurchaseRecommendationDetailEntity qPurchaseRecommendationDetailEntity = QPurchaseRecommendationDetailEntity.purchaseRecommendationDetailEntity;
        QMqqPriceEntity qMqqPriceEntity = QMqqPriceEntity.mqqPriceEntity;
        QVendorEntity qVendorEntity = QVendorEntity.vendorEntity;
        JPAQuery<PurchaseRecommendationDetailDTO> query = new JPAQueryFactory(entityManager)
            .select(new QPurchaseRecommendationDetailDTO(
                qPurchaseRecommendationDetailEntity.itemCode,
                qPurchaseRecommendationDetailEntity.itemDescription,
                qPurchaseRecommendationDetailEntity.quantity,
                qPurchaseRecommendationDetailEntity.receiveDate,
                qMqqPriceEntity.vendorCode,
                qVendorEntity.vendorName,
                qMqqPriceEntity.price,
                qMqqPriceEntity.currency,
                qMqqPriceEntity.isPromotion,
                qMqqPriceEntity.timeEnd,
                qPurchaseRecommendationDetailEntity.status,
                qPurchaseRecommendationDetailEntity.note,
                qPurchaseRecommendationDetailEntity.createdAt,
                qPurchaseRecommendationDetailEntity.assignedUser
            ))
            .from(qPurchaseRecommendationDetailEntity)
            .leftJoin(qMqqPriceEntity).on(qPurchaseRecommendationDetailEntity.moqPriceId.eq(qMqqPriceEntity.itemPriceId))
            .leftJoin(qVendorEntity).on(qMqqPriceEntity.vendorCode.eq(qVendorEntity.vendorCode))
            .orderBy(qPurchaseRecommendationDetailEntity.quantity.desc());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qPurchaseRecommendationDetailEntity.purchaseRecommendation.purchaseRecommendationId.eq(purchaseRecommendationId));

        if (!StringUtils.isEmpty(filter.getItemCode())) {
            booleanBuilder.and(qPurchaseRecommendationDetailEntity.itemCode.containsIgnoreCase(filter.getItemCode()));
        }
        if (!StringUtils.isEmpty(filter.getItemDescription())) {
            booleanBuilder.and(qPurchaseRecommendationDetailEntity.itemDescription.containsIgnoreCase(filter.getItemDescription()));
        }
        if (!CollectionUtils.isEmpty(filter.getStatus())) {
            booleanBuilder.and(qPurchaseRecommendationDetailEntity.status.in(filter.getStatus()));
        }
        query.where(booleanBuilder);
        return query;
    }

    private JPAQuery<PurchaseRecommendationDetailDTO> buildQueryGetItemHasRecommendation(Integer purchaseRecommendationId, Integer batch, ItemFilter filter) {
        QPurchaseRecommendationBatch qPurchaseRecommendationBatch = QPurchaseRecommendationBatch.purchaseRecommendationBatch;
        QPurchaseRecommendationDetailEntity qPurchaseRecommendationDetail = QPurchaseRecommendationDetailEntity.purchaseRecommendationDetailEntity;
        QVendorEntity qVendorEntity = QVendorEntity.vendorEntity;
        QMqqPriceEntity qMqqPriceEntity = QMqqPriceEntity.mqqPriceEntity;
        JPAQuery<PurchaseRecommendationDetailDTO> query = new JPAQueryFactory(entityManager)
            .select(new QPurchaseRecommendationDetailDTO(
                qPurchaseRecommendationBatch.itemCode,
                qPurchaseRecommendationBatch.itemDescription,
                qPurchaseRecommendationBatch.quantity,
                qPurchaseRecommendationBatch.receiveDate,
                qMqqPriceEntity.vendorCode,
                qVendorEntity.vendorName,
                qMqqPriceEntity.price,
                qMqqPriceEntity.currency,
                qMqqPriceEntity.isPromotion,
                qMqqPriceEntity.timeEnd,
                qPurchaseRecommendationBatch.status,
                qPurchaseRecommendationBatch.note,
                qPurchaseRecommendationBatch.createdAt,
                qPurchaseRecommendationDetail.assignedUser
            ))
            .from(qPurchaseRecommendationBatch)
            .leftJoin(qMqqPriceEntity).on(qPurchaseRecommendationBatch.moqPriceId.eq(qMqqPriceEntity.itemPriceId))
            .leftJoin(qPurchaseRecommendationDetail).on(qPurchaseRecommendationBatch.purchaseRecommendationDetailId.eq(qPurchaseRecommendationDetail.purchaseRecommendationDetailId))
            .leftJoin(qVendorEntity).on(qMqqPriceEntity.vendorCode.eq(qVendorEntity.vendorCode));
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qPurchaseRecommendationDetail.purchaseRecommendation.purchaseRecommendationId.eq(purchaseRecommendationId));
        booleanBuilder.and(qPurchaseRecommendationBatch.batch.eq(batch));

        if (!StringUtils.isEmpty(filter.getItemCode())) {
            booleanBuilder.and(qPurchaseRecommendationBatch.itemCode.containsIgnoreCase(filter.getItemCode()));
        }
        if (!StringUtils.isEmpty(filter.getItemDescription())) {
            booleanBuilder.and(qPurchaseRecommendationBatch.itemDescription.containsIgnoreCase(filter.getItemDescription()));
        }
        if (!CollectionUtils.isEmpty(filter.getStatus())) {
            booleanBuilder.and(qPurchaseRecommendationBatch.status.in(filter.getStatus()));
        }
        query.where(booleanBuilder);
        return query;
    }

    /**
     * @param soCode
     * @param mrpSubCode
     * @param itemCode
     * @param input
     */
    public void createNewRecommendationPlan(String soCode, String mrpSubCode, String itemCode, RecommendationPlanNoteDTO input) {

        PurchaseRecommendationPurchasePlanEntity entity;
        List<PurchaseRecommendationPurchasePlanEntity> entityList = new ArrayList<>();

        //Tìm RecommendationDetail để add theo mapping OneToMany
        List<PurchaseRecommendationDetailEntity> detailEntities = purchaseRecommendationDetailRepository.getAllPurchaseRecommendationDetailEntity(soCode, mrpSubCode, itemCode);

        if (detailEntities == null || detailEntities.isEmpty()) {
            throw new CustomException(HttpStatus.OK, "record.notfound");
        }
        detailEntities.get(0).setPlanNote(input.getNote());
        purchaseRecommendationDetailRepository.save(detailEntities.get(0));
        //Save record
        for (RecommendationPlanDto dto : input.getPlans()) {
            if (dto.getChecked() && dto.getStatus() != 2 && dto.getStatus() != 3) {
                entity = planMapper.dtoToEntity(dto);
                entity.setPurchaseRecommendationDetailId(detailEntities.get(0).getPurchaseRecommendationDetailId());
                entity.setStatus(1);
                entity.setBatch(null);
                entityList.add(entity);
            } else if ((!dto.getChecked() || dto.getChecked() == null) && dto.getStatus() != 2 && dto.getStatus() != 3) {
                entity = planMapper.dtoToEntity(dto);
                entity.setPurchaseRecommendationDetailId(detailEntities.get(0).getPurchaseRecommendationDetailId());
                if (entity.getStatus() != 4) {
                    entity.setStatus(0);
                }
                entityList.add(entity);
            }
        }
        planRepository.saveAll(entityList);
    }

    /**
     * @param dto
     */
    public void deleteRecommendationPlan(RecommendationPlanDto dto) {
        PurchaseRecommendationPurchasePlanEntity entity = planRepository.findByRecommendationPurchasePlanId(dto.getRecommendationPurchasePlanId());
        entity.setIsActive(false);
        planRepository.save(entity);
    }

    /**
     * hàm lấy tất cả kế hoạch khuyến nghị
     *
     * @param soCode
     * @param mrpSubCode
     * @param itemCode
     * @return
     */
    public RecommendationPlanNoteDTO getAllRecommendationPlanBatch(Integer batch, String soCode, String mrpSubCode, String itemCode) {
        RecommendationPlanDto dto;
        List<RecommendationPlanDto> dtoList = new ArrayList<>();

        //Tìm RecommendationDetail để add theo mapping OneToMany
        List<PurchaseRecommendationDetailEntity> detailEntities = purchaseRecommendationDetailRepository.getAllPurchaseRecommendationDetailEntity(soCode, mrpSubCode, itemCode);

        if (detailEntities == null || detailEntities.isEmpty()) {
            throw new CustomException(HttpStatus.OK, "record.notfound");
        }

        List<PurchaseRecommendationPurchasePlanEntity> plan = planRepository.findAllPlanBatch(detailEntities.get(0).getPurchaseRecommendationDetailId(), batch);

        if (plan == null || plan.isEmpty()) {
            throw new CustomException(HttpStatus.OK, "record.notfound");
        } else {
            for (PurchaseRecommendationPurchasePlanEntity entity : plan) {
                dto = planMapper.entityToDto(entity);
                dtoList.add(dto);
            }
        }

        RecommendationPlanNoteDTO result = new RecommendationPlanNoteDTO();
        result.setNote(detailEntities.get(0).getPlanNote());
        result.setPlans(dtoList);
        return result;
    }

    public RecommendationPlanNoteDTO getAllRecommendationPlan(String soCode, String mrpSubCode, String itemCode) {
        //Tìm RecommendationDetail để add theo mapping OneToMany
        List<PurchaseRecommendationDetailEntity> detailEntities = purchaseRecommendationDetailRepository.getAllPurchaseRecommendationDetailEntity(soCode, mrpSubCode, itemCode);

        if (detailEntities == null || detailEntities.isEmpty()) {
            throw new CustomException(HttpStatus.OK, "record.notfound");
        }

//        List<PurchaseRecommendationPurchasePlanEntity> planEntities = planRepository.findAllPlan(detailEntities.get(0).getPurchaseRecommendationDetailId());
        List<RecommendationPlanDto> planEntities = planRepository.findOtherPlanByStatus(
            soCode,
            mrpSubCode, itemCode
        );

        if (planEntities == null || planEntities.isEmpty()) {
            throw new CustomException(HttpStatus.OK, "record.notfound");
        }
//        else {
//            for (PurchaseRecommendationPurchasePlanEntity entity : planEntities){
//                planResult.add(planMapper.entityToDto(entity));
//            }
//        }

        RecommendationPlanNoteDTO result = new RecommendationPlanNoteDTO();
        result.setNote(detailEntities.get(0).getPlanNote());
        result.setPlans(planEntities);
        return result;
    }

    // Transactional for lazy load PurchaseRecommendationEntity
    @Transactional
    public void closePurchaseRequest(Integer purchaseRecommendationId, String itemCode, Integer status) {
        PurchaseRecommendationDetailEntity purchaseRecommendationDetailEntity = purchaseRecommendationDetailRepository.findByPRIdAndItemCode(purchaseRecommendationId, itemCode);
        if (purchaseRecommendationDetailEntity == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "record.notfound");

        purchaseRecommendationDetailEntity.setStatus(status);
        purchaseRecommendationDetailRepository.save(purchaseRecommendationDetailEntity);

        PurchaseRecommendationBatch purchaseRecommendationBatch = purchaseRecommendationBatchRepository.findByPRIdAndItemCode(purchaseRecommendationId, itemCode);
        if (purchaseRecommendationBatch != null) {
            purchaseRecommendationBatch.setStatus(status);
            purchaseRecommendationBatchRepository.save(purchaseRecommendationBatch);
        }

        int count = purchaseRecommendationDetailRepository.countPurchaseRecommendationNotClose(purchaseRecommendationId);
        if (count == 0) {
            purchaseRecommendationRepository.updateStatusPurchaseRecommend(purchaseRecommendationId, Constants.PurchaseRecommendation.COMPLETED);
        }
        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationDetailEntity.getPurchaseRecommendation();

        List<ItemHoldEntity> holdEntities = itemHoldRepository.getAllSourceHoldingItem(
            purchaseRecommendationEntity.getPurchaseRecommendationId(),
            List.of(itemCode)
        );
        holdEntities.forEach(holdEntity -> holdEntity.setActive(false));
        itemHoldRepository.saveAll(holdEntities);

        List<PurchaseRecommendationPurchasePlanEntity> prOfItem = planRepository.findAllByRecommendationPurchasePlanId(
            purchaseRecommendationDetailEntity.getPurchaseRecommendationDetailId()
        );
        prOfItem.forEach(entity -> {
            if (entity.getStatus() != Constants.PurchaseRecommendationPlan.ACCEPTED)
                entity.setStatus(Constants.PurchaseRecommendationPlan.CLOSED_ACCEPTED);
            else
                entity.setStatus(Constants.PurchaseRecommendationPlan.CLOSED);
        });
        planRepository.saveAll(prOfItem);
    }

    public byte[] exportPurchaseRecommendationDetailToExcel(Integer purchaseRecommendationId, Integer batch, PageFilterInput<ItemFilter> input)
    {
        List<PurchaseRecommendationDetailDTO> data = new ArrayList<>();
        data = getAllItemsHasRecommendation(purchaseRecommendationId, batch, input).getData();
        String[] columns = {"STT", "Tên vật tư, phụ tùng, thông số kỹ thuật", "Xuất xứ", "ĐVT", "Số lượng", "Thời gian cần", "Ghi chú", "Người mua hàng"};
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat vietnameseDateFormat = new SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("PHIẾU YÊU CẦU MUA VẬT TƯ");

            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);

            Font defaultFont = workbook.createFont();
            defaultFont.setFontName("Times New Roman");
            defaultFont.setFontHeightInPoints((short) 13);

            CellStyle defaultStyle = workbook.createCellStyle();
            defaultStyle.setFont(defaultFont);
            defaultStyle.setAlignment(HorizontalAlignment.CENTER); // Center alignment
            defaultStyle.setWrapText(true);

            CellStyle titleStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            titleFont.setFontName("Times New Roman");
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            // Main title row with "Số:"
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("PHIẾU YÊU CẦU MUA VẬT TƯ");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            // Add "Số:" in column G
            Cell titleCellWithNumber = titleRow.createCell(6);
            titleCellWithNumber.setCellValue("Số:" + purchaseRecommendationId);
            titleCellWithNumber.setCellStyle(titleStyle);

            CellStyle unitStyle = workbook.createCellStyle();
            unitStyle.setFont(defaultFont);
            unitStyle.setAlignment(HorizontalAlignment.CENTER);

            Row unitRow = sheet.createRow(1);
            Cell unitCell = unitRow.createCell(0);
            unitCell.setCellValue("Đơn vị đề nghị: Xưởng phích nước - thủy tinh");
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
            unitCell.setCellStyle(unitStyle);

            // Header row
            Row headerRow = sheet.createRow(2);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(defaultFont);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setWrapText(true);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerStyle);
            }

            // Create a new cell style for the "STT" column (center alignment)
            CellStyle sttStyle = workbook.createCellStyle();
            sttStyle.setFont(defaultFont);
            sttStyle.setBorderTop(BorderStyle.THIN);
            sttStyle.setBorderBottom(BorderStyle.THIN);
            sttStyle.setBorderLeft(BorderStyle.THIN);
            sttStyle.setBorderRight(BorderStyle.THIN);
            sttStyle.setAlignment(HorizontalAlignment.CENTER);  // Center horizontal alignment
            sttStyle.setVerticalAlignment(VerticalAlignment.CENTER);  // Middle vertical alignment

            // Write data rows
            int rowNum = 3;
            int stt = 1;
            for (PurchaseRecommendationDetailDTO prDTO : data) {
                Row row = sheet.createRow(rowNum++);
//                row.setHeightInPoints(50);
                CellStyle dataStyle = workbook.createCellStyle();
                dataStyle.setFont(defaultFont);
                dataStyle.setBorderTop(BorderStyle.THIN);    // THINer border
                dataStyle.setBorderBottom(BorderStyle.THIN); // THINer border
                dataStyle.setBorderLeft(BorderStyle.THIN);   // THINer border
                dataStyle.setBorderRight(BorderStyle.THIN);  // THINer border
                dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                dataStyle.setWrapText(true);
                OitmEntity oitmEntity = oitmRepository.getByItemCode(prDTO.getItemCode());

                Cell sttCell = row.createCell(0);
                sttCell.setCellValue(stt++);
                sttCell.setCellStyle(sttStyle);  // Apply the centered style

                row.createCell(1).setCellValue(prDTO.getItemDescription());
                row.createCell(2).setCellValue(prDTO.getVendorName());
                row.createCell(3).setCellValue(oitmEntity.getSalUnitMsr());
                row.createCell(4).setCellValue(Math.round(prDTO.getSumRequestQuantity() * 10) / 10.0);
                if (prDTO.getReceiveDate() != null) {
                    row.createCell(5).setCellValue(dateFormatter.format(prDTO.getReceiveDate()));
                } else {
                    row.createCell(5).setCellValue("");
                }
                row.createCell(6).setCellValue(prDTO.getNote());
                row.createCell(7).setCellValue(keycloakService.getFullNameByUsername(prDTO.getAssignedUser()));

                // Apply style to each cell
                for (int i = 0; i < columns.length; i++) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }

            // Manually set column widths to fit A4 portrait page
            sheet.setColumnWidth(0, 1500); // STT
            sheet.setColumnWidth(1, 6000); // Tên vật tư
            sheet.setColumnWidth(2, 6000); // Xuất xứ
            sheet.setColumnWidth(3, 2000); // ĐVT
            sheet.setColumnWidth(4, 3000); // Số lượng
            sheet.setColumnWidth(5, 4000); // Thời gian
            sheet.setColumnWidth(6, 5000); // Ghi chú
            sheet.setColumnWidth(7, 5000); // Người mua

//            // Adjust column sizes for better fit
//            for (int i = 0; i < columns.length; i++) {
//                sheet.autoSizeColumn(i);  // Adjust the column width based on the content
//            }

            CellStyle borderedStyle = workbook.createCellStyle();
            borderedStyle.setFont(defaultFont);
            borderedStyle.setBorderTop(BorderStyle.THIN);
            borderedStyle.setBorderBottom(BorderStyle.THIN);
            borderedStyle.setBorderLeft(BorderStyle.THIN);
            borderedStyle.setBorderRight(BorderStyle.THIN);
            borderedStyle.setAlignment(HorizontalAlignment.CENTER);

            // Add "Mục đích sử dụng" row and merge A to H, align text to the left
            Row usageRow = sheet.createRow(rowNum++);
            Cell usageCell = usageRow.createCell(0);
            usageCell.setCellValue("Mục đích sử dụng:");
            CellStyle leftAlignStyle = workbook.createCellStyle();
            leftAlignStyle.setFont(defaultFont);
            leftAlignStyle.setBorderTop(BorderStyle.THIN);
            leftAlignStyle.setBorderBottom(BorderStyle.THIN);
            leftAlignStyle.setBorderLeft(BorderStyle.THIN);
            leftAlignStyle.setBorderRight(BorderStyle.THIN);
            leftAlignStyle.setAlignment(HorizontalAlignment.LEFT); // Left-aligned
            usageCell.setCellStyle(leftAlignStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 7));

            // Add the approval row (Duyệt, Đơn vị mua hàng, and Ngày)
            Row approvalRow = sheet.createRow(rowNum++);

            // Merge A-B into "Duyệt"
            Cell approveCell = approvalRow.createCell(0);
            approveCell.setCellValue("DUYỆT");
            approveCell.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 1));
            Cell secondMergedCell = approvalRow.createCell(1); // cột B
            secondMergedCell.setCellStyle(borderedStyle);

            // Merge C-D into "Đơn vị mua hàng"
            Cell buyingUnitCell = approvalRow.createCell(2);
            buyingUnitCell.setCellValue("ĐƠN VỊ MUA HÀNG");
            buyingUnitCell.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 2, 3));
            Cell thirdMergedCell = approvalRow.createCell(3); // cột B
            thirdMergedCell.setCellStyle(borderedStyle);


            // Merge E-H into the current date in Vietnamese format
            Cell dateCell = approvalRow.createCell(4);
            dateCell.setCellValue(vietnameseDateFormat.format(new Date()));
            dateCell.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 4, 7));
            Cell fifthMergedCell = approvalRow.createCell(5);
            fifthMergedCell.setCellStyle(borderedStyle);
            Cell fifthMergedCell_1 = approvalRow.createCell(6);
            fifthMergedCell_1.setCellStyle(borderedStyle);
            Cell fifthMergedCell_2 = approvalRow.createCell(7);
            fifthMergedCell_2.setCellStyle(borderedStyle);

            // Add the "Đơn vị đề nghị" row and merge E-H
            Row suggestionRow = sheet.createRow(rowNum++);

            // Merge A-B in the second row for "Duyệt"
            Cell approveCell3 = suggestionRow.createCell(0);
            approveCell3.setCellValue("");
            approveCell3.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 1));
            Cell secondMergedCell_secondRow = suggestionRow.createCell(1); // cột B
            secondMergedCell_secondRow.setCellStyle(borderedStyle);

            // Merge C-D in the second row for "Đơn vị mua hàng"
            Cell buyingUnitCell3 = suggestionRow.createCell(2);
            buyingUnitCell3.setCellValue("");
            buyingUnitCell3.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 2, 3));
            Cell thirdMergedCell_secondRow = suggestionRow.createCell(3); // cột B
            thirdMergedCell_secondRow.setCellStyle(borderedStyle);

            Cell suggestionCell = suggestionRow.createCell(4);
            suggestionCell.setCellValue("ĐƠN VỊ ĐỀ NGHỊ");
            suggestionCell.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 4, 7));
            Cell fifthMergedCell_secondRow = suggestionRow.createCell(5);
            fifthMergedCell_secondRow.setCellStyle(borderedStyle);
            Cell fifthMergedCell_1_secondRow = suggestionRow.createCell(6);
            fifthMergedCell_1_secondRow.setCellStyle(borderedStyle);
            Cell fifthMergedCell_2_secondRow = suggestionRow.createCell(7);
            fifthMergedCell_2_secondRow.setCellStyle(borderedStyle);

            // Add new row below the "Duyệt", "Đơn vị mua hàng" and "Ngày"
            Row approvalRow2 = sheet.createRow(rowNum++);
            approvalRow2.setHeightInPoints(50);
            // Merge A-B in the second row for "Duyệt"
            Cell approveCell2 = approvalRow2.createCell(0);
            approveCell2.setCellValue("");
            approveCell2.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 1));
            Cell secondMergedCell_thirdRow = approvalRow2.createCell(1); // cột B
            secondMergedCell_thirdRow.setCellStyle(borderedStyle);

            // Merge C-D in the second row for "Đơn vị mua hàng"
            Cell buyingUnitCell2 = approvalRow2.createCell(2);
            buyingUnitCell2.setCellValue("");
            buyingUnitCell2.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 2, 3));
            Cell thirdMergedCell_thirdRow = approvalRow2.createCell(3); // cột B
            thirdMergedCell_thirdRow.setCellStyle(borderedStyle);

            // Merge E-H in the second row for the date
            Cell dateCell2 = approvalRow2.createCell(4);
            dateCell2.setCellValue("");
            dateCell2.setCellStyle(borderedStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 4, 7));
            Cell fifthMergedCell_thirdRow = approvalRow2.createCell(5);
            fifthMergedCell_thirdRow.setCellStyle(borderedStyle);
            Cell fifthMergedCell_1_thirdRow = approvalRow2.createCell(6);
            fifthMergedCell_1_thirdRow.setCellStyle(borderedStyle);
            Cell fifthMergedCell_2_thirdRow = approvalRow2.createCell(7);
            fifthMergedCell_2_thirdRow.setCellStyle(borderedStyle);

//             FileOutputStream out = new FileOutputStream(new File("E:/test.xlsx"));
//             workbook.write(out);
            // Convert workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            return null;
        }
    }




}
