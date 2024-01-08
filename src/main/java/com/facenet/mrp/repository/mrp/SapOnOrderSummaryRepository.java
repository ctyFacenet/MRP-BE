package com.facenet.mrp.repository.mrp;

import com.facenet.mrp.domain.mrp.SapOnOrderSummary;
import com.facenet.mrp.service.dto.mrp.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SapOnOrderSummaryRepository extends JpaRepository<SapOnOrderSummary,Integer> {
    SapOnOrderSummary findBySapCodeAndTypeAndLineNumber(String sapCode, String type, Integer lineNumber);
    List<SapOnOrderSummary> findBySapCodeInAndItemCodeInAndType(List<String> sapCode, List<String> itemCode,String type);
    List<SapOnOrderSummary> findAllByStatus(String status);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(DATE_FORMAT(dueDate , '%Y-%m-%d'), itemCode, sum(quantity), type) " +
        "from SapOnOrderSummary" +
        " where dueDate is not null" +
        " and dueDate >= :timeStart and dueDate <= :timeEnd" +
        " and itemCode in (:listItemCode)" +
        " and type = :type" +
        " and status = 'O'" +
        " group by DATE_FORMAT(dueDate , '%Y-%m-%d'), itemCode")
    List<ItemQuantityWithDate> getAllQuantityByDay(
        @Param("timeStart") Date timeStart,
        @Param("timeEnd")Date timeEnd,
        @Param("listItemCode")List<String> listItemCode
//        @Param("type") String type
    );

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithType(itemCode, sum(quantity), type) " +
        "from SapOnOrderSummary" +
        " where dueDate is not null" +
//        " and dueDate >= :timeStart and dueDate <= :timeEnd" +
        " and itemCode in (:listItemCode)" +
        " and status = 'O'" +
        " group by itemCode, type")
    List<ItemQuantityWithType> getAllQuantityByItemAndType(
//        @Param("timeStart") Date timeStart,
//        @Param("timeEnd")Date timeEnd,
        @Param("listItemCode")List<String> listItemCode
    );

    default Map<String, Map<String, Double>> getAllSumQuantityByItemsMap(Date timeStart, Date timeEnd, List<String> items) {
        List<ItemQuantityWithType> itemQuantityList = getAllQuantityByItemAndType(
//            timeStart, timeEnd,
            items);
        Map<String, Map<String, Double>> typeMap = new HashMap<>();
        for (ItemQuantityWithType itemQuantity : itemQuantityList) {
            typeMap.putIfAbsent(itemQuantity.getType(), new HashMap<>());
            Map<String, Double> itemCodeMap = typeMap.get(itemQuantity.getType());
            itemCodeMap.putIfAbsent(itemQuantity.getItemCode(), itemQuantity.getQuantity());
        }
        return typeMap;
    }

    default Map<String, Map<String, List<ItemQuantityWithDate>>> getAllPOSumQuantityByItemsMap(Date timeStart, Date timeEnd, List<String> items) {
//        return getAllQuantityByDay(timeStart, timeEnd, items, Constants.PO_TYPE).stream().collect(
//            Collectors.groupingBy(ItemQuantityWithDate::getItemCode));
        List<ItemQuantityWithDate> itemQuantityList = getAllQuantityByDay(timeStart, timeEnd, items);
        Map<String, Map<String, List<ItemQuantityWithDate>>> typeMap = new HashMap<>();
        for (ItemQuantityWithDate itemQuantity : itemQuantityList) {
            typeMap.putIfAbsent(itemQuantity.getType(), new HashMap<>());
            Map<String, List<ItemQuantityWithDate>> itemCodeMap = typeMap.get(itemQuantity.getType());
            itemCodeMap.putIfAbsent(itemQuantity.getItemCode(), new ArrayList<>());
            List<ItemQuantityWithDate> quantityList = itemCodeMap.get(itemQuantity.getItemCode());
            quantityList.add(itemQuantity);
        }
        return typeMap;
    }

//    default Map<String, List<ItemQuantityWithDate>> getAllPRSumQuantityByItemsMap(Date timeStart, Date timeEnd, List<String> items) {
//        return getAllQuantityByDay(timeStart, timeEnd, items, Constants.PR_TYPE).stream().collect(
//            Collectors.groupingBy(ItemQuantityWithDate::getItemCode));
//    }
//
//    default Map<String, List<ItemQuantityWithDate>> getAllGRPOSumQuantityByItemsMap(Date timeStart, Date timeEnd, List<String> items) {
//        return getAllQuantityByDay(timeStart, timeEnd, items, Constants.GRPO_TYPE).stream().collect(
//            Collectors.groupingBy(ItemQuantityWithDate::getItemCode));
//    }

