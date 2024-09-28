package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.PurchaseRequestDetailEntity;
import com.facenet.mrp.domain.mrp.PurchaseRequestEntity;
import com.facenet.mrp.domain.sap.*;
import com.facenet.mrp.repository.mrp.PurchaseRequestDetailEntityRepository;
import com.facenet.mrp.repository.mrp.PurchaseRequestEntityRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.request.PurchaseRequestDetailPagingDTO;
import com.facenet.mrp.service.dto.request.PurchaseRequestPagingDTO;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.PurchaseRequestDetailEntityMapper;
import com.facenet.mrp.service.mapper.PurchaseRequestEntityMapper;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.utils.Constants;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PurchaseRequestService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestService.class);

    @PersistenceContext
    private EntityManager entityManager;
    private final PurchaseRequestEntityMapper purchaseRequestEntityMapper;
    private final PurchaseRequestDetailEntityMapper purchaseRequestDetailEntityMapper;
    private final PurchaseRequestDetailEntityRepository purchaseRequestDetailEntityRepository;
    private final PurchaseRequestEntityRepository purchaseRequestEntityRepository;
    private final KeycloakService keycloakService;

    public PurchaseRequestService(PurchaseRequestEntityMapper purchaseRequestEntityMapper, PurchaseRequestDetailEntityMapper purchaseRequestDetailEntityMapper, PurchaseRequestDetailEntityRepository purchaseRequestDetailEntityRepository, PurchaseRequestEntityRepository purchaseRequestEntityRepository, KeycloakService keycloakService, ReportService reportService) {
        this.purchaseRequestEntityMapper = purchaseRequestEntityMapper;
        this.purchaseRequestDetailEntityMapper = purchaseRequestDetailEntityMapper;
        this.purchaseRequestDetailEntityRepository = purchaseRequestDetailEntityRepository;
        this.purchaseRequestEntityRepository = purchaseRequestEntityRepository;
        this.keycloakService = keycloakService;
    }

    public PageResponse<List<PurchaseRequestDTO>> getPurchaseRequestsWithPaging(PageFilterInput<PurchaseRequestDTO> input) {
        if (input == null || input.getPageNumber() == null || input.getPageSize() == null || input.getFilter() == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        logger.info("Enter get purchase request function with [params={}]", input.getFilter());

        Pageable pageable;
        if (input.getPageSize() == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        }
        PurchaseRequestDTO filter = input.getFilter();
        QPdn1Entity qPdn1Entity = QPdn1Entity.pdn1Entity;
        QPor1Entity qPor1Entity = QPor1Entity.por1Entity;
        QOprqEntity qOprqEntity = QOprqEntity.oprqEntity;
        QPrq1Entity qPrq1Entity = QPrq1Entity.prq1Entity;
        QOcrdEntity qOcrdEntity = QOcrdEntity.ocrdEntity;
        JPAQuery<PurchaseRequestDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QPurchaseRequestDTO(
                    qPrq1Entity.uSo,
                    qPrq1Entity.uMcode,
                    qPrq1Entity.docEntry.stringValue().trim(),
                    qPor1Entity.docEntry.stringValue().trim(),
                    qPdn1Entity.docEntry.stringValue().trim(),
                    qPrq1Entity.itemCode,
                    qPrq1Entity.dscription,
                    qPrq1Entity.uTenkythuat,
                    qOcrdEntity.cardCode,
                    qOcrdEntity.cardName,
                    qOprqEntity.docDate,
                    qPrq1Entity.pqtReqDate,
                    qPrq1Entity.quantity,
                    qOprqEntity.reqName,
                    qPrq1Entity.lineStatus,
                    qPrq1Entity.openQty,
                    qPrq1Entity.lineNum
                )
            )
            .from(qPrq1Entity)
            .leftJoin(qOprqEntity).on(qOprqEntity.docEntry.eq(qPrq1Entity.docEntry))
            .leftJoin(qPor1Entity).on(
                qPor1Entity.baseEntry.eq(qPrq1Entity.docEntry)
                    .and(qPor1Entity.baseType.eq(1470000113))
                    .and(qPor1Entity.baseLine.eq(qPrq1Entity.lineNum))
            )
            .leftJoin(qPdn1Entity).on(
                qPdn1Entity.baseEntry.eq(qPor1Entity.docEntry)
                    .and(qPdn1Entity.baseType.eq(22))
                    .and(qPdn1Entity.baseLine.eq(qPor1Entity.lineNum))
            )
            .leftJoin(qOcrdEntity).on(qPrq1Entity.lineVendor.eq(qOcrdEntity.cardCode));
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
//            booleanBuilder.and(qPrq1Entity.targetType.eq(22));
        if (!StringUtils.isEmpty(filter.getPrId())) {
            booleanBuilder.and(qPrq1Entity.docEntry.eq(Integer.valueOf(filter.getPrId())));
        }
        if (!StringUtils.isEmpty(filter.getPoId())) {
            booleanBuilder.and(qPor1Entity.docEntry.eq(Integer.valueOf(filter.getPoId())));
        }
        if (!StringUtils.isEmpty(filter.getGrpoId())) {
            booleanBuilder.and(qPdn1Entity.docEntry.eq(Integer.valueOf(filter.getGrpoId())));
        }
        if (!StringUtils.isEmpty(filter.getProductId())) {
            booleanBuilder.and(qPrq1Entity.itemCode.containsIgnoreCase(filter.getProductId()));
        }
        if (!StringUtils.isEmpty(filter.getVendor())) {
            booleanBuilder.and(qPrq1Entity.dscription.containsIgnoreCase(filter.getVendor()));
        }
        if (!StringUtils.isEmpty(filter.getTechnicalName())) {
            booleanBuilder.and(qPrq1Entity.uTenkythuat.containsIgnoreCase(filter.getTechnicalName()));
        }
        if (filter.getCreatedAt() != null) {
            booleanBuilder.and(qOprqEntity.docDate.eq(filter.getCreatedAt()).or(qOprqEntity.docDate.after(filter.getCreatedAt())));
        }
        if (!StringUtils.isEmpty(filter.getMrpId())) {
            booleanBuilder.and(qPrq1Entity.uMcode.containsIgnoreCase(filter.getMrpId()));
        }
        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qPrq1Entity.uSo.containsIgnoreCase(filter.getSoCode()));
        }
        if (filter.getDeliverAt() != null) {
            booleanBuilder.and(qPrq1Entity.pqtReqDate.eq(filter.getDeliverAt()).or(qPrq1Entity.pqtReqDate.before(filter.getDeliverAt())));
        }
        if (!StringUtils.isEmpty(filter.getCreatedBy())) {
            booleanBuilder.and(qOprqEntity.reqName.containsIgnoreCase(filter.getCreatedBy()));
        }
        if (!StringUtils.isEmpty(filter.getStatus())) {
            booleanBuilder.and(qPrq1Entity.lineStatus.eq(filter.getStatus()));
        }
        if (filter.getSapUpdatedAt() != null) {
            booleanBuilder.and(qOprqEntity.updateDate.eq(filter.getSapUpdatedAt()));
        }
        query.where(booleanBuilder);
        query.orderBy(qPrq1Entity.docEntry.desc());
