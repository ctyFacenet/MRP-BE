package com.facenet.mrp.service;

import com.facenet.mrp.domain.mrp.Param;
import com.facenet.mrp.repository.mrp.ParamRepository;
import com.facenet.mrp.service.dto.InputParamDto;
import com.facenet.mrp.service.dto.ParamDto;
import com.facenet.mrp.service.dto.response.CommonResponse;
import com.facenet.mrp.service.exception.CustomException;
import com.facenet.mrp.service.mapper.ParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParamServiceImpl implements ParamService{
    private static final Logger logger = LoggerFactory.getLogger(ParamServiceImpl.class);
    private final ParamRepository paramRepository;
    private final ParamMapper paramMapper;

    public ParamServiceImpl(ParamRepository paramRepository, ParamMapper paramMapper) {
        this.paramRepository = paramRepository;
        this.paramMapper = paramMapper;
    }

    /**
     * Get params in params table
     * @param inputParamDto users' params
     * @return
     */
    public ResponseEntity<?> getParams(InputParamDto inputParamDto) {
        List<String> paramList = inputParamDto.getParams();
        if (paramList == null)
            throw new CustomException(HttpStatus.BAD_REQUEST, "invalid.param");

        try {
            Map<Object, List<ParamDto>> result = new HashMap<>(paramList.size());
            for (Object param : paramList) {
                List<Param> params = paramRepository.getAllByParamCode(param);
                logger.info("[{}] values: [{}]", param, params);
                result.put(param, paramMapper.toDtoList(params));
            }
            return ResponseEntity.ok(
                new CommonResponse<>()
                    .errorCode("00")
                    .message("Thành công")
                    .isOk(true)
                    .data(result)
            );
        } catch (Exception e) {
            logger.error("Error when query for params", e);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error");
        }
    }
}
