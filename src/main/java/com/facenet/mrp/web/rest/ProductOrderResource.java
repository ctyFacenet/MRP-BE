package com.facenet.mrp.web.rest;

import com.facenet.mrp.domain.mrp.ProductOrder;
import com.facenet.mrp.repository.mrp.MrpRepository;
import com.facenet.mrp.repository.mrp.MrpSubRepository;
import com.facenet.mrp.security.SecurityUtils;
import com.facenet.mrp.service.ProductOrderService;
import com.facenet.mrp.service.dto.*;
import com.facenet.mrp.service.dto.request.SendAnalysisRequest;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.model.PageFilterInput;
import com.facenet.mrp.service.model.ProductOrderInput;
import com.facenet.mrp.service.model.ProductOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/product-orders")
public class ProductOrderResource {

    private static final Logger logger = LoggerFactory.getLogger(ProductOrderResource.class);

    private final ProductOrderService productOrderService;
    private final MrpSubRepository mrpSubRepository;
    private final MrpRepository mrpRepository;

    public ProductOrderResource(ProductOrderService productOrderService,
                                MrpSubRepository mrpSubRepository,
                                MrpRepository mrpRepository) {
        this.productOrderService = productOrderService;
        this.mrpSubRepository = mrpSubRepository;
        this.mrpRepository = mrpRepository;
    }

    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('DHSX', 'S', 'SELLORDER','VIEW')")
    public ResponseEntity<ProductOrderResponse> getAllPoPaging(@RequestBody ProductOrderInput input) throws IOException, org.apache.el.parser.ParseException {
        if (input == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        }
        ProductOrderResponse response = productOrderService.getAllProductOrderWithPaging(input);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/import-product-order/{isSend}")
    public ResponseEntity importOrder(@RequestParam("file") MultipartFile file,@PathVariable Boolean isSend) throws IOException, ParseException {
        if(file == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"file.import.empty");
        }
        productOrderService.importProductOrder(file.getInputStream(),isSend);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .errorCode("00")
                .message("Import đơn hàng thành công"));
    }

    @PostMapping("/import-product-order-csv/{isSend}")
    public ResponseEntity importOrderCsv(@RequestParam("file") MultipartFile file,@PathVariable Boolean isSend) throws IOException, ParseException {
        if(file == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"file.import.empty");
        }
        productOrderService.importProductOrderCsv(file.getInputStream(),isSend);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .errorCode("00")
                .message("Import đơn hàng thành công"));
    }
     @PostMapping("/new-product-order/{isSend}")
     @PreAuthorize("hasAnyAuthority('DHSX')")
    public ResponseEntity createNewProductOrder(@Valid @RequestBody List<ProductOrder> productOrder, @PathVariable Boolean isSend) throws ParseException {
        productOrderService.createNewProductOrder(productOrder,isSend);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .errorCode("00")
                .message("Thêm đơn hàng thành công"));
    }

    @PostMapping("/create-work-order")
    public String createWorkOrder(@RequestBody List<CreateWoFromMrp> createWoFromMrpsc) {
        return productOrderService.createWorkOrder(createWoFromMrpsc);
    }
    @GetMapping("/get-work-order/{po}")
    public List<WorkOrder> getWorkOrder(@PathVariable String po) {
        return productOrderService.getListWO(po);
    }

    @PutMapping("/new-status/{productOrderCode}/{status}")
    @PreAuthorize("hasAnyAuthority('DHSX')")
    public ResponseEntity updateStatusPO(@PathVariable String productOrderCode,@PathVariable Integer status){
        productOrderService.updateStatusPO(productOrderCode,status);
        return ResponseEntity.ok(
            new CommonResponse<>().isOk(true).message("Chuyển trạng thái thành công").errorCode("00"));
    }

    @PutMapping("/sendAnalysisPO/{productOrderCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity sendAnalysisPO(@PathVariable String productOrderCode){
        String in = productOrderService.sendAnalysisPO(productOrderCode);
        if(in.equals("ok")){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(true).message("Gửi phân tích thành công").errorCode("00"));
        }else {
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message(in).errorCode("400"));
        }
    }

    @PutMapping("/sendAnalysisProductInPO/{productOrderCode}/{productCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity sendAnalysisPO(@PathVariable String productOrderCode,@PathVariable String productCode){
        Integer in = productOrderService.sendAnalysisProductInPO(productOrderCode,productCode);
        if(in == -1 || in == -2){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("yêu cầu không hợp lệ").errorCode("400"));
        }
        return ResponseEntity.ok(
            new CommonResponse<>().isOk(true).message("Gửi phân tích thành công").errorCode("00"));
    }

    @PutMapping("/sendAnalysisListProductInPO")
    @PreAuthorize("hasAnyAuthority('DHSX', 'KHDH')")
    public ResponseEntity sendListAnalysisPO(@RequestBody List<SendAnalysisRequest> request){
        Integer in = productOrderService.sendAnalysisListProductInPO(request);
        if(in == -1 || in == -2){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("yêu cầu không hợp lệ").errorCode("400"));
        }
        return ResponseEntity.ok(
            new CommonResponse<>().isOk(true).message("Gửi phân tích thành công").errorCode("00"));
    }

    @PutMapping("/{productOrderCode}")
    @PreAuthorize("hasAnyAuthority('DHSX')")
    @Transactional
    public ResponseEntity deletePO(@PathVariable String productOrderCode){
        Integer in = productOrderService.deletePO(productOrderCode);
        mrpRepository.deleteAllBySoCode(productOrderCode);
        mrpSubRepository.deleteAllBySoCode(productOrderCode);
        if(in == -1){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("yêu cầu không hợp lệ").errorCode("400"));
        }
        if(in == -2){
            return ResponseEntity.ok(
                new CommonResponse<>().isOk(false).message("không có đơn hàng này").errorCode("404"));
        }
        return ResponseEntity.ok(
            new CommonResponse<>().isOk(true).message("xóa đơn hàng thành công").errorCode("00"));
    }

    @PutMapping("/update-product-order/{productOrderCode}")
    @PreAuthorize("hasAnyAuthority('DHSX', 'S')")
    public ResponseEntity updateProductOrder(
        @PathVariable("productOrderCode")String productOrderCode,
        @RequestBody ProductOrderDto dto
        ) {
        if (productOrderCode == null || dto == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"invalid.param");
        }
        productOrderService.updateProductOrder(productOrderCode, dto);
        return ResponseEntity.ok(
            new CommonResponse<>()
                .isOk(true)
                .errorCode("00")
                .message("Cập nhật đơn hàng thành công")
        );
    }


}
