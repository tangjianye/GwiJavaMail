package com.gwi.mail.parse;

import java.util.HashMap;

/**
 * Created by Administrator on 2016-10-27.
 */
public class ParseStrategy {
    private IParse mParse;

    public ParseStrategy(IParse parse) {
        mParse = parse;
    }

    public HashMap<String, String> doParse(String filePath) {
        return mParse.parse(filePath);
    }
}
