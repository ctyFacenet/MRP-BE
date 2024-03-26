
/*
Class được tạo bởi @author Nguyễn Quang Hiếu vào 18/10/2021 10:20 AM
*/
package com.facenet.mrp.service.utils;


import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.repository.mrp.LeadTimeRepository;
import com.facenet.mrp.repository.mrp.MqqPriceRepository;
import com.facenet.mrp.repository.mrp.ParamRepository;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.MqqPriceExcelModel;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Component
public class XlsxExcelHandle {

    private final MqqPriceRepository mqqPriceRepository;
    private final LeadTimeRepository leadTimeRepository;
    private final ParamRepository paramRepository;

    public XlsxExcelHandle(MqqPriceRepository mqqPriceRepository, LeadTimeRepository leadTimeRepository,
                           ParamRepository paramRepository) {
        this.mqqPriceRepository = mqqPriceRepository;
        this.leadTimeRepository = leadTimeRepository;
        this.paramRepository = paramRepository;
    }

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public HashMap<String,List<ProductOrder>> readDonHangExcel(InputStream fis) throws IOException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        // Lấy ra sheet đầu tiên từ workbook
        XSSFSheet sheet = workbook.getSheetAt(0);


        // Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
        boolean skipFirst = false;
        ArrayList<ProductOrder> result;
        HashMap<String,List<ProductOrder>> listHashMap  = new HashMap<>();
        ProductOrder emp;
        for (Row row : sheet) {
            // Lấy Iterator cho tất cả các cell của dòng hiện tại.
            Iterator<Cell> cellIterator = row.cellIterator();
            if(skipFirst) {
                emp = excelToDonHang(row);
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

        }
        return listHashMap;
    }


