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

package org.springblade.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springblade.gateway.provider.ResponseProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 异常处理
 *
 * @author Chill
 */
@Order(-1)
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ErrorExceptionHandler implements ErrorWebExceptionHandler {

	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		if (response.isCommitted()) {
			return Mono.error(ex);
		}

		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		if (ex instanceof ResponseStatusException) {
			response.setStatusCode(((ResponseStatusException) ex).getStatusCode());
		}

		return response.writeWith(Mono.fromSupplier(() -> {
			DataBufferFactory bufferFactory = response.bufferFactory();
			try {
				int status = 500;
				if (response.getStatusCode() != null) {
					status = response.getStatusCode().value();
				}
				Map<String, Object> result = ResponseProvider.response(status, this.buildMessage(request, ex));
				return bufferFactory.wrap(objectMapper.writeValueAsBytes(result));
			} catch (JsonProcessingException e) {
				return bufferFactory.wrap(new byte[0]);
			}
		}));
	}


	/**
	 * 构建异常信息
	 */
	private String buildMessage(ServerHttpRequest request, Throwable ex) {
		StringBuilder message = new StringBuilder("Failed to handle request [");
		message.append(request.getMethod().name());
		message.append(" ");
		message.append(request.getURI());
		message.append("]");
		if (ex != null) {
			message.append(": ");
			message.append(ex.getMessage());
		}
		return message.toString();
	}


}
