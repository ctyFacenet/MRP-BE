package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.security.AuthoritiesConstants;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.*;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.PurchaseRecommendationFilter;
import com.facenet.mrp.service.utils.Constants;
import com.facenet.mrp.service.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseRecommendationService {
    private final Logger log = LogManager.getLogger(PurchaseRecommendationService.class);

    private final EntityManager entityManager;

    private final PurchaseRecommendationRepository purchaseRecommendationRepository;

    private final PurchaseRecommendationDetailRepository purchaseRecommendationDetailRepository;

    private final MqqPriceRepository mqqPriceRepository;

    private final MrpSubRepository mrpSubRepository;

    private final ItemHoldMapper itemHoldMapper;

    private final DetailItemSyntheticMapper detailItemSyntheticMapper;

    private final PurchaseRecommendationMapper purchaseRecommendationMapper;

    private final PurchaseRecommendationDetailMapper purchaseRecommendationDetailMapper;

    private final RateExchangeService rateExchangeService;

    private final MaterialPlanningService materialPlanningService;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final ItemHoldRepository itemHoldRepository;

    private final PurchaseRecommendationBatchRepository recommendationBatchRepository;

    private final PurchaseHasRecommendationRepository purchaseHasRecommendationRepository;

    private final ForecastOrderDetailRepository forecastOrderDetailRepository;

    private final RecommendationPlanMapper planMapper;
    private final PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository;

    public PurchaseRecommendationService(@Qualifier("mrpEntityManager") EntityManager entityManager, PurchaseRecommendationRepository purchaseRecommendationRepository, PurchaseRecommendationDetailRepository purchaseRecommendationDetailRepository, MqqPriceRepository mqqPriceRepository, MrpSubRepository mrpSubRepository, ItemHoldMapper itemHoldMapper, PurchaseRecommendationMapper purchaseRecommendationMapper, PurchaseRecommendationDetailMapper purchaseRecommendationDetailMapper, RateExchangeService rateExchangeService, DetailItemSyntheticMapper detailItemSyntheticMapper, MaterialPlanningService materialPlanningService, ItemHoldRepository itemHoldRepository,
                                         PurchaseRecommendationBatchRepository recommendationBatchRepository, PurchaseHasRecommendationRepository purchaseHasRecommendationRepository,
                                         ForecastOrderDetailRepository forecastOrderDetailRepository, RecommendationPlanMapper planMapper,
                                         PurchaseRecommendationPlanRepository purchaseRecommendationPlanRepository) {
        this.entityManager = entityManager;
        this.purchaseRecommendationRepository = purchaseRecommendationRepository;
        this.purchaseRecommendationDetailRepository = purchaseRecommendationDetailRepository;
        this.mrpSubRepository = mrpSubRepository;
        this.mqqPriceRepository = mqqPriceRepository;
        this.itemHoldMapper = itemHoldMapper;
        this.purchaseRecommendationMapper = purchaseRecommendationMapper;
        this.purchaseRecommendationDetailMapper = purchaseRecommendationDetailMapper;
        this.rateExchangeService = rateExchangeService;
        this.detailItemSyntheticMapper = detailItemSyntheticMapper;
        this.materialPlanningService = materialPlanningService;
        this.itemHoldRepository = itemHoldRepository;
        this.recommendationBatchRepository = recommendationBatchRepository;
        this.purchaseHasRecommendationRepository = purchaseHasRecommendationRepository;
        this.forecastOrderDetailRepository = forecastOrderDetailRepository;
        this.planMapper = planMapper;
        this.purchaseRecommendationPlanRepository = purchaseRecommendationPlanRepository;
    }

    public PageResponse<List<PurchaseRecommendationDTO>> getAllPurchaseRecommendation(PageFilterInput<PurchaseRecommendationFilter> input) {
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        QPurchaseRecommendationEntity qPurchaseRecommendationEntity = QPurchaseRecommendationEntity.purchaseRecommendationEntity;
        QProductOrder qProductOrder = QProductOrder.productOrder;
        JPAQuery<PurchaseRecommendationDTO> query = new JPAQueryFactory(entityManager)
            .select(new QPurchaseRecommendationDTO(
                qPurchaseRecommendationEntity.purchaseRecommendationId,
                qProductOrder.productOrderCode,
                qPurchaseRecommendationEntity.mrpSubCode,
                qPurchaseRecommendationEntity.startTime,
                qPurchaseRecommendationEntity.endTime,
                qPurchaseRecommendationEntity.totalItems,
                qProductOrder.priority,
                qProductOrder.type,
                qPurchaseRecommendationEntity.note,
                qPurchaseRecommendationEntity.createdAt,
                qPurchaseRecommendationEntity.createdBy,
                qPurchaseRecommendationEntity.status
            ))
            .from(qPurchaseRecommendationEntity)
            .leftJoin(qProductOrder).on(qPurchaseRecommendationEntity.mrpPoId.eq(qProductOrder.productOrderCode))
            .orderBy(qPurchaseRecommendationEntity.updatedAt.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());

        PurchaseRecommendationFilter filter = input.getFilter();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qPurchaseRecommendationEntity.isActive.isTrue());
        booleanBuilder.and(qProductOrder.isActive.eq((byte) 1));

        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qPurchaseRecommendationEntity.mrpPoId.containsIgnoreCase(filter.getSoCode()));
        }
        if (!StringUtils.isEmpty(filter.getMrpSubCode())) {
            booleanBuilder.and(qPurchaseRecommendationEntity.mrpSubCode.containsIgnoreCase(filter.getMrpSubCode()));
        }
        if (filter.getStatus() != null) {
            booleanBuilder.and(qPurchaseRecommendationEntity.status.eq(filter.getStatus()));
        }
        if (filter.getCreatedStartDate() != null && !StringUtils.isEmpty(simpleDateFormat.format(filter.getCreatedStartDate())) && filter.getCreatedEndDate() != null && !StringUtils.isEmpty(simpleDateFormat.format(filter.getCreatedEndDate()))) {
            log.info("Vào hàm gọi createdDate");
            log.info("filter.getCreatedStartDate(): " + filter.getCreatedStartDate().toInstant());
            log.info("filter.getCreatedEndDate(): " + filter.getCreatedEndDate().toInstant());
            booleanBuilder.and(qPurchaseRecommendationEntity.createdAt.after(filter.getCreatedStartDate().toInstant())).and(qPurchaseRecommendationEntity.createdAt.before(filter.getCreatedEndDate().toInstant()));
        }
        query.where(booleanBuilder);
        log.info(query.toString());
        List<PurchaseRecommendationDTO> result = query.fetch();
        long count = query.fetchCount();

        return new PageResponse<List<PurchaseRecommendationDTO>>()
            .result("00", "Thành công", true)
            .dataCount(count)
            .data(result);
    }

    public PageResponse<List<PurchaseHasRecommendationDTO>> getAllPurchaseHasRecommendation(PageFilterInput<PurchaseRecommendationFilter> input) {
        if (SecurityUtils.getCurrentUsername().isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "access.denied");
        }
        boolean isAdmin = SecurityUtils.hasCurrentUserAnyOfAuthorities(AuthoritiesConstants.ADMIN);
        String username = SecurityUtils.getCurrentUsername().get();
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        QPurchaseHasRecommendationEntity qPurchaseHasRecommendationEntity = QPurchaseHasRecommendationEntity.purchaseHasRecommendationEntity;
        QProductOrder qProductOrder = QProductOrder.productOrder;
        QApprovalUserAuthorizationEntity qApprovalUserAuthorizationEntity = QApprovalUserAuthorizationEntity.approvalUserAuthorizationEntity;
        JPAQuery<PurchaseHasRecommendationDTO> query = new JPAQueryFactory(entityManager)
            .select(new QPurchaseHasRecommendationDTO(
                qPurchaseHasRecommendationEntity.purchaseRecommendationId,
                qProductOrder.productOrderCode,
                qPurchaseHasRecommendationEntity.mrpSubCode,
                qPurchaseHasRecommendationEntity.startTime,
                qPurchaseHasRecommendationEntity.endTime,
                qPurchaseHasRecommendationEntity.totalItems,
                qProductOrder.priority,
                qProductOrder.type,
                qPurchaseHasRecommendationEntity.note,
                qPurchaseHasRecommendationEntity.createdAt,
                qPurchaseHasRecommendationEntity.createdBy,
                qPurchaseHasRecommendationEntity.status,
                qPurchaseHasRecommendationEntity.batch,
                qPurchaseHasRecommendationEntity.dueDate
            ))
            .from(qPurchaseHasRecommendationEntity)
            .leftJoin(qProductOrder).on(qPurchaseHasRecommendationEntity.mrpPoId.eq(qProductOrder.productOrderCode))
            .orderBy(qPurchaseHasRecommendationEntity.updatedAt.desc())
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset());

        PurchaseRecommendationFilter filter = input.getFilter();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!isAdmin) {
            query.join(qApprovalUserAuthorizationEntity).on(
                qPurchaseHasRecommendationEntity.purchaseRecommendationId.eq(qApprovalUserAuthorizationEntity.purchaseRecommendationId)
                    .and(qPurchaseHasRecommendationEntity.batch.eq(qApprovalUserAuthorizationEntity.batch))
            );
            booleanBuilder.and(qApprovalUserAuthorizationEntity.username.eq(username));
        }
        booleanBuilder.and(qProductOrder.isActive.eq((byte) 1));
        booleanBuilder.and(qPurchaseHasRecommendationEntity.isActive.eq(true));
        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qPurchaseHasRecommendationEntity.mrpPoId.containsIgnoreCase(filter.getSoCode()));
        }
        if (!StringUtils.isEmpty(filter.getMrpSubCode())) {
            booleanBuilder.and(qPurchaseHasRecommendationEntity.mrpSubCode.containsIgnoreCase(filter.getMrpSubCode()));
        }
        if (filter.getStatus() != null) {
            booleanBuilder.and(qPurchaseHasRecommendationEntity.status.eq(filter.getStatus()));
        }
        if (filter.getCreatedStartDate() != null && !StringUtils.isEmpty(simpleDateFormat.format(filter.getCreatedStartDate())) && filter.getCreatedEndDate() != null && !StringUtils.isEmpty(simpleDateFormat.format(filter.getCreatedEndDate()))) {
            log.info("Vào hàm gọi createdDate");
            log.info("filter.getCreatedStartDate(): " + filter.getCreatedStartDate().toInstant());
            log.info("filter.getCreatedEndDate(): " + filter.getCreatedEndDate().toInstant());
            booleanBuilder.and(qPurchaseHasRecommendationEntity.createdAt.after(filter.getCreatedStartDate().toInstant())).and(qPurchaseHasRecommendationEntity.createdAt.before(filter.getCreatedEndDate().toInstant()));
        }
        query.where(booleanBuilder);
        log.info(query.toString());
        log.info("Get purchase recommendation for approve for username {}", username);
        List<PurchaseHasRecommendationDTO> result = query.fetch();
        long count = query.fetchCount();

        return new PageResponse<List<PurchaseHasRecommendationDTO>>()
            .result("00", "Thành công", true)
            .dataCount(count)
            .data(result);
    }

    /**
     * hàm lưu khuyến nghị sau khi click gửi khuyến nghị
     *
     * @param syntheticMrpDTO
     * @return
     * @throws JsonProcessingException
     * @throws ParseException
     */
    @Transactional
    public CommonResponse saveSyntheticMrp(SyntheticMrpDTO syntheticMrpDTO, Set<String> items) throws JsonProcessingException, ParseException {
        MrpSubEntity mrpSubEntity = mrpSubRepository.getByMrpSubCode(syntheticMrpDTO.getMrpSubCode());
        if (mrpSubEntity == null) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        if (purchaseRecommendationRepository.existsByMrpPoIdAndMrpSubCodeAndIsActiveTrue(mrpSubEntity.getMrpPoId(), mrpSubEntity.getMrpSubCode())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "duplicate.purchase.recommendation", mrpSubEntity.getMrpPoId(), mrpSubEntity.getMrpSubCode());
        }
        PurchaseRecommendationEntity pre = purchaseRecommendationMapper.toPurchaseRecommendationEntity(syntheticMrpDTO);
        // Save PRecommendation first
        PurchaseRecommendationEntity savedPre = purchaseRecommendationRepository.save(pre);

        List<ItemSyntheticDTO> itemSyntheticDTOList = syntheticMrpDTO.getResultData();
        ObjectMapper objectMapper = new ObjectMapper();

