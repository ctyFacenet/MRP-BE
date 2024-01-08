package com.facenet.mrp.service.dto.request;

public class InternalRequest{
    public InternalRequest() {
    }

    public InternalRequest(String poCode, String itemCode) {
        this.poCode = poCode;
        this.itemCode = itemCode;
    }

    private String poCode;
    private String itemCode;

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public static void main(String[] args) {
        System.out.println("select b.*,\n" +
            "(b.so_luong_nhap_kho/b.so_luong_dat_hang) tien_do_mua_hang,\n" +
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
            ") b;");
    }
}
