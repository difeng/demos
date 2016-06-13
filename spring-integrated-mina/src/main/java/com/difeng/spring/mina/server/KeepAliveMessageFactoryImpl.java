package com.difeng.spring.mina.server;
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
	static AtomicInteger count = new AtomicInteger();
	Logger logger = Logger.getLogger(KeepAliveMessageFactoryImpl.class);
	//客户端心跳请求信息的标志
	private static final String HEART_REQUEST_INFO_FLAG = "0";
    //返回给客户端的心跳反馈信息的标志
	private static final String HEART_RESPONSE_INFO_FLAG = "1";
	
	@Override
	public boolean isRequest(IoSession session, Object message) {
		String requstInfo = message.toString();
		if(requstInfo != null && requstInfo.startsWith(HEART_REQUEST_INFO_FLAG)){
			return true;
		}
		return false;
	}

	@Override
	public boolean isResponse(IoSession session, Object message) {
		return false;
	}

	@Override
	public Object getRequest(IoSession session) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResponse(IoSession session, Object request) {
		logger.info(" heartbeat response:" + HEART_RESPONSE_INFO_FLAG + ",sessionId=" + session.getId());
		//心跳回应信息
		logger.info("server response times:" + count.addAndGet(1));
		return HEART_RESPONSE_INFO_FLAG;
	}

}

