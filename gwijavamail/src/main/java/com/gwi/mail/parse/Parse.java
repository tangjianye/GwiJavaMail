package com.gwi.mail.parse;

import com.gwi.mail.entity.AttendanceEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016-10-27.
 */
public class Parse {
    protected ArrayList<AttendanceEntity> mParseList;
    protected HashMap<String, String> mHashMap;

    public Parse() {
        mParseList = new ArrayList<>();
        mHashMap = new HashMap<>();
    }
}
