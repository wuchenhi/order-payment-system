package com.nbcb.mapper;

import com.nbcb.pojo.ShopMsgProvider;
import com.nbcb.pojo.ShopMsgProviderExample;
import com.nbcb.pojo.ShopMsgProviderKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ShopMsgProviderMapper {
    long countByExample(ShopMsgProviderExample example);

    int deleteByExample(ShopMsgProviderExample example);

    int deleteByPrimaryKey(ShopMsgProviderKey key);

    int insert(ShopMsgProvider record);

    int insertSelective(ShopMsgProvider record);

    List<ShopMsgProvider> selectByExample(ShopMsgProviderExample example);

    ShopMsgProvider selectByPrimaryKey(ShopMsgProviderKey key);

    int updateByExampleSelective(@Param("record") ShopMsgProvider record, @Param("example") ShopMsgProviderExample example);

    int updateByExample(@Param("record") ShopMsgProvider record, @Param("example") ShopMsgProviderExample example);

    int updateByPrimaryKeySelective(ShopMsgProvider record);

    int updateByPrimaryKey(ShopMsgProvider record);
}