package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.repository.mrp.*;
import com.facenet.mrp.repository.sap.Pdn1Repository;
import com.facenet.mrp.repository.sap.Por1Repository;
import com.facenet.mrp.repository.sap.Prq1Repository;
import com.facenet.mrp.service.dto.PoExcelDTO;
import com.facenet.mrp.service.dto.PurchaseOrderDTO;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.dto.request.AddMonitoringItemRequest;
import com.facenet.mrp.service.dto.request.AddMonitoringRequest;
import com.facenet.mrp.service.dto.request.CreatePurchaseOrderDTO;
import com.facenet.mrp.service.dto.request.ListMonitoringRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.FindPurchaseOrderProgressFilter;
import com.facenet.mrp.service.model.MonitoringFilter;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.utils.Constants;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MonitoringService {
    private final Logger log = LogManager.getLogger(MqqPriceService.class);

    @Autowired
    SapOnOrderSummaryRepository repository;

    @Autowired
    SapOnOrderDurationDetailRepository durationDetailRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderPurchaseRequestRepository purchaseOrderPurchaseRequestRepository;

    @Autowired
    PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    PurchaseOrderItemProgressRepository purchaseOrderItemProgressRepository;
    @Autowired
    PurchaseRequestEntityRepository purchaseRequestEntityRepository;
    @Autowired
    PurchaseRequestDetailEntityRepository purchaseRequestDetailEntityRepository;
    @Autowired
    PurchaseRecommendationDetailRepository recommendationDetailRepository;

    @Autowired
    PurchaseRecommendationPlanRepository planRepository;

    @Autowired
    ItemHoldRepository itemHoldRepository;

    @Autowired
    DurationPlanRepository durationPlanRepository;

    @Qualifier("mrpEntityManager")
    @Autowired
    EntityManager entityManager;
    @Autowired
    private Prq1Repository prq1Repository;
    @Autowired
    private Por1Repository por1Repository;
    @Autowired
    private Pdn1Repository pdn1Repository;

    /**
     * hàm lấy sanh sách tiến độ theo po
     *
     * @param request
     * @return
     */
    public PageResponse<List<OnOrderMonitoringDTO>> monitoringDTOList(PageFilterInput<MonitoringFilter> request) {
        MonitoringFilter filter = request.getFilter();
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        long startTime1 = System.currentTimeMillis();
        QSapOnOrderSummary qSapOnOrderSummary = QSapOnOrderSummary.sapOnOrderSummary;
        JPAQuery<OnOrderMonitoringDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QOnOrderMonitoringDTO(
                    qSapOnOrderSummary.poCode,
                    qSapOnOrderSummary.prCode,
                    qSapOnOrderSummary.contractNumber,
                    qSapOnOrderSummary.providerCode,
                    qSapOnOrderSummary.providerName,
                    qSapOnOrderSummary.dueDate,
                    qSapOnOrderSummary.poCreateDate,
                    qSapOnOrderSummary.createPoUser,
                    qSapOnOrderSummary.type,
                    qSapOnOrderSummary.mrpCode,
                    qSapOnOrderSummary.soCode
                )
            )
            .from(qSapOnOrderSummary);
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSapOnOrderSummary.type.eq("PO"));
        booleanBuilder.and(qSapOnOrderSummary.status.eq("O"));

        if (!StringUtils.isEmpty(filter.getPoCode())) {
            booleanBuilder.and(qSapOnOrderSummary.poCode.containsIgnoreCase(filter.getPoCode()));
        }
        if (!StringUtils.isEmpty(filter.getPrCode())) {
            booleanBuilder.and(qSapOnOrderSummary.prCode.containsIgnoreCase(filter.getPrCode()));
        }
        if (!StringUtils.isEmpty(filter.getMrpCode())) {
            booleanBuilder.and(qSapOnOrderSummary.mrpCode.containsIgnoreCase(filter.getMrpCode()));
        }
        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qSapOnOrderSummary.soCode.containsIgnoreCase(filter.getSoCode()));
        }
        if (!StringUtils.isEmpty(filter.getContractNumber())) {
            booleanBuilder.and(qSapOnOrderSummary.contractNumber.containsIgnoreCase(filter.getContractNumber()));
        }
        if (!StringUtils.isEmpty(filter.getVendorCode())) {
            booleanBuilder.and(qSapOnOrderSummary.providerCode.containsIgnoreCase(filter.getVendorCode()));
        }
        if (!StringUtils.isEmpty(filter.getVendorName())) {
            booleanBuilder.and(qSapOnOrderSummary.providerName.containsIgnoreCase(filter.getVendorName()));
        }
        if (!StringUtils.isEmpty(filter.getPoCreateUser())) {
            booleanBuilder.and(qSapOnOrderSummary.createPoUser.containsIgnoreCase(filter.getPoCreateUser()));
        }
        if (filter.getDueDate() != null) {
            booleanBuilder.and(qSapOnOrderSummary.dueDate.eq(filter.getDueDate()));
        }
        if (filter.getPoCreateDate() != null) {
            booleanBuilder.and(qSapOnOrderSummary.poCreateDate.eq(filter.getPoCreateDate()));
        }
        query.where(booleanBuilder).groupBy(qSapOnOrderSummary.poCode).orderBy(qSapOnOrderSummary.poCreateDate.desc());
        List<OnOrderMonitoringDTO> result = query.fetch();
        long count = query.fetchCount();
        Double sumPO;
        Double sumGrpo;
        for (OnOrderMonitoringDTO item : result) {
            sumPO = 0.0;
            sumGrpo = 0.0;
            List<SapOnOrderSummary> list = repository.getAllByPo(item.getPoCode());
            for (SapOnOrderSummary index : list) {
                if (index.getType().equals("PO")) {
                    sumPO += index.getQuantity();
                } else {
                    sumGrpo += index.getQuantity();
                }
            }
            item.setOrderProgressPercent((int) ((sumGrpo / sumPO) * 100));
            SapOnOrderSummary sap = repository.getPr(item.getPrCode());
            if (sap != null) {
                long diffInMillies = Math.abs(item.getPoCreateDate().getTime() - sap.getPoCreateDate().getTime());
                Integer diff = Math.toIntExact(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));
                item.setOverDateNumber(diff);
            }
            Integer percent = item.getOrderProgressPercent();
            item.setState("Đúng hạn");
            if (percent != null && percent >= 100) {
                if (item.getPoDueDate() != null && item.getPoDueDate().after(new Date())) {
                    item.setState("Sớm hạn");
                } else {
                    item.setState("Đúng hạn");
                }
            } else if (item.getPoDueDate() != null && item.getPoDueDate().after(new Date())) {
                item.setState("Đúng hạn");
            } else if (item.getPoDueDate() != null) {
                item.setState("Quá hạn");
            }
        }
        return new PageResponse<List<OnOrderMonitoringDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(count)
            .data(result);
    }
    @Transactional
    public PageResponse<List<PurchaseOrderProgressDTO>> findPurchaseOrderProgress(PageFilterInput<FindPurchaseOrderProgressFilter> request,Pageable pageable) {
        FindPurchaseOrderProgressFilter filter = request.getFilter();
        StringBuilder sqlBuilderFirst = new StringBuilder();
        if(filter.getPrCode()!=null||filter.getSoCode()!=null||filter.getMrpCode()!=null) {
            sqlBuilderFirst.append("SELECT po.id, popr.purchase_request_code, pr.so_code, pr.mrp_code ")
                .append("FROM purchase_order po ")
                .append("LEFT JOIN purchase_order_purchase_request popr ON popr.purchase_order_id = po.id ")
                .append("LEFT JOIN purchase_request pr ON pr.pr_code = popr.purchase_request_code WHERE 1=1 ");
            if (filter.getPrCode() != null && !filter.getPrCode().isEmpty()) {
                sqlBuilderFirst.append(" AND popr.purchase_request_code LIKE :prCode");
            }
            if (filter.getSoCode() != null && !filter.getSoCode().isEmpty()) {
                sqlBuilderFirst.append(" AND pr.so_code LIKE :soCode");
            }
            if (filter.getMrpCode() != null && !filter.getMrpCode().isEmpty()) {
                sqlBuilderFirst.append(" AND pr.mrp_code LIKE :mrpCode");
            }
            sqlBuilderFirst.append(" ORDER BY po.created_at DESC");
            Query queryFirst = entityManager.createNativeQuery(sqlBuilderFirst.toString());

            if (filter.getPrCode() != null && !filter.getPrCode().isEmpty()) {
                queryFirst.setParameter("prCode", "%" + filter.getPrCode() + "%");
            }
            if (filter.getSoCode() != null && !filter.getSoCode().isEmpty()) {
                queryFirst.setParameter("soCode", "%" + filter.getSoCode() + "%");
            }
            if (filter.getMrpCode() != null && !filter.getMrpCode().isEmpty()) {
                queryFirst.setParameter("mrpCode", "%" + filter.getMrpCode() + "%");
            }
            List<Object[]> resultFirst = queryFirst.getResultList();
            List<Integer> prIdFirst = new ArrayList<>();
            for (Object[] row : resultFirst) {
                prIdFirst.add((Integer) row[0]);
            }
            if(prIdFirst.isEmpty()){
                List<PurchaseOrderProgressDTO> poProgressList = new ArrayList<>();
                return new PageResponse<List<PurchaseOrderProgressDTO>>()
                    .errorCode("00")
                    .message("Thành công")
                    .isOk(true)
                    .dataCount(0)
                    .data(poProgressList);
            }
            else {
                //////////////////////////////////////////////////////////
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                StringBuilder sqlBuilder = new StringBuilder();
                sqlBuilder.append("SELECT po.id, po.po_code, po.vendor_name, po.vendor_code, ")
                    .append("po.order_date, po.delivery_date, po.request_user, po.status, po.created_at, ")
                    .append("po.created_by, po.updated_at ")
                    .append("FROM purchase_order po ")
                    .append("WHERE 1=1  ");
                //Kiểm tra điêu kiện ba trường Pr_code, so_code, mrp_code
                if (!prIdFirst.isEmpty()) {
                    sqlBuilder.append(" AND po.id IN :prIdList");
                }
                // Kiểm tra và thêm điều kiện lọc cho mã PO
                if (filter.getPoCode() != null && !filter.getPoCode().isEmpty()) {
                    sqlBuilder.append(" AND po.po_code LIKE :poCode");

                }

                // Kiểm tra và thêm điều kiện lọc cho tên nhà cung cấp
                if (filter.getVendorName() != null && !filter.getVendorName().isEmpty()) {
                    sqlBuilder.append(" AND po.vendor_name LIKE :vendorName");
                }

                // Kiểm tra và thêm điều kiện lọc cho mã nhà cung cấp
                if (filter.getVendorCode() != null && !filter.getVendorCode().isEmpty()) {
                    sqlBuilder.append(" AND po.vendor_code LIKE :vendorCode");
                }

                // Kiểm tra và thêm điều kiện lọc cho ngày đặt hàng
                if (filter.getOrderDate() != null) {
                    sqlBuilder.append(" AND DATE(po.order_date) = :orderDate");
                }

                // Kiểm tra và thêm điều kiện lọc cho ngày giao hàng
                if (filter.getDeliveryDate() != null) {
                    sqlBuilder.append(" AND DATE(po.delivery_date) = :deliveryDate");
                }

                // Kiểm tra và thêm điều kiện lọc cho người yêu cầu
                if (filter.getRequestUser() != null && !filter.getRequestUser().isEmpty()) {
                    sqlBuilder.append(" AND po.request_user LIKE :requestUser");
                }

                // Cuối cùng, có thể thêm phần sắp xếp kết quả nếu cần
                sqlBuilder.append(" ORDER BY po.created_at DESC"); // Sắp xếp theo ngày tạo
                // Tạo truy vấn từ chuỗi SQL

                Query query = entityManager.createNativeQuery(sqlBuilder.toString());
                //Tham số
                if (!prIdFirst.isEmpty()) {
                    query.setParameter("prIdList", prIdFirst);
                }
                // Đặt tham số cho PO Code nếu có
                if (filter.getPoCode() != null && !filter.getPoCode().isEmpty()) {
                    query.setParameter("poCode", "%" + filter.getPoCode() + "%");
                }


                // Đặt tham số cho tên nhà cung cấp nếu có
                if (filter.getVendorName() != null && !filter.getVendorName().isEmpty()) {
                    query.setParameter("vendorName", "%" + filter.getVendorName() + "%");
                }

                // Đặt tham số cho mã nhà cung cấp nếu có
                if (filter.getVendorCode() != null && !filter.getVendorCode().isEmpty()) {
                    query.setParameter("vendorCode", "%" + filter.getVendorCode() + "%");
                }

                // Đặt tham số cho ngày đặt hàng nếu có
                if (filter.getOrderDate() != null) {
                    String orderDate = formatter.format(filter.getOrderDate());
                    query.setParameter("orderDate", orderDate);
                }

                // Đặt tham số cho ngày giao hàng nếu có

                if (filter.getDeliveryDate() != null) {
                    String deliveryDate = formatter.format(filter.getDeliveryDate());
                    query.setParameter("deliveryDate", deliveryDate);
                }

                // Đặt tham số cho người yêu cầu nếu có
                if (filter.getRequestUser() != null && !filter.getRequestUser().isEmpty()) {
                    query.setParameter("requestUser", "%" + filter.getRequestUser() + "%");
                }


                List<Object[]> results; // Khai báo đúng kiểu là List<Object[]>
                int totalRows = query.getResultList().size(); // Count the total rows
                List<PurchaseOrderProgressDTO> poProgressList = new ArrayList<>();

                if (pageable.isPaged()) {
                    results = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

                } else {
                    results = query.getResultList(); // No pagination
                }

                ////

                for (Object[] row : results) {
                    PurchaseOrderProgressDTO dto = new PurchaseOrderProgressDTO();
                    Long poId = (row[0] != null) ? ((Integer) row[0]).longValue() : null; // Kiểm tra null trước khi ép kiểu
                    dto.setId(poId);

                    dto.setPoCode(row[1] != null ? (String) row[1] : null); // Kiểm tra null
                    dto.setVendorName(row[2] != null ? (String) row[2] : null); // Kiểm tra null
                    dto.setVendorCode(row[3] != null ? (String) row[3] : null); // Kiểm tra null

// Kiểm tra null cho các trường kiểu Timestamp
                    if (row[4] != null && row[4] instanceof Timestamp timestamp4) {
                        java.sql.Date sqlDate4 = new java.sql.Date(timestamp4.getTime());
                        dto.setOrderDate(sqlDate4);
                    } else {
                        dto.setOrderDate(null); // Hoặc gán giá trị mặc định khác nếu cần
                    }

                    if (row[5] != null && row[5] instanceof Timestamp timestamp5) {
                        java.sql.Date sqlDate5 = new java.sql.Date(timestamp5.getTime());
                        dto.setDeliveryDate(sqlDate5);
                    } else {
                        dto.setDeliveryDate(null); // Hoặc gán giá trị mặc định khác nếu cần
                    }

                    dto.setRequestUser(row[6] != null ? (String) row[6] : null); // Kiểm tra null
                    dto.setStatus(row[7] != null ? (String) row[7] : null); // Kiểm tra null

                    if (row[8] != null && row[8] instanceof Timestamp timestamp8) {
                        java.sql.Date sqlDate8 = new java.sql.Date(timestamp8.getTime());
                        dto.setCreatedAt(sqlDate8);
                    } else {
                        dto.setCreatedAt(null); // Hoặc gán giá trị mặc định khác nếu cần
                    }

                    dto.setCreatedBy(row[9] != null ? (String) row[9] : null); // Kiểm tra null

                    if (row[10] != null && row[10] instanceof Timestamp timestamp10) {
                        java.sql.Date sqlDate10 = new java.sql.Date(timestamp10.getTime());
                        dto.setUpdatedAt(sqlDate10);
                    } else {
                        dto.setUpdatedAt(null); // Hoặc gán giá trị mặc định khác nếu cần
                    }


                    List<String> prCodes = purchaseOrderPurchaseRequestRepository.findAllByPurchaseOrderId(poId)
                        .stream()
                        .map(PurchaseorderPurchaseRequestEntity::getPurchaseRequestCode)
                        .collect(Collectors.toList());
                    dto.setPrCodes(prCodes);
                    List<String> soCodes = new ArrayList<>();
                    List<String> mrpCodes = new ArrayList<>();
                    for (String prCode : prCodes) {
                        PurchaseRequestEntity entity = purchaseRequestEntityRepository.findByPrCode(prCode);
                        soCodes.add(entity.getSoCode());
                        mrpCodes.add(entity.getMrpCode());
                    }
                    dto.setSoCodes(soCodes);
                    dto.setMrpCodes(mrpCodes);
                    List<PurchaseOrderItemEntity> items = purchaseOrderItemRepository.findByPurchaseOrderId(poId);
                    int unmetDeadlines = 0;
                    double totalItemQuantity = 0;
                    double totalProgressQuantity = 0;
                    if (row[5] != null && row[5] instanceof Timestamp timestamp5) {
                        java.sql.Date sqlDate5 = new java.sql.Date(timestamp5.getTime());
                        for (PurchaseOrderItemEntity item : items) {
                            totalItemQuantity += item.getQuantity();
                            List<PurchaseOrderItemProgressEntity> progressEntities = purchaseOrderItemProgressRepository.findByPurchaseOrderItemId(item.getId());
                            totalProgressQuantity += progressEntities.stream().mapToDouble(PurchaseOrderItemProgressEntity::getQuantity).sum();
                            unmetDeadlines += (int) progressEntities.stream().filter(progress -> progress.getDate().after(sqlDate5)).count();
                        }
                    }
                    if (totalItemQuantity > 0) {
                        dto.setBuyingProgress((int) ((totalProgressQuantity / totalItemQuantity) * 100));
                    } else {
                        dto.setBuyingProgress(0);
                    }
                    dto.setUnmetDeadlineCount(unmetDeadlines);
                    poProgressList.add(dto);
                }

                return new PageResponse<List<PurchaseOrderProgressDTO>>()
                    .errorCode("00")
                    .message("Thành công")
                    .isOk(true)
                    .dataCount(totalRows)
                    .data(poProgressList);
            }
        }
        else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT po.id, po.po_code, po.vendor_name, po.vendor_code, ")
                .append("po.order_date, po.delivery_date, po.request_user, po.status, po.created_at, ")
                .append("po.created_by, po.updated_at ")
                .append("FROM purchase_order po ")
                .append("WHERE 1=1  ");

            // Kiểm tra và thêm điều kiện lọc cho mã PO
            if (filter.getPoCode() != null && !filter.getPoCode().isEmpty()) {
                sqlBuilder.append(" AND po.po_code LIKE :poCode");

            }

            // Kiểm tra và thêm điều kiện lọc cho tên nhà cung cấp
            if (filter.getVendorName() != null && !filter.getVendorName().isEmpty()) {
                sqlBuilder.append(" AND po.vendor_name LIKE :vendorName");
            }

            // Kiểm tra và thêm điều kiện lọc cho mã nhà cung cấp
            if (filter.getVendorCode() != null && !filter.getVendorCode().isEmpty()) {
                sqlBuilder.append(" AND po.vendor_code LIKE :vendorCode");
            }

            // Kiểm tra và thêm điều kiện lọc cho ngày đặt hàng
            if (filter.getOrderDate() != null) {
                sqlBuilder.append(" AND DATE(po.order_date) = :orderDate");
            }

            // Kiểm tra và thêm điều kiện lọc cho ngày giao hàng
            if (filter.getDeliveryDate() != null) {
                sqlBuilder.append(" AND DATE(po.delivery_date) = :deliveryDate");
            }

            // Kiểm tra và thêm điều kiện lọc cho người yêu cầu
            if (filter.getRequestUser() != null && !filter.getRequestUser().isEmpty()) {
                sqlBuilder.append(" AND po.request_user LIKE :requestUser");
            }

            // Cuối cùng, có thể thêm phần sắp xếp kết quả nếu cần
            sqlBuilder.append(" ORDER BY po.created_at DESC"); // Sắp xếp theo ngày tạo
            // Tạo truy vấn từ chuỗi SQL

            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            //Tham số

            // Đặt tham số cho PO Code nếu có
            if (filter.getPoCode() != null && !filter.getPoCode().isEmpty()) {
                query.setParameter("poCode", "%" + filter.getPoCode() + "%");
            }


            // Đặt tham số cho tên nhà cung cấp nếu có
            if (filter.getVendorName() != null && !filter.getVendorName().isEmpty()) {
                query.setParameter("vendorName", "%" + filter.getVendorName() + "%");
            }

            // Đặt tham số cho mã nhà cung cấp nếu có
            if (filter.getVendorCode() != null && !filter.getVendorCode().isEmpty()) {
                query.setParameter("vendorCode", "%" + filter.getVendorCode() + "%");
            }

            // Đặt tham số cho ngày đặt hàng nếu có
            if (filter.getOrderDate() != null) {
                String orderDate = formatter.format(filter.getOrderDate());
                query.setParameter("orderDate", orderDate);
            }

            // Đặt tham số cho ngày giao hàng nếu có

            if (filter.getDeliveryDate() != null) {
                String deliveryDate = formatter.format(filter.getDeliveryDate());
                query.setParameter("deliveryDate", deliveryDate);
            }

            // Đặt tham số cho người yêu cầu nếu có
            if (filter.getRequestUser() != null && !filter.getRequestUser().isEmpty()) {
                query.setParameter("requestUser", "%" + filter.getRequestUser() + "%");
            }


            List<Object[]> results; // Khai báo đúng kiểu là List<Object[]>
            int totalRows = query.getResultList().size(); // Count the total rows
            List<PurchaseOrderProgressDTO> poProgressList = new ArrayList<>();

            if (pageable.isPaged()) {
                results = query.setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

            } else {
                results = query.getResultList(); // No pagination
            }

            ////

            for (Object[] row : results) {
                PurchaseOrderProgressDTO dto = new PurchaseOrderProgressDTO();
                Long poId = (row[0] != null) ? ((Integer) row[0]).longValue() : null; // Kiểm tra null trước khi ép kiểu
                dto.setId(poId);

                dto.setPoCode(row[1] != null ? (String) row[1] : null); // Kiểm tra null
                dto.setVendorName(row[2] != null ? (String) row[2] : null); // Kiểm tra null
                dto.setVendorCode(row[3] != null ? (String) row[3] : null); // Kiểm tra null

// Kiểm tra null cho các trường kiểu Timestamp
                if (row[4] != null && row[4] instanceof Timestamp timestamp4) {
                    java.sql.Date sqlDate4 = new java.sql.Date(timestamp4.getTime());
                    dto.setOrderDate(sqlDate4);
                } else {
                    dto.setOrderDate(null); // Hoặc gán giá trị mặc định khác nếu cần
                }

                if (row[5] != null && row[5] instanceof Timestamp timestamp5) {
                    java.sql.Date sqlDate5 = new java.sql.Date(timestamp5.getTime());
                    dto.setDeliveryDate(sqlDate5);
                } else {
                    dto.setDeliveryDate(null); // Hoặc gán giá trị mặc định khác nếu cần
                }

                dto.setRequestUser(row[6] != null ? (String) row[6] : null); // Kiểm tra null
                dto.setStatus(row[7] != null ? (String) row[7] : null); // Kiểm tra null

                if (row[8] != null && row[8] instanceof Timestamp timestamp8) {
                    java.sql.Date sqlDate8 = new java.sql.Date(timestamp8.getTime());
                    dto.setCreatedAt(sqlDate8);
                } else {
                    dto.setCreatedAt(null); // Hoặc gán giá trị mặc định khác nếu cần
                }

                dto.setCreatedBy(row[9] != null ? (String) row[9] : null); // Kiểm tra null

                if (row[10] != null && row[10] instanceof Timestamp timestamp10) {
                    java.sql.Date sqlDate10 = new java.sql.Date(timestamp10.getTime());
                    dto.setUpdatedAt(sqlDate10);
                } else {
                    dto.setUpdatedAt(null); // Hoặc gán giá trị mặc định khác nếu cần
                }


                List<String> prCodes = purchaseOrderPurchaseRequestRepository.findAllByPurchaseOrderId(poId)
                    .stream()
                    .map(PurchaseorderPurchaseRequestEntity::getPurchaseRequestCode)
                    .collect(Collectors.toList());
                dto.setPrCodes(prCodes);
                List<String> soCodes = new ArrayList<>();
                List<String> mrpCodes = new ArrayList<>();
                for (String prCode : prCodes) {
                    PurchaseRequestEntity entity = purchaseRequestEntityRepository.findByPrCode(prCode);
                    soCodes.add(entity.getSoCode());
                    mrpCodes.add(entity.getMrpCode());
                }
                dto.setSoCodes(soCodes);
                dto.setMrpCodes(mrpCodes);
                List<PurchaseOrderItemEntity> items = purchaseOrderItemRepository.findByPurchaseOrderId(poId);
                int unmetDeadlines = 0;
                double totalItemQuantity = 0;
                double totalProgressQuantity = 0;
                if (row[5] != null && row[5] instanceof Timestamp timestamp5) {
                    java.sql.Date sqlDate5 = new java.sql.Date(timestamp5.getTime());
                    for (PurchaseOrderItemEntity item : items) {
                        totalItemQuantity += item.getQuantity();
                        List<PurchaseOrderItemProgressEntity> progressEntities = purchaseOrderItemProgressRepository.findByPurchaseOrderItemId(item.getId());
                        totalProgressQuantity += progressEntities.stream().mapToDouble(PurchaseOrderItemProgressEntity::getQuantity).sum();
                        unmetDeadlines += (int) progressEntities.stream().filter(progress -> progress.getDate().after(sqlDate5)).count();
                    }
                }
                if (totalItemQuantity > 0) {
                    dto.setBuyingProgress((int) ((totalProgressQuantity / totalItemQuantity) * 100));
                } else {
                    dto.setBuyingProgress(0);
                }
                dto.setUnmetDeadlineCount(unmetDeadlines);
                poProgressList.add(dto);
            }

            return new PageResponse<List<PurchaseOrderProgressDTO>>()
                .errorCode("00")
                .message("Thành công")
                .isOk(true)
                .dataCount(totalRows)
                .data(poProgressList);
        }
    }

    public PageResponse<List<PurchaseOrderEntity>> createPurchaseOrder(CreatePurchaseOrderDTO createPurchaseOrderDto) {
        PurchaseOrderEntity purchaseOrder = new PurchaseOrderEntity();
        purchaseOrder.setPoCode(createPurchaseOrderDto.getPoCode());
        purchaseOrder.setVendorName(createPurchaseOrderDto.getVendorName());
        purchaseOrder.setVendorCode(createPurchaseOrderDto.getVendorCode());
        purchaseOrder.setOrderDate(createPurchaseOrderDto.getOrderDate());
        purchaseOrder.setDeliveryDate(createPurchaseOrderDto.getDeliveryDate());
        purchaseOrder.setRequestUser(createPurchaseOrderDto.getRequestUser());
        purchaseOrder.setStatus(createPurchaseOrderDto.getStatus());
        purchaseOrder.setNote(createPurchaseOrderDto.getNote());
        purchaseOrder.setUnit(createPurchaseOrderDto.getUnit());
        purchaseOrder.setShippingType(createPurchaseOrderDto.getShippingType());
        purchaseOrder.setReceiveAddress(createPurchaseOrderDto.getReceiveAddress());
        purchaseOrder.setPaymentType(createPurchaseOrderDto.getPaymentType());
        purchaseOrder.setPaymentAddress(createPurchaseOrderDto.getPaymentAddress());
        purchaseOrder.setTotalSummary(createPurchaseOrderDto.getTotalSummary());
        purchaseOrder.setDiscountSummary(createPurchaseOrderDto.getDiscountSummary());
        purchaseOrder.setTaxSummary(createPurchaseOrderDto.getTaxSummary());
        purchaseOrder.setGrossTotalSummary(createPurchaseOrderDto.getGrossTotalSummary());
        purchaseOrder.setWholeDiscountPercent(createPurchaseOrderDto.getWholeDiscountPercent());
        purchaseOrder.setWholeDiscountValue(createPurchaseOrderDto.getWholeDiscountValue());
        purchaseOrder.setWholeTaxPercent(createPurchaseOrderDto.getWholeTaxPercent());
        purchaseOrder.setWholeTaxValue(createPurchaseOrderDto.getWholeTaxValue());
        purchaseOrder.setFinalTotal(createPurchaseOrderDto.getFinalTotal());
        purchaseOrder.setIsDraft(createPurchaseOrderDto.getIsDraft());
        purchaseOrder.setCreatedAt(new Date());

        PurchaseOrderEntity savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        List<PurchaseorderPurchaseRequestEntity> purchaseRequestCodes = new ArrayList<>();
        List<PurchaseOrderItemEntity> items = new ArrayList<>();
        List<PurchaseOrderItemProgressEntity> progress = new ArrayList<>();

        for (String prCode : createPurchaseOrderDto.getPrCodes()) {
            PurchaseorderPurchaseRequestEntity purchaseRequest = new PurchaseorderPurchaseRequestEntity();
            purchaseRequest.setPurchaseOrderId(savedPurchaseOrder.getId());
            purchaseRequest.setPurchaseRequestCode(prCode);
            purchaseRequestCodes.add(purchaseRequest);
        }

        purchaseOrderPurchaseRequestRepository.saveAll(purchaseRequestCodes);

        for (CreatePurchaseOrderDTO.PurchaseOrderItemDTO itemDto : createPurchaseOrderDto.getItems()) {
            PurchaseOrderItemEntity item = new PurchaseOrderItemEntity();
            item.setPurchaseOrder(purchaseOrder);
            item.setItemCode(itemDto.getItemCode());
            item.setItemName(itemDto.getItemName());
            item.setUnit(itemDto.getUnit());
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(itemDto.getPrice());
            item.setTotal(itemDto.getTotal());
            item.setDiscountPercent(itemDto.getDiscountPercent());
            item.setDiscountValue(itemDto.getDiscountValue());
            item.setTaxPercent(itemDto.getTaxPercent());
            item.setTaxValue(itemDto.getTaxValue());
            item.setGrossTotal(itemDto.getGrossTotal());
            item.setNote(itemDto.getNote());
            items.add(item);

            for (CreatePurchaseOrderDTO.PurchaseOrderItemProgressDTO progressDto : itemDto.getProgress()) {
                PurchaseOrderItemProgressEntity progressEntity = new PurchaseOrderItemProgressEntity();
                progressEntity.setPurchaseOrderItem(item);
                progressEntity.setDate(progressDto.getDate());
                progressEntity.setQuantity(progressDto.getQuantity());
                progress.add(progressEntity);
            }

            List<PurchaseRequestDetailEntity> purchaseOrderDetails = purchaseRequestDetailEntityRepository.findByItemCode(itemDto.getItemCode());
            for (PurchaseRequestDetailEntity detail : purchaseOrderDetails) {
                detail.setPoPostedQuantity(detail.getPoPostedQuantity() + itemDto.getQuantity());
            }
            purchaseRequestDetailEntityRepository.saveAll(purchaseOrderDetails);
        }

        List<PurchaseOrderItemEntity> savedItems = purchaseOrderItemRepository.saveAll(items);

        for (PurchaseOrderItemProgressEntity progressEntity : progress) {
            for (PurchaseOrderItemEntity savedItem : savedItems) {
                if (progressEntity.getPurchaseOrderItem().getItemCode().equals(savedItem.getItemCode())) {
                    progressEntity.getPurchaseOrderItem().setId(savedItem.getId());
                    break;
                }
            }
        }

        purchaseOrderItemProgressRepository.saveAll(progress);

        List<PurchaseOrderEntity> result = new ArrayList<>();
        result.add(savedPurchaseOrder);

        return new PageResponse<List<PurchaseOrderEntity>>()
            .result("00", "Thành công", true)
            .data(result)
            .dataCount(result.size());
    }

    public CommonResponse<PurchaseOrderDTO> findPurchaseOrderById(Long id) {
        Optional<PurchaseOrderEntity> purchaseOrderOptional = purchaseOrderRepository.findById(id);
        if (!purchaseOrderOptional.isPresent()) {
            throw new CustomException("Purchase order not found");
        }

        PurchaseOrderEntity purchaseOrderEntity = purchaseOrderOptional.get();

        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        purchaseOrderDTO.setId(purchaseOrderEntity.getId());
        purchaseOrderDTO.setPoCode(purchaseOrderEntity.getPoCode());
        purchaseOrderDTO.setVendorName(purchaseOrderEntity.getVendorName());
        purchaseOrderDTO.setVendorCode(purchaseOrderEntity.getVendorCode());
        purchaseOrderDTO.setOrderDate(purchaseOrderEntity.getOrderDate());
        purchaseOrderDTO.setDeliveryDate(purchaseOrderEntity.getDeliveryDate());
        purchaseOrderDTO.setRequestUser(purchaseOrderEntity.getRequestUser());
        purchaseOrderDTO.setStatus(purchaseOrderEntity.getStatus());
        purchaseOrderDTO.setNote(purchaseOrderEntity.getNote());
        purchaseOrderDTO.setUnit(purchaseOrderEntity.getUnit());
        purchaseOrderDTO.setShippingType(purchaseOrderEntity.getShippingType());
        purchaseOrderDTO.setReceiveAddress(purchaseOrderEntity.getReceiveAddress());
        purchaseOrderDTO.setPaymentType(purchaseOrderEntity.getPaymentType());
        purchaseOrderDTO.setPaymentAddress(purchaseOrderEntity.getPaymentAddress());
        purchaseOrderDTO.setTotalSummary(purchaseOrderEntity.getTotalSummary());
        purchaseOrderDTO.setDiscountSummary(purchaseOrderEntity.getDiscountSummary());
        purchaseOrderDTO.setTaxSummary(purchaseOrderEntity.getTaxSummary());
        purchaseOrderDTO.setGrossTotalSummary(purchaseOrderEntity.getGrossTotalSummary());
        purchaseOrderDTO.setWholeDiscountPercent(purchaseOrderEntity.getWholeDiscountPercent());
        purchaseOrderDTO.setWholeDiscountValue(purchaseOrderEntity.getWholeDiscountValue());
        purchaseOrderDTO.setWholeTaxPercent(purchaseOrderEntity.getWholeTaxPercent());
        purchaseOrderDTO.setWholeTaxValue(purchaseOrderEntity.getWholeTaxValue());
        purchaseOrderDTO.setFinalTotal(purchaseOrderEntity.getFinalTotal());
        purchaseOrderDTO.setIsDraft(purchaseOrderEntity.getIsDraft());

        List<String> prCodes = purchaseOrderPurchaseRequestRepository.findAllByPurchaseOrderId(purchaseOrderEntity.getId())
            .stream()
            .map(PurchaseorderPurchaseRequestEntity::getPurchaseRequestCode)
            .collect(Collectors.toList());
        purchaseOrderDTO.setPrCodes(prCodes);

        // Lambda for mapping PurchaseOrderItemEntity to PurchaseOrderItemDTO
        Function<PurchaseOrderItemEntity, PurchaseOrderDTO.PurchaseOrderItemDTO> mapToItemDTO = entity -> {
            PurchaseOrderDTO.PurchaseOrderItemDTO dto = new PurchaseOrderDTO.PurchaseOrderItemDTO();
            dto.setItemCode(entity.getItemCode());
            dto.setItemName(entity.getItemName());
            dto.setUnit(entity.getUnit());
            dto.setQuantity(entity.getQuantity());
            dto.setPrice(entity.getPrice());
            dto.setTotal(entity.getTotal());
            dto.setDiscountPercent(entity.getDiscountPercent());
            dto.setDiscountValue(entity.getDiscountValue());
            dto.setTaxPercent(entity.getTaxPercent());
            dto.setTaxValue(entity.getTaxValue());
            dto.setGrossTotal(entity.getGrossTotal());
            dto.setNote(entity.getNote());

            // Lambda for mapping PurchaseOrderItemProgressEntity to PurchaseOrderItemProgressDTO
            Function<PurchaseOrderItemProgressEntity, PurchaseOrderDTO.PurchaseOrderItemProgressDTO> mapToProgressDTO = progressEntity -> {
                PurchaseOrderDTO.PurchaseOrderItemProgressDTO progressDTO = new PurchaseOrderDTO.PurchaseOrderItemProgressDTO();
                progressDTO.setDate(progressEntity.getDate());
                progressDTO.setQuantity(progressEntity.getQuantity());
                return progressDTO;
            };

            List<PurchaseOrderItemProgressEntity> progressEntities = purchaseOrderItemProgressRepository.findByPurchaseOrderItemId(entity.getId());
            List<PurchaseOrderDTO.PurchaseOrderItemProgressDTO> progressDTOs = progressEntities.stream().map(mapToProgressDTO).collect(Collectors.toList());
            dto.setProgress(progressDTOs);

            return dto;
        };

        List<PurchaseOrderItemEntity> items = purchaseOrderItemRepository.findByPurchaseOrderId(purchaseOrderEntity.getId());
        List<PurchaseOrderDTO.PurchaseOrderItemDTO> purchaseOrderItemDTOList = items.stream().map(mapToItemDTO).collect(Collectors.toList());

        purchaseOrderDTO.setItems(purchaseOrderItemDTOList);

        return new CommonResponse<PurchaseOrderDTO>().result("00", "Success", true).data(purchaseOrderDTO);
    }

    public CommonResponse<List<PurchaseOrderDTO.PurchaseOrderItemProgressDTO>> findPurchaseOrderItemProgressByItemId(Long purchaseOrderItemId) {
        List<PurchaseOrderItemProgressEntity> progressEntities = purchaseOrderItemProgressRepository.findByPurchaseOrderItemId(purchaseOrderItemId);

        Function<PurchaseOrderItemProgressEntity, PurchaseOrderDTO.PurchaseOrderItemProgressDTO> mapToProgressDTO = entity -> {
            PurchaseOrderDTO.PurchaseOrderItemProgressDTO dto = new PurchaseOrderDTO.PurchaseOrderItemProgressDTO();
            dto.setDate(entity.getDate());
            dto.setQuantity(entity.getQuantity());
            return dto;
        };
        CommonResponse<List<PurchaseOrderDTO.PurchaseOrderItemProgressDTO>> response = new PageResponse<>();
        List<PurchaseOrderDTO.PurchaseOrderItemProgressDTO> res = progressEntities.stream().map(mapToProgressDTO).collect(Collectors.toList());
        response.result("00", "Success", true).data(res);
        return response;
    }

    /**
     * Ham update
     */
    @Transactional
    public CommonResponse<PurchaseOrderEntity> updatePurchaseOrder(Long poId, CreatePurchaseOrderDTO updatePurchaseOrderDto) {
        Optional<PurchaseOrderEntity> purchaseOrderOpt = purchaseOrderRepository.findById(poId);

        if (purchaseOrderOpt.isPresent()) {
            PurchaseOrderEntity purchaseOrder = purchaseOrderOpt.orElseThrow();


            purchaseOrder.setPoCode(updatePurchaseOrderDto.getPoCode());
            purchaseOrder.setVendorName(updatePurchaseOrderDto.getVendorName());
            purchaseOrder.setVendorCode(updatePurchaseOrderDto.getVendorCode());
            purchaseOrder.setOrderDate(updatePurchaseOrderDto.getOrderDate());
            purchaseOrder.setDeliveryDate(updatePurchaseOrderDto.getDeliveryDate());
            purchaseOrder.setRequestUser(updatePurchaseOrderDto.getRequestUser());
            purchaseOrder.setStatus(updatePurchaseOrderDto.getStatus());
            purchaseOrder.setNote(updatePurchaseOrderDto.getNote());
            purchaseOrder.setUnit(updatePurchaseOrderDto.getUnit());
            purchaseOrder.setShippingType(updatePurchaseOrderDto.getShippingType());
            purchaseOrder.setReceiveAddress(updatePurchaseOrderDto.getReceiveAddress());
            purchaseOrder.setPaymentType(updatePurchaseOrderDto.getPaymentType());
            purchaseOrder.setPaymentAddress(updatePurchaseOrderDto.getPaymentAddress());
            purchaseOrder.setIsDraft(updatePurchaseOrderDto.getIsDraft());
            PurchaseOrderEntity savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
            //Xóa thông tin cũ ở các bảng
            List<PurchaseorderPurchaseRequestEntity> existsPurchaseOrderRequest  = purchaseOrderPurchaseRequestRepository.findAllByPurchaseOrderId(poId);
            purchaseOrderPurchaseRequestRepository.deleteAll(existsPurchaseOrderRequest);
            List<PurchaseOrderItemEntity> existPOItems = purchaseOrderItemRepository.findByPurchaseOrderId(poId) ;
            purchaseOrderItemRepository.deleteAll(existPOItems);
            for(PurchaseOrderItemEntity element :existPOItems){
                List<PurchaseOrderItemProgressEntity> itemProgress = purchaseOrderItemProgressRepository.findByPurchaseOrderItemId(element.getId());
                purchaseOrderItemProgressRepository.deleteAll(itemProgress);
            }
            // Lưu thông tin yêu cầu mua
            List<PurchaseorderPurchaseRequestEntity> purchaseRequestCodes = new ArrayList<>();

            for (String prCode : updatePurchaseOrderDto.getPrCodes()) {
                PurchaseorderPurchaseRequestEntity purchaseRequest = new PurchaseorderPurchaseRequestEntity();
                purchaseRequest.setPurchaseOrderId(poId);
                purchaseRequest.setPurchaseRequestCode(prCode);
                purchaseRequestCodes.add(purchaseRequest);
            }
            purchaseOrderPurchaseRequestRepository.saveAll(purchaseRequestCodes);

            for(CreatePurchaseOrderDTO.PurchaseOrderItemDTO itemDTO : updatePurchaseOrderDto.getItems()){
                PurchaseOrderItemEntity item = new PurchaseOrderItemEntity();
                item.setPurchaseOrder(purchaseOrder);
                item.setItemCode(itemDTO.getItemCode());
                item.setItemName(itemDTO.getItemName());
                item.setUnit(itemDTO.getUnit());
                item.setQuantity(itemDTO.getQuantity());
                item.setPrice(itemDTO.getPrice());
                item.setTotal(itemDTO.getTotal());
                item.setDiscountPercent(itemDTO.getDiscountPercent());
                item.setDiscountValue(itemDTO.getDiscountValue());
                item.setTaxPercent(itemDTO.getTaxPercent());
                item.setTaxValue(itemDTO.getTaxValue());
                item.setGrossTotal(itemDTO.getGrossTotal());
                item.setNote(itemDTO.getNote());
                purchaseOrderItemRepository.save(item);
                for(CreatePurchaseOrderDTO.PurchaseOrderItemProgressDTO itemProgressDTO : itemDTO.getProgress()){
                    PurchaseOrderItemProgressEntity progressEntity = new PurchaseOrderItemProgressEntity();
                    progressEntity.setPurchaseOrderItem(item);
                    progressEntity.setDate(itemProgressDTO.getDate());
                    progressEntity.setQuantity(itemProgressDTO.getQuantity());
                    purchaseOrderItemProgressRepository.save(progressEntity);
                }

            }

            // Tạo CommonResponse
            return new CommonResponse<PurchaseOrderEntity>()
                .success("Cập nhật thành công")
                .data(savedPurchaseOrder);
        } else {
            return new CommonResponse<PurchaseOrderEntity>()
                .result("404", "không tìm thấy", false);
        }
    }