//    @Query(value = "select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(DATE_FORMAT(dueDate , '%Y-%m-%d'), itemCode, sum(quantity))  from SapOnOrderSummary" +
//        " where dueDate  is not null" +
//        " and dueDate >= :timeStart and dueDate <= :timeEnd" +
//        " and itemCode in (:listItemCode)" +
//        " and type = :type" +
//        " and status = 'O'" +
//        " group by DATE_FORMAT(dueDate , '%Y-%m-%d')," +
//        " itemCode order by DATE_FORMAT(dueDate , '%Y-%m-%d')")
//    List<ItemQuantityWithDate> getAllPrPoQuantityByDay(
//        @Param("timeStart") Date timeStart,
//        @Param("timeEnd")Date timeEnd,
//        @Param("listItemCode")List<String> listItemCode,
//        @Param("type")String type
//        );
//
//
//    @Query(value = "SELECT new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(DATE_FORMAT(FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 7)+7), '%Y-%m-%d'), itemCode, sum(quantity)) " +
//        "FROM SapOnOrderSummary " +
//        "WHERE dueDate  is not null " +
//        "and dueDate >= :timeStart and dueDate <= :timeEnd " +
//        "and itemCode in (:listItemCode) " +
//        "and type = :type " +
//        "and status = 'O' " +
//        "GROUP BY FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 7)+7), itemCode " +
//        "ORDER BY FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 7)+7)")
//    List<ItemQuantityWithDate> getAllPrPoQuantityByWeek(
//        @Param("timeStart") Date timeStart,
//        @Param("timeEnd")Date timeEnd,
//        @Param("listItemCode")List<String> listItemCode,
//        @Param("type")String type
//    );
//
//    @Query(value = "select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate( DATE_FORMAT(dueDate , '%Y-%m'), itemCode,sum(quantity)) " +
//        " from SapOnOrderSummary" +
//        " where dueDate  is not null" +
//        " and dueDate >= :timeStart and dueDate <= :timeEnd" +
//        " and itemCode in (:listItemCode)" +
//        " and type = :type" +
//        " and status = 'O'" +
//        " group by DATE_FORMAT(dueDate , '%Y-%m'), itemCode" +
//        " order by DATE_FORMAT(dueDate , '%Y-%m')")
//    List<ItemQuantityWithDate> getAllPrPoQuantityByMonth(
//        @Param("timeStart") Date timeStart,
//        @Param("timeEnd")Date timeEnd,
//        @Param("listItemCode")List<String> listItemCode,
//        @Param("type")String type
//    );
//
//    @Query(value = "select new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(DATE_FORMAT(FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 3.5)+3),'%Y-%m-%d'), itemCode, sum(quantity)) " +
//        " from SapOnOrderSummary" +
//        " where dueDate  is not null" +
//        " and dueDate >= :timeStart and dueDate <= :timeEnd" +
//        " and itemCode in (:listItemCode)" +
//        " and type = :type" +
//        " and status = 'O'" +
//        " group by FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 3.5)+3), itemCode" +
//        " order by FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 3.5)+3)")
//    List<ItemQuantityWithDate> getAllPrPoQuantityByHalfWeek(
//        @Param("timeStart") Date timeStart,
//        @Param("timeEnd")Date timeEnd,
//        @Param("listItemCode")List<String> listItemCode,
//        @Param("type")String type
//    );
//
//    @Query(value = "SELECT new com.facenet.mrp.service.dto.mrp.ItemQuantityWithDate(DATE_FORMAT(FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 14)+14), '%Y-%m-%d'), itemCode, sum(quantity)) " +
//        "FROM SapOnOrderSummary " +
//        "WHERE dueDate  is not null " +
//        "and dueDate >= :timeStart and dueDate <= :timeEnd " +
//        "and itemCode in (:listItemCode) " +
//        "and type = :type " +
//        "and status = 'O' " +
//        "GROUP BY FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 14)+14), itemCode " +
//        "ORDER BY FROM_DAYS(TO_DAYS(dueDate) -MOD(TO_DAYS(dueDate) -1, 14)+14)")
//    List<ItemQuantityWithDate> getAllPrPoQuantityByTwoWeek(
//        @Param("timeStart") Date timeStart,
//        @Param("timeEnd")Date timeEnd,
//        @Param("listItemCode")List<String> listItemCode,
//        @Param("type")String type
//    );

    @Query(name = "get_on_order_monitoring_dto",nativeQuery = true)
    List<OnOrderMonitoringDTO> getOnOrderMonitoring(Pageable pageable,String prCode,String poCode,String contractNumber, String vendor,String itemCode,String vendorName,Date dueDateStart, Date dueDateEnd, String poCreateUser,Date poCreateDateStart, Date poCreateDateEnd,String itemName);

    @Query(name = "get_on_order_monitoring_dto",nativeQuery = true)
    List<OnOrderMonitoringDTO> getTotalElements(String prCode,String poCode,String contractNumber, String vendor,String itemCode,String vendorName,Date dueDateStart, Date dueDateEnd, String poCreateUser,Date poCreateDateStart, Date poCreateDateEnd,String itemName);

    @Query(value = "select count(*) from (select item_code,po_code from sap_on_order_summary where pr_code is null or pr_code = ?1 and po_code is null or po_code = ?2 and contract_numer is null or contract_numer = ?3 and provider_code is null or provider_code = ?4 and item_code is null or item_code = ?5 and provider_name is null or provider_name = ?6 and due_date is null or due_date = ?7 and create_po_user is null or create_po_user = ?8 and po_create_date is null or po_create_date = ?9 group by item_code,po_code) p", nativeQuery = true)
    long getTotalElementsOnOrderMonitoring(String prCode,String poCode,String contractNumber, String vendor,String itemCode,String vendorName,Date dueDate,String poCreateUser,Date poCreateDate);

    @Query(name = "get_on_order_monitoring_dto_with_detail",nativeQuery = true)
    List<OnOrderMonitoringDTO> getOnOrderMonitoringWithDetail(String prCode,String poCode,String contractNumber, String vendor,String itemCode,String vendorName,Date dueDate,String poCreateUser,Date poCreateDate,String itemName);

    @Query(name = "get_on_order_monitoring_dto_with_detail",nativeQuery = true)
    List<OnOrderMonitoringDTO> getTotalElement(String prCode,String poCode,String contractNumber, String vendor,String itemCode,String vendorName,Date dueDate,String poCreateUser,Date poCreateDate,String itemName);

    @Query(value = "select s from SapOnOrderSummary s where s.poCode = :poCode and s.itemCode = :itemCode and s.type = :type")
    SapOnOrderSummary findSapOnOrderSummaryByPoCodeAndItemCodeAndType(@Param("poCode") String poCode,@Param("itemCode") String itemCode,@Param("type") String type);

    @Query("select s from SapOnOrderSummary s where s.itemCode = :itemCode and s.mrpCode = :mrpCode and s.soCode = :soCode")
    SapOnOrderSummary findSapOnOrderSummaryByItemCodeAndSoCode(@Param("itemCode") String itemCode,@Param("mrpCode") String mrpCode,@Param("soCode") String soCode);

    @Query(value = "select count(sd) from SapOnOrderSummary s join SapOnOrderDurationDetail sd on s.id = sd.fkId where sd.isActive = true and s.poCode = :poCode and s.itemCode = :itemCode and s.type = :type")
    Integer countNumber(@Param("poCode") String poCode,@Param("itemCode") String itemCode,@Param("type") String type);


    @Query(value = "select new com.facenet.mrp.service.dto.mrp.PrGrpoAnalysisReport(s.itemCode ,s.type, sum(s.quantity)) " +
        "from SapOnOrderSummary s " +
        "where s.mrpCode = :mrpCode " +
        "and s.soCode = :soCode " +
        "and s.itemCode = :poCode " +
        "and s.dueDate >= :startDate " +
        "and s.dueDate <= :endDate " +
        "and s.type in ('GRPO', 'PO') " +
        "group by s.itemCode, s.type")
    List<PrGrpoAnalysisReport> getPoGrpoReport(@Param("mrpCode")String mrpCode,
                                               @Param("soCode")String soCode,
                                               @Param("poCode")String itemCode,
                                               @Param("startDate")Date startDate,
                                               @Param("endDate")Date endDate
    );

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.PoGrpoDetailReport(s.itemCode ,s.type, sum(s.quantity), s.mrpCode) " +
        "from SapOnOrderSummary s " +
        "where s.soCode = :soCode " +
        "and s.type in ('GRPO', 'PO') " +
        "group by s.itemCode, s.mrpCode, s.type")
    List<PoGrpoDetailReport> getPoGrpoDetailReport(@Param("soCode")String soCode);

    @Query(value = "select s from SapOnOrderSummary s where s.id = :id")
    SapOnOrderSummary findSapOnOrderSummaryByMrpCodeAndItemCodeAndType(@Param("id") int id);

    @Query(value = "select s from SapOnOrderSummary s where s.id = :id")
    SapOnOrderSummary findSapOnOrderSummaryById(@Param("id") Integer id);

    @Query("select sum(s.quantity) from SapOnOrderSummary s where s.status = 'O' and s.itemCode = :itemCode and s.prCode = :prCode and s.poCode = :poCode and s.type = 'GRPO' ")
    Double getTotalGrpo(@Param("itemCode") String itemCode,@Param("prCode") String prCode,@Param("poCode") String poCode);

    @Query("select sum(s.quantity) from SapOnOrderSummary s where s.status = 'O' and s.itemCode = :itemCode and s.poCode = :poCode and s.type = 'PO' ")
    Double getTotalPo(@Param("itemCode") String itemCode,@Param("poCode") String poCode);

    @Query("select s from SapOnOrderSummary s where s.id = :id")
    SapOnOrderSummary getDueDate(@Param("id") Integer id);

    @Query("select s.quantity from SapOnOrderSummary s where s.status = 'O' and s.prCode = :prCode and s.itemCode = :itemCode and s.type = 'PR'")
    Double getQuantityInSap(@Param("prCode") String prCode,@Param("itemCode") String itemCode);

