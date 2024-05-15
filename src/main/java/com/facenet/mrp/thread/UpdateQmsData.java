package com.facenet.mrp.thread;

import com.facenet.mrp.domain.mrp.PqcStoreCheckEntity;
import com.facenet.mrp.repository.mrp.PqcStoreCheckRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UpdateQmsData {
    private final PqcStoreCheckRepository pqcStoreCheckRepository;

    public UpdateQmsData(PqcStoreCheckRepository pqcStoreCheckRepository) {
        this.pqcStoreCheckRepository = pqcStoreCheckRepository;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void updateDataQms(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<PqcStoreCheckEntity> storeCheckEntityList = pqcStoreCheckRepository.getAllIsNull();
        for (PqcStoreCheckEntity pqcStoreCheck: storeCheckEntityList){
            try {
                Date date = dateFormat.parse(pqcStoreCheck.getCheckDate());
                System.out.println(date);
                pqcStoreCheck.setImportDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
