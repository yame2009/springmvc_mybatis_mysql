package com.hb.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TCPServer {

	@Autowired
	@Qualifier("serverBootstrap")
	private ServerBootstrap b;
	
	@Autowired
	@Qualifier("tcpSocketAddress")
	private InetSocketAddress tcpPort;

	private Channel serverChannel;

	/**
	 * 启动服务端
	 * @throws Exception
	 */
	@PostConstruct
	public void start() throws Exception {
		System.out.println("Starting server at " + tcpPort);
		serverChannel = b.bind(tcpPort).sync().channel().closeFuture().sync()
				.channel();
	}

	/**
	 * 退出服务，关闭服务端
	 */
	@PreDestroy
	public void stop() {
		serverChannel.close();
	}

	public ServerBootstrap getB() {
		return b;
	}

	public void setB(ServerBootstrap b) {
		this.b = b;
	}

	public InetSocketAddress getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(InetSocketAddress tcpPort) {
		this.tcpPort = tcpPort;
	}

}
