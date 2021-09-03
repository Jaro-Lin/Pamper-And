package com.nyw.pets.net.util;

/**
 * 版本更新
 */
public class VersionUtil {

	/**
	 * code : 0
	 * msg : 获取成功
	 * data : {"id":1,"type":"android","enforece":1,"version":"1.0","name":"1.0","url":"http://ml.nnddkj.com/meimeila/APK/app.apk","content":"美谷健2.0","create_time":1546565713}
	 */

	private int code;
	private String msg;
	private DataBean data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public static class DataBean {
		/**
		 * id : 1
		 * type : android
		 * enforece : 1
		 * version : 1.0
		 * name : 1.0
		 * url : http://ml.nnddkj.com/meimeila/APK/app.apk
		 * content : 美谷健2.0
		 * create_time : 1546565713
		 */

		private int id;
		private String type;
		private int enforece;
		private String version;
		private String name;
		private String url;
		private String content;
		private int create_time;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getEnforece() {
			return enforece;
		}

		public void setEnforece(int enforece) {
			this.enforece = enforece;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getCreate_time() {
			return create_time;
		}

		public void setCreate_time(int create_time) {
			this.create_time = create_time;
		}
	}
}
