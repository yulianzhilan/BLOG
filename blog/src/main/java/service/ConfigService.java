package service;

import org.springframework.stereotype.Service;

/**
 * Created by scott on 2017/3/15.
 */
//@Service
public interface ConfigService {
    String getConfig(String key);
}
