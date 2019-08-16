package spring.mooc.seckill.redis;

public interface KeyPrefix {

	/**
	 *过期时间
	 * @return
	 */
	public int expireSeconds();

	/**
	 * 前缀
	 * @return
	 */
	public String getPrefix();

}
