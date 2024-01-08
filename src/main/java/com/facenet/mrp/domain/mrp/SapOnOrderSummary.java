package com.facenet.mrp.domain.mrp;

import com.facenet.mrp.service.dto.mrp.OnOrderMonitoringDTO;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "sap_on_order_summary", schema = "material_requirements_planning", catalog = "")
@EntityListeners(AuditingEntityListener.class)
@NamedNativeQuery(
    name = "get_on_order_monitoring_dto",
    query = "select b.*,\n" +
        "(b.so_luong_nhap_kho*100/b.so_luong_dat_hang) tien_do_mua_hang,\n" +
        "(case when b.ngay_tao_po > b.pr_due_date then to_days( b.ngay_tao_po) - to_days( b.pr_due_date) else 0 end) so_ngay_qua_han\n" +
        "from \n" +
        "(\n" +
        "select soos.item_code,item_name , po_code ,\n" +
        "(case when soos.`type`  ='PR' then pr_code   end) pr_code ,\n" +
        "(case when soos.`type`  ='PO' then contract_numer  end) so_hop_dong,\n" +
        "(case when soos.`type`  ='PR' then provider_code  end) ma_ncc,\n" +
        "(case when soos.`type`  ='PO' then provider_name  end) ten_ncc,\n" +
        "(case when soos.`type`  ='PO' then due_date  end) ngay_ve_du_kien,\n" +
        "(case when soos.`type`  ='PR' then due_date  end) pr_due_date,\n" +
        "(case when soos.`type`  ='PO' then po_create_date  end) ngay_tao_po,\n" +
        "(case when soos.`type`  ='PO' then create_po_user  end) nguoi_tao_po,\n" +
        "(case when soos.`type`  ='GRPO' then due_date  end) ngay_nhap_kho,\n" +
        "(case when soos.`type`  ='GRPO' then quantity  end) so_luong_nhap_kho,\n" +
        "(case when soos.`type`  ='PO' then quantity  end) so_luong_dat_hang,\n" +
        "type,\n" +
        "mrp_code,\n" +
        "so_code \n" +
        "from sap_on_order_summary soos\n" +
        "where po_code is not null \n" +
        "and status = 'O' "+
        "group by po_code  \n" +
        ") b" +
        " where (?1 is null or b.pr_code like ?1)" +
        " and (?2 is null or b.po_code like ?2)" +
        " and (?3 is null or b.so_hop_dong like ?3)" +
        " and (?4 is null or b.ma_ncc like ?4)" +
        " and (?5 is null or b.item_code like ?5)"+
        " and (?6 is null or b.ten_ncc like ?6)" +
        " and (?7 is null or (b.ngay_ve_du_kien between ?7 and ?8))" +
        " and (?9 is null or b.nguoi_tao_po like ?9)" +
        " and (?10 is null or (b.ngay_tao_po between ?10 and ?11))"+
        " and (?12 is null or b.item_name like ?12)",
    resultSetMapping = "on_order_monitoring_dto"
)
@SqlResultSetMapping(
    name = "on_order_monitoring_dto",
    classes = @ConstructorResult(
        targetClass = OnOrderMonitoringDTO.class,
        columns = {
            @ColumnResult(name = "item_code", type = String.class),
            @ColumnResult(name = "item_name", type = String.class),
            @ColumnResult(name = "po_code", type = String.class),
            @ColumnResult(name = "pr_code", type = String.class),
            @ColumnResult(name = "so_hop_dong", type = String.class),
            @ColumnResult(name = "ma_ncc", type = String.class),
            @ColumnResult(name = "ten_ncc", type = String.class),
            @ColumnResult(name = "ngay_ve_du_kien", type = Date.class),
            @ColumnResult(name = "ngay_tao_po", type = Date.class),
            @ColumnResult(name = "nguoi_tao_po", type = String.class),
            @ColumnResult(name = "so_luong_dat_hang", type = Double.class),
            @ColumnResult(name = "tien_do_mua_hang", type = Integer.class),
            @ColumnResult(name = "so_ngay_qua_han", type = Integer.class),
            @ColumnResult(name = "type", type = String.class),
            @ColumnResult(name = "mrp_code", type = String.class),
            @ColumnResult(name = "so_code", type = String.class),
        }
    )
)