//    @Query(value = "select * from sap_on_order_summary as s where s.po_code = :poCode and s.type = 'PR' and s.status = 'O' having max(s.po_create_date) ",nativeQuery = true)
//    SapOnOrderSummary getPrByPo(@Param("poCode") String poCode);

    @Query(value = "select * from sap_on_order_summary as s where s.sap_code = :prCode and s.type = 'PR' having max(s.po_create_date) ",nativeQuery = true)
    SapOnOrderSummary getPr(@Param("prCode") String prCode);

    @Query(value = "select s from SapOnOrderSummary s where s.poCode = :poCode and (s.type = 'PO' or s.type = 'GRPO') and s.status = 'O' ")
    List<SapOnOrderSummary> getAllByPo(@Param("poCode") String poCode);

    @Query(value = "select a.id from SapOnOrderSummary a where a.status = 'O' and a.type = 'PO' and a.itemCode = :itemCode and a.prCode = :prCode")
    List<Integer> getListFk(@Param("prCode") String prCode,@Param("itemCode") String itemCode);

    @Query(value = "select a.id from SapOnOrderSummary a where a.status = 'O' and a.type = 'PO' and a.itemCode = :itemCode and a.prCode = :prCode")
    List<Integer> getListFkPO(@Param("prCode") String prCode,@Param("itemCode") String itemCode);

    @Query(value = "select new com.facenet.mrp.service.dto.mrp.MonitoringDetailDTO(a.id,a.dueDate,a.quantity,a.poCode,a.poCode)  from SapOnOrderSummary a where a.type = 'PO' and a.status = 'O' and a.itemCode = :itemCode and a.prCode = :prCode")
    List<MonitoringDetailDTO> getListPo(@Param("prCode") String prCode,@Param("itemCode") String itemCode);

    @Query(value = "select sum(s.quantity) from SapOnOrderSummary s where s.soCode = :soCode and s.mrpCode = :mrpCode and s.itemCode = :itemCode and s.type = 'PR' ")
    Double sumQuantityItemPr(@Param("soCode") String soCode,@Param("mrpCode") String mrpCode,@Param("itemCode") String itemCode);

    @Query(value = "select sum(s.quantity) from SapOnOrderSummary s where s.soCode = :soCode and s.mrpCode = :mrpCode and s.itemCode = :itemCode and s.type = 'PO' ")
    Double sumQuantityItemPO(@Param("soCode") String soCode,@Param("mrpCode") String mrpCode,@Param("itemCode") String itemCode);
}
