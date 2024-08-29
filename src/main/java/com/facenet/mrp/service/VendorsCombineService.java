package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.VendorItemEntity;
import com.facenet.mrp.domain.mrp.VendorsCombineEntity;
import com.facenet.mrp.domain.sap.QOcrdEntity;
import com.facenet.mrp.repository.mrp.VendorItemRepository;
import com.facenet.mrp.repository.mrp.VendorsCombineEntityRepository;
import com.facenet.mrp.repository.sap.Pdn1Repository;
import com.facenet.mrp.service.dto.DataVendorAndSale;
import com.facenet.mrp.service.dto.ListVendorDTO;
import com.facenet.mrp.service.dto.QListVendorDTO;
import com.facenet.mrp.service.dto.VendorsCombineEntityDto;
import com.facenet.mrp.service.dto.mrp.VendorItemEntityDto;
import com.facenet.mrp.service.dto.response.PageResponse;
import com.facenet.mrp.service.mapper.VendorsCombineEntityMapper;
import com.facenet.mrp.service.model.PageFilterInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

@Service
public class VendorsCombineService {
    @Autowired
    private VendorsCombineEntityRepository vendorsCombineEntityRepository;

    @Autowired
    private VendorsCombineEntityMapper vendorsCombineEntityMapper;

    @Autowired
    private ListSaleService listSaleService;

    @Autowired
    @Qualifier("sapEntityManager")
    private EntityManager entityManagerSap;

    @Autowired
    private Pdn1Repository pdn1Repository;

    @Autowired
    private VendorItemRepository vendorItemRepository;

    // Lấy danh sách vendors có phân trang
    public Page<VendorsCombineEntityDto> getAllVendors(PageFilterInput<VendorsCombineEntityDto> input) {
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        Specification<VendorsCombineEntity> spec = getVendorsSpecification(input.getFilter());
        Page<VendorsCombineEntity> vendorsPage = vendorsCombineEntityRepository.findAll(spec, pageable);
        return vendorsPage.map(vendorsCombineEntityMapper::toDto);
    }

