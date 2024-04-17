package com.facenet.mrp.service;


import com.facenet.mrp.domain.mrp.ColumnPropertyEntity;
import com.facenet.mrp.domain.mrp.KeyValueEntityV2;
import com.facenet.mrp.repository.mrp.ColumnPropertyRepository;
import com.facenet.mrp.repository.mrp.KeyDictionaryRepository;
import com.facenet.mrp.repository.mrp.KeyValueRepository;
import com.facenet.mrp.repository.mrp.KeyValueV2Repository;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.utils.Constants;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.facenet.mrp.service.utils.ExtractDateUtils.toLocalDateTime;


@Service
public class KeyValueService {

    private static final Logger log = LoggerFactory.getLogger(KeyValueService.class);
    private final KeyDictionaryRepository keyDictionaryRepository;
    private final ColumnPropertyRepository columnPropertyRepository;
    private final KeyValueRepository keyValueRepository;
    private final KeyValueV2Repository keyValueV2Repository;
//    private final BusinessLogService businessLogService;

    public KeyValueService(KeyDictionaryRepository keyDictionaryRepository, ColumnPropertyRepository columnPropertyRepository, KeyValueRepository keyValueRepository, KeyValueV2Repository keyValueV2Repository
//        , BusinessLogService businessLogService
    ) {
        this.keyDictionaryRepository = keyDictionaryRepository;
        this.columnPropertyRepository = columnPropertyRepository;
        this.keyValueRepository = keyValueRepository;
        this.keyValueV2Repository = keyValueV2Repository;
//        this.businessLogService = businessLogService;
    }

    public List<KeyValueEntityV2> createUpdateKeyValueOfEntity(Integer entityId, Map<String, String> inputMap, int entityType, boolean isUpdate) {
        return createUpdateKeyValueOfEntityWithLog(entityId, inputMap, entityType, isUpdate
//            , null
        );
    }

    public List<KeyValueEntityV2> createUpdateKeyValueOfEntityWithLog(Integer entityId, Map<String, String> inputMap, int entityType, boolean isUpdate
//        , BusinessLogEntity ownerLogEntity
    ) {
        List<ColumnPropertyEntity> columnPropertyEntities = columnPropertyRepository.getAllColumnByEntityType(entityType);
        Map<String, ColumnPropertyEntity> keyNameColumnMap = columnPropertyEntities.stream().collect(Collectors.toMap(ColumnPropertyEntity::getKeyName, Function.identity()));

        List<KeyValueEntityV2> insertList = new ArrayList<>();

//        List<BusinessLogDetailEntity> logEntities = new ArrayList<>();

        for (String keyName : inputMap.keySet()) {
//            BusinessLogDetailEntity businessLogDetailEntity = new BusinessLogDetailEntity();
            KeyValueEntityV2 keyValueEntity = null;
            if (isUpdate) {
                keyValueEntity = keyValueV2Repository.findByEntityKeyAndColumnPropertyEntity(entityId, keyNameColumnMap.get(keyName));
//                businessLogDetailEntity.setLastValue(keyValueEntity == null ? null : keyValueEntity.getCommonValue());
            }
            if (keyValueEntity == null) {
                keyValueEntity = new KeyValueEntityV2();
                keyValueEntity.setEntityType(entityType);
//                keyValueEntity.setKeyId();
                keyValueEntity.setColumnPropertyEntity(keyNameColumnMap.get(keyName));
                keyValueEntity.setEntityKey(entityId);
            }
            if (StringUtils.isEmpty(inputMap.get(keyName))) continue;
            int dataType = keyNameColumnMap.get(keyName).getDataType();
            try {
                if ((inputMap.get(keyName) == null)) {
                    keyValueEntity.setCommonValue(null);
                } else {
                    keyValueEntity.setCommonValue(inputMap.get(keyName));
//                    if(dataType != Constants.DATE_VALUE){
//
//                    }
                }
                String formatter = "dd/MM/yyyy";
                String formatter1 = "d/MM/yyyy";
                String formatter2 = "d/M/yyyy";
                String formatter3 = "yyyy/MM/dd'T'hh:mm:ss";
                String formatter4 = "yyyy-MM-dd";
                String formatter5 = "dd-MM-yyyy";
                String formatter6 = "MM-dd-yyyy";
                String formatter7 = "d-MM-yyyy";
                String formatter8 = "d-M-yyyy";
                String formatter9 = "yyyy-MM-dd'T'hh:mm:ss";
                String formatter10 = "YYYY-MM-DD'T'hh:mm:ss'Z'";
                String formatter11 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
                switch (dataType) {
                    case Constants.INT_VALUE:
                        keyValueEntity.setIntValue(Integer.parseInt(inputMap.get(keyName)));
                        break;
                    case Constants.FLOAT_VALUE:
                        keyValueEntity.setDoubleValue(Double.parseDouble(inputMap.get(keyName)));
                        break;
                    case Constants.STRING_VALUE:
                        keyValueEntity.setStringValue(inputMap.get(keyName));
                        break;
                    case Constants.JSON_VALUE:
                        keyValueEntity.setJsonValue(inputMap.get(keyName));
                        break;
                    case Constants.DATE_VALUE:
                        if (GenericValidator.isDate(inputMap.get(keyName), formatter, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter1, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter1)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter2, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter2)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter3, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter3)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter4, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter4)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter5, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter5)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter6, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter6)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter7, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter7)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter8, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter8)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter9, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter9)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter10, true))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter10)));
                        else if (GenericValidator.isDate(inputMap.get(keyName), formatter11, false))
                            keyValueEntity.setDateValue(LocalDate.parse(inputMap.get(keyName), DateTimeFormatter.ofPattern(formatter11)));
                        else throw new CustomException(HttpStatus.CONFLICT, "Ngày không hợp lệ");
                        break;
                    default:
                        throw new CustomException(HttpStatus.BAD_REQUEST, "Trường thông tin không hợp lệ: ", keyNameColumnMap.get(keyName).getKeyTitle());
                }

                // Set log
