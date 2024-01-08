package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ForecastOrderDetailEntity;
import com.facenet.mrp.domain.mrp.MrpBomVersionDetail;
import com.facenet.mrp.domain.mrp.ProductOrderDetail;
import com.facenet.mrp.repository.mrp.ForecastOrderDetailRepository;
import com.facenet.mrp.repository.mrp.MrpBomDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderDetailRepository;
import com.facenet.mrp.repository.sap.CoittRepository;
import com.facenet.mrp.service.dto.DetailBomVersionDTO;
import com.facenet.mrp.service.dto.mrp.ItemQuantity;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.request.AddItemToBomRequest;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.MrpBomDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MrpBomDetailService {

    private Logger logger = LogManager.getLogger(MrpBomDetailService.class);
    @Autowired
    private MrpBomDetailRepository repository;

    @Autowired
    private CoittRepository coittRepository;
    @Autowired
    private MrpBomDetailMapper mapper;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductOrderDetailRepository productOrderDetailRepository;

    @Autowired
    private ForecastOrderDetailRepository forecastOrderDetailRepository;

    public void addBomVersionDetail(AddItemToBomRequest request){
        logger.info("check request exist in mrp database");
        String parentPath = request.getParentPath();
        if(StringUtils.isEmpty(parentPath)){
            throw new CustomException("parent_path.can.not.empty");
        }
        MrpBomVersionDetail mrpBomVersionDetail = repository.findByMrpPoIdAndParentPathAndItemCodeIgnoreCase(
            request.getMrpPoId(),request.getParentPath(),request.getItemCode());
        if(mrpBomVersionDetail != null){
            throw new CustomException("item_code.is.already.exist",request.getItemCode());
        }

        String parentItemCode = parentPath.split("_")[parentPath.split("_").length-1];
        logger.info("parentItemCode " + parentItemCode);
        checkExistItemCodeSAP(parentItemCode, request.getParentVersion(),request.getItemCode());
        logger.info("dto.status: {}", request.getStatus());
        logger.info("dto.whsCode: {}", request.getWhsCode());

        //Add count NVL vào vật tư trong đơn hàng ở bảng Product_order_detail
        addCountMaterial(request);

        MrpBomVersionDetail entity = mapper.DTOToEntity(request);
        repository.save(entity);
    }

    private void checkExistItemCodeSAP(String parentItemCode, String version,String itemCode){
        DetailBomVersionDTO dto;
        if(StringUtils.isEmpty(version)){
            dto = coittRepository.getDetailBomVersionByProductAndItemCode(parentItemCode, itemCode);
        }else{
            dto = coittRepository.getDetailBomVersionByProductAndItemCode(parentItemCode,version, itemCode);
        }

        if(dto != null){
            throw new CustomException("item_code.is.already.exist",itemCode);
        }
    }

    private void addCountMaterial(AddItemToBomRequest request){
        ItemQuantity countChildren = new ItemQuantity();
        List<MrpDetailDTO> detailDTOS;

        String parentCode = request.getParentPath().split("_")[1];
        countChildren.setQuantity(0.0);

        if (request.getMrpPoId().contains("RAL-FC")){
            //Tìm được sản phẩm trong đơn hàng
            //=> lấy được số lượng hiện tại của NVl có trong Tp
            ForecastOrderDetailEntity forecastOrderDetail = forecastOrderDetailRepository.getForecastOrderDetailEntitiesByItem(request.getMrpPoId(), parentCode);

            //Tìm so lượng NVL có trong vật tư được thêm
            if (request.getItemGroup().equals("NVL")){
                countChildren.setQuantity(1.0);
            }else {
                //query lấy children của BTP
                detailDTOS = coittRepository.getAllMrpProductBom(request.getItemCode(), request.getVersion());
                logger.info("children của sản phẩm: {}", detailDTOS);

                //Cho vào hàm đệ quy để tìm các NVl/BTp con trong BTP
                productOrderService.getChildrenCountOfProduct(countChildren, detailDTOS);

                System.err.println("countChildren dff: " + countChildren.getQuantity());
            }

            //Lấy số lương NVL có trong BTP trước + số lượng vật tư mới
            int materialChildrenCount = (forecastOrderDetail.getMaterialChildrenCount() == null)? 0 : forecastOrderDetail.getMaterialChildrenCount();
            forecastOrderDetail.setMaterialChildrenCount(materialChildrenCount + countChildren.getQuantity().intValue());

            //Update nó vào db cho TP
            forecastOrderDetailRepository.save(forecastOrderDetail);

        } else {
            //Tìm được sản phẩm trong đơn hàng
            //=> lấy được số lượng hiện tại của NVl có trong Tp
            ProductOrderDetail productOrderDetail = productOrderDetailRepository.getProductOrderDetailByItem(request.getMrpPoId(), parentCode);

            //Tìm so lượng NVL có trong vật tư được thêm
            if (request.getItemGroup().equals("NVL")){
                countChildren.setQuantity(1.0);
            }else {
                //query lấy children của sản phẩm TP
                detailDTOS = coittRepository.getAllMrpProductBom(request.getItemCode(), request.getVersion());
                logger.info("children của sản phẩm: {}", detailDTOS);

                //Cho vào hàm đệ quy để tìm các NVl/BTp con trong TP
                productOrderService.getChildrenCountOfProduct(countChildren, detailDTOS);

                System.err.println("countChildren dff: " + countChildren.getQuantity());
            }

            //Lấy số lương NVL có trong TP trước + số lượng vật tư mới
            productOrderDetail.setMaterialChildrenCount(productOrderDetail.getMaterialChildrenCount() + countChildren.getQuantity().intValue());

            //Update nó vào db cho TP
            productOrderDetailRepository.save(productOrderDetail);

        }


    }
}
