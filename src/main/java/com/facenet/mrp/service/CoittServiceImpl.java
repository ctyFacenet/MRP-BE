package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.MrpBomVersionDetail;
import com.facenet.mrp.domain.sap.CoittEntity;
import com.facenet.mrp.domain.sap.OitmEntity;
import com.facenet.mrp.repository.mrp.MrpBomDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderDetailRepository;
import com.facenet.mrp.repository.sap.*;
import com.facenet.mrp.service.dto.DetailBomVersionDTO;
import com.facenet.mrp.service.dto.ListBomDTO;
import com.facenet.mrp.service.dto.ViewBomDTO;
import com.facenet.mrp.service.dto.mrp.CurrentInventory;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.CoittMapper;
import com.facenet.mrp.service.model.BomFilterInput;
import com.facenet.mrp.service.model.RequestInput;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author trần đình thành
 * Service xử lý dữ liệu liên quan đến bảng bom trong sap
 */
@Service
public class CoittServiceImpl implements CoittService {
    private final Logger log = LogManager.getLogger(CoittServiceImpl.class);

    @Autowired
    private CoittRepository coittRepository;

    @Autowired
    ProductOrderDetailRepository orderDetailRepository;

    @Autowired
    MrpBomDetailRepository mrpBomDetailRepository;

    @Autowired
    CoittMapper mapper;

    @Autowired
    OitwRepository oitwRepository;
    @Autowired
    private OitmRepository oitmRepository;
    @Autowired
    private OittRepository oittRepository;

    /**
     * Hàm lấy danh sách Bom version có trạng thái là active cho mã sản phẩm tương ứng
     *
     * @param productCode
     * @return
     */
    @Override
    public ListBomDTO getListBomAndActive(String productCode) {
        ListBomDTO listBom = new ListBomDTO();
        log.info("------Start get list Bom by productCode-------");
        if (StringUtils.isEmpty(productCode)) throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");

//        ArrayList<String> list = new ArrayList<>();
//        List<CoittEntity> listCoitt = coittRepository.getListBomVersionByCode(productCode);
//        for (CoittEntity l : listCoitt) {
//            list.add(l.getuVersions().trim());
//        }

        OitmEntity item = oitmRepository.getByItemCode(productCode);
        if (item == null) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
//        if (!CollectionUtils.isEmpty(listCoitt)) {
//            listBom.setBomVersions(list);
//        }

        listBom.setProName(item.getItemName());
        listBom.setItemGroup(item.getItmsGrpCod().getItmsGrpCode());
        log.info("------End get list Bom by productCode-------");
        return listBom;
    }

    /**
     * Hàm lấy chi tiết danh sách nvl của Bom version có trạng thái là active theo mã sản phẩm
     *
     * @param productCode
     * @param version
     * @return
     */
    @Override
    public ViewBomDTO getDetailBomVersionWithProduct(String productCode, String version) throws CustomException {
        log.info("------Start get detail Bom with product-------");
        if (StringUtils.isEmpty(productCode) || StringUtils.isEmpty(version)) {
            log.error("tham số truyền vào không đúng!");
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");
        }
        CoittEntity coittEntity = coittRepository.getViewBomVersionWithProduct(productCode, version);
        if (coittEntity == null) {
            return null;
        }
        List<DetailBomVersionDTO> listCitt = coittRepository.getDetailBomVersionWithProduct(productCode, version);
        ViewBomDTO viewBomDTO = new ViewBomDTO();
        viewBomDTO.setProductCode(coittEntity.getuProNo());
        viewBomDTO.setProductName(coittEntity.getuProNam());
        viewBomDTO.setVersionBom(coittEntity.getuVersions());
        viewBomDTO.setRemark(coittEntity.getuRemark());
        viewBomDTO.setSpeciality(coittEntity.getuSpec());
        viewBomDTO.setList(listCitt);
        log.info("------End get detail Bom with product-------");
        return viewBomDTO;
    }

