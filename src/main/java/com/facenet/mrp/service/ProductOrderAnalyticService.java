package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.domain.mrp.ProductOrderDetail;
import com.facenet.mrp.domain.mrp.ResultMrpJsonEntity;
import com.facenet.mrp.repository.MrpAnalysisCache;
import com.facenet.mrp.repository.mrp.ForecastOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderRepository;
import com.facenet.mrp.repository.mrp.ResultMrpRepository;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.mrp.MrpDetailDTO;
import com.facenet.mrp.service.dto.request.AddItemToBomRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.ProductOrderFilter;
import com.facenet.mrp.service.model.ProductOrderInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductOrderAnalyticService {

    private static final Logger logger = LogManager.getLogger(ProductOrderAnalyticService.class);

    @Autowired
    private  ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductOrderDetailRepository productOrderDetailRepository;

    @Autowired
    private ForecastOrderDetailRepository forecastOrderDetailRepository;

    @Autowired
    ResultMrpRepository resultMrpRepository;

    @Autowired
    FileTransferService fileTransferService;

    @Autowired
    ObjectMapper objectMapper;

    private final MrpAnalysisCache mrpAnalysisCache;
    public ProductOrderAnalyticService(MrpAnalysisCache mrpAnalysisCache){
        this.mrpAnalysisCache = mrpAnalysisCache;
    }

    public Page<ProductOrderDto> getProductOrderAnalytics(ProductOrderInput input) throws CustomException {
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());

        ProductOrderFilter filter = input.getFilter();
        Page<ProductOrderDto> productOrders = productOrderRepository.getProductOrderByStatus(pageable,filter);
        if (productOrders != null) {
            for (ProductOrderDto dto : productOrders) {
                if (dto.getMrpPoId() == null){
                    dto.setMrpPoId(dto.getPoCode());
                }
            }

            return productOrders;
        } else {
            throw new CustomException("record.notfound");
        }
    }

    public void addItemToBom(AddItemToBomRequest request){

    }

    /**
     * Lấy danh sách sản phẩm trong đơn hàng
     * @param productOrderCode mã đơn hàng
     * @param productCode mã sản phầm nếu có, không có sẽ = null
     * @return
     */
    public CommonResponse<List<ProductOrderItemsDTO>> getAllProductDetail(String productOrderCode, String productCode) {
        ProductOrder productOrder = productOrderRepository.findByProductOrderCode(productOrderCode);
        if (productOrder == null) throw new CustomException(HttpStatus.NOT_FOUND,"record.notfound");
        // Lấy danh sách sản phẩm trong đơn hàng, nếu chọn phân tích sản phẩm trong đơn hàng thì productOrder != null
        List<ProductOrderItemsDTO> result = productOrderDetailRepository.findByMrpPoId(productOrder.getMrpPoId(), productCode);
        // Danh sách trống thì kiểm tra Forecast
        if (CollectionUtils.isEmpty(result)) {
           result = forecastOrderDetailRepository.getAllItems(productOrderCode);
        }
        if (CollectionUtils.isEmpty(result)) throw new CustomException(HttpStatus.NOT_FOUND, "record.notfound");
        return new CommonResponse<>().success().data(result);
    }

    public PageResponse<ProductOrderDTOAPS> sendPlaningForAPS(String ssId, String mrpCode){

        // lấy AdvancedMrpDTO trong cache theo ssId
        AdvancedMrpDTO input = mrpAnalysisCache.getMrpResult(ssId);

        // nếu AdvancedMrpDTO trong cache bị xóa
        if(input == null){
            // lấy ra đường dẫn file
            ResultMrpJsonEntity resultMrpJsonEntity = resultMrpRepository.getResultMrpJson(mrpCode);
            if(resultMrpJsonEntity != null){

                // lấy file theo đường dẫn
                File file = new File(resultMrpJsonEntity.getDataResultJson());

                String decompressedContent = null;
                try {
                    // đọc data trong file dưới dạng string
                    decompressedContent = fileTransferService.decompressGzipFile(file);

                    // convert sang CommonResponse
                    CommonResponse<AdvancedMrpDTO> readData = objectMapper.readValue(decompressedContent, CommonResponse.class);

                    // convert data sang AdvancedMrpDTO
                    input = objectMapper.convertValue(readData.getData(), AdvancedMrpDTO.class);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Lỗi phân tích");
            }
        }


        ProductOrderDTOAPS productOrderDTOAPS = new ProductOrderDTOAPS();
        ProductOrder productOrder = productOrderRepository.findByProductOrderCode(input.getSoCode());
        productOrderDTOAPS.setProductOrderId(productOrder.getProductOrderCode());
        productOrderDTOAPS.setCustomerCode(productOrder.getCustomerId());
        productOrderDTOAPS.setCustomerName(productOrder.getCustomerName());
        productOrderDTOAPS.setProductOrderType(productOrder.getProductOrderType());
        productOrderDTOAPS.setOrderDate(productOrder.getOrderDate());
        productOrderDTOAPS.setCompleteDate(productOrder.getDeliverDate());
        productOrderDTOAPS.setState(productOrder.getStatus());
        productOrderDTOAPS.setNote(productOrder.getNote());
        productOrderDTOAPS.setPlanningMode(input.getAnalysisPeriod());

        List<ProductOrderDetailDTOAPS> productOrderDetailDTOAPS = new ArrayList<>();
        for (ProductOrderDetail productOrderDetail : productOrder.getProductOrderDetails()){
            ProductOrderDetailDTOAPS orderDetailDTOAPS = new ProductOrderDetailDTOAPS();
            orderDetailDTOAPS.setProductCode(productOrderDetail.getProductCode());
            orderDetailDTOAPS.setProductName(productOrderDetail.getProductName());
            orderDetailDTOAPS.setQuantity(productOrderDetail.getQuantity());
            orderDetailDTOAPS.setBomVersion(productOrderDetail.getBomVersion());
            orderDetailDTOAPS.setStartDate(productOrderDetail.getOrderDate());
            orderDetailDTOAPS.setEndDate(productOrderDetail.getDeliverDate());
            orderDetailDTOAPS.setNote(productOrderDetail.getNote());

            //lấy ra landmark
            for (MrpDetailDTO mrpDetailDTO : input.getResultData()){
                if(orderDetailDTOAPS.getProductCode().equals(mrpDetailDTO.getItemCode())){
                    orderDetailDTOAPS.setDetailResult(mrpDetailDTO.getDetailResult());
                }
                getDetailResult(orderDetailDTOAPS, mrpDetailDTO);
            }
            productOrderDetailDTOAPS.add(orderDetailDTOAPS);
        }

        productOrderDTOAPS.setProductOrderItem(productOrderDetailDTOAPS);

        return (PageResponse<ProductOrderDTOAPS>) new PageResponse<ProductOrderDTOAPS>().data(productOrderDTOAPS).success();
    }


    //Đệ quy lấy ra landmark
    public ProductOrderDetailDTOAPS getDetailResult(ProductOrderDetailDTOAPS orderDetailDTOAPS, MrpDetailDTO mrpDetailDTO){
        if(mrpDetailDTO.getGroupItem().equals("NVL")) return orderDetailDTOAPS;
        for (MrpDetailDTO mrpDetailDTOGetChildren : mrpDetailDTO.getChildren()){
            if(orderDetailDTOAPS.getProductCode().equals(mrpDetailDTOGetChildren.getItemCode())){
                orderDetailDTOAPS.setDetailResult(mrpDetailDTOGetChildren.getDetailResult());
            }
            getDetailResult(orderDetailDTOAPS, mrpDetailDTOGetChildren);
        }
        return orderDetailDTOAPS;
    }

}
