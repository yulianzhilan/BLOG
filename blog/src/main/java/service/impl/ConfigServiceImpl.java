package service.impl;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import service.ConfigService;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by scott on 2017/3/15.
 */
public class ConfigServiceImpl implements ConfigService {
    private static String _TOKEN_KEY;

    static {
        Properties prop;

        try {
            ClassPathResource resource = new ClassPathResource("env.properties");
            prop = PropertiesLoaderUtils.loadProperties(resource);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not load 'env.properties': " + ex.getMessage());
        }

        _TOKEN_KEY = prop.getProperty("_TOKEN_KEY");
    }

    @Override
    public String getConfig(String key) {
        return _TOKEN_KEY;
    }
}
