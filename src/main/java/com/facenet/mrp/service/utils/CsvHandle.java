package com.facenet.mrp.service.utils;

import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.repository.mrp.MqqPriceRepository;
import com.facenet.mrp.service.ListSaleService;
import com.facenet.mrp.service.dto.mrp.MaterialPlanDTO;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.MqqPriceExcelModel;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Component
public class CsvHandle {
    private final Logger log = LogManager.getLogger(ListSaleService.class);

    @Autowired
    MqqPriceRepository mqqPriceRepository;

    public HashMap<String,List<ProductOrder>> readFileToProductOrder(InputStream fileName) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(fileName, StandardCharsets.UTF_8));

        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("MÃ SO", "MÃ KH", "TÊN KH", "Loại đơn hàng", "Mã SP", "Tên SP", "Bom version", "Số lượng", "Phương thức cung ứng", "Mức độ ưu tiên ĐH", "Thời gian đặt hàng", "Thời gian trả hàng", "Thời gian phát sinh SP", "Thời gian trả hàng SP", "Mã NV Sale", "Tên NV Sale").withIgnoreHeaderCase().withTrim());
        ArrayList<ProductOrder> result;
        HashMap<String,List<ProductOrder>> listHashMap  = new HashMap<>();
        ProductOrder emp;
        boolean skipFirst = false;

        for (CSVRecord csvRecord: csvParser) {
            if(skipFirst) {
                emp = csvToDonHang(csvRecord);
                if(emp == null){
                    continue;
                }
                else if(listHashMap.containsKey(emp.getProductOrderCode())){
                    listHashMap.get(emp.getProductOrderCode()).add(emp);
                }else{
                    result = new ArrayList<>();
                    result.add(emp);
                    listHashMap.put(emp.getProductOrderCode(),result);
                }
            } else skipFirst = true;
//                ProductOrder donhang = csvToDonHang(csvRecord);
//                productOrderList.add(donhang);
        }

        return listHashMap;
    }

    public ProductOrder csvToDonHang(CSVRecord csvRecord) throws ParseException {

        ProductOrder donHang = new ProductOrder();

        donHang.setProductOrderCode("RAL-SO-" + csvRecord.get(0).trim());
        donHang.setSaleCode(csvRecord.get(1).trim());
        donHang.setSaleName(csvRecord.get(2).trim());
        donHang.setProductOrderType(csvRecord.get(3).trim());
        donHang.setType("Đơn hàng");

        donHang.setProductCode(csvRecord.get(4));
        donHang.setProductName(csvRecord.get(5));
        donHang.setBomVersion(csvRecord.get(6));
        donHang.setQuantity(Integer.valueOf(csvRecord.get(7).trim()));

        if (csvRecord.get(8).equals(""))
            donHang.setSupplyType("MRP");
        else
            donHang.setSupplyType(csvRecord.get(8).trim());

        if (csvRecord.get(9).trim().equals(""))
            donHang.setPriorityProduct(1);
        else
            donHang.setPriorityProduct(Integer.parseInt(csvRecord.get(9).trim()));

        if (csvRecord.get(10).trim().equals(""))
            donHang.setPriority(1);
        else
            donHang.setPriority(Integer.parseInt(csvRecord.get(10).trim()));

        donHang.setStatus(1);
        if(csvRecord.get(11).trim().equals("")) {
            donHang.setOrderDate(new Date());
        }else{
            donHang.setOrderDate(new SimpleDateFormat("dd/MM/yyyy").parse(csvRecord.get(11).trim()));
        }

        if (csvRecord.get(12).trim() != null) {
            Date deliverDate = new SimpleDateFormat("dd/MM/yyyy").parse(csvRecord.get(12).trim());
            if (deliverDate.compareTo(donHang.getOrderDate()) < 0)
                throw new CustomException("object.must.be.greater.than.other.at", "thời gian trả hàng", "thời gian đặt hàng", String.valueOf(csvRecord.get(12).trim() + 1));
            donHang.setDeliverDate(deliverDate);
        }

        if(csvRecord.get(13).trim() != null) {

            donHang.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(csvRecord.get(13).trim()));
        }
        if(csvRecord.get(14).trim() != null) {

            Date endTime = new SimpleDateFormat("dd/MM/yyyy").parse(csvRecord.get(14).trim());
            if (endTime.compareTo(donHang.getStartDate()) < 0)
                throw new CustomException("object.must.be.greater.than.at", "thời gian trả hàng", "thời gian phát sinh", String.valueOf(csvRecord.get(11).trim() + 1));
            donHang.setEndDate(endTime);
        }
        donHang.setPartCode(csvRecord.get(15));
        donHang.setPartName(csvRecord.get(16));
        donHang.setCreatedAt(Instant.now());

