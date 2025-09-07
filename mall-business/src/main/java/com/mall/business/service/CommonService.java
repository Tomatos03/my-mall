package com.mall.business.service;

import com.mall.api.service.ICommonService;
import com.mall.business.mapper.CommonMapper;
import com.mall.common.domain.convert.Convert;
import com.mall.common.enums.FileTypeEnum;
import com.mall.common.util.ExcelUtil;
import com.mall.common.util.PageUtil;
import com.mall.common.util.TimeUtil;
import com.mall.dto.FileDTO;
import com.mall.dto.PageDTO;
import com.mall.dto.condition.CommonConditionDTO;
import com.mall.dto.condition.PageConditionDTO;
import com.mall.model.FileMultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * K 表示DO, V表示ConditionDTO
 *
 * @author : Tomatos
 * @date : 2025/8/15
 */
@Slf4j
public abstract class CommonService<DO, DTO, ConditionDTO>
        implements ICommonService<DO, DTO,ConditionDTO> {
    private final Class<DO> doClass;

    @Autowired
    private UploadService uploadService;

    protected CommonService(Class<DO> doClass) {
        this.doClass = doClass;
    }

    /**
     * 获取定义的Mapper
     *
     * @return com.mall.mapper.BaseMapper<K,V>
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/15 22:05
     */
    protected abstract CommonMapper<DO, ConditionDTO> getMapper();

    /**
     * 导出数据到Excel到浏览器下载
     *
     * @param condition 条件实体
     * @param response  响应对象
     * @param kClazz    待添加对象Class对象
     * @param fileName  文件名称
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/15 22:28
     */
    @Deprecated
    public void export(ConditionDTO condition, HttpServletResponse response, Class<DO> kClazz,
                       String fileName) throws IOException {
        CommonConditionDTO commonCondition = (CommonConditionDTO) condition;
        commonCondition.setPageSize(PageConditionDTO.ALL_PAGE);
        TimeUtil.fillTimeInterval(commonCondition);

        List<DO> dataList = getMapper().searchByCondition(condition);
        ExcelUtil.export(fileName, kClazz, dataList, response);
    }

    /**
     * 分页查询
     *
     * @param conditionDTO 条件传输对象
     * @return com.mall.domain.ResponsePage<K> 分页数据
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/8/15 22:44
     */
    public PageDTO<DO> searchByPage(ConditionDTO conditionDTO) {
        CommonConditionDTO condition = (CommonConditionDTO) conditionDTO;
        TimeUtil.fillTimeInterval(condition);

        List<DO> dataList = getMapper().searchByCondition(conditionDTO);

        return PageUtil.buildPageDTO(condition, dataList);
    }

//    /**
//     * 分段导出数据到Excel到指定储存位置
//     *
//     * @param condition 条件实体
//     * @param kClass    待添加对象实体
//     * @param fileName  文件名称
//     * @return void
//     * @since : 1.0
//     * @author : Tomatos
//     * @date : 2025/8/15 22:28
//     */
//    public void export(ConditionDTO condition, Class<DO> kClass, String fileName) throws IOException {
//        CommonConditionDTO commonCondition = (CommonConditionDTO) condition;
//        commonCondition.setPageSize(PageConditionDTO.ALL_PAGE);
//        TimeUtil.fillTimeInterval(commonCondition);
//
//        List<DO> dataList = getMapper().searchByCondition(condition);
//        ExcelUtil.phasedExport(fileName, kClass, dataList);
//    }

    /**
     * 分段导出数据到Excel到七牛云OOS
     *
     * @param condition 条件实体
     * @param kClass    待添加对象实体
     * @param fileName  文件名称
     * @return void
     * @author : Tomatos
     * @date : 2025/8/15 22:28
     * @since : 1.0
     */
    public String export(ConditionDTO condition, Class<DO> kClass, String fileName) throws IOException {
        CommonConditionDTO commonCondition = (CommonConditionDTO) condition;
        commonCondition.setPageSize(PageConditionDTO.ALL_PAGE);
        TimeUtil.fillTimeInterval(commonCondition);

        List<DO> dataList = getMapper().searchByCondition(condition);
        String path = ExcelUtil.phasedExport(fileName, kClass, dataList);
        File file = new File(path);
        return exportExcelToOOS(file);
    }

    /**
     * 上传文件到OOS
     *
     * @param file 文件对象
     * @return java.lang.String
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/9/6 17:08
     */
    private String exportExcelToOOS(File file) {
        try {
            MultipartFile multipartFile = new FileMultipartFile(file.toPath());
            FileDTO upload = uploadService.upload(multipartFile, FileTypeEnum.FILE);

            return upload.getDownloadUrl();
        } catch (Exception e) {
            log.info("导出excel到七牛云OOS失败原因:{}", e.getMessage());
        }
        return "";
    }


    @Override
    public int insert(DTO dto) {
        return getMapper().insert(Convert.convertDO(dto, doClass));
    }

    @Override
    public int deleteByIds(List<Long> ids) {
        return getMapper().deleteByIds(ids);
    }

    @Override
    public int update(DTO dto) {
        return getMapper().update(Convert.convertDO(dto, doClass));
    }

    @Override
    public List<DO> searchByCondition(ConditionDTO condition) {
        return getMapper().searchByCondition(condition);
    }
}
