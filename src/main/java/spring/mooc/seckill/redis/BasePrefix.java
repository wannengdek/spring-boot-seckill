package spring.mooc.seckill.redis;

public abstract class BasePrefix implements KeyPrefix{


	private int expireSeconds;
	//过期时间

	private String prefix;
	// 前缀

	public BasePrefix(String prefix) {//0代表永不过期
		this(0, prefix);
	}

	public BasePrefix( int expireSeconds, String prefix) {
		this.expireSeconds = expireSeconds;
		this.prefix = prefix;
	}

	@Override
	public int expireSeconds() {//默认0代表永不过期
		return expireSeconds;
	}

	/**
	 *  以类名为前缀
	 * @return
	 */
	@Override
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":" + prefix;
	}

}
