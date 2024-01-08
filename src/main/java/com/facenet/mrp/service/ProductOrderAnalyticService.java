package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.repository.mrp.ForecastOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderDetailRepository;
import com.facenet.mrp.repository.mrp.ProductOrderRepository;
import com.facenet.mrp.service.dto.ProductOrderDto;
import com.facenet.mrp.service.dto.ProductOrderItemsDTO;
import com.facenet.mrp.service.dto.request.AddItemToBomRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.ProductOrderFilter;
import com.facenet.mrp.service.model.ProductOrderInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
}
