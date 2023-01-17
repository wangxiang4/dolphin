package com.cloud.dolphin.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * 订单实体类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("demo_seata_order")
public class Order {

	private static final long serialVersionUID = 1L;

	@TableId
	private String id;

	private Integer money;

}
