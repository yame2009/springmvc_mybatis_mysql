package com.hb.util.json.jackson;

import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.ser.BeanSerializerFactory;

/**
 * @description 重写jackson 序列化工厂
 * @author liudejian  http://my.oschina.net/zuiwoxing/blog/115857
 * @date 2013-03-21
 */
public class BidBeanSerializerFactory extends BeanSerializerFactory {

	public final static BidBeanSerializerFactory instance = new BidBeanSerializerFactory(
			null);

	private Object filterId;

	protected BidBeanSerializerFactory(Config config) {
		super(config);
	}

	@Override
	protected Object findFilterId(SerializationConfig config,
			BasicBeanDescription beanDesc) {
		return getFilterId();
	}

	public Object getFilterId() {
		return filterId;
	}

	public void setFilterId(Object filterId) {
		this.filterId = filterId;
	}
}