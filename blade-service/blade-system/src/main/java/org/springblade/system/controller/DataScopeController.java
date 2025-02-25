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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.CacheUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.system.entity.DataScope;
import org.springblade.system.service.IDataScopeService;
import org.springblade.system.vo.DataScopeVO;
import org.springblade.system.wrapper.DataScopeWrapper;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import static org.springblade.core.tool.utils.CacheUtil.SYS_CACHE;

/**
 * 数据权限控制器
 *
 * @author BladeX
 */
@RestController
@AllArgsConstructor
@RequestMapping("/data-scope")
@Tag(name = "数据权限", description = "数据权限")
public class DataScopeController extends BladeController {

	private final IDataScopeService dataScopeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "详情", description = "传入dataScope")
	public R<DataScope> detail(DataScope dataScope) {
		DataScope detail = dataScopeService.getOne(Condition.getQueryWrapper(dataScope));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@Operation(summary = "分页", description = "传入dataScope")
	public R<IPage<DataScopeVO>> list(DataScope dataScope, Query query) {
		IPage<DataScope> pages = dataScopeService.page(Condition.getPage(query), Condition.getQueryWrapper(dataScope));
		return R.data(DataScopeWrapper.build().pageVO(pages));
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@Operation(summary = "新增", description = "传入dataScope")
	public R save(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(dataScopeService.save(dataScope));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@Operation(summary = "修改", description = "传入dataScope")
	public R update(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(dataScopeService.updateById(dataScope));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@Operation(summary = "新增或修改", description = "传入dataScope")
	public R submit(@Valid @RequestBody DataScope dataScope) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(dataScopeService.saveOrUpdate(dataScope));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@Operation(summary = "逻辑删除", description = "传入ids")
	public R remove(@Parameter(description = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(dataScopeService.deleteLogic(Func.toLongList(ids)));
	}

}
