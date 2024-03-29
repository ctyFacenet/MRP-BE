package com.facenet.mrp.thread;

import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.service.dto.mrp.CloneBomDTO;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.utils.Utils;
import org.mapstruct.ap.shaded.freemarker.ext.util.IdentityHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class CloneBomService {
    private final static Logger log = LoggerFactory.getLogger(CloneBomService.class);
    private final CoittRepository coittRepository;
    private final ConcurrentMap<String, List<MrpDetailDTO>> bomTree = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, MrpDetailDTO> product = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, List<String>> item = new ConcurrentHashMap<>();


    public CloneBomService(CoittRepository coittRepository) {
        this.coittRepository = coittRepository;
    }
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void cloneBom(){
        log.info("----Start BOM clone----");
        MrpDetailDTO childDetailDTO;
        MrpDetailDTO parentDetailDTO;
        String key;
        String itemChildKey;
        String itemParentKey;

        bomTree.clear();
        product.clear();
        List<CloneBomDTO> cloneBomDTOFromSAP = coittRepository.cloneAllMrpProductBom();
//        System.err.println("coittRepository: " + cloneBomDTOFromSAP.size());
        if (!CollectionUtils.isEmpty(cloneBomDTOFromSAP)){
            for (CloneBomDTO dto : cloneBomDTOFromSAP){
                key = Utils.toItemKey(dto.getParentItemCode(), dto.getParentBomVersion());
                itemParentKey = Utils.toItemKey(dto.getParentItemCode(), dto.getParentItemName());
                itemChildKey =Utils.toItemKey(dto.getParentItemCode(), dto.getParentItemName());
//                System.err.println(itemChildKey);
                childDetailDTO = new MrpDetailDTO(
                    dto.getChildItemCode()
                    , dto.getChildItemName()
                    , dto.getAltItemCode()
                    , dto.getChildQuota()
                    , dto.getChildBomVersion()
                    , dto.getChildGroupItem()
                );
                List<MrpDetailDTO> bomList = bomTree.get(key);
                if (bomList == null) {
                    bomList = new ArrayList<>();
                    bomTree.put(key, bomList);
                }
                bomList.add(childDetailDTO);

                if (product.get(key) == null){
                    parentDetailDTO = new MrpDetailDTO(
                        dto.getParentItemCode()
                        , dto.getParentItemName()
                        , dto.getParentQuota()
                        , dto.getParentBomVersion()
                        , dto.getParentGroupItem()
                    );

                    product.put(key, parentDetailDTO);
                }

                //Lấy mapping cha con của item để đưa ra search
                List<String> productParentList = item.get(itemParentKey);
                if (productParentList == null){
                    productParentList = new ArrayList<>();
                    item.put(itemParentKey, productParentList);
                }
                productParentList.add(dto.getParentItemCode());

                List<String> productChildList = item.get(itemChildKey);
                if (productChildList == null){
                    productChildList = new ArrayList<>();
                    item.put(itemChildKey, productChildList);
                }
                productChildList.add(dto.getParentItemCode());
            }
        }
        log.info("----End BOM clone----");
    }

    public boolean isContainValidBom(String itemCode, String bomVersion) {
        return bomTree.containsKey(Utils.toItemKey(itemCode, bomVersion));
    }

    public Map<String, List<MrpDetailDTO>> getBomTree() {
        return bomTree;
    }

    public Map<String, MrpDetailDTO> getProduct() {
        return product;
    }

    public ConcurrentMap<String, List<String>> getItem() {
        return item;
    }
}