    public static Specification<VendorsCombineEntity> getVendorsSpecification(VendorsCombineEntityDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("active"), 1));
            if (filter.getCode() != null && !filter.getCode().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("code"), "%" + filter.getCode() + "%"));
            }
            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
            }
            if (filter.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), filter.getActive()));
            }
            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + filter.getEmail() + "%"));
            }
            if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + filter.getAddress() + "%"));
            }
            if (filter.getTaxcode() != null && !filter.getTaxcode().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("taxcode"), "%" + filter.getTaxcode() + "%"));
            }
            if (filter.getSap() != null) {
                predicates.add(criteriaBuilder.equal(root.get("sap"), filter.getSap()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Thêm mới vendor
    public VendorsCombineEntityDto createVendor(VendorsCombineEntityDto vendorsCombineEntityDto) {
        // Kiểm tra trùng mã code
        if (vendorsCombineEntityRepository.existsByCode(vendorsCombineEntityDto.getCode())) {
            throw new IllegalArgumentException("Vendor code already exists: " + vendorsCombineEntityDto.getCode());
        }

        // Nếu không trùng mã code, tiếp tục tạo vendor
        VendorsCombineEntity vendorsCombineEntity = vendorsCombineEntityMapper.toEntity(vendorsCombineEntityDto);
        vendorsCombineEntity = vendorsCombineEntityRepository.save(vendorsCombineEntity);
        return vendorsCombineEntityMapper.toDto(vendorsCombineEntity);
    }

    // Cập nhật vendor
    public Optional<VendorsCombineEntityDto> updateVendor(int id, VendorsCombineEntityDto vendorsCombineEntityDto) {
        return vendorsCombineEntityRepository.findById(id)
            .map(existingVendor -> {
                // Chỉ cập nhật các trường có thay đổi (khác null)
                VendorsCombineEntity updatedVendor = vendorsCombineEntityMapper.partialUpdate(vendorsCombineEntityDto, existingVendor);
                // Lưu thay đổi vào database
                updatedVendor = vendorsCombineEntityRepository.save(updatedVendor);
                // Trả về DTO sau khi cập nhật
                return vendorsCombineEntityMapper.toDto(updatedVendor);
            });
    }
    @Scheduled(fixedDelay = 30 * 60 * 1000)
    @Transactional
    public void syncVendorsFromSap() {
        // Bước 1: Lấy tất cả các bản ghi hiện có trong bảng VendorsCombineEntity có cột sap = 1
        List<VendorsCombineEntity> existingVendors = vendorsCombineEntityRepository.findAllBySap(1);

        // Bước 2: Lấy dữ liệu từ SAP
        List<ListVendorDTO> vendorsFromSap = getAllVendor();

        // Bước 3: Tạo một tập hợp các code từ danh sách mới nhận được từ SAP
        Set<String> sapCodes = vendorsFromSap.stream()
            .map(ListVendorDTO::getVendorCode)
            .collect(Collectors.toSet());

        // Bước 4: Xóa tất cả các bản ghi có cột sap = 1 nhưng không có trong danh sách code từ SAP
        List<VendorsCombineEntity> vendorsToDelete = existingVendors.stream()
            .filter(vendor -> !sapCodes.contains(vendor.getCode()))
            .collect(Collectors.toList());
        vendorsCombineEntityRepository.deleteAll(vendorsToDelete);

        // Bước 5: Cập nhật hoặc thêm mới các bản ghi dựa trên danh sách từ SAP
        List<VendorsCombineEntity> vendorsToSave = vendorsFromSap.stream()
            .map(dto -> {
                // Tìm bản ghi hiện có với code tương ứng
                VendorsCombineEntity existingVendor = existingVendors.stream()
                    .filter(vendor -> vendor.getCode().equals(dto.getVendorCode()))
                    .findFirst()
                    .orElse(new VendorsCombineEntity());

                // Cập nhật thông tin
                existingVendor.setCode(dto.getVendorCode());
                existingVendor.setName(dto.getVendorName());
                existingVendor.setEmail(dto.getEmail());
                existingVendor.setAddress(dto.getAddress());
                existingVendor.setTaxcode(dto.getTaxCode());
                existingVendor.setFax(dto.getFaxNumber());
                existingVendor.setPhone(dto.getPhoneNumber());
                existingVendor.setCurrency(dto.getTransactionMoney());
                existingVendor.setOtherName(dto.getOtherName());
                existingVendor.setSap(1); // Đặt cột sap là 1 cho biết rằng dữ liệu này đến từ SAP
                existingVendor.setActive(1);

                return existingVendor;
            })
            .collect(Collectors.toList());

        // Lưu dữ liệu vào database
        vendorsCombineEntityRepository.saveAll(vendorsToSave);
    }

    public List<ListVendorDTO> getAllVendor(){
        QOcrdEntity qOcrdEntity = QOcrdEntity.ocrdEntity;
        JPAQuery<ListVendorDTO> query = new JPAQueryFactory(entityManagerSap)
            .selectDistinct(
                new QListVendorDTO(
                    qOcrdEntity.cardCode,
                    qOcrdEntity.cardName,
                    qOcrdEntity.cartFName,
                    qOcrdEntity.EMail,
                    qOcrdEntity.address,
                    qOcrdEntity.currency,
                    qOcrdEntity.phone1,
                    qOcrdEntity.fax,
                    qOcrdEntity.validFor,
                    qOcrdEntity.frozenFor
                )
            )
            .from(qOcrdEntity);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qOcrdEntity.cardType.eq("S"));
        query.where(booleanBuilder);
        List<ListVendorDTO> result = query.fetch();
        return result;
    }

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    @Transactional
    public void syncVendorsItemFromSap() {
        // Bước 1: Lấy tất cả các bản ghi hiện có trong bảng VendorItemEntity có cột sap = 1
        List<VendorItemEntity> existingVendorItems = vendorItemRepository.findAllBySap(1);

        // Bước 2: Lấy dữ liệu từ SAP
        List<VendorItemEntityDto> vendorItemsFromSap = pdn1Repository.findDistinctVendorItems();

        // Bước 3: Tạo một tập hợp các code từ danh sách mới nhận được từ SAP
        Set<String> sapVendorItemCodes = vendorItemsFromSap.stream()
            .map(dto -> dto.getVendorCode() + "_" + dto.getItemCode())
            .collect(Collectors.toSet());

        // Bước 4: Xóa tất cả các bản ghi có cột sap = 1 nhưng không có trong danh sách code từ SAP
        List<VendorItemEntity> vendorItemsToDelete = existingVendorItems.stream()
            .filter(item -> !sapVendorItemCodes.contains(item.getVendorCode() + "_" + item.getItemCode()))
            .collect(Collectors.toList());
        vendorItemRepository.deleteAll(vendorItemsToDelete);

        // Bước 5: Cập nhật hoặc thêm mới các bản ghi dựa trên danh sách từ SAP
        List<VendorItemEntity> vendorItemsToSave = vendorItemsFromSap.stream()
            .map(dto -> {
                // Tìm bản ghi hiện có với vendorCode và itemCode tương ứng
                VendorItemEntity existingVendorItem = existingVendorItems.stream()
                    .filter(item -> item.getVendorCode().equals(dto.getVendorCode()) && item.getItemCode().equals(dto.getItemCode()))
                    .findFirst()
                    .orElse(new VendorItemEntity());

                // Cập nhật thông tin
                existingVendorItem.setVendorCode(dto.getVendorCode());
                existingVendorItem.setItemCode(dto.getItemCode());
//                existingVendorItem.setTimeUsed(0);
                existingVendorItem.setSap(1); // Đặt cột sap là 1 cho biết rằng dữ liệu này đến từ SAP

                return existingVendorItem;
            })
            .collect(Collectors.toList());

        // Lưu dữ liệu vào database
        vendorItemRepository.saveAll(vendorItemsToSave);
    }

}
