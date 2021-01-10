package net.javafxchina.xmdp.addon.h2db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.javafxchina.xmdp.core.XMDPParams;
import net.javafxchina.xmdp.core.XMDPParams.DESSecurityUtil;


/**
 * 
 * 初始化H2数据库，一般用于调试<br>
 *
 * 
 * 
 * @version 1.0.0
 */
public class H2Service {
	private static Logger logger = LoggerFactory.getLogger(H2Service.class);
	private static Connection con;
	private Server server;

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
		if (!on) {
			logger.warn("===================Database is NOT enable!===================");
			return;
		}else {
			logger.info("===================Start init Database!===================");
		}
		try {
//			params = (XMDPParams) XMDPContext.getInstance().getService("xmdpParams");
//			if (params != null) {
//				debug = params.isDebug();
//				port = params.getStringParam("h2.port");
//				url = params.getStringParam("h2.url");
//				username = params.getStringParam("h2.username");
//				password = params.getStringParam("h2.password");
//			} else {
//				logger.warn("Database's param config to default values!");
//			}
			// 启动server
			server = Server.createTcpServer(new String[] { "-tcpPort", port, "-tcpAllowOthers", "-ifNotExists" })
					.start();
			Class.forName(driverClassName);
			url = parseURL(url);
			username = parsePWD(username);
			password = parsePWD(password);
			if (debug) {
				con = DriverManager.getConnection(url, username, password);
				Thread th = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Server.startWebServer(con);
						} catch (SQLException e) {
							logger.error("初始化Web服务器失败" + e.getMessage());
							e.printStackTrace();
						}
					}

				});
				th.start();
				test();
			}
			logger.info("===================SUCCESS init Database!===================");
		} catch (SQLException e) {
			logger.error("===================Failed SUCCESS Start init Database!===================");
			logger.error(e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	private String parsePWD(String pwd) {
		if (!pwd.endsWith(".PWD")) {
			return pwd;
		}
		pwd = pwd.substring(0, pwd.length() - 4);
		DESSecurityUtil des =new XMDPParams().new DESSecurityUtil();
		try {
			return des.decrypt(pwd).toString();
		} catch (Exception e) {
			logger.error("Params read failed", e);
			return null;
		}
	}

	private void test() throws SQLException {
		Statement stmt = con.createStatement();
		String id;
		try {
			id = DateTimeUtil.getCurretnFullTime("yyyyMMddHHmmssSSS");
		} catch (Exception e) {
			id = SIDCreator.getUUID();
		}
		String tn = "TEST_" + id;
		stmt.execute("DROP TABLE IF EXISTS " + tn);
		// 创建USER_INFO表
		stmt.execute("CREATE TABLE " + tn + "(id VARCHAR(36) PRIMARY KEY,name VARCHAR(100))");
		// 新增
		stmt.executeUpdate("INSERT INTO " + tn + " VALUES('" + UUID.randomUUID() + "','javafx中国')");
		String nameUUID=SIDCreator.getUUID();
		// 修改
		stmt.executeUpdate("UPDATE " + tn + " SET name='" + nameUUID + "' WHERE name='javafx中国'");
		// 查询
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + tn);
		// 遍历结果集
		while (rs.next()) {
			System.out.println("数据库测试:" + rs.getString("id") + "," + rs.getString("name"));
		}
		rs.close();
		// 删除
		stmt.executeUpdate("DELETE FROM " + tn + " WHERE name='"+nameUUID+"'");
		stmt.execute("DROP TABLE IF EXISTS " + tn);
		stmt.close();
	}

	private String parseURL(String url) {
		String pa = "@\\{root}";
		String rootpath = System.getProperty("user.dir");
		rootpath = rootpath.replaceAll("\\\\", "/");
		url = url.replaceAll(pa, rootpath);
		return url;
	}

	public void close() throws Exception {
		try {
			con.close();
			// stop the TCP Server
			server.stop();

			logger.info("关闭成功");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("关闭失败" + e.getMessage());
			throw e;
		}

	}

	public void getInput() {
		System.out.println("如果需要退出，请输入字母e。");
		while (true) {
			String str = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				str = br.readLine();
				System.out.println(str);
				if (str.equalsIgnoreCase("e")) {
					System.exit(0);
				} else {
					System.out.println("如果需要退出，请输入字母e。");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws Exception {
//		H2Service h2 = new H2Service();
//		h2.setOn(true);
//		h2.init();
//		h2.getInput();
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
