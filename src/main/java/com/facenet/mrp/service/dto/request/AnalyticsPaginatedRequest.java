package com.facenet.mrp.service.dto.request;

import com.facenet.mrp.service.dto.AdvancedMrpDTO;
import com.facenet.mrp.service.model.PageFilterInput;
import org.springframework.data.domain.PageRequest;

public class AnalyticsPaginatedRequest extends PageFilterInput {

    private AdvancedMrpDTO advancedMrpDTO;
    private AnalyticsSearchRequest filter;

    public AdvancedMrpDTO getAdvancedMrpDTO() {
        return advancedMrpDTO;
    }

    public void setAdvancedMrpDTO(AdvancedMrpDTO advancedMrpDTO) {
        this.advancedMrpDTO = advancedMrpDTO;
    }

    @Override
    public AnalyticsSearchRequest getFilter() {
        return filter;
    }

    public void setFilter(AnalyticsSearchRequest filter) {
        this.filter = filter;
    }
}
