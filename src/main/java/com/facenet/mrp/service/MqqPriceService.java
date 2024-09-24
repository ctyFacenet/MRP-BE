package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.LeadTimeEntity;
import com.facenet.mrp.domain.mrp.MqqPriceEntity;
import com.facenet.mrp.domain.sap.Pdn1Entity;
import com.facenet.mrp.repository.mrp.LeadTimeRepository;
import com.facenet.mrp.repository.mrp.MqqPriceRepository;
import com.facenet.mrp.repository.sap.Pdn1Repository;
import com.facenet.mrp.service.dto.MqqLeadTimeDTO;
import com.facenet.mrp.service.dto.MqqPriceDTO;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.LeadTimeMapper;
import com.facenet.mrp.service.mapper.MqqPriceMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MqqPriceService {
    private final Logger log = LogManager.getLogger(MqqPriceService.class);
    private final MqqPriceRepository mqqPriceRepository;
    private final LeadTimeRepository leadTimeRepository;
    private final MqqPriceMapper mqqPriceMapper;
    private final LeadTimeMapper leadTimeMapper;
    private final Pdn1Repository pdn1Repository;

    public MqqPriceService(MqqPriceRepository mqqPriceRepository, LeadTimeRepository leadTimeRepository, MqqPriceMapper mqqPriceMapper, LeadTimeMapper leadTimeMapper,
                           Pdn1Repository pdn1Repository) {
        this.mqqPriceRepository = mqqPriceRepository;
        this.leadTimeRepository = leadTimeRepository;
        this.mqqPriceMapper = mqqPriceMapper;
        this.leadTimeMapper = leadTimeMapper;
        this.pdn1Repository = pdn1Repository;
    }

    public void createMqqPrice (String vendorCode, String itemCode, MqqLeadTimeDTO dto){
        LeadTimeEntity leadtimeEntity;
        MqqPriceEntity priceEntity;

        leadtimeEntity = leadTimeMapper.dtoToEntity(vendorCode,itemCode,dto);
        log.info("-----leadtimeEntity:"+leadtimeEntity.getItemCode()+"/"+leadtimeEntity.getLeadTime());
        leadTimeRepository.save(leadtimeEntity);
        for (MqqPriceDTO mqqPriceDTO : dto.getMqqPriceList()) {
            priceEntity = mqqPriceMapper.DTOToEntity(mqqPriceDTO,vendorCode,itemCode,dto.getDueDate());
            priceEntity.setTimeEnd(dto.getDueDate());
            priceEntity.setIsPromotion(false);
            Integer id = priceEntity.getItemPriceId();
            mqqPriceRepository.save(priceEntity);
        }
    }

    public MqqLeadTimeDTO getAllLeadTimeAndMqqPrice (String vendorCode, String itemCode){
        return mqqPriceRepository.findMqqPriceAndLeadTime(vendorCode, itemCode);
    }

    public void deleteMqqPrice(String id){
        MqqPriceEntity entity = mqqPriceRepository.findByItemPriceId(Integer.valueOf(id));
        if (!ObjectUtils.isEmpty(entity)){
            entity.setIsActive(Byte.valueOf("2"));
            try {
                mqqPriceRepository.save(entity);
            }catch (Exception e){
                throw new CustomException("internal.error");
            }

        }else {
            log.info("Không tim thấy mqqPriceID = {}",id);
            throw new CustomException("record.notfound");
        }
    }
    @Scheduled(fixedDelay = 30 * 60 * 1000)
    @Transactional
    public void synchronizeMqqPrice() {
        // Bước 1: Lấy tất cả bản ghi sap = 1 từ bảng MqqPriceEntity
        List<MqqPriceEntity> mqqPriceEntities = mqqPriceRepository.findBySap(1);

        // Bước 2: Sử dụng map để lưu dữ liệu trong bảng MqqPriceEntity
        Map<String, List<MqqPriceEntity>> mqqPriceMap = mqqPriceEntities.stream()
            .collect(Collectors.groupingBy(mqq -> mqq.getItemCode() + "|" + mqq.getVendorCode()));

        // Bước 3: Lấy tất cả các bản ghi từ SAP (Pdn1Entity) và nhóm theo itemCode và baseCard
        List<Pdn1Entity> pdn1Records = pdn1Repository.findAllFrom2024();
        Map<String, List<Pdn1Entity>> pdn1GroupedRecords = pdn1Records.stream()
            .collect(Collectors.groupingBy(pdn1 -> pdn1.getItemCode() + "|" + pdn1.getBaseCard()));

        List<MqqPriceEntity> priceEntitiesToSave = new ArrayList<>();
        List<MqqPriceEntity> priceEntitiesToUpdate = new ArrayList<>();
        List<LeadTimeEntity> leadTimeEntitiesToSave = new ArrayList<>();

        // Bước 4: Lấy tất cả bản ghi từ LeadTimeEntity trước và lưu vào map
        Iterable<LeadTimeEntity> leadTimeEntitiesIterable = leadTimeRepository.findAll();
        List<LeadTimeEntity> leadTimeEntities = StreamSupport
            .stream(leadTimeEntitiesIterable.spliterator(), false)
            .collect(Collectors.toList());
        Map<String, LeadTimeEntity> leadTimeMap = leadTimeEntities.stream()
            .collect(Collectors.toMap(
                lead -> lead.getItemCode() + "|" + lead.getVendorCode(),
                lead -> lead,
                (existing, replacement) -> existing
            ));

        // Bước 5: Lặp qua từng cặp itemCode và baseCard từ SAP, lấy 3 bản ghi gần nhất
        for (Map.Entry<String, List<Pdn1Entity>> entry : pdn1GroupedRecords.entrySet()) {
            String key = entry.getKey();
            List<Pdn1Entity> sortedPdn1Records = entry.getValue().stream()
                .sorted(Comparator.comparing(Pdn1Entity::getDocDate).reversed())
                .limit(3)
                .collect(Collectors.toList());

            // Kiểm tra xem có tồn tại trong bảng MqqPriceEntity không
            List<MqqPriceEntity> existingMqqPrices = mqqPriceMap.get(key);

            if (existingMqqPrices == null) {
                // Bước 6: Nếu không tồn tại trong MqqPriceEntity, thêm mới
                for (Pdn1Entity pdn1 : sortedPdn1Records) {
                    MqqPriceEntity newPriceEntity = new MqqPriceEntity();
                    newPriceEntity.setVendorCode(pdn1.getBaseCard()); // map baseCard thành vendorCode
                    newPriceEntity.setItemCode(pdn1.getItemCode());
                    newPriceEntity.setPrice(pdn1.getPrice().doubleValue());
                    newPriceEntity.setCurrency(pdn1.getCurrency());
                    newPriceEntity.setPromotion(false);
                    newPriceEntity.setTimeStart(pdn1.getDocDate());  // docDate map vào timeStart
                    newPriceEntity.setIsActive((byte) 1); // giả định trạng thái active
                    newPriceEntity.setSap(1); // giữ giá trị sap = 1

                    priceEntitiesToSave.add(newPriceEntity);
                }
            } else {
                // Bước 7: Nếu đã tồn tại, cập nhật các bản ghi
                for (int i = 0; i < sortedPdn1Records.size(); i++) {
                    Pdn1Entity pdn1 = sortedPdn1Records.get(i);
                    if (i < existingMqqPrices.size()) {
                        MqqPriceEntity existingPrice = existingMqqPrices.get(i);
                        existingPrice.setPrice(pdn1.getPrice().doubleValue());
                        existingPrice.setCurrency(pdn1.getCurrency());
                        existingPrice.setTimeStart(pdn1.getDocDate());
                        existingPrice.setPromotion(false);
                        existingPrice.setIsActive((byte) 1); // Cập nhật trạng thái
                        priceEntitiesToUpdate.add(existingPrice);
                    } else {
                        // Nếu số bản ghi trong SAP nhiều hơn, thêm mới
                        MqqPriceEntity newPriceEntity = new MqqPriceEntity();
                        newPriceEntity.setVendorCode(pdn1.getBaseCard());
                        newPriceEntity.setItemCode(pdn1.getItemCode());
                        newPriceEntity.setPrice(pdn1.getPrice().doubleValue());
                        newPriceEntity.setCurrency(pdn1.getCurrency());
                        newPriceEntity.setTimeStart(pdn1.getDocDate());
                        newPriceEntity.setPromotion(false);
                        newPriceEntity.setIsActive((byte) 1);
                        newPriceEntity.setSap(1);

                        priceEntitiesToSave.add(newPriceEntity);
                    }
                }
                // Bước 8: Nếu số bản ghi trong MqqPriceEntity nhiều hơn, xóa bớt
                if (sortedPdn1Records.size() < existingMqqPrices.size()) {
                    for (int i = sortedPdn1Records.size(); i < existingMqqPrices.size(); i++) {
                        MqqPriceEntity priceToDelete = existingMqqPrices.get(i);
                        mqqPriceRepository.delete(priceToDelete);
                    }
                }
            }

            // Bước 9: Lưu bản ghi vào LeadTimeEntity nếu chưa có cặp vendorCode và itemCode
            if (!leadTimeMap.containsKey(key)) {
                String[] keyParts = key.split("\\|");
                String itemCode = keyParts[0];
                String vendorCode = keyParts[1];

                LeadTimeEntity newLeadTimeEntity = new LeadTimeEntity();
                newLeadTimeEntity.setItemCode(itemCode);
                newLeadTimeEntity.setVendorCode(vendorCode);
                newLeadTimeEntity.setLeadTime(null);
                newLeadTimeEntity.setIsActive((byte) 1);

                leadTimeEntitiesToSave.add(newLeadTimeEntity);
            }
        }

        // Bước 10: Lưu các bản ghi mới
        if (!priceEntitiesToSave.isEmpty()) {
            mqqPriceRepository.saveAll(priceEntitiesToSave);
        }

        if (!leadTimeEntitiesToSave.isEmpty()) {
            leadTimeRepository.saveAll(leadTimeEntitiesToSave);
        }

        // Bước 11: Cập nhật các bản ghi đã tồn tại
        if (!priceEntitiesToUpdate.isEmpty()) {
            mqqPriceRepository.saveAll(priceEntitiesToUpdate);
        }

        // Bước 12: Xóa các bản ghi MqqPriceEntity mà không có trong SAP
        for (String key : mqqPriceMap.keySet()) {
            if (!pdn1GroupedRecords.containsKey(key)) {
                List<MqqPriceEntity> pricesToDelete = mqqPriceMap.get(key);
                mqqPriceRepository.deleteAll(pricesToDelete);
            }
        }
    }


}
