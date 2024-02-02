
package com.facenet.mrp.service.utils;

import com.facenet.mrp.service.exception.CustomException;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class ExcelUtils {

    private static final Logger log = LogManager.getLogger(ExcelUtils.class);

    public static Double getNumberCellValue(Cell cell){
        System.out.println("cell.getCellType()"+cell.getCellType());
        switch (cell.getCellType()){
            case STRING: {
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (Exception e) {
                    log.info(e.getMessage());
                    return null;
                }
            }
            case FORMULA:
                switch (cell.getCachedFormulaResultType()){
                    case STRING:
                        return Double.parseDouble(cell.getStringCellValue());
                    case NUMERIC:
                        return (double) Math.round(cell.getNumericCellValue());
                    default:
                        return Double.parseDouble(cell.getCellFormula());
                }
            default:
                return cell.getNumericCellValue();
        }
    }



    public static String getStringCellValue(Cell cell){
        if(cell == null || cell.getCellType() == null) return null;
        switch (cell.getCellType()){
            case STRING:
                return cell.getStringCellValue().trim();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return String.valueOf(Double.valueOf(cell.getNumericCellValue()));
        }
    }

    public static Integer getIntegerCellValue(Cell cell){
        switch (cell.getCellType()){
            case STRING: {
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (Exception e) {
                    log.info(e.getMessage());
                    return null;
                }
            }
            case FORMULA:
                switch (cell.getCachedFormulaResultType()){
                    case STRING:
                        return Integer.parseInt(cell.getStringCellValue());
                    case NUMERIC:
                        return (int)Math.round(cell.getNumericCellValue());
                    default:
                        return Integer.parseInt(cell.getCellFormula());
                }
            default:
                return Double.valueOf(cell.getNumericCellValue()).intValue();
        }
    }

    public static void validateRow(Row row, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (isEmpty(row.getCell(i)))
                throw new CustomException(HttpStatus.BAD_REQUEST, "cell.must.not.empty", String.valueOf(i + 1), String.valueOf(row.getRowNum() + 1));
        }
    }

    public static void validateRowCsv(CSVRecord csvRecord, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (csvRecord.get(i).trim().isEmpty())
                throw new CustomException(HttpStatus.BAD_REQUEST, "cell.must.not.empty", String.valueOf(i + 1), String.valueOf(csvRecord.getRecordNumber() + 1));
        }
    }

    public static boolean isEmpty(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return true;
        }
        return !StringUtils.hasText(getStringCellValue(cell));
    }
}
