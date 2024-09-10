
/*
Class được tạo bởi @author Nguyễn Quang Hiếu vào 18/10/2021 10:20 AM
*/
package com.facenet.mrp.service.utils;


import com.facenet.mrp.domain.mrp.*;
import com.facenet.mrp.repository.mrp.LeadTimeRepository;
import com.facenet.mrp.repository.mrp.MqqPriceRepository;
import com.facenet.mrp.repository.mrp.ParamRepository;
import com.facenet.mrp.repository.mrp.ProductOrderRepository;
import com.facenet.mrp.service.dto.KeyDictionaryDTO;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.MqqPriceExcelModel;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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

import static com.facenet.mrp.service.utils.ExcelUtils.getIntegerCellValue;
import static com.facenet.mrp.service.utils.ExcelUtils.getStringCellValue;
import static org.apache.poi.ss.usermodel.CellType.STRING;

@Component
public class XlsxExcelHandle {

    private final MqqPriceRepository mqqPriceRepository;
    private final LeadTimeRepository leadTimeRepository;
    private final ParamRepository paramRepository;
    private final ProductOrderRepository productOrderRepository;

    public XlsxExcelHandle(MqqPriceRepository mqqPriceRepository, LeadTimeRepository leadTimeRepository,
                           ParamRepository paramRepository,
                           ProductOrderRepository productOrderRepository) {
        this.mqqPriceRepository = mqqPriceRepository;
        this.leadTimeRepository = leadTimeRepository;
        this.paramRepository = paramRepository;
        this.productOrderRepository = productOrderRepository;
    }

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public List<KeyDictionaryDTO> readColumnFromExcel(InputStream file, String entityType) throws IOException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        // Lấy ra sheet đầu tiên từ workbook
        Sheet sheet = workbook.getSheetAt(0);
        List<KeyDictionaryDTO> result = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Cell firstCell = row.getCell(0);
            //            if (firstCell == null || firstCell.getCellType() == CellType.BLANK) continue;
            for (int i = 1; i <= 4; i++) {
                if (ExcelUtils.isEmpty(row.getCell(i))) throw new CustomException(
                    HttpStatus.BAD_REQUEST,
                    "cell.must.not.empty",
                    String.valueOf(i + 1),
                    String.valueOf(row.getRowNum() + 1)
                );
            }
            //            for (int i = 1; i <= 4; i++) {
            //                Utils.validateSpecialCharacters(ExcelUtils.getStringCellValue(row.getCell(i)));
            //            }

