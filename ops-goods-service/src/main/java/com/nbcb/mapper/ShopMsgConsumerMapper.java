package com.nbcb.mapper;

import com.nbcb.pojo.ShopMsgConsumer;
import com.nbcb.pojo.ShopMsgConsumerExample;
import com.nbcb.pojo.ShopMsgConsumerKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ShopMsgConsumerMapper {
    long countByExample(ShopMsgConsumerExample example);

    int deleteByExample(ShopMsgConsumerExample example);

    int deleteByPrimaryKey(ShopMsgConsumerKey key);

    int insert(ShopMsgConsumer record);

    int insertSelective(ShopMsgConsumer record);

    List<ShopMsgConsumer> selectByExample(ShopMsgConsumerExample example);

    ShopMsgConsumer selectByPrimaryKey(ShopMsgConsumerKey key);

    int updateByExampleSelective(@Param("record") ShopMsgConsumer record, @Param("example") ShopMsgConsumerExample example);

    int updateByExample(@Param("record") ShopMsgConsumer record, @Param("example") ShopMsgConsumerExample example);

    int updateByPrimaryKeySelective(ShopMsgConsumer record);

    int updateByPrimaryKey(ShopMsgConsumer record);
}