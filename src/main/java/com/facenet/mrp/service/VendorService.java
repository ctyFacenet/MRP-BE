package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.ItemEntity;
import com.facenet.mrp.domain.mrp.VendorItemEntity;
import com.facenet.mrp.domain.mrp.VendorEntity;
import com.facenet.mrp.domain.sap.QOcrdEntity;
import com.facenet.mrp.repository.mrp.ItemRepository;
import com.facenet.mrp.repository.mrp.VendorItemRepository;
import com.facenet.mrp.repository.mrp.VendorEntityRepository;
import com.facenet.mrp.repository.sap.OitmEntityRepository;
import com.facenet.mrp.repository.sap.Pdn1Repository;
import com.facenet.mrp.service.dto.ListVendorDTO;
import com.facenet.mrp.service.dto.QListVendorDTO;
import com.facenet.mrp.service.dto.mrp.ItemEntityDto;
import com.facenet.mrp.service.dto.mrp.VendorEntityDto;
import com.facenet.mrp.service.dto.mrp.VendorItemEntityDto;
import com.facenet.mrp.service.mapper.VendorEntityMapper;
import com.facenet.mrp.service.model.PageFilterInput;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
public class VendorService {
    @Autowired
    private VendorEntityRepository VendorEntityRepository;

    @Autowired
    private VendorEntityMapper VendorEntityMapper;

    @Autowired
    private ListSaleService listSaleService;

    @Autowired
    @Qualifier("sapEntityManager")
    private EntityManager entityManagerSap;

    @Autowired
    private Pdn1Repository pdn1Repository;

    @Autowired
    private VendorItemRepository vendorItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OitmEntityRepository oitmEntityRepository;

    // Lấy danh sách vendors có phân trang
    public Page<VendorEntityDto> getAllVendors(PageFilterInput<VendorEntityDto> input) {
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());
        Specification<VendorEntity> spec = getVendorsSpecification(input.getFilter());
        Page<VendorEntity> vendorsPage = VendorEntityRepository.findAll(spec, pageable);
        return vendorsPage.map(VendorEntityMapper::toDto);
    }

    public static Specification<VendorEntity> getVendorsSpecification(VendorEntityDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("active"), 1));
            if (filter.getVendorCode() != null && !filter.getVendorCode().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("code"), "%" + filter.getVendorCode() + "%"));
            }
            if (filter.getVendorName() != null && !filter.getVendorName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getVendorName() + "%"));
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
    public VendorEntityDto createVendor(VendorEntityDto VendorEntityDto) {
        // Kiểm tra trùng mã code
        if (VendorEntityRepository.existsByVendorCode(VendorEntityDto.getVendorCode())) {
            throw new IllegalArgumentException("Vendor code already exists: " + VendorEntityDto.getVendorCode());
        }

        // Nếu không trùng mã code, tiếp tục tạo vendor
        VendorEntity VendorEntity = VendorEntityMapper.toEntity(VendorEntityDto);
        VendorEntity = VendorEntityRepository.save(VendorEntity);
        return VendorEntityMapper.toDto(VendorEntity);
    }

    // Cập nhật vendor
    public Optional<VendorEntityDto> updateVendor(int id, VendorEntityDto VendorEntityDto) {
        return VendorEntityRepository.findById(id)
            .map(existingVendor -> {
                // Chỉ cập nhật các trường có thay đổi (khác null)
                VendorEntity updatedVendor = VendorEntityMapper.partialUpdate(VendorEntityDto, existingVendor);
                // Lưu thay đổi vào database
                updatedVendor = VendorEntityRepository.save(updatedVendor);
                // Trả về DTO sau khi cập nhật
                return VendorEntityMapper.toDto(updatedVendor);
            });
    }
    @Scheduled(fixedDelay = 30 * 60 * 1000)
    @Transactional
    public void syncVendorsFromSap() {
        // Bước 1: Lấy tất cả các bản ghi hiện có trong bảng VendorEntity có cột sap = 1
        List<VendorEntity> existingVendors = VendorEntityRepository.findAllBySap(1);

        // Bước 2: Lấy dữ liệu từ SAP
        List<ListVendorDTO> vendorsFromSap = getAllVendor();

        // Bước 3: Tạo một tập hợp các code từ danh sách mới nhận được từ SAP
        Set<String> sapCodes = vendorsFromSap.stream()
            .map(ListVendorDTO::getVendorCode)
            .collect(Collectors.toSet());

        // Bước 4: Xóa tất cả các bản ghi có cột sap = 1 nhưng không có trong danh sách code từ SAP
        List<VendorEntity> vendorsToDelete = existingVendors.stream()
            .filter(vendor -> !sapCodes.contains(vendor.getVendorCode()))
            .collect(Collectors.toList());
        VendorEntityRepository.deleteAll(vendorsToDelete);

        // Bước 5: Cập nhật hoặc thêm mới các bản ghi dựa trên danh sách từ SAP
        List<VendorEntity> vendorsToSave = vendorsFromSap.stream()
            .map(dto -> {
                // Tìm bản ghi hiện có với code tương ứng
                VendorEntity existingVendor = existingVendors.stream()
                    .filter(vendor -> vendor.getVendorCode().equals(dto.getVendorCode()))
                    .findFirst()
                    .orElse(new VendorEntity());

                // Cập nhật thông tin
                existingVendor.setVendorCode(dto.getVendorCode());
                existingVendor.setVendorName(dto.getVendorName());
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
        VendorEntityRepository.saveAll(vendorsToSave);
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

    @Scheduled(fixedDelay = 30 * 60 * 1000)
    @Transactional
    public void syncItemsFromSap() {
        // Bước 1: Lấy tất cả các bản ghi hiện có trong bảng ItemEntity
        List<ItemEntity> existingItems = itemRepository.findAll();

        // Bước 2: Lấy dữ liệu từ SAP
        List<ItemEntityDto> itemsFromSap = oitmEntityRepository.findAllItemsFromSap();

        // Bước 3: Tạo một tập hợp các item code từ danh sách mới nhận được từ SAP
        Set<String> sapItemCodes = itemsFromSap.stream()
            .map(ItemEntityDto::getItemCode)
            .collect(Collectors.toSet());

        // Bước 4: Xóa tất cả các bản ghi không có trong danh sách code từ SAP
        List<ItemEntity> itemsToDelete = existingItems.stream()
            .filter(item -> !sapItemCodes.contains(item.getItemCode()))
            .collect(Collectors.toList());
        itemRepository.deleteAll(itemsToDelete);

        // Bước 5: Cập nhật hoặc thêm mới các bản ghi dựa trên danh sách từ SAP
        List<ItemEntity> itemsToSave = itemsFromSap.stream()
            .map(dto -> {
                // Tìm bản ghi hiện có với itemCode tương ứng
                ItemEntity existingItem = existingItems.stream()
                    .filter(item -> item.getItemCode().equals(dto.getItemCode()))
                    .findFirst()
                    .orElse(new ItemEntity());

                // Cập nhật thông tin
                existingItem.setItemCode(dto.getItemCode());
                existingItem.setItemName(dto.getItemName());

                return existingItem;
            })
            .collect(Collectors.toList());

        // Lưu dữ liệu vào database
        itemRepository.saveAll(itemsToSave);
    }

}
