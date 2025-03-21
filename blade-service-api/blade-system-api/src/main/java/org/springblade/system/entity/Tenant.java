/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springblade.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springblade.core.mp.base.BaseEntity;

import java.io.Serial;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("blade_tenant")
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Tenant对象")
public class Tenant extends BaseEntity {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	/**
	 * 租户ID
	 */
	@Schema(description = "租户ID")
	private String tenantId;
	/**
	 * 租户名称
	 */
	@Schema(description = "租户名称")
	private String tenantName;
	/**
	 * 域名地址
	 */
	@Schema(description = "域名地址")
	private String domain;
	/**
	 * 联系人
	 */
	@Schema(description = "联系人")
	private String linkman;
	/**
	 * 联系电话
	 */
	@Schema(description = "联系电话")
	private String contactNumber;
	/**
	 * 联系地址
	 */
	@Schema(description = "联系地址")
	private String address;


}
