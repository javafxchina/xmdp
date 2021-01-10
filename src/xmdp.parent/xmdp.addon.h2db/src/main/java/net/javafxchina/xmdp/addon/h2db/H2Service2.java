package net.javafxchina.xmdp.addon.h2db;


/**
 * 
 * 初始化H2数据库，一般用于调试<br>
 *
 * 
 * 
 * @version 1.0.0
 */
public class H2Service2 {

	private boolean on = false;// 是否要打开数据库，若为false则不会启动
	private boolean debug = true;// 是否开web控制台
	private String driverClassName = "org.h2.Driver";
	private String port = "54321";
	// private String url =
	// "jdbc:h2:tcp://localhost:"+port+"/"+System.getProperty("user.dir")+"/db";
	private String url = "jdbc:h2:tcp://localhost:" + port + "/#{root}/db/data;DB_CLOSE_DELAY=1000";
	private String username = "javafxchina";
	/** 在加密模式下前半部为file password，后半部为user password */
	// private String password = "abc 123";
	private String password = "javafxchina";

	public void init() throws Exception {
		System.err.println("====================Database Demo init===================");
		if (!on) {
			System.err.println("Database Demo is NOT enable!");
			return;
		}else {
			System.err.println("Start init Database Demo!");
		}
		System.err.println("====================Database Demo init===================");
	}


	@Override
	public String toString() {
		return "H2Dao [debug=" + debug + ", driverClassName=" + driverClassName + ", port=" + port + ", url=" + url
				+ ", username=" + username + ", password=" + password + "]";
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}


}