//        String po_id = donHang.getCustomerId() + "-" + new SimpleDateFormat("yyyyMMdd").format(donHang.getOrderDate());
        String po_id = donHang.getProductOrderCode();
        donHang.setMrpPoId(po_id);
        return donHang;
    }


    public MqqPriceExcelModel readPriceCsv(InputStream file) throws IOException {
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(file));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Mã NCC", "Tên NCC", "Mã NV KD quản lý", "Tên NV KD quản lý", "Mã hàng hóa", "Mô tả hàng hóa", "Leadtime", "Ngày hết hạn", "Số lượng tối thiểu", "Số lượng tối đa", "Đơn giá", "Đơn vị tính", "Ghi chú").withIgnoreHeaderCase().withTrim());
        MqqPriceExcelModel result = new MqqPriceExcelModel();
        MultiValuedMap<String, String> itemVendorMap = new ArrayListValuedHashMap<>();
        int count = 0;
        for (CSVRecord csvRecord : csvParser) {
            if(count > 0){
                csvToMqqPrice(csvRecord, result, itemVendorMap);
            }
            count ++;
        }
//        List<String> checkItem = checkItemCode(result.getMqqPriceEntities());
//        String check = "";
//        if(checkItem.size() > 0){
//            for (String item:checkItem
//            ) {
//                check += item+" ,";
//            }
//            return new CommonResponse<>()
//                    .isOk(false)
//                    .errorCode("400")
//                    .message("Những mã item sau bị trùng: "+check);
//        }

        return result;
    }

    public List<String> checkItemCode(List<MqqPriceEntity> result){
        List<String> check = new ArrayList<>();
        for (int i = 0 ; i < result.size() - 1 ; i++){
            for (int j = i + 1 ;j < result.size();j++){
                if(result.get(i).getItemCode().equals(result.get(j).getItemCode()) && !check.contains(result.get(i).getItemCode())){
                    check.add(result.get(i).getItemCode());
                }
            }
        }
        return check;
    }

    private void csvToMqqPrice(CSVRecord csvRecord, MqqPriceExcelModel result, MultiValuedMap<String, String> itemVendorMap) {
        ExcelUtils.validateRowCsv(csvRecord, 0, 6);
        ExcelUtils.validateRowCsv(csvRecord, 8, 11);
        MqqPriceEntity mqqPriceEntity = new MqqPriceEntity();
        String vendorCode = csvRecord.get(0).trim();
        String itemCode = csvRecord.get(4).trim();
        // Add new vendor for insert/update to VendorEntity
        if (!result.getVendorCodes().contains(vendorCode)) {
            VendorEntity vendorEntity = new VendorEntity();
            vendorEntity.setVendorCode(vendorCode);
            vendorEntity.setVendorName(csvRecord.get(1).trim());
            result.getVendorEntities().add(vendorEntity);
        }
        result.getVendorCodes().add(vendorCode);
        mqqPriceEntity.setVendorCode(vendorCode);
        mqqPriceEntity.setItemCode(itemCode);
        mqqPriceEntity.setIsPromotion(false);
        //Update bản ghi này là của giá MOQ mới nhất
        mqqPriceEntity.setCheckNew(true);
        //update các giá MOQ của nhà cung cấp và của mã item này về cũ ( new = false )
        mqqPriceRepository.updateNewestMoq(itemCode, vendorCode);
        try {
            mqqPriceEntity.setTimeEnd(new SimpleDateFormat("dd/MM/yyyy").parse(csvRecord.get(7).trim()));
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "unparsable.date");
        }
        Integer rangeStart = Integer.parseInt(csvRecord.get(8).trim());
        Integer rangeEnd = Integer.parseInt(csvRecord.get(9).trim());
        if (rangeStart > rangeEnd) throw new CustomException(HttpStatus.BAD_REQUEST, "object.must.be.greater.than.other.at", "số lượng tối thiểu", "số lượng tối đa", String.valueOf(csvRecord.getRecordNumber() + 1));
        mqqPriceEntity.setRangeStart(rangeStart);
        mqqPriceEntity.setRangeEnd(rangeEnd);
        mqqPriceEntity.setPrice(Double.valueOf(csvRecord.get(10).trim()));
        mqqPriceEntity.setCurrency(csvRecord.get(11).trim());
        result.getMqqPriceEntities().add(mqqPriceEntity);

        if (itemVendorMap.containsMapping(itemCode, vendorCode)) return;

        LeadTimeEntity leadTimeEntity = new LeadTimeEntity();
        leadTimeEntity.setVendorCode(vendorCode);
        leadTimeEntity.setItemCode(itemCode);
        leadTimeEntity.setLeadTime(Integer.parseInt(csvRecord.get(6).trim()));
        leadTimeEntity.setNote(csvRecord.get(12).trim());
        result.getLeadTimeEntities().add(leadTimeEntity);
        result.getSaleEntityMap().putIfAbsent(vendorCode,
            new SaleEntity(
                vendorCode,
                csvRecord.get(2).trim(),
                csvRecord.get(3).trim()
            )
        );
        if (!itemVendorMap.containsKey(itemCode)) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItemCode(itemCode);
            itemEntity.setItemName(csvRecord.get(5).trim());
            result.getItemEntities().add(itemEntity);
        }
        itemVendorMap.put(itemCode, vendorCode);
    }
}
