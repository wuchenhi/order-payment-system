package com.nbcb.mapper;

import com.nbcb.pojo.ShopOrderGoodsLog;
import com.nbcb.pojo.ShopOrderGoodsLogExample;
import com.nbcb.pojo.ShopOrderGoodsLogKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ShopOrderGoodsLogMapper {
    long countByExample(ShopOrderGoodsLogExample example);

    int deleteByExample(ShopOrderGoodsLogExample example);

    int deleteByPrimaryKey(ShopOrderGoodsLogKey key);

    int insert(ShopOrderGoodsLog record);

    int insertSelective(ShopOrderGoodsLog record);

    List<ShopOrderGoodsLog> selectByExample(ShopOrderGoodsLogExample example);

    ShopOrderGoodsLog selectByPrimaryKey(ShopOrderGoodsLogKey key);

    int updateByExampleSelective(@Param("record") ShopOrderGoodsLog record, @Param("example") ShopOrderGoodsLogExample example);

    int updateByExample(@Param("record") ShopOrderGoodsLog record, @Param("example") ShopOrderGoodsLogExample example);

    int updateByPrimaryKeySelective(ShopOrderGoodsLog record);

    int updateByPrimaryKey(ShopOrderGoodsLog record);
}