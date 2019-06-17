package com.github.chillibox.exp.utils;

import com.google.common.io.BaseEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;

/**
 * ID util
 * <p>Created on 2017/6/25.</p>
 *
 * @author Gonster
 */

@Component
public class IdUtil {

    private static final int bitLength = 5;

    private int couponBytes = 7;

    private Constants constants;

    @Autowired
    public void setConstants(Constants constants) {
        this.constants = constants;
    }

    private final SecureRandom RANDOM = new SecureRandom();

    @PostConstruct
    void init() {
        couponBytes = 16 * bitLength / Byte.SIZE;
    }

    /**
     * 生成根据配置文件中配置的长度和分割符生成对应的随机字符串,
     * 由于输出字符串由BASE32编码，对应5位，随机源最少增加8位，故不能一一对应输出长度，
     * 如设置长度为1时，输出的id长度为0
     *
     * @return 随机字符串
     */
    public String nextCouponId() {
        byte[] bytes = new byte[couponBytes];
        RANDOM.nextBytes(bytes);
        return BaseEncoding.base32().omitPadding()
                .withSeparator("-", 4)
                .encode(bytes);
    }
}
