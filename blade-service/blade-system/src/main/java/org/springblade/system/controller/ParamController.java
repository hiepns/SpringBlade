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
package org.springblade.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.Param;
import org.springblade.system.service.IParamService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/param")
@Tag(name = "参数管理", description = "接口")
public class ParamController extends BladeController {

	private IParamService paramService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入param")
	public R<Param> detail(Param param) {
		Param detail = paramService.getOne(Condition.getQueryWrapper(param));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@Parameters({
		@Parameter(name = "paramName", description = "参数名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "paramKey", description = "参数键名", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
		@Parameter(name = "paramValue", description = "参数键值", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
	})
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入param")
	@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<Param>> list(@Parameter(hidden = true) @RequestParam Map<String, Object> param, Query query) {
		IPage<Param> pages = paramService.page(Condition.getPage(query), Condition.getQueryWrapper(param, Param.class));
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增或修改", description = "传入param")
	public R submit(@Valid @RequestBody Param param) {
		return R.status(paramService.saveOrUpdate(param));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		return R.status(paramService.deleteLogic(Func.toLongList(ids)));
	}


}