            KeyDictionaryDTO keyDictionaryDTO = new KeyDictionaryDTO();
            // số thứ tự
            if (StringUtils.isEmpty(getStringCellValue(row.getCell(0))) || getStringCellValue(row.getCell(0)) == null) {
                keyDictionaryDTO.setEntryIndex(null);
            } else if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                keyDictionaryDTO.setEntryIndex(ExcelUtils.getIntegerCellValue(row.getCell(0)));
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.datatype", getStringCellValue(row.getCell(0)));
            }
            // tên cột
            if (
                StringUtils.isEmpty(getStringCellValue(row.getCell(1))) ||
                    getStringCellValue(row.getCell(1)) == null ||
                    row.getCell(1).getCellType() != STRING
            ) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.datatype", getStringCellValue(row.getCell(1)));
            } else {
                keyDictionaryDTO.setKeyTitle(getStringCellValue(row.getCell(1)));
            }
            // loại dữ liệu
            if (row.getCell(2).getCellType() != STRING) throw new CustomException(
                HttpStatus.BAD_REQUEST,
                "invalid.datatype",
                getStringCellValue(row.getCell(2))
            );
            switch (getStringCellValue(row.getCell(2)).toLowerCase()) {
                case "integer":
                    keyDictionaryDTO.setDataType(1);
                    break;
                case "float":
                    keyDictionaryDTO.setDataType(2);
                    break;
                case "string":
                    keyDictionaryDTO.setDataType(3);
                    break;
                case "json":
                    keyDictionaryDTO.setDataType(4);
                    break;
                case "date":
                    keyDictionaryDTO.setDataType(5);
                    break;
                case "boolean":
                    keyDictionaryDTO.setDataType(6);
                    break;
                default:
                    throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.datatype", getStringCellValue(row.getCell(2)));
            }
            // bắt buộc
            if (row.getCell(3).getCellType() != STRING) throw new CustomException(
                HttpStatus.BAD_REQUEST,
                "invalid.datatype",
                getStringCellValue(row.getCell(3))
            );
            if (getStringCellValue(row.getCell(3)).trim().toLowerCase().equalsIgnoreCase("Bắt buộc".trim())) {
                keyDictionaryDTO.setIsRequired(true);
            } else if (getStringCellValue(row.getCell(3)).trim().toLowerCase().equalsIgnoreCase("Không bắt buộc".trim())) {
                keyDictionaryDTO.setIsRequired(false);
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.datatype", getStringCellValue(row.getCell(3)));
            }
            // trạng thái
            if (row.getCell(4).getCellType() != STRING) throw new CustomException(
                HttpStatus.BAD_REQUEST,
                "invalid.datatype",
                getStringCellValue(row.getCell(4))
            );
            System.err.println(getStringCellValue(row.getCell(4)).trim().toLowerCase());
            if (getStringCellValue(row.getCell(4)).trim().toLowerCase().equalsIgnoreCase("Hiển thị".trim())) {
                keyDictionaryDTO.setCheck(true);
            } else if (getStringCellValue(row.getCell(4)).trim().toLowerCase().equalsIgnoreCase("Không hiển thị".trim())) {
                keyDictionaryDTO.setCheck(false);
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.datatype", getStringCellValue(row.getCell(4)));
            }
            //keyName
            keyDictionaryDTO.setKeyName(String.valueOf(UUID.randomUUID()));
            //entityType
            switch (entityType.toLowerCase().trim()) {
                case "production-stage":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.PRODUCTIONSTAGE);
                    break;
                case "vendor":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.VENDOR);
                    break;
                case "job":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.JOB);
                    break;
                case "error":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.ERROR);
                    break;
                case "error-group":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.ERRORGROUP);
                    break;
                case "machine":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.MACHINE);
                    break;
                case "production-line":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.PRODUCTION_LINE);
                    break;
                case "btp":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.BTP);
                    break;
                case "tp":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.TP);
                    break;
                case "nvl":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.NVL);
                    break;
                case "employee":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.EMPLOYEE);
                    break;
                case "team-group":
                    keyDictionaryDTO.setEntityType(Constants.EntityType.TEAM_GROUP);
                    break;
                default:
                    throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.datatype", entityType);
            }
            result.add(keyDictionaryDTO);
        }
        return result;
    }


    public HashMap<String,List<ProductOrder>> readDonHangExcel(InputStream fis) throws IOException, ParseException {
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        // Lấy ra sheet đầu tiên từ workbook
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rowIndex = 1;
        // Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
        boolean skipFirst = false;
        ArrayList<ProductOrder> result;
        HashMap<String,List<ProductOrder>> listHashMap  = new HashMap<>();
        ProductOrder emp;
        for (Row row : sheet) {
            // Lấy Iterator cho tất cả các cell của dòng hiện tại.
            Iterator<Cell> cellIterator = row.cellIterator();
            if(skipFirst) {
                emp = excelToDonHang(row, rowIndex);
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
                rowIndex++;
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

    public ProductOrder excelToDonHang(Row row, int rowIndex) throws ParseException {
        //TODO: optimize code
        ExcelUtils.validateRow(row, 0, 11);
        ExcelUtils.validateRow(row, 15, 18);

        ProductOrder donHang = new ProductOrder();
//        donHang.setId(UUID.randomUUID());
        Integer count = productOrderRepository.countActiveProductOrders();
        count = count + 1;
        String baseOrderCode = "RAL-SO-" + count;
        donHang.setProductOrderCode(baseOrderCode);
//        donHang.setCustomerId(ExcelUtils.getStringCellValue(row.getCell(1)));
//        donHang.setCustomerName(ExcelUtils.getStringCellValue(row.getCell(2)));
        donHang.setProductOrderType(getStringCellValue(row.getCell(0)));
        donHang.setType("Đơn hàng");
        donHang.setProductCodeChild(baseOrderCode + "-" + rowIndex);
        donHang.setProductCode(getStringCellValue(row.getCell(1)));
        donHang.setProductName(row.getCell(2).getStringCellValue().trim());
        donHang.setBomVersion("1.0");
        donHang.setQuantity(ExcelUtils.getIntegerCellValue(row.getCell(3)));
        donHang.setCustomerName(row.getCell(4) != null ? getStringCellValue(row.getCell(4)) : null);
        donHang.setSaleCode(row.getCell(5) != null ? getStringCellValue(row.getCell(5)) : null);

        if(row.getCell(6) != null) {
            try{
                //startTime
                donHang.setStartDate(new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(row.getCell(10))));
            }catch (Exception e){
                //startTime
                donHang.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(row.getCell(10))));
            }

        }
        if(row.getCell(7) != null) {
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

        if (row.getCell(8).getCellType() == CellType.BLANK)
            donHang.setSupplyType("MRP");
        else
            donHang.setSupplyType(getStringCellValue(row.getCell(12)));

        if (row.getCell(9).getCellType() == CellType.BLANK)
            donHang.setPriorityProduct(1);
        else
            donHang.setPriorityProduct(ExcelUtils.getIntegerCellValue(row.getCell(13)));

        if (row.getCell(10).getCellType() == CellType.BLANK)
            donHang.setPriority(1);
        else
            donHang.setPriority(ExcelUtils.getIntegerCellValue(row.getCell(14)));
        donHang.setStatus(1);
        if(row.getCell(11) != null) {
            try{
                donHang.setOrderDate(new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(row.getCell(15))));
            }catch (Exception e){
                donHang.setOrderDate(new SimpleDateFormat("dd/MM/yyyy").parse(String.valueOf(row.getCell(15))));
            }
        }else{
            donHang.setOrderDate(new Date());
        }
        if (row.getCell(12) != null) {
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
        donHang.setPartCode(row.getCell(13) != null ? getStringCellValue(row.getCell(13)) : null);
        donHang.setPartName(row.getCell(14) != null ? getStringCellValue(row.getCell(14)) : null);
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
        String vendorCode = getStringCellValue(row.getCell(0));
        String itemCode = getStringCellValue(row.getCell(4));
        // Add new vendor for insert/update to VendorEntity
        if (!result.getVendorCodes().contains(vendorCode)) {
            VendorEntity vendorEntity = new VendorEntity();
            vendorEntity.setVendorCode(vendorCode);
            vendorEntity.setVendorName(getStringCellValue(row.getCell(1)));
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
        if (getStringCellValue(row.getCell(7)) != null){
            mqqPriceEntity.setTimeEnd(row.getCell(7).getDateCellValue());
        }

        Integer rangeStart = ExcelUtils.getIntegerCellValue(row.getCell(8));
        Integer rangeEnd = ExcelUtils.getIntegerCellValue(row.getCell(9));
        if (rangeStart > rangeEnd) throw new CustomException(HttpStatus.BAD_REQUEST, "object.must.be.greater.than.other.at", "số lượng tối thiểu", "số lượng tối đa", String.valueOf(row.getRowNum() + 1));
        mqqPriceEntity.setRangeStart(rangeStart);
        mqqPriceEntity.setRangeEnd(rangeEnd);
        mqqPriceEntity.setPrice(ExcelUtils.getNumberCellValue(row.getCell(10)));
        String currency = getStringCellValue(row.getCell(11));
        if (!currencies.contains(currency))
            throw new CustomException(HttpStatus.BAD_REQUEST, "unknown.currency", currency);
        mqqPriceEntity.setCurrency(getStringCellValue(row.getCell(11)));
        result.getMqqPriceEntities().add(mqqPriceEntity);

        if (itemVendorMap.containsMapping(itemCode, vendorCode)) return;

        //Check Exist leadTime on DB
        LeadTimeEntity leadTimeEntity = leadTimeRepository.getLeadTimeByVendorCodeAndItemCode(vendorCode, itemCode);

        if (leadTimeEntity == null){
            leadTimeEntity = new LeadTimeEntity();
            leadTimeEntity.setVendorCode(vendorCode);
            leadTimeEntity.setItemCode(itemCode);
        }
        if (getStringCellValue(row.getCell(6)) != null){
            leadTimeEntity.setLeadTime(ExcelUtils.getIntegerCellValue(row.getCell(6)));
        }
        leadTimeEntity.setNote(getStringCellValue(row.getCell(12)));

        result.getLeadTimeEntities().add(leadTimeEntity);

        result.getSaleEntityMap().putIfAbsent(vendorCode,
            new SaleEntity(
                vendorCode,
                getStringCellValue(row.getCell(2)),
                getStringCellValue(row.getCell(3))
            )
        );
        if (!itemVendorMap.containsKey(itemCode)) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItemCode(itemCode);
            itemEntity.setItemName(getStringCellValue(row.getCell(5)));
            result.getItemEntities().add(itemEntity);
        }

        itemVendorMap.put(itemCode, vendorCode);
    }

    public List<ExecutionPlanReportDetailEntity> convertToReport(InputStream fis) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0); // Lấy hàng đầu tiên (tiêu đề)

        int columnCount = headerRow.getLastCellNum();
        // Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
        boolean skipFirst = false;
        ArrayList<ExecutionPlanReportDetailEntity> result;
        List<ExecutionPlanReportDetailEntity> listHashMap  = new ArrayList<>();
        ExecutionPlanReportDetailEntity emp;
        for (Row row : sheet) {
            // Lấy Iterator cho tất cả các cell của dòng hiện tại.
            Iterator<Cell> cellIterator = row.cellIterator();
            if(skipFirst) {
                emp = excelToExecutionPlanReportDetail(columnCount,row);
                if(emp == null){
                    continue;
                }else {
                    listHashMap.add(emp);
                }

            } else skipFirst = true;

        }
        return listHashMap;
    }

    private ExecutionPlanReportDetailEntity excelToExecutionPlanReportDetail(int columnCount,Row row){
        ExcelUtils.validateRow(row, 0, columnCount-1);
        ExecutionPlanReportDetailEntity planReportDetailEntity = new ExecutionPlanReportDetailEntity();
        planReportDetailEntity.setPlanReportDetail(new ArrayList<>());

        planReportDetailEntity.setProductCode(getStringCellValue(row.getCell(0)));
        planReportDetailEntity.setProductName(getStringCellValue(row.getCell(1)));
        planReportDetailEntity.setVersion(getStringCellValue(row.getCell(2)));
        planReportDetailEntity.setTotalQuantity(getIntegerCellValue(row.getCell(3)));
        planReportDetailEntity.setIsActive(true);

        for (int i = 4; i < columnCount; i++){
            ExecutionPlanReportDetailQuantityEntity detailQuantityEntity = new ExecutionPlanReportDetailQuantityEntity();
            detailQuantityEntity.setQuantity(getIntegerCellValue(row.getCell(i)));
            detailQuantityEntity.setStt(i-3);
            planReportDetailEntity.getPlanReportDetail().add(detailQuantityEntity);
        }
        return planReportDetailEntity;
    }

}
