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
package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.service.IDynamicService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 多数据源
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("dynamic")
@Tag(name = "多数据源接口", description = "多数据源")
public class DynamicController {

	private IDynamicService dynamicService;

	/**
	 * master列表
	 */
	@GetMapping("/master-list")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "master列表", description = "master列表")
	public R<List<Notice>> masterList() {
		List<Notice> list = dynamicService.masterList();
		return R.data(list);
	}

	/**
	 * slave列表
	 */
	@GetMapping("/slave-list")
	@ApiOperationSupport(order = 1)
	@Operation(summary = "slave列表", description = "slave列表")
	public R<List<Notice>> slaveList() {
		List<Notice> list = dynamicService.slaveList();
		return R.data(list);
	}

}
