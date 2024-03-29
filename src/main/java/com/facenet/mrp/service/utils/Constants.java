package com.facenet.mrp.service.utils;

public class Constants {
    public interface ProductOrder{
        int STATUS_NEW = 1;
        int STATUS_ORDER_ANALYTICS = 2;
        int STATUS_ORDER_ANALYTICS_FULL = 3;
        int CLOSED = 6;
    }

    public interface Param {
        String CURRENCY = "currency";
    }

    public interface AnalysisType {
        int RE_RUN = 1;
        int NEW = 2;
    }

    public interface PurchaseRecommendation {
        int STATUS_NEW = 1;
        int SEND_APPROVAL = 2;
        int COMPLETED = 3;
    }

    public interface PurchaseRecommendationDetail {
        int STATUS_NEW = 1;
        int SEND_APPROVAL = 2;
        int ACCEPTED = 3;
        int REJECTED = 4;
        int COMPLETED = 5;
        int CLOSED = 6;
    }

    public interface MrpSub {
        byte NEW = 0;
        byte SENT_PURCHASE_RECOMMENDATION = 1; // Gui khuyen nghi
        byte SENT_PR_PO = 2; // Da len PR/PO
        byte CLOSED = 3; // Dong
    }

    public interface PurchaseRecommendationPlan {
        int STATUS_NEW = 0;
        int CHECKED = 1;
        int SEND_APPROVAL = 2;
        int ACCEPTED = 3;
        int REJECTED = 4;
        int CLOSED = 5;
        int CLOSED_ACCEPTED = 6;
    }

    public interface ItemHold {
        int NEW = 1;
        int ACTIVE = 2;
    }

    public interface ForecastOrder {
        int STATUS_NEW = 1;
        int SENT_PURCHASE_RECOMMENDATION = 2;
    }

    public interface ForecastOrderDetail {
        int STATUS_NEW = 1;
        int SENT_PURCHASE_RECOMMENDATION = 3;
        int SENT_MRP = 2;
    }

    public interface MrpAnalysis {
        String SAP_QUANTITY_STRING = "Số lượng trên kho SAP";
    }


    public static final String PO_TYPE = "PO";
    public static final String PR_TYPE = "PR";
    public static final String GRPO_TYPE = "GRPO";

    public static final int TP = 104;
    public static final int BTP = 101;

    public static final int DAY_MODE = 0;
    public static final int WEEK_MODE = 1;
    public static final int MONTH_MODE = 2;
    public static final int QUARTER_MODE = 3;

    public static final int BUILD_BOM_EXCLUDE_BTP = 1;
    public static final int BUILD_BOM_EXCLUDE_NVL = 2;
}
