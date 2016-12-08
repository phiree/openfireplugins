/**
 * $RCSfile: ChatArchivePlugin.java,v $
 * $Revision: 3117 $
 * $Date: 2005-11-25 22:57:29 -0300 (Fri, 25 Nov 2005) $
 *
 * Copyright (C) 2004-2008 Jive Software. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jivesoftware.openfire.plugin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserManager;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.dom4j.Element;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
/**
 * Broadcast service plugin. It accepts messages and broadcasts them out to
 * groups of connected users. The address <tt>all@[serviceName].[server]</tt> is
 * reserved for sending a broadcast message to all connected users. Otherwise,
 * broadcast messages can be sent to groups of users by using addresses
 * in the form <tt>[group]@[serviceName].[server]</tt>.
 *
 * @author Matt Tucker
 */
public class ChatArchivePlugin implements PacketInterceptor ,Plugin{

	private static final Logger log = LoggerFactory.getLogger(ChatArchivePlugin.class);
    private InterceptorManager interceptorManager;

    public ChatArchivePlugin(){
        interceptorManager = InterceptorManager.getInstance();
    }
    // Plugin Interface
@Override
    public void initializePlugin(PluginManager manager, File pluginDirectory) {
    	System.out.println("ChatArchivePlugin Started gd");
        interceptorManager.addInterceptor(this);


    }
    @Override
    public void destroyPlugin() {
        debug("ChatArchivePlugin Destroyed");
        interceptorManager.removeInterceptor(this);
    }
    @Override
    public void interceptPacket(Packet packet, Session session, boolean incoming, boolean processed) throws PacketRejectedException {

            JID recipient = packet.getTo();
        if (recipient != null) {
            String username = recipient.getNode();
            // 广播消息或是不存在/没注册的用户.
            if (username == null || !UserManager.getInstance().isRegisteredUser(recipient)) {
                 debug("no username for the message");
                return;
            } else if (!XMPPServer.getInstance().getServerInfo().getXMPPDomain().equals(recipient.getDomain())) {
                // 非当前openfire服务器信息
                 debug("message not from this server");
                return;
            } else if ("".equals(recipient.getResource())) {
                 debug("resouce is empty");
            }
        }

        Packet copyPacket = packet.createCopy();
        if (packet instanceof Message) {
            Message message = (Message) copyPacket;

            Element extElement =message.getChildElement("ext","ihelper:notice:order");
            debug("extElement is "+extElement);
            // 一对一聊天，单人模式
            if (message.getType() == Message.Type.chat ||extElement!=null) {
                log.info("单人聊天信息：{}", message.toXML());
                // 程序执行中；是否为结束或返回状态（是否是当前session用户发送消息）
                if (processed || !incoming) {
                    return;
                }
                //调用接口
                String postData = BuildPostData(message.toXML());
                try {
                    debug("message to be hanlder-------------------------");
                    sendPost("http://localhost:8037/dianzhuapi.ashx", postData);
                    //logsManager.add(this.get(packet, incoming, session));
                }
                catch(Exception ex)
                {
                    log.error(ex.toString());
                }

                // 群聊天，多人模式
            }
            else
            {
                log.debug("type is not chat, skipped.");
            }


        }

    }
    private String BuildPostData(String message)
    {
        JSONObject  jsonChat=new JSONObject();
        JSONObject jsonChat_Req=new JSONObject();

        try{

            jsonChat.put("protocol_CODE","Chat001008");
            jsonChat_Req.put("message",message);
            jsonChat.put("reqData",jsonChat_Req);

            String requestData= jsonChat.toString();
            debug("requestData:"+requestData);
            return requestData;
        }
        catch(Exception ex)
        {
            return "";
        }

    }
    private void sendPost(String url,String postBody) throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        HttpEntity body=new ByteArrayEntity(postBody.getBytes("UTF-8"));
        post.setEntity(body);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
         // add header

        HttpResponse response = client.execute(post);
        int responseCode=response.getStatusLine().getStatusCode();

        debug("\nSending 'POST' request to URL : " + url);
        debug("Post parameters : " + postBody);
        debug("Response Code : " +responseCode);


        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        String responseBody=result.toString();
        log.debug(responseBody);
        if(responseCode!=200)
        {
            log.error(responseBody);
        }
       debug(responseBody);

    }
    private void doAction(Packet packet, boolean incoming, boolean processed, Session session) {

    }
    private void debug(String content)
    {
       // System.out.println(content);
        log.debug(content);
    }



}