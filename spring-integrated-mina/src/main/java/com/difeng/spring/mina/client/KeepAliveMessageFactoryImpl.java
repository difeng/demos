package com.difeng.spring.mina.client;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * @Description:心跳信息工厂类,采用的是被动心跳机制
 * @author:difeng
 * @time:2016年6月12日 上午10:44:27
 */
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory{
	private static Logger logger = Logger.getLogger(KeepAliveMessageFactoryImpl.class);
	//客户端心跳请求信息的标志
	private static final String HEART_REQUEST_INFO_FLAG = "0";
    //返回给客户端的心跳反馈信息的标志
	private static final String HEART_RESPONSE_INFO_FLAG = "1";
	static AtomicInteger count = new AtomicInteger();
	@Override
	public boolean isRequest(IoSession session, Object message) {
		return false;
	}
	
	@Override
	public boolean isResponse(IoSession session, Object message) {
		String responseInfo = message.toString();
		System.out.println("received heart response from server:" + message.toString());
		logger.info("server response times:" + count.addAndGet(1));
		if(responseInfo != null && responseInfo.startsWith(HEART_RESPONSE_INFO_FLAG)){
			return true;
		}
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		String heartRequestInfo = HEART_REQUEST_INFO_FLAG.concat("_").concat("client1");
		logger.info("send heart request to server:" + heartRequestInfo + ",sessionId=" + session.getId());
		// TODO Auto-generated method stub
		return heartRequestInfo;
	}
    
	@Override
	public Object getResponse(IoSession session, Object request) {
		 return  null;
	}
}

