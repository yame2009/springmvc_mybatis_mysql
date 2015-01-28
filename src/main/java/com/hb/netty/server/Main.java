package com.hb.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.hb.netty.cfg.SpringConfig;

/**
 * Netty

	为了创建一个netty服务端, 通常我们需要以下几个步骤:

	创建ServerBootstrap对象
	设置参数像bossGroup, workerGroup, childHandler以及设置ChannelOption就像keep_alive针对bootstrap.
	使用bootstrap创建channel.
	确保可以退出程序, 你调用shutDownGracefully方法或者相似的方法.
	现在假设你已经在Spring中创建了bootstrap, 下面这几行代码足够启动服务端以及关闭.
 *
 */
public class Main {
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		LOG.debug("Starting application context");
		@SuppressWarnings("resource")
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(
				SpringConfig.class);
		ctx.registerShutdownHook();
	}

}