    public Page<DetailBomVersionDTO> listBomDetail(RequestInput<BomFilterInput> requestInput) {
        BomFilterInput filterInput = requestInput.getFilter();
        Pageable pageable = PageRequest.of(requestInput.getPageNumber(), requestInput.getPageSize());
        List<String> poCodeList = new ArrayList<>();

        String itemCode = requestInput.getFilter().getProductCode();
        String version = requestInput.getFilter().getBomVersion();
        String mrpPoId = requestInput.getFilter().getMrpPoId();

        if (StringUtils.isEmpty(itemCode)) {
            throw new CustomException("productCode.can.not.empty");
        }
        if (StringUtils.isEmpty(version)) {
            throw new CustomException("bomversion.can.not.empty");
        }

        log.info("get list bom by product code {} and bom version {}", itemCode, version);
        Page<DetailBomVersionDTO> detailBomVersionDTOS =oittRepository.getDetailBomVersionByProductPaging(pageable, itemCode);
        if (detailBomVersionDTOS != null && !CollectionUtils.isEmpty(detailBomVersionDTOS.getContent())) {
            //Lấy list PoCode
            detailBomVersionDTOS.forEach(detail -> {
                poCodeList.add(detail.getItemCode());
            });

            //Query hàng tồn kho
            List<CurrentInventory> currentInventoryList = oitwRepository.getAllInStockQuantityByItemCode(poCodeList);
            log.info("currentInventoryList.size: {}", currentInventoryList.size());
            log.info("currentInventoryList: {}", currentInventoryList.size());

            List<DetailBomVersionDTO> newResult = detailBomVersionDTOS.getContent()
                .stream()
                .map(detailBomVersionDTO -> {
                    detailBomVersionDTO.setQuantity(filterInput.getQuantity() * detailBomVersionDTO.getBasicQuantity());
                    detailBomVersionDTO.setItemGroup(
                        StringUtils.isEmpty(detailBomVersionDTO.getVersion()) ?
                            "NVL" : "BTP");
                    if (!CollectionUtils.isEmpty(currentInventoryList)) {
                        for (CurrentInventory currentInventory : currentInventoryList) {
                            if (currentInventory.getItemCode().equals(detailBomVersionDTO.getItemCode())) {
                                Double missingQ = (filterInput.getQuantity() * detailBomVersionDTO.getBasicQuantity()) - currentInventory.getCurrentQuantity();
                                String missingQResult = String.format("%.1f", missingQ);

                                detailBomVersionDTO.setInstockQuantity(currentInventory.getCurrentQuantity());
                                detailBomVersionDTO.setMissingQuantity(missingQ > 0 ? missingQResult : "0.0");
                                break;
                            } else {
                                detailBomVersionDTO.setInstockQuantity(0.0);
                                detailBomVersionDTO.setMissingQuantity("0.0");
                            }
                        }
                    } else {
                        detailBomVersionDTO.setInstockQuantity(0.0);
                        detailBomVersionDTO.setMissingQuantity("0.0");
                    }
                    return detailBomVersionDTO;
                })
                .collect(Collectors.toList());
            log.info("mergeWithMrpBom");
            mergeWithMrpBom(newResult, itemCode, mrpPoId, filterInput.getQuantity());
            detailBomVersionDTOS = new PageImpl<>(newResult, pageable, detailBomVersionDTOS.getTotalElements());
            return detailBomVersionDTOS;
        } else {
            // check mrp db
            List<DetailBomVersionDTO> newResult = new ArrayList<>();
            mergeWithMrpBom(newResult, itemCode, mrpPoId, filterInput.getQuantity());
            if (newResult.isEmpty())
                throw new CustomException("record.notfound");
            return new PageImpl<>(newResult, pageable, newResult.size());
        }
    }

    private void mergeWithMrpBom(List<DetailBomVersionDTO> detailBomVersionDTOList, String itemCode, String mrpPoId, Long poQuantity) {
        List<MrpBomVersionDetail> mrpBomVersionDetailList = mrpBomDetailRepository.findAllByMrpPoIdAndParentPathEndingWithIgnoreCaseAndStatus(mrpPoId, itemCode, "O");
        List<String> poCodeList = new ArrayList<>();
        DetailBomVersionDTO bomVersionDTO;

        if (!CollectionUtils.isEmpty(mrpBomVersionDetailList)) {

            mrpBomVersionDetailList.stream().forEach(mrpBomVersionDetail -> {
                poCodeList.add(mrpBomVersionDetail.getItemCode());
            });

            List<CurrentInventory> currentInventoryList = oitwRepository.getAllInStockQuantityByItemCode(poCodeList);

            log.info("mrpBomVersionDetailList size " + mrpBomVersionDetailList.size());
            for (MrpBomVersionDetail dto : mrpBomVersionDetailList) {
                String parentpath = dto.getParentPath();
                bomVersionDTO = mapper.convert(dto, poQuantity);

                if (!CollectionUtils.isEmpty(currentInventoryList)) {
                    for (CurrentInventory item : currentInventoryList) {
                        if (item.getItemCode().equals(dto.getItemCode())) {
                            double missingQ = (poQuantity * dto.getQuantity()) - item.getCurrentQuantity();
                            String missingQResult = String.format("%.1f", missingQ);

                            bomVersionDTO.setInstockQuantity(item.getCurrentQuantity());
                            bomVersionDTO.setMissingQuantity(missingQ > 0 ? missingQResult : "0.0");
                            break;
                        } else {
                            bomVersionDTO.setInstockQuantity(0.0);
                            bomVersionDTO.setMissingQuantity("0.0");
                        }
                    }
                } else {
                    bomVersionDTO.setInstockQuantity(0.0);
                    bomVersionDTO.setMissingQuantity("0.0");
                }
                log.info("mrpBomVersionDetail.getParentPath() {}", parentpath);
                detailBomVersionDTOList.add(bomVersionDTO);
            }
        }
    }
}
