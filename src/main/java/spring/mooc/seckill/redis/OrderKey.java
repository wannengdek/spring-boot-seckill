package spring.mooc.seckill.redis;

public class OrderKey extends BasePrefix {

	public OrderKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

	public OrderKey(String moug) {
		super(moug);
	}
}