/**
 * Export PO to Excel
 *
 */
private void mergeCellExcel (Sheet sheet,Row row,Workbook workbook,int rowStart, int rowEnd, int colStart, int colEnd, Object value){
    Cell cell = row.createCell(colStart);
    cell.setCellValue(value.toString());
    CellRangeAddress merge = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
    sheet.addMergedRegion(merge);

}
    public ResponseEntity<InputStreamResource> exportToExcel(PoExcelDTO input){
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Purchase Order Detail");
            Row headerRow1 = sheet.createRow(0);
            headerRow1.createCell(0).setCellValue("Mã PR");
            headerRow1.createCell(1).setCellValue(input.getPrCodes() != null ? String.join(", ", input.getPrCodes()) : "");
            Row headerRow2 = sheet.createRow(1);
            headerRow2.createCell(0).setCellValue("Mã PO");
            headerRow2.createCell(1).setCellValue(input.getPoCode() != null ? input.getPoCode() : "");
            Row headerRow3 = sheet.createRow(2);
            headerRow3.createCell(0).setCellValue("Mã MRP");
            String mrpString = String.join(", ",input.getMrpCodes());
            headerRow3.createCell(1).setCellValue(input.getMrpCodes() != null ? String.join(", ", input.getMrpCodes()) : "");
            Row headRow4 = sheet.createRow(4);
            headRow4.createCell(0).setCellValue("STT");
            headRow4.createCell(1).setCellValue("Mã vật tư");
            headRow4.createCell(2).setCellValue("Mô tả");
            headRow4.createCell(3).setCellValue("Đơn vị tính");
            headRow4.createCell(4).setCellValue("Số lượng ");
            headRow4.createCell(5).setCellValue("Đơn giá");
            headRow4.createCell(6).setCellValue("Tổng");
            headRow4.createCell(7).setCellValue("Giảm giá");
            headRow4.createCell(8).setCellValue("Tax");
            headRow4.createCell(9).setCellValue("Giá cuối");
            headRow4.createCell(10).setCellValue("Ghi chú");
            sheet.setColumnWidth(0,10*256);
            sheet.setColumnWidth(1,15*256);
            sheet.setColumnWidth(2,40*256);
            sheet.setColumnWidth(3,15*256);
            sheet.setColumnWidth(4,10*256);
            sheet.setColumnWidth(5,10*256);
            sheet.setColumnWidth(6,5*256);
            sheet.setColumnWidth(7,10*256);
            sheet.setColumnWidth(8,5*256);
            sheet.setColumnWidth(9,10*256);
            sheet.setColumnWidth(10,40*256);

            int rowDetail = 5;
            int maxColProgress = 12;
            int itemNumber =1 ;
            for(PoExcelDTO.PurchaseOrderItemDTO itemDTO : input.getItems()){
                Row row =sheet.createRow(rowDetail);
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 0, 0, itemNumber++);
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 1, 1, itemDTO.getItemCode() != null ? itemDTO.getItemCode() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 2, 2, itemDTO.getItemName() != null ? itemDTO.getItemName() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 3, 3, itemDTO.getUnit() != null ? itemDTO.getUnit() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 4, 4, itemDTO.getQuantity() != null ? itemDTO.getQuantity().toString() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 5, 5, itemDTO.getPrice() != null ? itemDTO.getPrice().toString() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 6, 6, itemDTO.getTotal() != null ? itemDTO.getTotal().toString() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 7, 7, itemDTO.getDiscountPercent() != null ? itemDTO.getDiscountPercent().toString() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 8, 8, itemDTO.getTaxValue() != null ? itemDTO.getTaxValue().toString() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 9, 9, itemDTO.getGrossTotal() != null ? itemDTO.getGrossTotal().toString() : "");
                mergeCellExcel(sheet, row, workbook, rowDetail, rowDetail + 1, 10, 10, itemDTO.getNote() != null ? itemDTO.getNote() : "");

                int colProgress = 12;
                Row row2 = sheet.createRow(rowDetail+1);
                row.createCell(11).setCellValue("Nhập thời gian theo format (yyyy/MM/dd");
                row2.createCell(11).setCellValue("Nhập số lượng po");
                sheet.setColumnWidth(11,40*256);
                for(PoExcelDTO.PurchaseOrderItemProgressDTO itemProgressDTO : itemDTO.getProgress()){
                    Cell dateCell = row.createCell(colProgress);
                    sheet.setColumnWidth(colProgress, 15 * 256); // Đặt độ rộng cho cột

                    CellStyle dateStyle = workbook.createCellStyle();
                    CreationHelper creationHelper = workbook.getCreationHelper();
                    dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd")); // Định dạng ngày tháng
                    dateCell.setCellStyle(dateStyle);
                    if (itemProgressDTO.getDate() != null) {
                        dateCell.setCellValue(itemProgressDTO.getDate());
                        dateCell.setCellStyle(dateStyle);
                    } else {
                        dateCell.setCellValue("");
                    }
                    row2.createCell(colProgress).setCellValue(itemProgressDTO.getQuantity());
                    colProgress++;
                }
                if(maxColProgress<colProgress-1) maxColProgress=colProgress-1;
                rowDetail=rowDetail+2;
            }
            if(maxColProgress>12) {
                mergeCellExcel(sheet, headRow4, workbook, 4, 4, 12, maxColProgress, "Tiến độ mua hàng");
            }else {
                headRow4.createCell(12).setCellValue("Tiến độ mua hàng");
            }
            for(int i=0;i<=maxColProgress;i++){
                Cell cellFont = headRow4.getCell(i);

                if (cellFont == null) {
                    cellFont = headRow4.createCell(i);
                }
                CellStyle titleStyle = workbook.createCellStyle();
                titleStyle.setAlignment(HorizontalAlignment.CENTER);
                titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                titleStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
                titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Thiết lập kiểu gạch nền

                Font titleFont = workbook.createFont();
                titleFont.setBold(true);
                titleStyle.setFont(titleFont);
                cellFont.setCellStyle(titleStyle);
            }
            for(int i=5;i<=rowDetail-2;i=i+4){
                Row rowFont1 = sheet.getRow(i);

                for(int j =0;j<=maxColProgress;j++){
                    Cell cellFont1 = rowFont1.getCell(j);
                    CellStyle titleStyle = workbook.createCellStyle();
                    titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Thiết lập kiểu gạch nền
                    cellFont1.setCellStyle(titleStyle);
                    if(j>=11){
                        Row rowFont2 = sheet.getRow(i+1) ;
                        Cell cellFont2 = rowFont2.getCell(j);
                        cellFont2.setCellStyle(titleStyle);
                    }
                }
            }
            workbook.write(out);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=product_records.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
    /**
     * ham lay sach item trong po va tien do cua item
     *
     * @param request
     * @return
     */
    public PageResponse<List<OnOrderWithDurationDetailDTO>> monitoringDTOWithDetail(PageFilterInput<MonitoringFilter> request) {
        MonitoringFilter filter = request.getFilter();
        Pageable pageable = PageRequest.of(0, 10);
        if (request.getPageNumber() >= 0) {
            pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        }
        QSapOnOrderSummary qSapOnOrderSummary = QSapOnOrderSummary.sapOnOrderSummary;
        JPAQuery<OnOrderMonitoringDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QOnOrderMonitoringDTO(
                    qSapOnOrderSummary.id,
                    qSapOnOrderSummary.itemCode,
                    qSapOnOrderSummary.itemName,
                    qSapOnOrderSummary.poCode,
                    qSapOnOrderSummary.lineNumber,
                    qSapOnOrderSummary.dueDate,
                    qSapOnOrderSummary.poCreateDate,
                    qSapOnOrderSummary.createPoUser,
                    qSapOnOrderSummary.type
                )
            )
            .from(qSapOnOrderSummary);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSapOnOrderSummary.type.eq("PO"));
        booleanBuilder.and(qSapOnOrderSummary.sapCode.eq(request.getFilter().getPoCode()));
        booleanBuilder.and(qSapOnOrderSummary.status.eq("O"));

        if (!StringUtils.isEmpty(filter.getItemCode())) {
            booleanBuilder.and(qSapOnOrderSummary.itemCode.containsIgnoreCase(filter.getItemCode()));
        }
        if (!StringUtils.isEmpty(filter.getItemName())) {
            booleanBuilder.and(qSapOnOrderSummary.itemName.containsIgnoreCase(filter.getItemName()));
        }

        query.where(booleanBuilder).groupBy(qSapOnOrderSummary.itemCode).groupBy(qSapOnOrderSummary.prCode).orderBy(qSapOnOrderSummary.poCreateDate.desc());
        if (request.getPageNumber() < 0) {
            query = query.limit(pageable.getPageSize()).offset(pageable.getOffset());
            int count = (int) query.fetchCount();
            pageable = PageRequest.of(0, count);
        }
        query = query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        List<OnOrderMonitoringDTO> result = query.fetch();
        long count = query.fetchCount();

        //group data by po code
        List<OnOrderWithDurationDetailDTO> onOrderWithDurationDetailDTOS = new ArrayList<>();
        for (OnOrderMonitoringDTO onOrderMonitoringDTO : result) {
            List<MonitoringDetailDTO> list = durationDetailRepository.getAllDetailMonitoring(onOrderMonitoringDTO.getId());
            //if not exists in list -> add new dto
            OnOrderWithDurationDetailDTO order = new OnOrderWithDurationDetailDTO(onOrderMonitoringDTO);
            order.setPoCode(filter.getPoCode());
            order.setNote(durationDetailRepository.getNote(onOrderMonitoringDTO.getId()));
            order.setDurationDetail(new ArrayList<>());
            Collections.sort(list);
            order.getDurationDetail().addAll(list);
            //----------------------------------------------------------------------------------------------------------
            //số lượng đã về PO 24600 24657
            Double grpoQuantity = pdn1Repository.getGrpoQuantityByItemCodeAndPoCode(
//                onOrderMonitoringDTO.getItemCode(),
                Integer.valueOf(onOrderMonitoringDTO.getPoCode()),
                onOrderMonitoringDTO.getLineNumber()
            );
            order.setWarehouseQuantity(Objects.requireNonNullElse(grpoQuantity, 0.0));

            //số lượng sắp về
            Double poQuantity = por1Repository.getPoQuantityByItemCodeAndPoCode(
                Integer.valueOf(onOrderMonitoringDTO.getPoCode()),
                onOrderMonitoringDTO.getLineNumber()
            );

            //số lượng yêu cầu
//            Double summary = repository.getQuantityInSap(onOrderMonitoringDTO.getPrCode(), onOrderMonitoringDTO.getItemCode());
            Double summary = por1Repository.getPrQuantityByItemCodeAndPoCode(
                Integer.valueOf(onOrderMonitoringDTO.getPoCode()),
                onOrderMonitoringDTO.getLineNumber()
            );
            if (summary == null || summary == 0) {
                // if po without pr
                order.setQuantity(poQuantity);
            } else {
                order.setQuantity(summary);
            }

            if (poQuantity == null) {
                order.setComingQuantity(0.0);
            } else {
                order.setComingQuantity(poQuantity - order.getWarehouseQuantity());
            }

            //số lượng mua thêm = sl yeu cau - sl da ve - sl sap ve
//            order.setPurchaseQuantity(Math.max(
//                order.getQuantity()
//                    - order.getWarehouseQuantity()
//                    - order.getComingQuantity(), 0.0));
//            if (order.getQuantity() == 0.0) {
//                order.setPercentRate(0.0);
//            } else {
//                order.setPercentRate((order.getWarehouseQuantity() / order.getQuantity()) * 100);
//            }
            onOrderWithDurationDetailDTOS.add(order);
        }

        Collections.sort(onOrderWithDurationDetailDTOS);
        return new PageResponse<List<OnOrderWithDurationDetailDTO>>()
            .result("00", "Thành công", true)
            .data(onOrderWithDurationDetailDTOS)
            .dataCount(count);
    }

    /**
     * ham lay ds tien do theo pr
     *
     * @param request
     * @return
     */
    public PageResponse<List<MonitorListPrDTO>> prListMonitoring(PageFilterInput<prMonitorFilter> request) {
        prMonitorFilter filter = request.getFilter();
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        QSapOnOrderSummary qSapOnOrderSummary = QSapOnOrderSummary.sapOnOrderSummary;
        JPAQuery<MonitorListPrDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QMonitorListPrDTO(
                    qSapOnOrderSummary.id,
                    qSapOnOrderSummary.prCode,
                    qSapOnOrderSummary.soCode,
                    qSapOnOrderSummary.mrpCode,
                    qSapOnOrderSummary.createPoUser,
                    qSapOnOrderSummary.poCreateDate
                )
            ).distinct()
            .from(qSapOnOrderSummary);
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSapOnOrderSummary.status.eq("O"));
        booleanBuilder.and(qSapOnOrderSummary.type.eq("PR"));

        if (!StringUtils.isEmpty(filter.getMrpCode())) {
            booleanBuilder.and(qSapOnOrderSummary.mrpCode.containsIgnoreCase(filter.getMrpCode()));
        }
        if (!StringUtils.isEmpty(filter.getPrCode())) {
            booleanBuilder.and(qSapOnOrderSummary.prCode.containsIgnoreCase(filter.getPrCode()));
        }
        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qSapOnOrderSummary.soCode.containsIgnoreCase(filter.getSoCode()));
        }
        if (!StringUtils.isEmpty(filter.getPrCreateUser())) {
            booleanBuilder.and(qSapOnOrderSummary.createPoUser.containsIgnoreCase(filter.getPrCreateUser()));
        }
        if (filter.getPrCreateDate() != null) {
            booleanBuilder.and(qSapOnOrderSummary.poCreateDate.eq(filter.getPrCreateDate()));
        }

        query.where(booleanBuilder).groupBy(qSapOnOrderSummary.prCode).orderBy(qSapOnOrderSummary.poCreateDate.desc());
        List<MonitorListPrDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<MonitorListPrDTO>>()
            .result("00", "Thành công", true)
            .data(result)
            .dataCount(count);
    }

    /**
     * hàm lấy danh sách tiến độ vật tư của pr
     *
     * @param request
     * @param prCode
     * @return
     */
    public PageResponse<List<ItemListInPrDTO>> prListDetailMonitoring(PageFilterInput<prMonitorFilter> request, String prCode) {
        prMonitorFilter filter = request.getFilter();
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        QSapOnOrderSummary qSapOnOrderSummary = QSapOnOrderSummary.sapOnOrderSummary;
        JPAQuery<ItemListInPrDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QItemListInPrDTO(
                    qSapOnOrderSummary.id,
                    qSapOnOrderSummary.prCode,
                    qSapOnOrderSummary.soCode,
                    qSapOnOrderSummary.mrpCode,
                    qSapOnOrderSummary.itemCode,
                    qSapOnOrderSummary.itemName,
                    qSapOnOrderSummary.dueDate,
                    qSapOnOrderSummary.quantity
                )
            )
            .from(qSapOnOrderSummary);
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize()).offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSapOnOrderSummary.type.eq("PR"));
        booleanBuilder.and(qSapOnOrderSummary.prCode.eq(prCode));
        booleanBuilder.and(qSapOnOrderSummary.status.eq("O"));

        if (!StringUtils.isEmpty(filter.getMrpCode())) {
            booleanBuilder.and(qSapOnOrderSummary.mrpCode.containsIgnoreCase(filter.getMrpCode()));
        }
        if (!StringUtils.isEmpty(filter.getSoCode())) {
            booleanBuilder.and(qSapOnOrderSummary.soCode.containsIgnoreCase(filter.getSoCode()));
        }
        if (!StringUtils.isEmpty(filter.getItemCode())) {
            booleanBuilder.and(qSapOnOrderSummary.itemCode.containsIgnoreCase(filter.getItemCode()));
        }
        if (!StringUtils.isEmpty(filter.getItemName())) {
            booleanBuilder.and(qSapOnOrderSummary.itemName.containsIgnoreCase(filter.getItemName()));
        }
        query.where(booleanBuilder).orderBy(qSapOnOrderSummary.poCreateDate.desc());
        List<ItemListInPrDTO> result = query.fetch();
        long count = query.fetchCount();
        for (ItemListInPrDTO item : result) {
            List<Integer> listFk = repository.getListFk(item.getPrCode(), item.getItemCode());
            List<MonitoringDetailDTO> listSap = repository.getListPo(item.getPrCode(), item.getItemCode());
            item.setMonitorOnSap(new ArrayList<>());
            Collections.sort(listSap);
            item.getMonitorOnSap().addAll(listSap);
            List<MonitoringDetailDTO> list = durationDetailRepository.getListMonitorWithPo(listFk);
            item.setMonitorOnMrp(new ArrayList<>());
            Collections.sort(list);
            item.getMonitorOnMrp().addAll(list);
            item.setSumPoInMrp(sumPo(list));
            item.setSumPoInSap(sumPo(listSap));
        }
        return new PageResponse<List<ItemListInPrDTO>>()
            .result("00", "Thành công", true)
            .data(result)
            .dataCount(count);
    }

    public Integer sumPo(List<MonitoringDetailDTO> list) {
        List<String> listPo = new ArrayList<>();
        for (MonitoringDetailDTO item : list
        ) {
            if (!listPo.contains(item.getPoCode())) {
                listPo.add(item.getPoCode());
            }
        }
        return listPo.size();
    }

    public void addMonitoringDueDate(AddMonitoringRequest request) {
        if (CollectionUtils.isEmpty(request.getPoList())) {
            throw new CustomException("po.list.can.not.empty");
        }

        request.getPoList().forEach(internalRequest -> {
            //get on order summary record
            SapOnOrderSummary tmp = repository.findSapOnOrderSummaryByPoCodeAndItemCodeAndType(
                internalRequest.getPoCode(), internalRequest.getItemCode(), Constants.PO_TYPE);
            if (tmp != null) {
                SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                entity.setSapOnOrderSummary(tmp);
                entity.setDueDate(request.getDueDate());
                entity.setQuantity(request.getQuantity());
                entity.setNote(request.getNote());
                durationDetailRepository.save(entity);
            }
        });
    }

    /**
     * hàm insert hoặc update các item theo nhiều mốc tiến độ
     *
     * @param request
     * @return
     * @throws ParseException
     */
    @Transactional
    public String addMultipleMonitoringDueDate(List<OnOrderWithDurationDetailDTO> request) throws ParseException {
        List<SapOnOrderDurationDetail> listEntity = new ArrayList<>();
        for (OnOrderWithDurationDetailDTO item : request
        ) {
            List<SapOnOrderDurationDetail> listCheck = new ArrayList<>();
            SapOnOrderSummary tmp = repository.findSapOnOrderSummaryByPoCodeAndItemCodeAndType(
                item.getPoCode(), item.getItemCode(), Constants.PO_TYPE);
            if (tmp != null) {
                if(item.getDurationDetail() != null){
                    List<MonitoringDetailDTO> list = item.getDurationDetail();
                    for (MonitoringDetailDTO mdd : list
                    ) {
                        //chỉ cập nhật các bản ghi có id khác null
                        if (mdd.getId() != null) {
                            if (mdd.getDueDate() == null && mdd.getQuantity() == null) {
                                SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                                entity.setSapOnOrderSummary(tmp);
                                entity.setId(mdd.getId());
                                entity.setDueDate(mdd.getDueDate());
                                entity.setQuantity(mdd.getQuantity());
                                entity.setNote(mdd.getNote());
                                entity.setIsActive(false);
                                listEntity.add(entity);
                            } else {
                                SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                                entity.setSapOnOrderSummary(tmp);
                                entity.setId(mdd.getId());
                                entity.setDueDate(mdd.getDueDate());
                                entity.setQuantity(mdd.getQuantity());
                                entity.setNote(mdd.getNote());
                                entity.setIsActive(true);
                                listCheck.add(entity);
                                listEntity.add(entity);
                            }
                            //chỉ insert các bản ghi có id bang null và note khac sap
                        } else if ((mdd.getId() == null) && (mdd.getQuantity() != null) && (mdd.getDueDate() != null)) {
                            SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                            entity.setSapOnOrderSummary(tmp);
                            entity.setDueDate(mdd.getDueDate());
                            entity.setQuantity(mdd.getQuantity());
                            entity.setNote(mdd.getNote());
                            entity.setIsActive(true);
                            listEntity.add(entity);
                            listCheck.add(entity);
                            System.out.println("--------------------------------"+entity.getDueDate());
                        }
                    }
                }

            }
            Boolean check = checkDate(listCheck);
            if (check == true) {
                throw new CustomException("time.not.exist");
            }
        }
        durationDetailRepository.saveAll(listEntity);
        return "thành công!";
    }

    /**
     * hàm check date kiểm tra xem các mốc thờ gian có trùng nhau không
     *
     * @param listCheck
     * @return
     * @throws ParseException
     */
    private Boolean checkDate(List<SapOnOrderDurationDetail> listCheck) throws ParseException {
        for (int i = 0; i < listCheck.size() - 1; i++) {
            for (int j = i + 1; j < listCheck.size(); j++) {
                if (listCheck.get(i).getId() != null && listCheck.get(j).getId() != null && LocalDate.ofInstant(listCheck.get(j).getDueDate().toInstant(), ZoneId.systemDefault()).equals(LocalDate.ofInstant(listCheck.get(i).getDueDate().toInstant(), ZoneId.systemDefault()))) {
                    listCheck.remove(i);
                }
            }
        }
        for (int i = 0; i < listCheck.size() - 1; i++) {
            for (int j = i + 1; j < listCheck.size(); j++) {
                LocalDate date2 = LocalDate.ofInstant(listCheck.get(j).getDueDate().toInstant(), ZoneId.systemDefault());
                LocalDate date1 = LocalDate.ofInstant(listCheck.get(i).getDueDate().toInstant(), ZoneId.systemDefault());
                if (date1.equals(date2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * hàm insert các mốc thời gian theo tiến độ
     *
     * @param request
     * @return
     */
    @Transactional
    public String addListMonitoringDueDate(ListMonitoringRequest request) {
        if (CollectionUtils.isEmpty(request.getPoList())) {
            throw new CustomException("po.list.can.not.empty");
        }
        List<SapOnOrderDurationDetail> listData = new ArrayList<>();
        request.getPoList().forEach(listInternalRequest -> {
            //get on order summary record
            for (String item : listInternalRequest.getItemCode()
            ) {
                SapOnOrderSummary tmp = repository.findSapOnOrderSummaryByPoCodeAndItemCodeAndType(
                    listInternalRequest.getPoCode(), item, Constants.PO_TYPE);
                if (tmp != null) {
                    SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                    entity.setSapOnOrderSummary(tmp);
                    entity.setDueDate(request.getDueDate());
                    entity.setQuantity(request.getQuantity());
                    entity.setNote(request.getNote());
                    entity.setIsActive(true);
                    listData.add(entity);
                }
            }
        });
        durationDetailRepository.saveAll(listData);
        return "OK";
    }

    /**
     * hàm insert hoặc update các item theo nhiều mốc tiến độ
     *
     * @param request
     * @return
     * @throws ParseException
     */
    @Transactional
    public String addMultipleMonitoringByItem(List<AnalysisDetailReport> request) throws ParseException {

        List<SapOnOrderDurationDetail> listEntity = new ArrayList<>();

        for (AnalysisDetailReport item : request
        ) {
            List<SapOnOrderDurationDetail> listCheck = new ArrayList<>();
            SapOnOrderSummary tmp = repository.findSapOnOrderSummaryByMrpCodeAndItemCodeAndType(
                item.getId());
            if (tmp != null) {
                List<MonitoringDetailDTO> list = item.getDurationDetail();
                for (MonitoringDetailDTO mdd : list
                ) {
                    //chỉ cập nhật các bản ghi có id khác null
                    if (mdd.getId() != null) {
                        if (mdd.getDueDate() == null && mdd.getQuantity() == null) {
                            SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                            entity.setSapOnOrderSummary(tmp);
                            entity.setId(mdd.getId());
                            entity.setDueDate(mdd.getDueDate());
                            entity.setQuantity(mdd.getQuantity());
                            entity.setNote(mdd.getNote());
                            entity.setIsActive(false);
                            listEntity.add(entity);
                        } else {
                            SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                            entity.setSapOnOrderSummary(tmp);
                            entity.setId(mdd.getId());
                            entity.setDueDate(mdd.getDueDate());
                            entity.setQuantity(mdd.getQuantity());
                            entity.setNote(mdd.getNote());
                            entity.setIsActive(true);
                            listCheck.add(entity);
                            listEntity.add(entity);
                        }
                        //chỉ insert các bản ghi có id bang null và note khac sap
                    } else if ((mdd.getId() == null) && (mdd.getQuantity() != null) && (mdd.getDueDate() != null)) {
                        SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                        entity.setSapOnOrderSummary(tmp);
                        entity.setDueDate(mdd.getDueDate());
                        entity.setQuantity(mdd.getQuantity());
                        entity.setNote(mdd.getNote());
                        entity.setIsActive(true);
                        listEntity.add(entity);
                        listCheck.add(entity);
                    }
                }
            }
            Boolean check = checkDate(listCheck);
            if (check == true) {
                throw new CustomException("time.not.exist");
            }
        }
        durationDetailRepository.saveAll(listEntity);
        return "thành công!";
    }

    /**
     * hàm lấy danh sách kế hoạch
     *
     * @param input
     * @return
     */
    public PageResponse<List<DurationPlanDTO>> getAllDurationPlan(PageFilterInput<DurationPlanDTO> input) {
        Pageable pageable;
        if (input.getPageSize() == 0) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        }
        QDurationPlanEntity qDurationPlanEntity = QDurationPlanEntity.durationPlanEntity;
        JPAQuery<DurationPlanDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QDurationPlanDTO(
                    qDurationPlanEntity.id,
                    qDurationPlanEntity.planCode,
                    qDurationPlanEntity.planName,
                    qDurationPlanEntity.quantityItem,
                    qDurationPlanEntity.listItem
                )
            )
            .from(qDurationPlanEntity);
        if (pageable.isPaged()) {
            query = query.limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(Expressions.TRUE.isTrue());
        booleanBuilder.and(qDurationPlanEntity.isActive.eq(true));
        if (!StringUtils.isEmpty(input.getFilter().getPlanCode())) {
            booleanBuilder.and(qDurationPlanEntity.planCode.containsIgnoreCase(input.getFilter().getPlanCode()));
        }
        if (!StringUtils.isEmpty(input.getFilter().getPlanName())) {
            booleanBuilder.and(qDurationPlanEntity.planName.containsIgnoreCase(input.getFilter().getPlanName()));
        }
        query.where(booleanBuilder).orderBy(qDurationPlanEntity.id.desc());
        List<DurationPlanDTO> result = query.fetch();
        long count = query.fetchCount();
        return new PageResponse<List<DurationPlanDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(count)
            .data(result);
    }

    /**
     * hàm search theo item kê hoạch thêm mới
     *
     * @param input
     * @return
     */
    public PageResponse<List<ItemInPlanDurationDTO>> getAllItemDurationPlan(String input) {
        if (StringUtils.isEmpty(input)) {
            throw new CustomException("list.item.plan");
        }
        List<String> listItem = new ArrayList<>(List.of(input.split(";")));
        if (listItem.size() == 0) {
            throw new CustomException("list.item.plan");
        }
        QSapOnOrderSummary qSapOnOrderSummary = QSapOnOrderSummary.sapOnOrderSummary;
        JPAQuery<ItemInPlanDurationDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QItemInPlanDurationDTO(
                    qSapOnOrderSummary.id,
                    qSapOnOrderSummary.prCode,
                    qSapOnOrderSummary.soCode,
                    qSapOnOrderSummary.mrpCode,
                    qSapOnOrderSummary.itemCode,
                    qSapOnOrderSummary.itemName,
                    qSapOnOrderSummary.providerCode,
                    qSapOnOrderSummary.providerName,
                    qSapOnOrderSummary.quantity,
                    qSapOnOrderSummary.dueDate
                )
            )
            .from(qSapOnOrderSummary);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSapOnOrderSummary.status.eq("O"));
        booleanBuilder.and(qSapOnOrderSummary.type.eq("PR"));
//        booleanBuilder.and(qSapOnOrderSummary.poCode.isNotNull());
        booleanBuilder.and(qSapOnOrderSummary.itemCode.in(listItem));
        query.where(booleanBuilder);
        List<ItemInPlanDurationDTO> result = query.fetch();
        long count = query.fetchCount();
        if (count == 0) {
            return new PageResponse<List<ItemInPlanDurationDTO>>()
                .errorCode("00")
                .message("Không có mã vật tư nào có PR")
                .isOk(false);
        }
        for (ItemInPlanDurationDTO item : result) {
            if (listItem.contains(item.getItemCode())) {
                listItem.remove(item.getItemCode());
            }
            List<Integer> listFk = repository.getListFkPO(item.getPrCode(), item.getItemCode());
            List<MonitoringDetailDTO> list = durationDetailRepository.getListMonitorWithPo(listFk);
            item.setMonitorOnMrp(new ArrayList<>());
            Collections.sort(list);
            item.getMonitorOnMrp().addAll(list);
            Double sumQuantityPo = 0.0;
            for (MonitoringDetailDTO index : item.getMonitorOnMrp()) {
                sumQuantityPo += index.getQuantity();
            }
            item.setPercent((int) ((sumQuantityPo / item.getRequiredQuantity()) * 100));
            item.setSumPo(sumPo(list));
        }
        if (listItem.size() > 0) {
            StringBuilder mess = new StringBuilder();
            int dem = 0;
            for (String index : listItem) {
                dem++;
                if (dem > 1) {
                    mess.append(";").append(index);
                } else {
                    mess.append(index);
                }
            }
            return new PageResponse<List<ItemInPlanDurationDTO>>()
                .errorCode("00")
                .message("Những mã vật tư này không có PR: " + mess)
                .isOk(false)
                .dataCount(count)
                .data(result);

        }

        return new PageResponse<List<ItemInPlanDurationDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(count)
            .data(result);
    }

    /**
     * hàm search
     *
     * @param input
     * @return
     */
    public PageResponse<List<ItemInPlanDurationWithPoDTO>> getAllItemDurationPlanWithPo(List<ItemInPlanDurationDTO> input) {
        List<ItemInPlanDurationWithPoDTO> dtoList = new ArrayList<>();
        List<String> itemList = new ArrayList<>();
        long sumCount = 0;
        for (ItemInPlanDurationDTO index : input
        ) {
            itemList.add(index.getItemCode());
            QSapOnOrderSummary qSapOnOrderSummary = QSapOnOrderSummary.sapOnOrderSummary;
            JPAQuery<ItemInPlanDurationWithPoDTO> query = new JPAQueryFactory(entityManager)
                .select(
                    new QItemInPlanDurationWithPoDTO(
                        qSapOnOrderSummary.id,
                        qSapOnOrderSummary.prCode,
                        qSapOnOrderSummary.soCode,
                        qSapOnOrderSummary.mrpCode,
                        qSapOnOrderSummary.poCode,
                        qSapOnOrderSummary.itemCode,
                        qSapOnOrderSummary.itemName,
                        qSapOnOrderSummary.providerCode,
                        qSapOnOrderSummary.providerName,
                        qSapOnOrderSummary.dueDate
                    )
                )
                .from(qSapOnOrderSummary);
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(Expressions.TRUE.isTrue());
            booleanBuilder.and(qSapOnOrderSummary.status.eq("O"));
            booleanBuilder.and(qSapOnOrderSummary.type.eq("PO"));
            booleanBuilder.and(qSapOnOrderSummary.itemCode.eq(index.getItemCode()));
            booleanBuilder.and(qSapOnOrderSummary.prCode.eq(index.getPrCode()));
            query.where(booleanBuilder);

            List<ItemInPlanDurationWithPoDTO> result = query.fetch();
            long count = query.fetchCount();
            sumCount += count;

            for (ItemInPlanDurationWithPoDTO item : result) {
                if (itemList.contains(item.getItemCode())) {
                    itemList.remove(item.getItemCode());
                }
                List<MonitoringDetailDTO> list = durationDetailRepository.getAllDetailMonitoring(item.getId());
                Collections.sort(list);
                item.setMonitoringDetailDTOS(new ArrayList<>());
                item.getMonitoringDetailDTOS().addAll(list);
                item.setNote(durationDetailRepository.getNote(item.getId()));
                //số lượng yêu cầu
                Double summary = repository.getQuantityInSap(item.getPrCode(), item.getItemCode());
                if (summary == null) {
                    item.setRequiredQuantity(0.0);
                } else {
                    item.setRequiredQuantity(summary);
                }
                //số lượng đã về
                Double grpoQuantity = repository.getTotalGrpo(item.getItemCode(), item.getPrCode(), item.getPoCode());
                if (grpoQuantity == null) {
                    item.setArrivedQuantity(0.0);
                } else {
                    item.setArrivedQuantity(grpoQuantity);
                }
                //số lượng sắp về
                Double poQuantity = repository.getTotalPo(item.getItemCode(), item.getPoCode());
                if (poQuantity == null) {
                    item.setNotArrivedQuantity(0.0);
                } else {
                    item.setNotArrivedQuantity(poQuantity - item.getArrivedQuantity());
                }
                //số lượng mua thêm

                item.setEstimateQuantity(item.getRequiredQuantity() - item.getArrivedQuantity() - item.getNotArrivedQuantity());
                if (item.getRequiredQuantity() == 0.0) {
                    item.setPercent(0);
                } else {
                    item.setPercent((int) ((item.getArrivedQuantity() / item.getRequiredQuantity()) * 100));
                }
            }
            dtoList.addAll(result);
        }
        if (sumCount == 0) {
            return new PageResponse<List<ItemInPlanDurationWithPoDTO>>()
                .errorCode("00")
                .message("không có mã vật tư nào có PO")
                .isOk(false);
        }
        if (itemList.size() > 0) {
            String mess = "";
            int dem = 0;
            for (String index : itemList
            ) {
                dem++;
                if (dem > 1) {
                    mess += ";" + index;
                } else {
                    mess += index;
                }
            }
            return new PageResponse<List<ItemInPlanDurationWithPoDTO>>()
                .errorCode("00")
                .message("Những mã vật tư này không có PO: " + mess)
                .isOk(false)
                .dataCount(sumCount)
                .data(dtoList);
        }

        return new PageResponse<List<ItemInPlanDurationWithPoDTO>>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .dataCount(sumCount)
            .data(dtoList);
    }

    /**
     * insert hoặc cập nhật nhiều bản ghi
     *
     * @param planDuration
     * @param request
     * @return
     * @throws ParseException
     */
    @Transactional
    public String addMultipleMonitoringByItem(String planDuration, List<ItemInPlanDurationWithPoDTO> request) throws ParseException {
        List<SapOnOrderDurationDetail> listEntity = new ArrayList<>();
        for (ItemInPlanDurationWithPoDTO item : request
        ) {
            SapOnOrderSummary tmp = repository.findSapOnOrderSummaryByMrpCodeAndItemCodeAndType(
                item.getId());
            if (tmp != null) {
                List<MonitoringDetailDTO> list = item.getMonitoringDetailDTOS();
                List<SapOnOrderDurationDetail> listCheck = new ArrayList<>();
                if (list != null) {
                    for (MonitoringDetailDTO mdd : list
                    ) {
                        //chỉ cập nhật các bản ghi có id khác null
                        if (mdd.getId() != null) {
                            if (mdd.getDueDate() == null && mdd.getQuantity() == null) {
                                SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                                entity.setSapOnOrderSummary(tmp);
                                entity.setId(mdd.getId());
                                entity.setDueDate(mdd.getDueDate());
                                entity.setQuantity(mdd.getQuantity());
                                entity.setNote(mdd.getNote());
                                entity.setIsActive(false);
                                listEntity.add(entity);
                            } else {
                                SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                                entity.setSapOnOrderSummary(tmp);
                                entity.setId(mdd.getId());
                                entity.setDueDate(mdd.getDueDate());
                                entity.setQuantity(mdd.getQuantity());
                                entity.setNote(mdd.getNote());
                                entity.setIsActive(true);
                                listCheck.add(entity);
                                listEntity.add(entity);
                            }
                            //chỉ insert các bản ghi có id bang null và note khac sap
                        } else if ((mdd.getId() == null) && (mdd.getQuantity() != null) && (mdd.getDueDate() != null)) {
                            SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                            entity.setSapOnOrderSummary(tmp);
                            entity.setDueDate(mdd.getDueDate());
                            entity.setQuantity(mdd.getQuantity());
                            entity.setNote(mdd.getNote());
                            entity.setIsActive(true);
                            listEntity.add(entity);
                            listCheck.add(entity);
                        }
                    }
                }
                Boolean check = checkDate(listCheck);
                if (check == true) {
                    return "Không được để ngày các mốc trùng nhau";
                }
            }
        }
        durationDetailRepository.saveAll(listEntity);
        return "thành công!";
    }

    /**
     * cập nhật nhiều bản ghi
     *
     * @param planDuration
     * @param request
     * @return
     */
    @Transactional
    public String addListMonitoringDueDateByItem(String planDuration, AddMonitoringItemRequest request) {

        List<SapOnOrderDurationDetail> listData = new ArrayList<>();
        for (ItemInPlanDurationWithPoDTO item : request.getData()
        ) {
            SapOnOrderSummary tmp = repository.findSapOnOrderSummaryById(
                item.getId());
            if (tmp != null) {
                SapOnOrderDurationDetail entity = new SapOnOrderDurationDetail();
                entity.setSapOnOrderSummary(tmp);
                entity.setDueDate(request.getDueDate());
                entity.setQuantity(request.getQuantity());
                entity.setNote(request.getNote());
                entity.setIsActive(true);
                listData.add(entity);
            }
        }
        durationDetailRepository.saveAll(listData);
        return "OK";
    }

    /**
     * new plan duration
     *
     * @return
     */
    public PageResponse<String> newDurationPlan(NewDurationPlanDTO request) {
        if (StringUtils.isEmpty(request.getListItem())) {
            throw new CustomException("list.item.plan");
        }
        Integer count = durationDetailRepository.countAll() + 1;
        String planDurationCode = "KH-" + count;
        DurationPlanEntity durationPlan = new DurationPlanEntity();
        durationPlan.setPlanCode(planDurationCode);
        durationPlan.setPlanName(request.getPlanName());
        durationPlan.setListItem(request.getListItem());
        durationPlan.setQuantityItem(request.getListItem().split(";").length);
        durationPlan.setIsActive(true);
        DurationPlanEntity result = durationPlanRepository.save(durationPlan);
        if (result == null) {
            return new PageResponse<String>()
                .errorCode("00")
                .message("Thất bại khi thêm mới kế hoạch")
                .isOk(true);
        }
        return new PageResponse<String>()
            .errorCode("00")
            .message("Thành công")
            .isOk(true)
            .data(planDurationCode);
    }

    @Transactional
    public PageResponse deleteDurationPlan(String planCode) {
        Integer result = durationPlanRepository.deleteDurationPlanEntity(planCode);
        if (result == 1) {
            return new PageResponse<String>()
                .errorCode("00")
                .message("Thành công")
                .isOk(true);
        }
        return new PageResponse<String>()
            .errorCode("00")
            .message("Xóa thất bại")
            .isOk(false);
    }

    public byte[] exportToExcelPr() {
        List<MonitorListPrDTO> data = prListMonitoring();
        String[] columns = {"Mã PR", "Mã SO/FC", "Mã chạy MRP", "Người tạo", "Ngày tạo PR", "Ngày phê duyệt", "Trạng thái"};
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách tiến độ mua hàng theo PR");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Write data rows
            int rowNum = 1;
            for (MonitorListPrDTO prDTO : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(prDTO.getPrCode());
                row.createCell(1).setCellValue(prDTO.getSoCode());
                row.createCell(2).setCellValue(prDTO.getMrpCode());
                row.createCell(3).setCellValue(prDTO.getPrCreateUser());
                if (prDTO.getPrCreateDate() != null) {
                    row.createCell(4).setCellValue(dateFormat.format(prDTO.getPrCreateDate()));
                } else {
                    row.createCell(4).setCellValue("");
                }
                if (prDTO.getAsignDate() != null) {
                    row.createCell(5).setCellValue(dateFormat.format(prDTO.getAsignDate()));
                } else {
                    row.createCell(5).setCellValue("");
                }
                row.createCell(6).setCellValue(prDTO.getStatus());
            }

            // Resize columns to fit content
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convert workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MonitorListPrDTO> prListMonitoring() {
        QSapOnOrderSummary qSapOnOrderSummary = QSapOnOrderSummary.sapOnOrderSummary;
        JPAQuery<MonitorListPrDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QMonitorListPrDTO(
                    qSapOnOrderSummary.id,
                    qSapOnOrderSummary.prCode,
                    qSapOnOrderSummary.soCode,
                    qSapOnOrderSummary.mrpCode,
                    qSapOnOrderSummary.createPoUser,
                    qSapOnOrderSummary.poCreateDate
                )
            ).distinct()
            .from(qSapOnOrderSummary);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSapOnOrderSummary.status.eq("O"));
        booleanBuilder.and(qSapOnOrderSummary.type.eq("PR"));
        query.where(booleanBuilder).groupBy(qSapOnOrderSummary.prCode).orderBy(qSapOnOrderSummary.poCreateDate.desc());
        List<MonitorListPrDTO> result = query.fetch();
        return result;
    }

    public List<OnOrderMonitoringDTO> monitoringDTOList() {
        QSapOnOrderSummary qSapOnOrderSummary = QSapOnOrderSummary.sapOnOrderSummary;
        JPAQuery<OnOrderMonitoringDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QOnOrderMonitoringDTO(
                    qSapOnOrderSummary.poCode,
                    qSapOnOrderSummary.prCode,
                    qSapOnOrderSummary.contractNumber,
                    qSapOnOrderSummary.providerCode,
                    qSapOnOrderSummary.providerName,
                    qSapOnOrderSummary.dueDate,
                    qSapOnOrderSummary.poCreateDate,
                    qSapOnOrderSummary.createPoUser,
                    qSapOnOrderSummary.type,
                    qSapOnOrderSummary.mrpCode,
                    qSapOnOrderSummary.soCode
                )
            )
            .from(qSapOnOrderSummary);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qSapOnOrderSummary.type.eq("PO"));
        booleanBuilder.and(qSapOnOrderSummary.status.eq("O"));

        query.where(booleanBuilder).groupBy(qSapOnOrderSummary.poCode).orderBy(qSapOnOrderSummary.poCreateDate.desc());
        List<OnOrderMonitoringDTO> result = query.fetch();
        long count = query.fetchCount();
        Double sumPO;
        Double sumGrpo;
        for (OnOrderMonitoringDTO item : result) {
            sumPO = 0.0;
            sumGrpo = 0.0;
            List<SapOnOrderSummary> list = repository.getAllByPo(item.getPoCode());
            for (SapOnOrderSummary index : list) {
                if (index.getType().equals("PO")) {
                    sumPO += index.getQuantity();
                } else {
                    sumGrpo += index.getQuantity();
                }
            }
            item.setOrderProgressPercent((int) ((sumGrpo / sumPO) * 100));
            SapOnOrderSummary sap = repository.getPr(item.getPrCode());
            if (sap != null) {
                long diffInMillies = Math.abs(item.getPoCreateDate().getTime() - sap.getPoCreateDate().getTime());
                Integer diff = Math.toIntExact(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS));
                item.setOverDateNumber(diff);
            }
            Integer percent = item.getOrderProgressPercent();
            item.setState("Đúng hạn");
            if (percent != null && percent >= 100) {
                if (item.getPoDueDate() != null && item.getPoDueDate().after(new Date())) {
                    item.setState("Sớm hạn");
                } else {
                    item.setState("Đúng hạn");
                }
            } else if (item.getPoDueDate() != null && item.getPoDueDate().after(new Date())) {
                item.setState("Đúng hạn");
            } else if (item.getPoDueDate() != null) {
                item.setState("Quá hạn");
            }
        }
        return result;
    }

    public byte[] exportToExcelPO() {
        List<OnOrderMonitoringDTO> data = monitoringDTOList();
        String[] columns = {"Mã PR","Mã PO", "Mã SO/FC", "Mã chạy MRP", "Số hợp đồng", "Mã NCC", "Tên NCC", "Ngày tạo PO", "Người tạo", "Ngày về dự kiến", "Tiến độ mua hàng", "Số ngày quá hạn", "Trạng thái"};
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách tiến độ mua hàng theo PO");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Write data rows
            int rowNum = 1;
            for (OnOrderMonitoringDTO poDTO : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(poDTO.getPrCode());
                row.createCell(1).setCellValue(poDTO.getPoCode());
                row.createCell(2).setCellValue(poDTO.getSoCode());
                row.createCell(3).setCellValue(poDTO.getMrpCode());
                row.createCell(4).setCellValue(poDTO.getContractNumber());
                row.createCell(5).setCellValue(poDTO.getVendorCode());
                row.createCell(6).setCellValue(poDTO.getVendorName());
                if (poDTO.getPoCreateDate() != null) {
                    row.createCell(7).setCellValue(dateFormat.format(poDTO.getPoCreateDate()));
                } else {
                    row.createCell(7).setCellValue("");
                }
                row.createCell(8).setCellValue(poDTO.getPoCreateUser());
                if (poDTO.getPoDueDate() != null) {
                    row.createCell(9).setCellValue(dateFormat.format(poDTO.getPoDueDate()));
                } else {
                    row.createCell(9).setCellValue("");
                }
                row.createCell(10).setCellValue(poDTO.getOrderProgressPercent());
                if (poDTO.getOrderProgressPercent() != null) {
                    row.createCell(10).setCellValue(poDTO.getOrderProgressPercent()+"%");
                } else {
                    row.createCell(10).setCellValue("");
                }
                if (poDTO.getOverDateNumber() != null) {
                    row.createCell(11).setCellValue(poDTO.getOverDateNumber());
                } else {
                    row.createCell(11).setCellValue("");
                }
                row.createCell(12).setCellValue(poDTO.getState());
            }

            // Resize columns to fit content
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convert workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DurationPlanDTO> getAllDurationPlan() {
        QDurationPlanEntity qDurationPlanEntity = QDurationPlanEntity.durationPlanEntity;
        JPAQuery<DurationPlanDTO> query = new JPAQueryFactory(entityManager)
            .select(
                new QDurationPlanDTO(
                    qDurationPlanEntity.id,
                    qDurationPlanEntity.planCode,
                    qDurationPlanEntity.planName,
                    qDurationPlanEntity.quantityItem,
                    qDurationPlanEntity.listItem
                )
            )
            .from(qDurationPlanEntity);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(Expressions.TRUE.isTrue());
        booleanBuilder.and(qDurationPlanEntity.isActive.eq(true));
        query.where(booleanBuilder).orderBy(qDurationPlanEntity.id.desc());
        List<DurationPlanDTO> result = query.fetch();
        return result;
    }

    public byte[] exportToExcelDuration() {
        List<DurationPlanDTO> data = getAllDurationPlan();
        String[] columns = {"Mã kế hoạch","Mô tả kế hoạch", "Số lượng vật tư trong kế hoạch"};
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Danh sách tiến độ mua hàng theo PO");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Write data rows
            int rowNum = 1;
            for (DurationPlanDTO DTO : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(DTO.getPlanCode());
                row.createCell(1).setCellValue(DTO.getPlanName());
                if (DTO.getQuantityItem() != null) {
                    row.createCell(2).setCellValue(DTO.getQuantityItem());
                } else {
                    row.createCell(2).setCellValue("");
                }
            }

            // Resize columns to fit content
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convert workbook to byte array
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