@NamedNativeQuery(name = "get_on_order_monitoring_dto_with_detail", query = "select b.*,\n" +
    "(case when b.so_luong_nhap_kho is not null then (b.so_luong_dat_hang - b.so_luong_nhap_kho) else b.so_luong_dat_hang end) so_luong_chua_ve," +
    "soodd.*\n" +
    "from \n" +
    "(\n" +
    "select soos.item_code,item_name , po_code,id, \n" +
    "max(case when soos.`type`  ='PR' then pr_code  end) pr_code ,\n" +
    "max(case when soos.`type`  ='PO' then contract_numer  end) so_hop_dong,\n" +
    "max(case when soos.`type`  ='PR' then provider_code  end) ma_ncc,\n" +
    "max(case when soos.`type`  ='PO' then provider_name  end) ten_ncc,\n" +
    "max(case when soos.`type`  ='PO' then due_date  end) ngay_ve_du_kien,\n" +
    "max(case when soos.`type`  ='PR' then due_date  end) pr_due_date,\n" +
    "max(case when soos.`type`  ='PO' then po_create_date  end) ngay_tao_po,\n" +
    "max(case when soos.`type`  ='PO' then create_po_user  end) nguoi_tao_po,\n" +
    "max(case when soos.`type`  ='GRPO' then due_date  end) ngay_nhap_kho,\n" +
    "max(case when soos.`type`  ='GRPO' then quantity  end) so_luong_nhap_kho,\n" +
    "max(case when soos.`type`  ='PO' then quantity  end) so_luong_dat_hang,\n" +
    "type\n" +
    "from sap_on_order_summary soos \n" +
    "where po_code is not null\n" +
    "and status = 'O' "+
    "group by item_code,po_code \n" +
    "order by soos.created_at desc\n"+
    ") b left join sap_on_order_duration_detail soodd  on b.id = soodd.fk_id" +
    " where (?1 is null or b.pr_code like ?1)" +
    " and (?2 is null or b.po_code like ?2)" +
    " and (?3 is null or b.so_hop_dong = ?3)" +
    " and (?4 is null or b.ma_ncc = ?4)" +
    " and (?5 is null or b.item_code like ?5)"+
    " and (?6 is null or b.ten_ncc = ?6)" +
    " and (?7 is null or b.ngay_ve_du_kien = ?7)" +
    " and (?8 is null or b.nguoi_tao_po = ?8)" +
    " and (?9 is null or b.ngay_tao_po = ?9)"+
    " and (?10 is null or b.item_name like ?10)"+
    " order by soodd.detail_due_date asc",
    resultSetMapping = "on_order_monitoring_dto_with_detail")