//                businessLogDetailEntity.setNewValue(keyValueEntity.getCommonValue());
//                businessLogDetailEntity.setKeyName(keyName);
//                logEntities.add(businessLogDetailEntity);
            } catch (Exception e) {
                //                log.error("Invalid data type", e);
                log.error(e.getMessage());
                throw new CustomException(HttpStatus.BAD_REQUEST, "Trường thông tin không hợp lệ: ", keyNameColumnMap.get(keyName).getKeyTitle());
            }
            insertList.add(keyValueEntity);
        }
        List<KeyValueEntityV2> result = keyValueV2Repository.saveAll(insertList);

//        if (isUpdate && ownerLogEntity != null)
//            businessLogService.insertUpdateDynamicPropertiesLog(ownerLogEntity, logEntities);
        return result;
    }

    public List<KeyValueEntityV2> createOrUpdateKeyValueEntity(Integer entityId, Map<String, String> inputMap, int entityType) {
        List<ColumnPropertyEntity> columnPropertyEntities = columnPropertyRepository.findByKeyNameInAndEntityTypeAndIsActiveTrue(inputMap.keySet(), entityType);
        //        List<KeyDictionaryEntity> keyDictionaryEntities = keyDictionaryRepository.findByKeyNameIn(inputMap.keySet());
        //        Map<String, KeyDictionaryEntity> keyNameIdMap = keyDictionaryEntities
        //            .stream()
        //            .collect(Collectors.toMap(KeyDictionaryEntity::getKeyName, Function.identity()));
        Map<String, ColumnPropertyEntity> keyNameColumnMap = columnPropertyEntities.stream().collect(Collectors.toMap(ColumnPropertyEntity::getKeyName, Function.identity()));
        List<KeyValueEntityV2> keyValueEntities = new ArrayList<>();
        for (String keyName : inputMap.keySet()) {
            KeyValueEntityV2 keyValueEntity = keyValueV2Repository.findByEntityKeyAndColumnPropertyEntity(entityId, keyNameColumnMap.get(keyName));

            if (keyValueEntity == null) {
                keyValueEntity = new KeyValueEntityV2();
                keyValueEntity.setEntityType(entityType);
                keyValueEntity.setEntityKey(entityId);
                keyValueEntity.setColumnPropertyEntity(keyNameColumnMap.get(keyName));
            }

            int dataType = keyNameColumnMap.get(keyName).getDataType();
            //            System.err.println(inputMap.get(keyName) + "----" + dataType);
            try {
                if ((inputMap.get(keyName) == null)) {
                    keyValueEntity.setCommonValue(null);
                } else {
                    keyValueEntity.setCommonValue(inputMap.get(keyName));
                }
                if (dataType == Constants.INT_VALUE) {
                    keyValueEntity.setIntValue(Integer.parseInt(inputMap.get(keyName)));
                } else if (dataType == Constants.FLOAT_VALUE) {
                    keyValueEntity.setDoubleValue(Double.parseDouble(inputMap.get(keyName)));
                } else if (dataType == Constants.STRING_VALUE) {
                    keyValueEntity.setStringValue(inputMap.get(keyName));
                } else if (dataType == Constants.JSON_VALUE) {
                    keyValueEntity.setJsonValue(inputMap.get(keyName));
                } else if (dataType == Constants.DATE_VALUE) {
                    keyValueEntity.setDateValue(toLocalDateTime(inputMap.get(keyName)));
                }
            } catch (Exception e) {
                log.error("Invalid data type", e);
                throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.datatype", keyNameColumnMap.get(keyName).getKeyTitle());
            }

            keyValueEntities.add(keyValueEntity);
        }
        return keyValueEntities;
    }
}
