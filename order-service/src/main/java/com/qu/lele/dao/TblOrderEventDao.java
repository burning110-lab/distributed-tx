package com.qu.lele.dao;

import com.qu.lele.dto.TblOrderEvent;
import com.qu.lele.dto.TblOrderEventExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TblOrderEventDao {
    long countByExample(TblOrderEventExample example);

    int deleteByExample(TblOrderEventExample example);

    int deleteByPrimaryKey(String id);

    int insert(TblOrderEvent record);

    int insertSelective(TblOrderEvent record);

    List<TblOrderEvent> selectByExample(TblOrderEventExample example);

    TblOrderEvent selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TblOrderEvent record, @Param("example") TblOrderEventExample example);

    int updateByExample(@Param("record") TblOrderEvent record, @Param("example") TblOrderEventExample example);

    int updateByPrimaryKeySelective(TblOrderEvent record);

    int updateByPrimaryKey(TblOrderEvent record);
}