@SqlResultSetMapping(
    name = "on_order_monitoring_dto_with_detail",
    classes = @ConstructorResult(
    targetClass = OnOrderMonitoringDTO.class,
        columns = {
            @ColumnResult(name = "id", type = Integer.class),
            @ColumnResult(name = "item_code", type = String.class),
            @ColumnResult(name = "item_name", type = String.class),
            @ColumnResult(name = "po_code", type = String.class),
            @ColumnResult(name = "pr_code", type = String.class),
            @ColumnResult(name = "so_hop_dong", type = String.class),
            @ColumnResult(name = "ma_ncc", type = String.class),
            @ColumnResult(name = "ten_ncc", type = String.class),
            @ColumnResult(name = "ngay_ve_du_kien", type = Date.class),
            @ColumnResult(name = "ngay_tao_po", type = Date.class),
            @ColumnResult(name = "nguoi_tao_po", type = String.class),
            @ColumnResult(name = "so_luong_dat_hang", type = Double.class),
            @ColumnResult(name = "so_luong_chua_ve", type = Double.class),
            @ColumnResult(name = "so_luong_nhap_kho", type = Double.class),
            @ColumnResult(name = "monitor_id", type = Integer.class),
            @ColumnResult(name = "detail_due_date", type = Date.class),
            @ColumnResult(name = "detail_quantity", type = Double.class),
            @ColumnResult(name = "type", type = String.class),
            @ColumnResult(name = "note", type = String.class)
        })
)
public class SapOnOrderSummary {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "mrp_code")
    private String mrpCode;

    @Column(name = "so_code")
    private String soCode;

    @Column(name = "sap_code")
    private String sapCode;

    @Column(name = "pr_code")
    private String prCode;

    @Column(name = "po_code")
    private String poCode;

    @Column(name = "grpo_code")
    private String grpoCode;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit")
    private String unit;

    @Column(name = "type")
    private String type;

    @Column(name = "provider_code")
    private String providerCode;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "contract_numer")
    private String contractNumber;

    @Column(name = "po_create_date")
    private Date poCreateDate;

    @Column(name = "create_po_user")
    private String createPoUser;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at", nullable = true, updatable = false)
    @CreatedDate
    private Timestamp createdAt;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "updated_at", nullable = true)
    @LastModifiedDate
    private Timestamp updatedAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoCode() {
        return soCode;
    }

    public void setSoCode(String soCode) {
        this.soCode = soCode;
    }

    public String getMrpCode() {
        return mrpCode;
    }

    public void setMrpCode(String mrpCode) {
        this.mrpCode = mrpCode;
    }

    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getGrpoCode() {
        return grpoCode;
    }

    public void setGrpoCode(String grpoCode) {
        this.grpoCode = grpoCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getPoCreateDate() {
        return poCreateDate;
    }

    public void setPoCreateDate(Date poCreateDate) {
        this.poCreateDate = poCreateDate;
    }

    public String getCreatePoUser() {
        return createPoUser;
    }

    public void setCreatePoUser(String createPoUser) {
        this.createPoUser = createPoUser;
    }

    public static void main(String[] args) {
        System.out.println("select b.*,\n" +
            "(b.so_luong_nhap_kho*100/b.so_luong_dat_hang) tien_do_mua_hang,\n" +
                "(case when b.ngay_tao_po > b.pr_due_date then to_days( b.ngay_tao_po) - to_days( b.pr_due_date) else 0 end) so_ngay_qua_han\n" +
                "from \n" +
                "(\n" +
                "select soos.item_code,item_name , po_code ,\n" +
                "max(case when soos.`type`  ='PR' then pr_code   end) pr_code ,\n" +
                "max(case when soos.`type`  ='PO' then contract_numer  end) so_hop_dong,\n" +
                "max(case when soos.`type`  ='PR' then provider_name  end) ma_ncc,\n" +
                "max(case when soos.`type`  ='PO' then provider_name  end) ten_ncc,\n" +
                "max(case when soos.`type`  ='PO' then due_date  end) ngay_ve_du_kien,\n" +
                "max(case when soos.`type`  ='PR' then due_date  end) pr_due_date,\n" +
                "max(case when soos.`type`  ='PO' then po_create_date  end) ngay_tao_po,\n" +
                "max(case when soos.`type`  ='PO' then create_po_user  end) nguoi_tao_po,\n" +
                "max(case when soos.`type`  ='GRPO' then due_date  end) ngay_nhap_kho,\n" +
                "max(case when soos.`type`  ='GRPO' then quantity  end) so_luong_nhap_kho,\n" +
                "max(case when soos.`type`  ='PO' then quantity  end) so_luong_dat_hang\n" +
                "from sap_on_order_summary soos\n" +
                "where po_code is not null\n" +
                "group by item_code,po_code  \n" +
                ") b");
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}
