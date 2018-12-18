package com.zkhc.foot.data.modules.dao;

import com.zkhc.foot.data.modules.entity.ZnkfHospital;

import java.util.List;

/**
 * @author 武海升
 * @date 2018/12/18 12:58
 */
public interface ZnkfHospitalMapper {
    /**
     * 批量插入医院
     * @param hospitalList
     * @return
     */
    int insertHospitalBatch(List<ZnkfHospital> hospitalList);

}
