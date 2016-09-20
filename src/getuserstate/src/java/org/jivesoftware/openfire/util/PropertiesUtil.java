package org.jivesoftware.openfire.util;


import java.io.*;
import java.util.Properties;

//http://localhost:8080/openFireWeb/getStateServlet

public class PropertiesUtil {
//	static String filePath = "src/plugins/getuserstate/url.properties";    
	/**  
     * 
     * 根据KEY，读取文件对应的值  
     * @param filePath 文件路径，即文件所在包的路径 
     * @param key 键  
     * @return key对应的值  
     */    
    public static String readData(String filePath,String key) {    
       
        Properties props = new Properties();    
        try {    
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));    
            props.load(in);    
            in.close();    
            String value = props.getProperty(key);    
            return value;    
        } catch (Exception e) {    
            e.printStackTrace();    
            return null;    
        }    
    }  
    
 
    /**  
     * 
     * 修改或添加键值对 如果key存在，修改, 反之，添加。  
     * @param filePath 文件路径，即文件所在包的路径， 
     * @param key 键  
     * @param value 键对应的值  
     */    
    public static void writeData(String filePath, String key, String value) {    
       
        Properties prop = new Properties();    
        try {    
            File file = new File(filePath);    
            if (!file.exists())    
                file.createNewFile();    
            InputStream fis = new FileInputStream(file);    
            prop.load(fis);    
            //一定要在修改值之前关闭fis    
            fis.close();    
            OutputStream fos = new FileOutputStream(filePath);    
            prop.setProperty(key, value);    
            //保存，并加入注释    
            prop.store(fos, "Update '" + key + "' value");    
            fos.close();    
        } catch (IOException e) {    
            System.err.println("Visit " + filePath + " for updating " + value + " value error");    
        }    
    }    
        
    public static void main(String[] args) {    
   //PropertiesUtil.writeData("url","http://localhost:8080/openFireWeb/getStateServlet");  
    	 String adminLibDirString = System.getProperty("openfireHome");
		 System.out.println("adminLibDirString="+adminLibDirString);
		//String filePath=adminLibDirString+"/plugins/getuserstate/url.properties";
     // System.out.println(PropertiesUtil.readData(filePath,"url"));    
    }    
}
