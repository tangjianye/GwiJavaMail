package com.gwi.mail.parse;

import java.util.HashMap;

/**
 * Created by Administrator on 2016-10-27.
 */
public interface IParse {
    /**
     * <邮箱账号，邮件内容>
     *
     * @param filePath 解析的文件路径
     * @return
     */
    HashMap<String, String> parse(String filePath);
}
