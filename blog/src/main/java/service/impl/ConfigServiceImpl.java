package service.impl;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import service.ConfigService;
import util.Constants;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by scott on 2017/3/15.
 */
@Service("configService")
public class ConfigServiceImpl implements ConfigService {
    /**
     * 密钥
     */
//    private static String _TOKEN_KEY;

    private static Properties prop;

    static {
//        Properties prop;

        try {
            // 读取配置文件
            ClassPathResource resource = new ClassPathResource("env.properties");
            prop = PropertiesLoaderUtils.loadProperties(resource);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not load 'env.properties': " + ex.getMessage());
        }

//        _TOKEN_KEY = prop.getProperty("_TOKEN_KEY");
    }


    /**
     * 加载配置文件
     */
    // todo 后期把这个接口设为私有接口
    @Override
    public String getConfig(String key) {
//        return _TOKEN_KEY;
        return prop.getProperty(Constants.SAVE_PATH);
    }

    /**
     * 加密
     */
    // todo 暂时没有加密代码。直接使用明文。
    @Override
    public String encode(String message) {
        return message;
    }

    /**
     * 解密
     * @param message
     * @return
     */
    // todo 暂时没有解密代码。直接使用原文。
    @Override
    public String decode(String message) {
        return message;
    }
}
