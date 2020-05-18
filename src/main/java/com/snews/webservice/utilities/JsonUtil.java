package com.snews.webservice.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {
	private static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	public static String ObjectToJson(Object obj) {
		try {
			return ow.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
}
