package com.google.mall.dao;

import com.google.mall.model.PmsProductVertifyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by sohyun on 2020/1/1 10:55.
 */
public interface PmsProductVertifyRecordDao {
    void insertList(@Param("list") List<PmsProductVertifyRecord> list);
}
