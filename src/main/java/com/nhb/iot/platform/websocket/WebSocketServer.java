package com.nhb.iot.platform.websocket;

import java.util.List;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nhb.iot.platform.util.JsonMapper;
import com.nhb.iot.platform.websocket.response.WebSockerResponse;

/**
 * @author sunlei
 * @ClassName: WebSocketServer
 * @Function: WebSocket通信类
 * @date: Aug 8, 2017 11:59:49 AM
 * @since JDK 1.8
 */
@ServerEndpoint(value = "/api/websocket/info")
@Component
public class WebSocketServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

	/**
	 * 客户端在线列表
	 */
	public static Map<String, Object> connections = Maps.newHashMap();

	/**
	 * 客户端连接session
	 */
	private Session session;

	/**
	 * start: 客户端建立连接
	 *
	 * @param session
	 * @author sunlei
	 * @since JDK 1.8
	 */
	@OnOpen
	public void afterConnectionEstablished(Session session) {
		this.session = session;
	}

	/**
	 * end: 客户端断开连接
	 *
	 * @author sunlei
	 * @since JDK 1.8
	 */
	@OnClose
	public void afterConnectionClosed() {
		connections.remove(this);
		LOGGER.info("websocket closed ......");
	}

	/**
	 * incoming: 客户端发送的消息
	 *
	 * @param msgInfo
	 * @author sunlei
	 * @since JDK 1.8
	 */
	@OnMessage
	public void handleMessage(String msgInfo) {
		if (!StringUtils.isEmpty(msgInfo)) {
			@SuppressWarnings("unchecked")
			Map<String, String> msgValue = JsonMapper.fromJsonString(msgInfo, Map.class);
			if (MapUtils.isNotEmpty(msgValue)) {
				String userId = msgValue.get("userId");
				connections.put(userId, this);
			}
			LOGGER.info("websocket connection ......");
		}
	}

	/**
	 * onError: 连接过程中出现异常
	 *
	 * @param
	 * @throws Throwable
	 * @author sunlei
	 * @since JDK 1.8
	 */
	@OnError
	public void handleTransportError(Session session, Throwable exception) throws Throwable {
		if (session.isOpen()) {
			session.close();
		}
		LOGGER.error("websocket connection closed......" + exception.toString());
		connections.remove(session);
	}

	/**
	 * sendAll: 推送给所有在线用户
	 *
	 * @param msg
	 * @author sunlei
	 * @since JDK 1.8
	 */
	public static void pushMessageToUsers(String msg) {
		WebSocketServer client = null;
		List<String> removeKey = Lists.newArrayList();
		for (String key : connections.keySet()) {
			try {
				sendMsg(client, key, msg);
			} catch (Exception e) {
				LOGGER.error("Push message error: Failed to send message to user", e);
				removeKey.add(key);
			}
		}
		removeKey(removeKey, client);
	}

	static void sendMsg(WebSocketServer client, String key, Object msg) throws Exception {
		client = (WebSocketServer) connections.get(key);
		if (null == client) {
			return;
		}
		if (null == client.session) {
			return;
		}
		if (!client.session.isOpen()) {
			return;
		}
		synchronized (client) {
			client.session.getBasicRemote().sendText(JsonMapper.toJsonString(msg));
		}
	}

	static void removeKey(List<String> removeKey, WebSocketServer client) {
		if (CollectionUtils.isNotEmpty(removeKey)) {
			for (String key : removeKey) {
				client = (WebSocketServer) connections.get(key);
				if (null == client) {
					continue;
				}
				if (null == client.session) {
					continue;
				}
				try {
					client.session.close();
				} catch (Exception e) {
				}
				connections.remove(key);
			}
		}
	}

	/**
	 * pushUser: 推送给指定用户
	 *
	 * @param userId
	 * @param msg
	 * @author sunlei
	 * @since JDK 1.8
	 */
	public static void pushMessageToUser(String userId, WebSockerResponse msg) {
		WebSocketServer client = null;
		List<String> removeKey = Lists.newArrayList();
		if (!StringUtils.isEmpty(userId)) {
			for (String key : connections.keySet()) {
				if (key.indexOf(userId) == -1) {
					continue;
				}
				try {
					sendMsg(client, key, msg);
				} catch (Exception e) {
					LOGGER.error("Push message error: Failed to send message to user,userId:" + userId, e);
					removeKey.add(key);
				}
			}
			removeKey(removeKey, client);
		}
	}
}
