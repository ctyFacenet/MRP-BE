package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.VendorsCombineEntity;
import com.facenet.mrp.domain.sap.QOcrdEntity;
import com.facenet.mrp.repository.mrp.VendorsCombineEntityRepository;
import com.facenet.mrp.service.dto.DataVendorAndSale;
import com.facenet.mrp.service.dto.ListVendorDTO;
import com.facenet.mrp.service.dto.QListVendorDTO;
import com.facenet.mrp.service.dto.VendorsCombineEntityDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    public void syncVendorsFromSap() {
        // Bước 1: Xóa tất cả các bản ghi trong bảng VendorsCombineEntity có cột sap = 1
        vendorsCombineEntityRepository.deleteAllBySap(1);

        // Bước 2: Lấy dữ liệu từ SAP
        List<ListVendorDTO> vendorsFromSap = getAllVendor();
        // Bước 3: Lưu các bản ghi mới vào bảng VendorsCombineEntity
        List<VendorsCombineEntity> newVendors = vendorsFromSap.stream()
            .map(dto -> {
                VendorsCombineEntity entity = new VendorsCombineEntity();
                entity.setCode(dto.getVendorCode());
                entity.setName(dto.getVendorName());
                entity.setEmail(dto.getEmail());
                entity.setAddress(dto.getAddress());
                entity.setTaxcode(dto.getTaxCode());
                entity.setSap(1); // Đặt cột sap là 1 cho biết rằng dữ liệu này đến từ SAP
                entity.setActive(1);
                return entity;
            })
            .collect(Collectors.toList());

        // Lưu dữ liệu vào database
        vendorsCombineEntityRepository.saveAll(newVendors);
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

}