//        Map<String, Float> rateExchange = rateExchangeService.getRateExchange();
        List<ItemHoldEntity> itemHoldEntities = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<PurchaseRecommendationPurchasePlanEntity> planEntities = new ArrayList<>();

        List<String> listItemCode = itemSyntheticDTOList.stream().map(ItemSyntheticDTO::getItemCode).collect(Collectors.toList());

        // Map item code => Moq Price
        Map<String, List<MoqDTO>> moqPrice = mqqPriceRepository.findPriceByItemCodeMap(listItemCode);

        for (ItemSyntheticDTO item : itemSyntheticDTOList) {
            if (items != null &&
                !items.contains(Utils.toItemKey(item.getItemCode(), item.getBomVersion()))) continue;

            // Kiem tra NVL co nhu cau khong ?
            boolean isValidItem = false;
            for (DetailItemSyntheticDTO analysisResult : item.getDetailData()) {
                if (analysisResult.getOriginQuantity() != 0) {
                    log.info("Đã vào đây");
                    isValidItem = true;
                    break;
                }
            }
            if (!isValidItem) continue;
//            if (item.getRequestNumber() == 0) continue;
            PurchaseRecommendationDetailEntity prde = new PurchaseRecommendationDetailEntity();
            prde.setPurchaseRecommendation(savedPre);
            prde.setRequiredQuantity(item.getRequiredQuantity());
            prde.setItemCode(item.getItemCode());
            prde.setItemDescription(item.getItemName());
            String jsonString = objectMapper.writeValueAsString(item.getDetailData());
            prde.setAnalysisResult(jsonString);
            prde.setStatus(Constants.PurchaseRecommendationDetail.STATUS_NEW);
            prde.setQuantity(item.getRequestNumber());

            //TODO: Need OPTIMIZE
//            double exchange;

            choosePrice(prde, moqPrice.get(item.getItemCode()));

            // Lưu đối tượng PurchaseRecommendationDetailEntity và kiểm tra kết quả
            try {
                prde = purchaseRecommendationDetailRepository.save(prde);

                // Nếu lưu thành công, log thông tin chi tiết
                log.info("Saved PurchaseRecommendationDetailEntity: [ItemCode: {}, Quantity: {}, Status: {}, RequiredQuantity: {}]",
                    prde.getItemCode(), prde.getQuantity(), prde.getStatus(), prde.getRequiredQuantity());
            } catch (Exception e) {
                // Log thông tin lỗi nếu xảy ra ngoại lệ
                log.error("Failed to save PurchaseRecommendationDetailEntity for ItemCode: {}, Error: {}", prde.getItemCode(), e.getMessage());
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "error.save.purchaseRecommendationDetail", String.valueOf(e));
            }

            if (item.getRequestNumber() == 0) {
                planEntities.add(planMapper.toEntity(prde, item.getDetailData().get(1), Constants.PurchaseRecommendationPlan.CHECKED));
            }

            int numberOfOutOfSpaceWarehouse = 0;

            // Hold item
            // Skip hien trang
            log.info("Calculating hold quantity for {}", savedPre.getMrpSubCode());
            for (int i = 1; i < item.getDetailData().size(); i++) {
                DetailItemSyntheticDTO itemQuantityDetail = item.getDetailData().get(i);

                // Khong co nhu cau san xuat
                if (itemQuantityDetail.getOriginQuantity() == 0) continue;
                // Hold nhu cau san xuat
                itemHoldEntities.add(itemHoldMapper.toItemHoldEntity(item, savedPre,
//                                itemQuantityDetail.getRequiredQuantity(),
                        itemQuantityDetail.getOriginQuantity(),
                        null,
                        simpleDateFormat.parse(item.getDetailData().get(i).getLandMark())
                    )
                );

                if (itemQuantityDetail.getRequiredQuantity() > 0) {
                    planEntities.add(
                        planMapper.toEntity(prde, itemQuantityDetail, Constants.PurchaseRecommendationPlan.CHECKED)
                    );
                }
            }
        }
        itemHoldEntities.forEach(holdEntity -> {
            System.err.println("Item " + holdEntity.getItemCode() + " quantity " + holdEntity.getQuantity() + " whs " + holdEntity.getWarehouseCode() + " date: " + simpleDateFormat.format(holdEntity.getHoldDate()));
            log.info("Item {} quantity {} whs {} date {}", holdEntity.getItemCode(), holdEntity.getQuantity(), holdEntity.getWarehouseCode(), simpleDateFormat.format(holdEntity.getHoldDate()));
        });
        purchaseRecommendationPlanRepository.saveAll(planEntities);

