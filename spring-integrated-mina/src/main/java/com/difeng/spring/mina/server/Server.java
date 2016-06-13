package com.difeng.spring.mina.server;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * @Description:TODO
 * @author:duyongfeng
 * @time:2016年6月12日 下午10:21:08
 */
public class Server {
	public static void main(String[] args) {
		//Socket
		SocketAcceptor acceptor = new NioSocketAcceptor();
		// 创建接收数据的过滤器
		DefaultIoFilterChainBuilder chain = new org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder();
		// 设定这个过滤器将一行一行(/r/n)的读取数据
		ObjectSerializationCodecFactory codec = new ObjectSerializationCodecFactory();
		chain.addLast("chain", new ProtocolCodecFilter(codec));
		
        KeepAliveMessageFactoryImpl kamfi = new KeepAliveMessageFactoryImpl();
        KeepAliveFilter kaf = new KeepAliveFilter(kamfi);
        kaf.setForwardEvent(false);
        kaf.setRequestInterval(30);
        chain.addLast("heart", kaf);
        
        acceptor.setHandler(new ServerHandler());
        try {
  			acceptor.bind(new InetSocketAddress(30001));
  		} catch (Exception e) {
  		}
	}
}

