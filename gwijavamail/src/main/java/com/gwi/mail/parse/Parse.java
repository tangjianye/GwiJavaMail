package com.gwi.mail.parse;

import com.gwi.mail.entity.TxtEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016-10-27.
 */
public class Parse {
    /**
     * 直接解析出来的对象
     */
    protected ArrayList<TxtEntity> mParseList;
    /**
     * 合成的发送邮件的对象<邮箱账号，邮件内容>
     */
    protected HashMap<String, String> mHashMap;

    public Parse() {
        mParseList = new ArrayList<>();
        mHashMap = new HashMap<>();
    }
}
