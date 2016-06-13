package com.difeng.spring.mina.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @Description:mina的客户端
 * @author:duyongfeng
 * @time:2016年6月12日 上午11:33:44
 */

public class Client {
	private static Client client;
	private ConnectFuture cf;
	private SocketConnector connector;
	private IoHandlerAdapter clientHandler =  new ClientHandler();
	private String ip = "127.0.0.1";
	private int port = 30001;
	private Client(){
		init();
	}
	private void init(){
		clientHandler = new ClientHandler();
		// 创建一个socket连接        
		connector=new NioSocketConnector();  
	    // 创建接收数据的过滤器          
	    DefaultIoFilterChainBuilder chain=connector.getFilterChain();  
	    ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();
	    factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
	    factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
	    // 设定这个过滤器将一行一行(/r/n)的读取数据
	    ProtocolCodecFilter filter= new ProtocolCodecFilter(factory);   
	    // 添加编码过滤器 处理乱码、编码问题    
	    chain.addLast("chain",filter);  
	    // 设置链接超时时间       
	    connector.setConnectTimeoutMillis(180*1000); 
	    //心跳信息工厂实例
	    KeepAliveMessageFactoryImpl ckafi = new KeepAliveMessageFactoryImpl(); 
		KeepAliveFilter kaf = new KeepAliveFilter(ckafi, IdleStatus.READER_IDLE,KeepAliveRequestTimeoutHandler.CLOSE); 
		kaf.setForwardEvent(true); 
		kaf.setRequestInterval(15); 
		chain.addLast("heart", kaf);
		 // 消息核心处理器       
	    connector.setHandler(clientHandler);
		cf = connector.connect(new InetSocketAddress(
				this.ip, this.port));
		cf.awaitUninterruptibly();
	}
	public static Client getClient(){
		if(client == null){
			client = new Client();
		}
		return client;
	}
	
	public static void main(String[] args) {
		Client client = Client.getClient();
	}
}