    public String getStringValueExcel(Cell cell) {
        if (cell == null) return null;
        CellType cellType = cell.getCellType();
        String value = null;
        switch (cellType) {
            case _NONE:
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                break;
            case FORMULA:
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    System.out.println("The cell contains a date value: "
                            + cell.getDateCellValue());
                    value = String.valueOf(DateUtil.getJavaDate(cell.getNumericCellValue()));
                }
                else value = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                value = cell.getStringCellValue().trim();
                break;
            case ERROR:
                System.out.print("!");
                System.out.print("\t");
                break;
        }
        return value;
    }

    public String convertToDate (Cell date) {

        double d = Double.parseDouble(getStringValueExcel(date));
        long l = Math.round(d);
        return Long.toString(l);
    }

    public Date stringToDate(String startDateString) {
        System.out.println(startDateString);
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        Date startDate;
        try {
            startDate = df.parse(startDateString);
            return startDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductOrder excelToDonHang(Row row) throws ParseException {
        //TODO: optimize code
        ExcelUtils.validateRow(row, 0, 11);
        ExcelUtils.validateRow(row, 15, 18);

        ProductOrder donHang = new ProductOrder();
//        donHang.setId(UUID.randomUUID());
        donHang.setProductOrderCode("RAL-SO-"+ExcelUtils.getStringCellValue(row.getCell(0)));
//        donHang.setCustomerId(ExcelUtils.getStringCellValue(row.getCell(1)));
//        donHang.setCustomerName(ExcelUtils.getStringCellValue(row.getCell(2)));
        donHang.setProductOrderType(ExcelUtils.getStringCellValue(row.getCell(1)));
        donHang.setType("Đơn hàng");
        donHang.setProductCodeChild(ExcelUtils.getStringCellValue(row.getCell(2)));
        donHang.setProductCode(ExcelUtils.getStringCellValue(row.getCell(3)));
        donHang.setProductName(row.getCell(4).getStringCellValue().trim());
        donHang.setBomVersion(ExcelUtils.getStringCellValue(row.getCell(5)));
        donHang.setQuantity(ExcelUtils.getIntegerCellValue(row.getCell(6)));
        donHang.setCustomerId(ExcelUtils.getStringCellValue(row.getCell(7)));

        if(StringUtils.isEmpty(donHang.getCustomerId())){
            throw new CustomException("customer.id.is.empty",row.getRowNum() + "");
        }

        donHang.setCustomerName(ExcelUtils.getStringCellValue(row.getCell(8)));
        donHang.setSaleCode(ExcelUtils.getStringCellValue(row.getCell(9)));

        if(row.getCell(10) != null) {
            try{
                //startTime
                donHang.setStartDate(new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(row.getCell(10))));
            }catch (Exception e){
                //startTime
                donHang.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(row.getCell(10))));
            }

        }
        if(row.getCell(11) != null) {
            Date endTime;
            try{
                 endTime = new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(row.getCell(11)));
            }catch (Exception e){
                endTime = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(row.getCell(11)));
            }
            //endTime
            if (endTime.compareTo(donHang.getStartDate()) < 0)
                throw new CustomException("object.must.be.greater.than.at", "thời gian trả hàng", "thời gian phát sinh", String.valueOf(row.getRowNum() + 1));
            donHang.setEndDate(endTime);
        }

        if (row.getCell(12).getCellType() == CellType.BLANK)
            donHang.setSupplyType("MRP");
        else
            donHang.setSupplyType(ExcelUtils.getStringCellValue(row.getCell(12)));

        if (row.getCell(13).getCellType() == CellType.BLANK)
            donHang.setPriorityProduct(1);
        else
            donHang.setPriorityProduct(ExcelUtils.getIntegerCellValue(row.getCell(13)));

        if (row.getCell(14).getCellType() == CellType.BLANK)
            donHang.setPriority(1);
        else
            donHang.setPriority(ExcelUtils.getIntegerCellValue(row.getCell(14)));
        donHang.setStatus(1);
        if(row.getCell(15) != null) {
            try{
                donHang.setOrderDate(new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(row.getCell(15))));
            }catch (Exception e){
                donHang.setOrderDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(row.getCell(15))));
            }
        }else{
            donHang.setOrderDate(new Date());
        }
        if (row.getCell(16) != null) {
            Date deliverDate;
            try{
                deliverDate = new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(row.getCell(16)));
            }catch (Exception e){
                deliverDate = new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(row.getCell(16)));
            }
            if (deliverDate.compareTo(donHang.getOrderDate()) < 0)
                throw new CustomException("object.must.be.greater.than.other.at", "thời gian trả hàng", "thời gian đặt hàng", String.valueOf(row.getRowNum() + 1));
            donHang.setDeliverDate(deliverDate);
        }

//        if(row.getCell(13) != null) {
//            //startTime
//            donHang.setStartDate(new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(row.getCell(13))));
//        }
//        if(row.getCell(14) != null) {
//            //endTime
//            Date endTime = new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(row.getCell(14)));
//            if (endTime.compareTo(donHang.getStartDate()) < 0)
//                throw new CustomException("object.must.be.greater.than.at", "thời gian trả hàng", "thời gian phát sinh", String.valueOf(row.getRowNum() + 1));
//            donHang.setEndDate(endTime);
//        }
        donHang.setPartCode(ExcelUtils.getStringCellValue(row.getCell(17)));
        donHang.setPartName(ExcelUtils.getStringCellValue(row.getCell(18)));
        donHang.setCreatedAt(Instant.now());

