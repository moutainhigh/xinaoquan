package cn.enn.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ErrorMessageCache {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Properties properties = new Properties();
	
	private static Map<Integer, MeteringError> errorMap = new HashMap<>();
	
	/**
	 * 构造私有化
	 */
	private ErrorMessageCache() {
		
	}

	private static class Holder {
		public static final ErrorMessageCache instance = new ErrorMessageCache();
	}

	public static ErrorMessageCache getInstance() {
		return Holder.instance;
	}

	/**
	 * 加载资源文件
	 * @param filePath
	 * @return
	 */
	public void loadProperties(String filePath) {
		try {
			properties.load(ErrorMessageCache.class.getClassLoader().getResourceAsStream(filePath));
			Enumeration<Object> keyEnum = properties.keys();
			while(keyEnum.hasMoreElements()) {
				String key = (String)keyEnum.nextElement();
				String message = properties.getProperty(key);
				int code = Integer.valueOf(key);
				errorMap.put(code, new MeteringError(code, message));
			}
		} catch (Exception e) {
			logger.error("初始化系统配置信息失败!", e);
		}
	}
	
	/**
	 * 获取key对应值
	 * @param key
	 * @return
	 */
	public static MeteringError getError(int key) {
		return errorMap.get(key);
	}
	
	public class MeteringError {
		
		private Integer code;
		
		private String message;

		public MeteringError(Integer code, String message) {
			super();
			this.code = code;
			this.message = message;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "MeteringError [code=" + code + ", message=" + message + "]";
		}
	}
	
}
