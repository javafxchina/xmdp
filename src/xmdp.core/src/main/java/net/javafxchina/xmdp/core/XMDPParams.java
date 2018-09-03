package net.javafxchina.xmdp.core;

import java.security.SecureRandom;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author Victor
 *
 */
@Component("xmdpParams")
public class XMDPParams {
	private static Logger logger = LoggerFactory.getLogger(XMDPParams.class);
	@Value("${xmdp.isDebug}")
	private boolean isDebug;
	@SuppressWarnings("rawtypes")
	@Autowired
	private Map cfgParams;
	
	public Object getParam(String key) {
		if(!key.endsWith(".PWD")) {
			return cfgParams.get(key);
		}else {
			if(cfgParams==null||cfgParams.get(key)==null) {
				return null;
			}
			String pwd=cfgParams.get(key).toString();
			DESSecurityUtil des  = new XMDPParams().new DESSecurityUtil();
			try {
				return des.decrypt(pwd).toString();
			} catch (Exception e) {
				logger.error("Params read failed",e);
				return null;
			}
		}
		
	}
	public Integer getIntParam(String key) {
		if(getParam(key)==null) {
			return null;
		}
		String param=getParam(key).toString();
		return Integer.parseInt(param);
	}
	public String getStringParam(String key) {
		if(getParam(key)==null) {
			return null;
		}
		String param=getParam(key).toString();
		return param;
	}
	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
	
	public class DESSecurityUtil {
		private String key = "javafxchina";
		private SecureRandom sr;
		private SecretKey securekey;

		public DESSecurityUtil() {
			// DES算法要求有一个可信任的随机数源
			sr = new SecureRandom();
			try {
				// 从原始密匙数据创建DESKeySpec对象
				DESKeySpec dks = new DESKeySpec(key.getBytes());
				// 创建一个密匙工厂，然后用它把DESKeySpec转换成
				// 一个SecretKey对象
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

				securekey = keyFactory.generateSecret(dks);
			} catch (Exception e) {
			}
		}

		/**
		 * 加密
		 * 
		 * @param obj
		 *            待加密对象
		 * @return 加密完毕对象
		 * @throws Exception
		 *             异常
		 */
		public Object encrypt(Object obj) throws Exception {

			try {
				// Cipher对象实际完成加密操作
				Cipher cipher = Cipher.getInstance("DES");
				// 用密匙初始化Cipher对象
				cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
				return getBASE64(cipher.doFinal(String.valueOf(obj).getBytes()));
			} catch (Exception e) {
				throw new Exception("DES 加密发生异常", e);
			}
		}
		
		/**
		 * 解密
		 * 
		 * @param obj
		 *            待解密对象
		 * @return 解密完毕对象
		 * @throws Exception
		 *             异常
		 */
		public Object decrypt(Object obj) throws Exception {
			try {
				// Cipher对象实际完成加密操作
				Cipher cipher = Cipher.getInstance("DES");
				// 用密匙初始化Cipher对象
				cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
				byte[] bytes = getFromBASE64(String.valueOf(obj));
				byte[] ret = cipher.doFinal(bytes);
				
				String retStr = new String(ret);
				return retStr;
			} catch (Exception e) {
				throw new Exception("DES 解密发生异常", e);
			}
		}
		/**
		 * 将data进行base64编码
		 * @param data
		 * @return
		 */
		private String getBASE64(byte[] data){
	        Base64 base64=new Base64();  
	        byte[] result=base64.encode(data);  
	        String re=new String(result);  
	        return re;  
		}

		
		/**
		 * 将encodeStr进行base64解码
		 * @param encodeStr 待解密字符串
		 * @return 解码后的数据
		 */
		private byte[] getFromBASE64(String encodeStr){  
	        byte[] b=encodeStr.getBytes();  
	        Base64 base64=new Base64();  
	       return base64.decode(b);  
	    } 
	}
}
