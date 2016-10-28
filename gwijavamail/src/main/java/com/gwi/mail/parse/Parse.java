package com.gwi.mail.parse;

import java.util.HashMap;

/**
 * Created by Administrator on 2016-10-27.
 */
public class Parse {
    /**
     * 合成的发送邮件的对象<邮箱账号，邮件内容>
     */
    protected HashMap<String, String> mHashMap;

    public Parse() {
        mHashMap = new HashMap<>();
    }
}
