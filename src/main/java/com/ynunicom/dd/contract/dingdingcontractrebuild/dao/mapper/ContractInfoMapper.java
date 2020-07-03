package com.ynunicom.dd.contract.dingdingcontractrebuild.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ynunicom.dd.contract.dingdingcontractrebuild.dao.ContractInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: jinye.Bai
 * @date: 2020/6/22 17:17
 */

@Mapper
public interface ContractInfoMapper extends BaseMapper<ContractInfoEntity> {
    @Select("select * from CONTRACT_INFO where theirQualityFilePath = #{fileName} or reasonOfNotUsingStandTemplateFilePath = #{fileName} or " +
            "contractTextFilePath = #{fileName} or attachmentFilePath1 = #{fileName} or attachmentFilePath2 = #{fileName} or" +
            "attachmentFilePath3 = #{fileName}")
    public ContractInfoEntity fileSelect(@Param("fileName") String fileName);
}