//            query.orderBy(qOprqEntity.docEntry.desc());
        List<PurchaseRequestDTO> result = query.fetch();
        return new PageResponse<List<PurchaseRequestDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(query.fetchCount())
            .data(result);
    }

    public void addPurchaseRequest(PurchaseRequestEntityDto purchaseRequestEntityDto)
    {
        PurchaseRequestEntity purchaseRequestEntity = purchaseRequestEntityMapper.toEntity(purchaseRequestEntityDto);

        List<String> codeList = purchaseRequestEntityRepository.findLastPRCode();
        String newCode;
        if (!codeList.isEmpty()) {
            String lastCode = codeList.get(0);
            int lastNumber = Integer.parseInt(lastCode.substring(3));
            newCode = String.format("PR-%d", lastNumber + 1);
        } else {
            newCode = "PR-1";
        }

        purchaseRequestEntity.setPrCode(newCode);
        purchaseRequestEntity.setApprovalDate(new Timestamp(System.currentTimeMillis()));
        for(PurchaseRequestDetailEntityDto purchaseRequestDetailEntityDto : purchaseRequestEntityDto.getPurchaseRequestDetailEntityDtos())
        {
            PurchaseRequestDetailEntity purchaseRequestDetailEntity = purchaseRequestDetailEntityMapper.toEntity(purchaseRequestDetailEntityDto);
            purchaseRequestDetailEntity.setIsActive(1);
            purchaseRequestDetailEntity.setStatus(Constants.PurchaseRequestStatus.NEWLY_CREATED);
            purchaseRequestDetailEntity.setPrCode(newCode);
            purchaseRequestDetailEntityRepository.save(purchaseRequestDetailEntity);
        }
        // Save full name for user
        purchaseRequestEntity.setPrCreateUser (
            keycloakService.getFullNameByUsername(purchaseRequestEntityDto.getPrCreateUser())
        );
        purchaseRequestEntity.setApprovalUser(
            keycloakService.getFullNameByUsername(purchaseRequestEntityDto.getApprovalUser())
        );
        purchaseRequestEntity.setStatus(Constants.PurchaseRequestStatus.NEWLY_CREATED);
        purchaseRequestEntityRepository.save(purchaseRequestEntity);
    }

    public void deletePurchaseRequest(String prCode)
    {
        PurchaseRequestEntity purchaseRequestEntity = purchaseRequestEntityRepository.findByPrCode(prCode);
        purchaseRequestEntity.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        purchaseRequestEntity.setDeletedBy(SecurityUtils.getCurrentUsername().orElseThrow());

        List<PurchaseRequestDetailEntity> prDetailListByPrCode = purchaseRequestDetailEntityRepository.findAllByPrCodeAndIsActive(prCode, 1);

        for(PurchaseRequestDetailEntity prDetailEntity : prDetailListByPrCode) {
            prDetailEntity.setIsActive(0);
            purchaseRequestDetailEntityRepository.save(prDetailEntity);
        }
        purchaseRequestEntityRepository.save(purchaseRequestEntity);
    }

    @Transactional
    public Page<PurchaseRequestEntity> getAllPurchaseRequest(PageFilterInput<PurchaseRequestPagingDTO> input, Pageable pageable)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PurchaseRequestEntity> cq = cb.createQuery(PurchaseRequestEntity.class);
        Root<PurchaseRequestEntity> root = cq.from(PurchaseRequestEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.isNull(root.get("deletedBy")));

        if (input.getFilter().getPrCode() != null && !input.getFilter().getPrCode().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("prCode")), "%" + input.getFilter().getPrCode().trim().toLowerCase() + "%"));
        }
        if (input.getFilter().getSoCode() != null && !input.getFilter().getSoCode().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("soCode")), "%" + input.getFilter().getSoCode().trim().toLowerCase() + "%"));
        }
        if (input.getFilter().getMrpCode() != null && !input.getFilter().getMrpCode().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("mrpCode")), "%" + input.getFilter().getMrpCode().trim().toLowerCase() + "%"));
        }
        if (input.getFilter().getPeriod() != null && !input.getFilter().getPeriod().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("period")), "%" + input.getFilter().getPeriod().trim().toLowerCase() + "%"));
        }
        if (input.getFilter().getPrCreateUser() != null && !input.getFilter().getPrCreateUser().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("prCreateUser")), "%" + input.getFilter().getPrCreateUser().trim().toLowerCase() + "%"));
        }
        if (input.getFilter().getApprovalUser() != null && !input.getFilter().getApprovalUser().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("approvalUser")), "%" + input.getFilter().getApprovalUser().trim().toLowerCase() + "%"));
        }
        if (input.getFilter().getStatus() != null && !input.getFilter().getStatus().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("status")), "%" + input.getFilter().getStatus().trim().toLowerCase() + "%"));
        }

        // Filter by time
        if (input.getFilter().getPrCreateDate() != null && !input.getFilter().getPrCreateDate().trim().isEmpty()) {
            LocalDate searchDate = LocalDate.parse(input.getFilter().getPrCreateDate().trim());

            Instant startInstant = searchDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant endInstant = searchDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();
            predicates.add(cb.between(root.get("prCreateDate"), Timestamp.from(startInstant), Timestamp.from(endInstant)));
        }
        if (input.getFilter().getApprovalDate() != null && !input.getFilter().getApprovalDate().trim().isEmpty()) {
            LocalDate searchDate = LocalDate.parse(input.getFilter().getApprovalDate().trim());

            Instant startInstant = searchDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Instant endInstant = searchDate.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();
            predicates.add(cb.between(root.get("approvalDate"), Timestamp.from(startInstant), Timestamp.from(endInstant)));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<PurchaseRequestEntity> query = entityManager.createQuery(cq);

        // Pagination
        int totalRows;
        List<PurchaseRequestEntity> results;
        if (pageable.getPageSize() == 0) {
            results = query.getResultList();
            totalRows = results.size();
        } else {
            totalRows = query.getResultList().size();
            results = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        }
        logger.info(
            "Query executed successfully, return {} results (Page {} of {})",
            results.size(),
            pageable.getPageNumber(),
            (totalRows + pageable.getPageSize() - 1) / pageable.getPageSize()
        );
        return new PageImpl<>(results, pageable, totalRows);
    }

    @Transactional
    public Page<PurchaseRequestDetailEntity> getAllPRDetailDetailListByPRId(PageFilterInput<PurchaseRequestDetailPagingDTO> input, Pageable pageable, Integer prId)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PurchaseRequestDetailEntity> cq = cb.createQuery(PurchaseRequestDetailEntity.class);
        Root<PurchaseRequestDetailEntity> root = cq.from(PurchaseRequestDetailEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        PurchaseRequestEntity purchaseRequest = purchaseRequestEntityRepository.findById(prId).orElseThrow(
            () -> new CustomException(HttpStatus.NOT_FOUND, "not.found", "PR ID " + String.valueOf(prId))
        );
        predicates.add(cb.equal(root.get("prCode"), purchaseRequest.getPrCode().trim()));
        predicates.add(cb.equal(root.get("isActive"), 1));

        // Filter
        if (input.getFilter().getItemCode() != null && !input.getFilter().getItemCode().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("itemCode")), "%" + input.getFilter().getItemCode().trim().toLowerCase() + "%"));
        }
        if (input.getFilter().getItemName() != null && !input.getFilter().getItemName().trim().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("itemName")), "%" + input.getFilter().getItemName().trim().toLowerCase() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<PurchaseRequestDetailEntity> query = entityManager.createQuery(cq);

        // Pagination
        int totalRows;
        List<PurchaseRequestDetailEntity> results;
        if (pageable.getPageSize() == 0) {
            results = query.getResultList();
            totalRows = results.size();
        } else {
            totalRows = query.getResultList().size();
            results = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        }
        logger.info(
            "Query executed successfully, return {} results (Page {} of {})",
            results.size(),
            pageable.getPageNumber(),
            (totalRows + pageable.getPageSize() - 1) / pageable.getPageSize()
        );
        return new PageImpl<>(results, pageable, totalRows);
    }
}
