package org.jivesoftware.openfire.plugin;

/**
 * 获取用户状态
 */

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.util.PropertiesUtil;
import org.jivesoftware.openfire.util.VariableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

import java.io.File;
import java.util.Date;
import java.util.HashSet;

public class UseStatePlugin implements PacketInterceptor, Plugin {

	private InterceptorManager interceptorManager;

	private static final Logger log = LoggerFactory.getLogger(UseStatePlugin.class);
	private  HashSet<String> userLineHs = new HashSet<String>();//保存用户发送
	

	public UseStatePlugin() {
		interceptorManager = InterceptorManager.getInstance();

	}

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		System.out.println("GetUserStatus plugin started ff");
		interceptorManager.addInterceptor(this);
		String adminLibDirString = System.getProperty("openfireHome");
		String filePath=adminLibDirString+"/plugins/getuserstate/url.properties";
		VariableUtil.url=PropertiesUtil.readData(filePath,"url");
		userLineHs.add("0");
	}
	@Override
	public void destroyPlugin() {
	}

	@Override
	public void interceptPacket(Packet packet, Session session,
			boolean incoming, boolean processed) throws PacketRejectedException {


		if(processed&&incoming){
			doAction(packet, incoming, processed, session);
		}

	}

	private void doAction(Packet packet, boolean incoming, boolean processed,
			Session session) {
		Packet copyPacket = packet.createCopy();
	        if (packet instanceof Presence) {
		 		Presence presence = (Presence) copyPacket;


			String status = "";
			String state = "";
			String userJid = "";
			String jid = copyPacket.getFrom().toString();

			String url = VariableUtil.url;
			

			if (presence.getType() == Presence.Type.unavailable) {
				state = "unavailable";//下线

			    sendData(url,jid,state,"");


			} else {

				try {
					String ipaddress="";
					if(presence.getShow()==null){
						state = "available";//在线
						
						ipaddress=session.getHostAddress();

						sendData(url,jid,state,ipaddress);

						
					}else if(presence.getShow()==Presence.Show.away){
						state = "unavailable";//离开
						ipaddress="";
					}
					
					//

				} catch (Exception e) {

					e.printStackTrace();
				}


			}

		}else{
			
		}

	}
	public static void sendData(final String url,String jid,String state,String ipaddress){
		 //System.out.println("status=="+state);
		final JSONObject  jsonData=new JSONObject();	
		 jsonData.put("protocol_CODE", "OFP001001");
		 JSONObject  jsonUser=new JSONObject();	
		 String[] info=jid.split("@");
		jsonUser.put("Jid", jid);
		// jsonUser.put("Jid", info[0]);
		 jsonUser.put("status", state);
		 jsonUser.put("ipaddress", ipaddress);
		 jsonData.put("ReqData", jsonUser);
		 jsonData.put("stamp_TIMES", new Date().getTime());

		 log.info("发送前的时间＝"+new Date().getTime());
			new Thread() {                     
			      public void run() {  
			         
			    	  postJson(url, jsonData);
			      }                         
			    }.start();

		 log.info("发送之后的时间＝"+new Date().getTime());
		
	}
	
	public static void postJson(String url,JSONObject json){
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject response = null;
		try {
			StringEntity s = new StringEntity(json.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			
			HttpResponse res = client.execute(post);

			
		} catch (Exception e) {
		    log.info("网址不能访问");

		}
		
	}
	
	
	

	
	
	

}
