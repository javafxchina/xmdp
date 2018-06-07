package net.javafxchina.xmdp.ui.dk;

import java.util.HashMap;
import java.util.Map;

/**
 * 入口信息，包括菜单、按钮、模块信息
 * @author Victor
 *
 */
public class EntryDefineInfo {
    /**
     * 侧边栏菜单
     */
    public class SideMenu{
    	public String key;
    	public String showName;
    	public String iconpath;
    }
    /**
     * 模块按钮
     */
    public class ModuleButton{
    	/** 一个menukey可以关联多个ModuleButton**/
    	public String menuKey;
    	/** ModuleButton可以分组管理，默认为空字符串**/
    	public String group="";
    	public String key;
    	public String showName;
    	public String iconpath;
    	/** 每个ModuleButton可以关联到一个ModuleInfo**/
    	public String moduleKey;
    }
    /**
     * 模块信息
     */
    public class ModuleInfo{
    	public final static String TYPE_CLIENT="CLIENT";
    	public final static String TYPE_CLIENT_FXML="CLIENT_FXML";
		public final static String TYPE_WEB="WEB";
		public final static String TYPE_EXTERN_JAR="EXTERN_JAR";
    	public String key;
    	public String showName;
    	/** 用于选项卡图标**/
    	public String iconpath;
    	/** 本地模块：CLIENT;WEB模块：WEB;本地FXML模块：CLIENT_FXML；外部Java执行程序：EXTERN_JAR**/
    	public String type;
    	/** 模块的入口类**/
    	public String className;
    	/** WEB模块的网址;CLIENT_FXML模块的fxml文件名，位于产品文件夹中;EXTERN_JAR模块的jar文件名，位于产品文件夹中**/
    	public String uri;
    	/** 模块运行的扩展参数**/
    	public String extParam;
		@Override
		public String toString() {
			return "ModuleInfo [key=" + key + ", showName=" + showName + ", iconpath=" + iconpath + ", type=" + type
					+ ", className=" + className + ", uri=" + uri + ", extParam=" + extParam + "]";
		}
    	
    }
    /**
	 * 模块运行的扩展参数工具类，可以用于ModuleInfo中的extParam属性解析，支持类似a=1&b=2格式的参数配置（参考URI的QueryString格式）
	 */
	public class Params {
		private Map<String,String> params=new HashMap<String,String>();
		public Params(String extParam) {
			if(extParam==null) return;
			String[]ps=extParam.split("&");
			for(String p:ps){
				String[] kv=p.split("=");
				if(kv==null||kv[0]==null){
					return;
				}
				if(kv.length!=2){
					throw new RuntimeException("错误的URI QueryString格式："+extParam);
				}
				params.put(kv[0], kv[1]);
			}
		}
		public String getParam(String key){
			return params.get(key);
		}
	}
}
