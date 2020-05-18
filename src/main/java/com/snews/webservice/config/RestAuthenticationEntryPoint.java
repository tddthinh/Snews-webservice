package com.snews.webservice.config;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.snews.webservice.repository.result.AccountDTO;
import com.snews.webservice.utilities.JsonUtil;

public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
//    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		response.setHeader("Content-Type", "application/json");
		response.getWriter().write(JsonUtil.ObjectToJson(new AccountDTO()));

	}
}