//         Change mrpSub status to sent purchase recommendation
        mrpSubEntity.setStatus(Constants.MrpSub.SENT_PURCHASE_RECOMMENDATION);
        mrpSubRepository.save(mrpSubEntity);

        // Hold Item
        itemHoldRepository.saveAll(itemHoldEntities);

        return new CommonResponse()
            .success()
            .data(new SaveRecommendationResponse(pre.getPurchaseRecommendationId(), pre.getMrpSubCode(), pre.getMrpPoId()));
    }

    private void choosePrice(PurchaseRecommendationDetailEntity item, List<MoqDTO> priceList) {
        if (CollectionUtils.isEmpty(priceList)) return;
//        ----------------------------
//        Optional<MoqDTO> maxTimeUsedOptional = priceList.stream()
//            .filter(moq -> moq.getTimeUsed() != null)
//            .max(Comparator.comparing(MoqDTO::getTimeUsed));
//
//        int maxTimeUsed = maxTimeUsedOptional.map(MoqDTO::getTimeUsed).orElse(0);
//
//        if (maxTimeUsedOptional.isPresent()) {
//            System.out.println("Max Time Used: " + maxTimeUsed);
//        } else {
//            System.out.println("Không có giá trị thích hợp.");
//        }
//        ------------------------------------------------------
        int maxTimeUsed = priceList.stream().max(Comparator.comparing(MoqDTO::getTimeUsed)).get().getTimeUsed();
        Date maxStartTime = new Date(Long.MIN_VALUE);
        MoqDTO bestPrice = priceList.get(0);
        boolean isFoundSuitableRange = false;
        double minRangeEndDiff = Double.MAX_VALUE;
        for (MoqDTO price : priceList) {
            // Most used
            if(price.getTimeUsed() != null){
                if (price.getTimeUsed() == maxTimeUsed) {
                    log.debug("Max timeUsed {} ", maxTimeUsed);
                    Date startTime = price.getTimeStart();
                    if (startTime == null) startTime = new Date(Long.MIN_VALUE);

                    if (startTime.compareTo(maxStartTime) > 0) {
                        log.debug("Found new closet date {} of most fav vendor", startTime);
                        maxStartTime = startTime;
                        bestPrice = price;

                        // Reset helper variable to new max date
                        isFoundSuitableRange = false;
                        minRangeEndDiff = Double.MAX_VALUE;
                    } else if (startTime.equals(maxStartTime)) {
                        if (isFoundSuitableRange) continue;
                        // Found suitable range
                        if ((price.getRangeStart() == 0 && price.getRangeEnd() == 0) // all range
                            || (price.getRangeStart() <= item.getQuantity() && item.getQuantity() <= price.getRangeEnd())) {
                            bestPrice = price;
                            isFoundSuitableRange = true;
                        }
                        // Find min diff of range end and quantity
                        if(item.getQuantity() == null){
                            item.setQuantity(0.0);
                        }
                        double rangeEndDiff = Math.abs(item.getQuantity() - price.getRangeEnd());
                        if (rangeEndDiff < minRangeEndDiff) {
                            bestPrice = price;
                            minRangeEndDiff = rangeEndDiff;
                        }
                    }
                }
            }
        }

        // Set to best price
        item.setMoqPriceId(bestPrice.getItemPriceId());
        if (bestPrice.getLeadTime() != null) {
            item.setReceiveDate(DateUtils.addDays(new Date(), bestPrice.getLeadTime()));
        }
    }

    @Transactional
    public CommonResponse deletePurchaseRecommendation(Integer purchaseRecommendationId) {
        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationRepository.findByPurchaseRecommendationIdAndIsActiveTrue(purchaseRecommendationId);
        if (purchaseRecommendationEntity == null)
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        purchaseRecommendationEntity.setActive(false);
        purchaseRecommendationRepository.save(purchaseRecommendationEntity);
        List<PurchaseHasRecommendationEntity> list = purchaseHasRecommendationRepository.getPurchaseHasRecommendationEntity(purchaseRecommendationId);
        if (list.size() != 0) {
            for (PurchaseHasRecommendationEntity item : list) {
                item.setActive(false);
            }
            purchaseHasRecommendationRepository.saveAll(list);
        }

        List<PurchaseRecommendationDetailEntity> purchaseRecommendationDetailEntities = purchaseRecommendationDetailRepository.getAllByPurchaseRecommendationAndIsActiveTrue(purchaseRecommendationEntity);
        purchaseRecommendationDetailEntities.forEach(entity -> entity.setActive(false));
        purchaseRecommendationDetailRepository.saveAll(purchaseRecommendationDetailEntities);

        log.info("Deleted purchaseRecommendation {}", purchaseRecommendationEntity.getPurchaseRecommendationId());
        int numberOfUnHoldItems = itemHoldRepository.unHoldItemsOf(purchaseRecommendationEntity.getMrpSubCode());
        log.info("Un-hold {} items with mrpSubCode {}", numberOfUnHoldItems, purchaseRecommendationEntity.getMrpSubCode());
        return new CommonResponse().success("Xóa khuyến nghị thành công");
    }

    @Transactional
    public CommonResponse deletePurchaseBatchRecommendation(Integer purchaseRecommendationId, Integer batch) {
        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationRepository.findByPurchaseRecommendationIdAndIsActiveTrue(purchaseRecommendationId);
        if (purchaseRecommendationEntity == null)
            throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        PurchaseHasRecommendationEntity purchaseHasRecommendation = purchaseHasRecommendationRepository.getPurchaseHasRecommendationBatch(purchaseRecommendationId, batch);
        if (purchaseHasRecommendation == null) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        Integer check = recommendationBatchRepository.countByStatus(purchaseRecommendationId, batch);
        List<String> listItems = new ArrayList<>();
        if (check == 0) {
            purchaseHasRecommendation.setActive(false);
            purchaseHasRecommendationRepository.save(purchaseHasRecommendation);
            log.info("Deleted purchaseRecommendation {}", purchaseRecommendationEntity.getPurchaseRecommendationId());
            int numberOfUnHoldItems = itemHoldRepository.unHoldItemsOfBatch(purchaseRecommendationEntity.getMrpSubCode(), listItems);
            log.info("Un-hold {} items with mrpSubCode {}", numberOfUnHoldItems, purchaseRecommendationEntity.getMrpSubCode());
            return new CommonResponse().success("Xóa khuyến nghị thành công");
        } else {
            List<PurchaseRecommendationBatch> list = recommendationBatchRepository.getListBatch(purchaseRecommendationId, batch);
            if (list.size() != 0) {
                for (PurchaseRecommendationBatch item : list
                ) {
                    listItems.add(item.getItemCode());
                    item.setActive(false);
                }
                recommendationBatchRepository.saveAll(list);
            }
            log.info("Deleted purchaseRecommendation {}", purchaseRecommendationEntity.getPurchaseRecommendationId());
            int numberOfUnHoldItems = itemHoldRepository.unHoldItemsOfBatch(purchaseRecommendationEntity.getMrpSubCode(), listItems);
            log.info("Un-hold {} items with mrpSubCode {}", numberOfUnHoldItems, purchaseRecommendationEntity.getMrpSubCode());
            return new CommonResponse().success("Đã có bản ghi được phê duyêt,chỉ xóa các bản ghi chưa phê duyệt!");
        }
    }

    public PageResponse<?> getSyntheticByItemCode(Integer purchaseRecommendationId, String itemCode) throws JsonProcessingException {
        PurchaseRecommendationDetailEntity prde = purchaseRecommendationDetailRepository.getSyntheticByItemCode(itemCode, purchaseRecommendationId);

        if (prde != null) {
            String data = prde.getAnalysisResult();

            ObjectMapper mapper = new ObjectMapper();
            DetailItemSyntheticDTO[] dto = mapper.readValue(data, DetailItemSyntheticDTO[].class);
            return new PageResponse<>()
                .errorCode("00")
                .message("Thành công")
                .isOk(true)
                .data(dto);
        }

        return new PageResponse<>()
            .errorCode("400")
            .message("Yêu cầu không hợp lê !")
            .isOk(false);
    }

    public PageResponse<?> getItemOnPrPo(PageFilterInput<OrderItemForm> formPageFilterInput) {
        Pageable pageable = PageRequest.of(formPageFilterInput.getPageNumber(), formPageFilterInput.getPageSize());
        Page<ItemOnPrPo> list = mqqPriceRepository.getItemOnPrPo(formPageFilterInput.getFilter().getItemCode(),
//            formPageFilterInput.getFilter().getTimeStart(), formPageFilterInput.getFilter().getTimeEnd(),
            pageable);

        return new PageResponse<>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(list.getTotalElements())
            .data(list.getContent());
    }

    /**
     * hàm lưu thông tin forecast
     *
     * @param input
     * @return
     * @throws JsonProcessingException
     * @throws ParseException
     */
    @Transactional
    public CommonResponse saveForecastOrder(ForecastMaterialDTO input) throws JsonProcessingException, ParseException {
        input.setForecastOrderStatus(Constants.ForecastOrder.SENT_PURCHASE_RECOMMENDATION);
        materialPlanningService.saveOrUpdateMaterialPlan(input);
        PurchaseRecommendationEntity purchaseRecommendationEntity;
        purchaseRecommendationEntity = purchaseRecommendationRepository.getPurchaseRecommendationEntityByFc(input.getForecastCode());
        if (purchaseRecommendationEntity == null) {
            purchaseRecommendationEntity = purchaseRecommendationRepository.save(
                purchaseRecommendationMapper.toPurchaseRecommendationEntity(input)
            );
        } else {
            purchaseRecommendationEntity.setUpdatedAt(new Date().toInstant());
            purchaseRecommendationRepository.save(purchaseRecommendationEntity);
        }
        double totalQuantity;
        List<MaterialPlanDTO> itemList = input.getListData();
        List<String> itemCodeList = new ArrayList<>();
        List<PurchaseRecommendationDetailEntity> purchaseRecommendationDetailEntities = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> listItemCode = itemList.stream().map(MaterialPlanDTO::getItemCode).collect(Collectors.toList());

        List<MoqDTO> moqDTOList = mqqPriceRepository.findMoqMinAndLeadTimeByItemCode(listItemCode);
        Map<String, Float> rateExchange = rateExchangeService.getRateExchange();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<ItemHoldEntity> holdEntities = new ArrayList<>();
        for (MaterialPlanDTO item : itemList) {
            // Pre-check
            if (!item.getChecked() || "BTP".equals(item.getGroupItem())) continue;
            PurchaseRecommendationDetailEntity prde = purchaseRecommendationDetailMapper.toPurchaseRecommendationEntity(item);
            prde.setPurchaseRecommendation(purchaseRecommendationEntity);
            //Tính tổng quantity trong thời gian kế hoạch cho vật tư
            if (item.getItemStartTime() == null && item.getItemEndTime() == null) {
                totalQuantity = Utils.getTotalItem(input.getForecastMode(), input.getStartDate(), input.getEndDate(), item.getListLandMark());
            } else if (item.getItemStartTime() != null && item.getItemEndTime() == null) {
                totalQuantity = Utils.getTotalItem(input.getForecastMode(), item.getItemStartTime(), input.getEndDate(), item.getListLandMark());
            } else if (item.getItemStartTime() == null && item.getItemEndTime() != null) {
                totalQuantity = Utils.getTotalItem(input.getForecastMode(), input.getStartDate(), item.getItemEndTime(), item.getListLandMark());
            } else {
                totalQuantity = Utils.getTotalItem(input.getForecastMode(), item.getItemStartTime(), item.getItemEndTime(), item.getListLandMark());
            }
            if (totalQuantity == 0) continue;
            itemCodeList.add(item.getItemCode());
            //TODO: Need OPTIMIZE
            double exchange;
            for (MoqDTO m : moqDTOList) {
                exchange = rateExchange.get(m.getCurrency()) * m.getPrice();
                if (item.getItemCode().equals(m.getItemCode())
                    // All range
                    && ((m.getRangeStart() == 0 && m.getRangeEnd() == 0)
                    // or Between range
                    || (m.getRangeStart() <= totalQuantity && totalQuantity <= m.getRangeEnd()))
                    && exchange < prde.getPrice()
                ) {
                    prde.setPrice(exchange);
                    Calendar cal = Calendar.getInstance();
                    prde.setMoqPriceId(m.getItemPriceId());
                    cal.add(Calendar.DAY_OF_MONTH, m.getLeadTime());
                    prde.setReceiveDate(cal.getTime());
                }
            }
            String jsonString = objectMapper.writeValueAsString(detailItemSyntheticMapper.detailItemSyntheticDTOAdapter(item.getListLandMark()));
            prde.setAnalysisResult(jsonString);
            prde.setQuantity(totalQuantity);
            purchaseRecommendationDetailEntities.add(prde);
            // Add hold item
//            log.info("Hold item {} with quantity {}", item.getItemCode(), totalQuantity);
//            for (LandMarkDTO landMarkDTO : item.getListLandMark())
//                holdEntities.add(itemHoldMapper.toItemHoldEntity(item, purchaseRecommendationEntity, totalQuantity,
//                    simpleDateFormat.parse(landMarkDTO.getLandMarkDay())));
        }
        purchaseRecommendationDetailRepository.saveAll(purchaseRecommendationDetailEntities);
//        itemHoldRepository.saveAll(holdEntities);
        // Update forecast item status
        int numberOfSentItem = forecastOrderDetailRepository.changeItemStatusOf(itemCodeList, Constants.ForecastOrderDetail.SENT_PURCHASE_RECOMMENDATION);
        log.info("{} sent to Purchase Recommendation", numberOfSentItem);
        return new CommonResponse().success();
    }

    public void updateStatus(String mrpSubCode, Integer status) {
        PurchaseRecommendationEntity purchaseRecommendationEntity = purchaseRecommendationRepository.findByMrpSubCodeAndIsActiveTrue(mrpSubCode);
        if (purchaseRecommendationEntity == null) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        if (status == Constants.PurchaseRecommendation.COMPLETED) {
            purchaseRecommendationEntity.setStatus(status);
            purchaseRecommendationRepository.save(purchaseRecommendationEntity);

            List<PurchaseRecommendationDetailEntity> purchaseRecommendationDetailEntities = purchaseRecommendationDetailRepository.getAllByPurchaseRecommendationAndIsActiveTrue(purchaseRecommendationEntity);
            purchaseRecommendationDetailEntities.forEach(entity -> entity.setStatus(Constants.PurchaseRecommendationDetail.CLOSED));
            purchaseRecommendationDetailRepository.saveAll(purchaseRecommendationDetailEntities);

            List<PurchaseHasRecommendationEntity> purchaseHasRecommendationEntities = purchaseHasRecommendationRepository.getPurchaseHasRecommendationEntity(purchaseRecommendationEntity.getPurchaseRecommendationId());
            purchaseHasRecommendationEntities.forEach(entity -> entity.setStatus(Constants.PurchaseRecommendation.COMPLETED));
            purchaseHasRecommendationRepository.saveAll(purchaseHasRecommendationEntities);

            List<PurchaseRecommendationBatch> purchaseRecommendationBatches = recommendationBatchRepository.getListBatch(purchaseRecommendationEntity.getPurchaseRecommendationId());
            purchaseRecommendationBatches.forEach(entity -> entity.setStatus(Constants.PurchaseRecommendationDetail.CLOSED));
            recommendationBatchRepository.saveAll(purchaseRecommendationBatches);

            MrpSubEntity mrpSubEntity = mrpSubRepository.getByMrpSubCode(mrpSubCode);
            mrpSubEntity.setStatus(Constants.MrpSub.CLOSED);
            mrpSubRepository.save(mrpSubEntity);

            // Unhold item
            List<ItemHoldEntity> holdItem = itemHoldRepository.findByPurchaseRecommendationId(purchaseRecommendationEntity.getPurchaseRecommendationId());
            holdItem.forEach(entity -> entity.setActive(false));
            itemHoldRepository.saveAll(holdItem);

            // Change plan status
            List<PurchaseRecommendationPurchasePlanEntity> planEntities = purchaseRecommendationPlanRepository.findAllByMrpSubCode(mrpSubCode);
            planEntities.forEach(entity -> {
                if (entity.getStatus() == Constants.PurchaseRecommendationPlan.ACCEPTED)
                    entity.setStatus(Constants.PurchaseRecommendationPlan.CLOSED_ACCEPTED);
                else
                    entity.setStatus(Constants.PurchaseRecommendationPlan.CLOSED);
            });
            purchaseRecommendationPlanRepository.saveAll(planEntities);
        }
    }
}
