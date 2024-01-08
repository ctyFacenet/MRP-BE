package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ForecastOrderDetailEntity;
import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.repository.mrp.ForecastOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderRepository;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.dto.mrp.*;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.utils.ExcelUtils;
import com.facenet.mrp.service.utils.Utils;
import com.facenet.mrp.thread.CloneBomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class MaterialPlanningService {
    @Autowired
    private CoittRepository coittRepository;

    @Autowired
    private ForecastOrderDetailRepository forecastOrderDetailRepository;

    @Autowired
    private ProductOrderRepository forecastRepository;

    @Autowired
    private CloneBomService bomService;

    private final ForecastOrderDetailService forecastOrderDetailService;
    private static final Logger log = LogManager.getLogger(MqqPriceService.class);

    public MaterialPlanningService(ForecastOrderDetailService forecastOrderDetailService) {
        this.forecastOrderDetailService = forecastOrderDetailService;
    }

    public ResponseEntity readForeCastCsv(InputStream fis) throws IOException, ParseException, SQLException, CsvException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        ForecastMaterialDTO forecastMaterialDTO = new ForecastMaterialDTO();
        List<String> landMark = new ArrayList<>();
        CSVReader csvReader = new CSVReaderBuilder(bufferedReader).build();
        List<String[]> allData = csvReader.readAll();
        //lấy data từ file csv
        List<List<String>> trueData = new ArrayList<>();
        for (String[] item : allData) {
            List<String> data = new ArrayList<>();
            for (String item2 : item) {
                data.add(item2);
            }
            trueData.add(data);
        }
        //lấy ds mốc landmark
        for (int i = 13; i < trueData.get(0).size(); i++) {
            landMark.add(trueData.get(0).get(i));
        }
        //set attribute cho forecastMaterialDto
        forecastMaterialDTO.setForecastName(trueData.get(1).get(0));
        if (trueData.get(1).get(1) != null) {
            forecastMaterialDTO.setStartDate(new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(trueData.get(1).get(1))));
        } else {
            forecastMaterialDTO.setStartDate(new Date());
        }
        if (trueData.get(1).get(2) != null) {
            forecastMaterialDTO.setEndDate(new SimpleDateFormat("dd-MMM-yyyy").parse(String.valueOf(trueData.get(1).get(2))));
        } else {
            forecastMaterialDTO.setEndDate(new Date());
        }
        forecastMaterialDTO.setForecastMode(trueData.get(1).get(3));
        forecastMaterialDTO.setLevelPriority(Integer.parseInt(trueData.get(1).get(4)));
        forecastMaterialDTO.setForecastWhs(trueData.get(1).get(5));
        forecastMaterialDTO.setForecastSource(trueData.get(1).get(6));
        forecastMaterialDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
        forecastMaterialDTO.setCreatedAt(new Date());
        boolean skipFirst = false;
        List<MaterialPlanDTO> result = new ArrayList<>();
        MaterialPlanDTO emp;
        //for để lấy và add các materialPlanDto
        for (int i = 1; i < trueData.size(); i++) {
            if (skipFirst) {
                emp = csvToDto(trueData.get(i), trueData.size());
                if (emp == null) {
                    return ResponseEntity.ok(
                        new CommonResponse<>()
                            .isOk(false)
                            .errorCode("400")
                            .message("Mã item " + trueData.get(i).get(7) + " không có bom hoặc bom không hợp lệ!"));
                } else {
                    result.add(emp);
                }
            } else skipFirst = true;
        }
        forecastMaterialDTO.setLandMark(landMark);
        forecastMaterialDTO.setListData(result);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .success("00")
                .data(forecastMaterialDTO)
                .message("Import success"));
    }

    public MaterialPlanDTO csvToDto(List<String> row, int numberOfColumn) {
        MaterialPlanDTO mpd = new MaterialPlanDTO();
        mpd.setItemCode(row.get(7));
        mpd.setItemName(row.get(8));
        mpd.setGroupItem(row.get(9));
        //Integer check = checkBom(row.get(7),row.get(10));
        //if (check == 1){ return null; }
        mpd.setBomVersion(row.get(10));
        mpd.setLevelPriorityItem(Integer.parseInt(row.get(11)));
        mpd.setNote(row.get(12));
        List<LandMarkDTO> landMarkDTOList = new ArrayList<>();
        for (int i = 13; i < numberOfColumn; i++) {
            LandMarkDTO landMarkDTO = new LandMarkDTO();
            landMarkDTO.setQuantity(Integer.parseInt(row.get(i)));
            landMarkDTOList.add(landMarkDTO);
        }
        mpd.setListLandMark(landMarkDTOList);
        return mpd;
    }

    /**
     * hàm đọc file excel và trả dữ liệu lên cho fe
     *
     * @param fis
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public ResponseEntity readForeCast(InputStream fis) throws IOException, ParseException {
        ForecastMaterialDTO forecastMaterialDTO = new ForecastMaterialDTO();
        List<String> landMark = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // Lấy ra sheet đầu tiên từ workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        // Lấy ra Iterator cho tất cả các dòng của sheet hiện tại.
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        Row firstRow = sheet.getRow(0);
        //set các mốc landmark
        for (int i = 6; i < numberOfColumn; i++) {
            landMark.add(ExcelUtils.getStringCellValue(firstRow.getCell(i)));
        }
        //set attribute cho forecastMaterialDto
        Row secondRow = sheet.getRow(1);
        ExcelUtils.validateRow(secondRow, 0, 2);
        if (secondRow.getCell(4) == null) {
            forecastMaterialDTO.setLevelPriority(1);
        } else {
            forecastMaterialDTO.setLevelPriority(getIntegerCellValue(secondRow.getCell(4)));
        }

        forecastMaterialDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
        forecastMaterialDTO.setCreatedAt(new Date());

        Set<String> listItemCode = new HashSet<>();

        StringBuilder duplicateItem = new StringBuilder();

        StringBuilder invalidBomMessageBuilder = new StringBuilder();

        boolean skipFirst = false;
        List<MaterialPlanDTO> result = new ArrayList<>();
        MaterialPlanDTO emp;
        //for để set các planMaterialDto
        for (Row row : sheet) {
            if (ExcelUtils.isEmpty(row.getCell(0))) break;
            if (skipFirst) {
                emp = excelToDto(row, numberOfColumn, landMark);
                result.add(emp);
                if (!emp.isValidItem()) invalidBomMessageBuilder.append(emp.getItemCode()).append(", ");
                if (!listItemCode.contains(emp.getItemCode()))
                    listItemCode.add(emp.getItemCode());
                else
                    duplicateItem.append(emp.getItemCode()).append(", ");
            } else skipFirst = true;
        }
        forecastMaterialDTO.setLandMark(landMark);

        if (duplicateItem.toString().length() > 0) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "duplicate.param", duplicateItem.toString());
        }

        forecastMaterialDTO.setListData(result);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .success("00")
                .data(forecastMaterialDTO)
                .message(invalidBomMessageBuilder.length() > 0 ? ("Tồn tại BOM không hợp lệ: " + invalidBomMessageBuilder) : "Import thành công"));
    }

    /**
     * hàm kiểm tra người dùng nhập đủ số cột so với range time không
     *
     * @param mode
     * @param numColumn
     * @param start
     * @param end
     * @return
     */
    public Boolean checkNumberOfColumn(String mode, Integer numColumn, Date start, Date end) {
        mode = mode.toLowerCase(Locale.ROOT);
        LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (mode.equals("tuần")) {
            int sundayCount = 0;
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    sundayCount++;
                }
            }
            if (sundayCount + 1 == numColumn) {
                return true;
            }
        } else if (mode.equals("tháng")) {
            int numOfMonths = 0;
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                if (date.getDayOfMonth() == 1) {
                    numOfMonths++;
                }
            }
            if (numOfMonths + 1 == numColumn) {
                return true;
            }
        } else if (mode.equals("quý") || mode.equals("qúy")) {
            int numOfThirdMonth = 0;
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(1)) {
                for (int i = 0; i <= 12; i++) {
                    if (date.getMonthValue() == i) {
                        if (date.getMonthValue() == 3 || date.getMonthValue() == 6 || date.getMonthValue() == 9 || date.getMonthValue() == 12) {
                            numOfThirdMonth++;
                        }
                        break;
                    }
                }
            }
            if (numOfThirdMonth + 1 == numColumn) {
                return true;
            }
        } else if (mode.equals("năm")) {
            int numOfFirstDaysOfYear = 0;
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(1)) {
                if (date.getMonthValue() == 1) {
                    numOfFirstDaysOfYear++;
                }
            }
            if (numOfFirstDaysOfYear + 1 == numColumn) {
                return true;
            }
        }
        return false;
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
                } else value = String.valueOf(cell.getNumericCellValue());
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

    public String convertToDate(Cell date) {
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

    public static Integer getIntegerCellValue(Cell cell) {
        if (cell == null) {
            return 0;
        }
        System.out.println("cell.getCellType()" + cell.getCellType());
        switch (cell.getCellType()) {
            case STRING: {
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (Exception e) {
                    log.info(e.getMessage());
                    return 0;
                }
            }
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case STRING:
                        return Integer.parseInt(cell.getStringCellValue());
                    case NUMERIC:
                        return (int) Math.round(cell.getNumericCellValue());
                    default:
                        return Integer.parseInt(cell.getCellFormula());
                }
            default:
                return Double.valueOf(cell.getNumericCellValue()).intValue();
        }
    }

    /**
     * hàm đọc từng row excel sang dạng dto
     *
     * @param row
     * @param numberOfColumn
     * @param landMark
     * @return
     */
    public MaterialPlanDTO excelToDto(Row row, int numberOfColumn, List<String> landMark) {
        //TODO: optimize code
        ExcelUtils.validateRow(row, 0, 2);
//        ExcelUtils.validateRow(row, 13, numberOfColumn - 1);

        MaterialPlanDTO mpd = new MaterialPlanDTO();
        mpd.setItemCode(ExcelUtils.getStringCellValue(row.getCell(0)));
        mpd.setItemName(ExcelUtils.getStringCellValue(row.getCell(1)));
        String groupItem = ExcelUtils.getStringCellValue(row.getCell(2));

        if (!MrpDetailDTO.NVL.equalsIgnoreCase(groupItem) || !MrpDetailDTO.BTP.equalsIgnoreCase(groupItem))
            mpd.setGroupItem(groupItem);
        else
            throw new CustomException(HttpStatus.BAD_REQUEST, "unknown.group.item", groupItem);

        if (row.getCell(3) != null)
            mpd.setBomVersion(ExcelUtils.getStringCellValue(row.getCell(3)));

        if (MrpDetailDTO.BTP.equalsIgnoreCase(groupItem)) {
            if (bomService.isContainValidBom(mpd.getItemCode(), mpd.getBomVersion())) {
                mpd.setValidItem(true);
            } else {
                mpd.setGroupItem(MrpDetailDTO.NVL);
                mpd.setValidItem(false);
            }
        }
        if (row.getCell(4) == null) {
            mpd.setLevelPriorityItem(1);
        } else {
            mpd.setLevelPriorityItem(getIntegerCellValue(row.getCell(4)));
        }
        mpd.setNote(ExcelUtils.getStringCellValue(row.getCell(5)));
        List<LandMarkDTO> landMarkDTOList = new ArrayList<>();
        for (int i = 6; i < numberOfColumn; i++) {
            LandMarkDTO landMarkDTO = new LandMarkDTO();
            landMarkDTO.setQuantity(getIntegerCellValue(row.getCell(i)));
            landMarkDTO.setLandMarkDay(landMark.get(i - 6));
            landMarkDTOList.add(landMarkDTO);
        }
        mpd.setListLandMark(landMarkDTOList);
        return mpd;
    }

    /**
     * create hoặc update material plan
     *
     * @param forecastMaterialDTO
     * @throws JsonProcessingException
     * @throws ParseException
     */
    @Transactional
    public void saveOrUpdateMaterialPlan(ForecastMaterialDTO forecastMaterialDTO) throws JsonProcessingException, ParseException {
        // Tránh lưu lặp lại
        ProductOrder forecastOrder = forecastRepository.findProductOrderByProductOrderCodeAndIsActive(forecastMaterialDTO.getForecastCode(), (byte) 1);
        if (forecastOrder == null) {
            forecastOrder = new ProductOrder();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        forecastOrder.setProductOrderCode(forecastMaterialDTO.getForecastCode());
        forecastOrder.setForecastName(forecastMaterialDTO.getForecastName());
        forecastOrder.setForecastMode(forecastMaterialDTO.getForecastMode());
        forecastOrder.setWarehouseList(forecastMaterialDTO.getForecastWhs());
        forecastOrder.setPriority(forecastMaterialDTO.getLevelPriority());
        forecastOrder.setOrderDate(forecastMaterialDTO.getStartDate());
        forecastOrder.setDeliverDate(forecastMaterialDTO.getEndDate());
        forecastOrder.setCreatedBy(forecastMaterialDTO.getCreatedBy());
        forecastOrder.setCreatedAt(forecastMaterialDTO.getCreatedAt().toInstant());
        forecastOrder.setForecastSource(forecastMaterialDTO.getForecastSource());
        forecastOrder.setForecastLandMark(objectMapper.writeValueAsString(forecastMaterialDTO.getLandMark()));
        forecastOrder.setIsActive((byte) 1);
        forecastOrder.setProductOrderType("Forecast");
        forecastOrder.setCustomerId("RAL");
        forecastOrder.setCustomerName("Rang Dong");
        forecastOrder.setType("Forecast");
        forecastOrder.setStatus(forecastMaterialDTO.getForecastOrderStatus());
        forecastRepository.save(forecastOrder);
        List<ForecastOrderDetailEntity> list = new ArrayList<>();
        for (MaterialPlanDTO item : forecastMaterialDTO.getListData()) {
            ForecastOrderDetailEntity forecastOrderDetail = new ForecastOrderDetailEntity();
            forecastOrderDetail.setForecastOrderDetailId(item.getId());
            forecastOrderDetail.setItemCode(item.getItemCode());
            forecastOrderDetail.setItemDescription(item.getItemName());
            forecastOrderDetail.setBomVersion(item.getBomVersion());
            forecastOrderDetail.setQuantity(item.getCurrentInventory());
            forecastOrderDetail.setCreatedBy(forecastMaterialDTO.getCreatedBy());
            forecastOrderDetail.setCreatedAt(forecastMaterialDTO.getCreatedAt().toInstant());
            forecastOrderDetail.setItemGroupCode(item.getGroupItem());
            forecastOrderDetail.setNote(item.getNote());
            forecastOrderDetail.setProductOrderCode(forecastMaterialDTO.getForecastCode());
            forecastOrderDetail.setPriority(item.getLevelPriorityItem());
            forecastOrderDetail.setStatus(item.getStatus());
            forecastOrderDetail.setDetailResult(objectMapper.writeValueAsString(item.getListLandMark()));
            forecastOrderDetail.setIsActive(true);
            //Count số lượng của NVL trong từng item
            forecastOrderDetail.setMaterialChildrenCount(forecastOrderDetailService.countMaterialInFc(item));

            if (item.getItemStartTime() == null && item.getItemEndTime() == null) {
                forecastOrderDetail.setEndTime(forecastMaterialDTO.getEndDate());
                forecastOrderDetail.setStartTime(forecastMaterialDTO.getStartDate());
                forecastOrderDetail.setTotalRequest(Utils.getTotalItem(forecastMaterialDTO.getForecastMode(), forecastMaterialDTO.getStartDate(), forecastMaterialDTO.getEndDate(), item.getListLandMark()));
            } else if (item.getItemStartTime() != null && item.getItemEndTime() == null) {
                forecastOrderDetail.setEndTime(forecastMaterialDTO.getEndDate());
                forecastOrderDetail.setStartTime(item.getItemStartTime());
                forecastOrderDetail.setTotalRequest(Utils.getTotalItem(forecastMaterialDTO.getForecastMode(), forecastMaterialDTO.getStartDate(), forecastMaterialDTO.getEndDate(), item.getListLandMark()));
            } else if (item.getItemStartTime() == null && item.getItemEndTime() != null) {
                forecastOrderDetail.setEndTime(item.getItemEndTime());
                forecastOrderDetail.setStartTime(forecastMaterialDTO.getStartDate());
                forecastOrderDetail.setTotalRequest(Utils.getTotalItem(forecastMaterialDTO.getForecastMode(), forecastMaterialDTO.getStartDate(), forecastMaterialDTO.getEndDate(), item.getListLandMark()));
            } else {
                forecastOrderDetail.setEndTime(item.getItemEndTime());
                forecastOrderDetail.setStartTime(item.getItemStartTime());
                forecastOrderDetail.setTotalRequest(Utils.getTotalItem(forecastMaterialDTO.getForecastMode(), forecastMaterialDTO.getStartDate(), forecastMaterialDTO.getEndDate(), item.getListLandMark()));
            }
            list.add(forecastOrderDetail);
        }
        forecastOrderDetailRepository.saveAll(list);
    }

    /**
     * hàm gen tự động mã fc
     *
     * @return
     */
    public GenForecastCodeDTO createForecastCode() {
        GenForecastCodeDTO genForecastCodeDTO = new GenForecastCodeDTO();
        int forecastOrder = forecastRepository.getForecastCode() + 1;
        genForecastCodeDTO.setCreatedBy(SecurityUtils.getCurrentUserLogin().get());
        genForecastCodeDTO.setForecastCode("RAL-FC-" + forecastOrder);
        genForecastCodeDTO.setCreatedAt(new Date());
        return genForecastCodeDTO;
    }
}
