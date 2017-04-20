package com.example.kimo.axdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:${TYPE_NAME} <br/>
 * Function: ${TODO} ADD FUNCTION. <br/>
 * Reason:   ${TODO} ADD REASON. <br/>
 * Date:     2017/4/17 17:11 <br/>
 *
 * @author 76dgs02
 * @see
 * @since JDK 1.6
 */


public class BindIdInstance {
    private List<String> bindIds = new ArrayList<String>();

    private BindIdInstance() {
    }

    private static BindIdInstance instance;

    public static BindIdInstance getInstance() {
        if (instance == null) {
            synchronized (BindIdInstance.class) {
                if (instance == null) {
                    instance = new BindIdInstance();
                }
            }
        }
        return instance;
    }

    public void addBindId(String bindId) {
        bindIds.add(bindId);
    }

    public void removeBindId(String bindId) {
        bindIds.remove(bindId);
    }

    public List<String> getBindIds() {
        return bindIds;
    }
}