//        String po_id = donHang.getCustomerId() + "-" + new SimpleDateFormat("yyyyMMdd").format(donHang.getOrderDate());
        String po_id = donHang.getProductOrderCode();
        donHang.setMrpPoId(po_id);
        return donHang;
    }

    public MqqPriceExcelModel readPriceExcel(InputStream file) throws IOException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        // Lấy ra sheet đầu tiên từ workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        MqqPriceExcelModel result = new MqqPriceExcelModel();
        MultiValuedMap<String, String> itemVendorMap = new ArrayListValuedHashMap<>();
        Set<String> currencies = paramRepository.findAllParamValueByCode(Constants.Param.CURRENCY);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Cell firstCell = row.getCell(0);
            if (firstCell == null || firstCell.getCellType() == CellType.BLANK) break;
            excelToMqqPrice(row, result, itemVendorMap, currencies);
        }
        return result;
    }

    private void excelToMqqPrice(Row row, MqqPriceExcelModel result, MultiValuedMap<String, String> itemVendorMap, Set<String> currencies) {
//        ExcelUtils.validateRow(row, 0, 6);
        ExcelUtils.validateRow(row, 0, 1);
        ExcelUtils.validateRow(row, 4, 5);
        ExcelUtils.validateRow(row, 8, 11);
        MqqPriceEntity mqqPriceEntity = new MqqPriceEntity();
        String vendorCode = ExcelUtils.getStringCellValue(row.getCell(0));
        String itemCode = ExcelUtils.getStringCellValue(row.getCell(4));
        // Add new vendor for insert/update to VendorEntity
        if (!result.getVendorCodes().contains(vendorCode)) {
            VendorEntity vendorEntity = new VendorEntity();
            vendorEntity.setVendorCode(vendorCode);
            vendorEntity.setVendorName(ExcelUtils.getStringCellValue(row.getCell(1)));
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

//        try {
//            mqqPriceEntity.setTimeEnd((row.getCell(7).getDateCellValue() == null)? null : row.getCell(7).getDateCellValue());
//        } catch (Exception e) {
//            throw new CustomException(HttpStatus.BAD_REQUEST, "unparsable.date");
//        }
        if (ExcelUtils.getStringCellValue(row.getCell(7)) != null){
            mqqPriceEntity.setTimeEnd(row.getCell(7).getDateCellValue());
        }

        Integer rangeStart = ExcelUtils.getIntegerCellValue(row.getCell(8));
        Integer rangeEnd = ExcelUtils.getIntegerCellValue(row.getCell(9));
        if (rangeStart > rangeEnd) throw new CustomException(HttpStatus.BAD_REQUEST, "object.must.be.greater.than.other.at", "số lượng tối thiểu", "số lượng tối đa", String.valueOf(row.getRowNum() + 1));
        mqqPriceEntity.setRangeStart(rangeStart);
        mqqPriceEntity.setRangeEnd(rangeEnd);
        mqqPriceEntity.setPrice(ExcelUtils.getNumberCellValue(row.getCell(10)));
        String currency = ExcelUtils.getStringCellValue(row.getCell(11));
        if (!currencies.contains(currency))
            throw new CustomException(HttpStatus.BAD_REQUEST, "unknown.currency", currency);
        mqqPriceEntity.setCurrency(ExcelUtils.getStringCellValue(row.getCell(11)));
        result.getMqqPriceEntities().add(mqqPriceEntity);

        if (itemVendorMap.containsMapping(itemCode, vendorCode)) return;

        //Check Exist leadTime on DB
        LeadTimeEntity leadTimeEntity = leadTimeRepository.getLeadTimeByVendorCodeAndItemCode(vendorCode, itemCode);

        if (leadTimeEntity == null){
            leadTimeEntity = new LeadTimeEntity();
            leadTimeEntity.setVendorCode(vendorCode);
            leadTimeEntity.setItemCode(itemCode);
        }
        if (ExcelUtils.getStringCellValue(row.getCell(6)) != null){
            leadTimeEntity.setLeadTime(ExcelUtils.getIntegerCellValue(row.getCell(6)));
        }
        leadTimeEntity.setNote(ExcelUtils.getStringCellValue(row.getCell(12)));

        result.getLeadTimeEntities().add(leadTimeEntity);

        result.getSaleEntityMap().putIfAbsent(vendorCode,
            new SaleEntity(
                vendorCode,
                ExcelUtils.getStringCellValue(row.getCell(2)),
                ExcelUtils.getStringCellValue(row.getCell(3))
            )
        );
        if (!itemVendorMap.containsKey(itemCode)) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItemCode(itemCode);
            itemEntity.setItemName(ExcelUtils.getStringCellValue(row.getCell(5)));
            result.getItemEntities().add(itemEntity);
        }

        itemVendorMap.put(itemCode, vendorCode);
    }
}
