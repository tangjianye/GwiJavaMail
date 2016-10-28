package com.gwi.mail.parse;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.entity.TxtEntity;
import com.gwi.mail.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2016-10-25.
 * 解析逻辑需要优化
 */
@Deprecated
public class ParseText extends Parse implements IParse {
    private static final String ENCODING = "GBK";

    public ParseText() {
        super();
    }

    private void readTxtFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                ArrayList<TxtEntity> list = new ArrayList<>();
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                    TxtEntity bean = parseStr(lineTxt);
                    if (null != bean) {
                        list.add(bean);
                    }
                }
                // 解析打卡异常的工号
                getAbnormalJobNomberTxt(list);
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    private TxtEntity parseStr(String str) {
        if (null == str) {
            return null;
        }
        TxtEntity bean = new TxtEntity();
        String strr = str.trim();
        String[] abc = strr.split("\\t");
        bean.setJobNumber(abc[0]);
        bean.setDate(abc[1]);
        System.out.println(bean.getJobNumber());
        System.out.println(bean.getDate());
        return bean;
    }

    /**
     * 获取打卡异常员工工号
     *
     * @return
     */
    private void getAbnormalJobNomberTxt(ArrayList<TxtEntity> list) {
        mHashMap.clear();
        final Date MORNING = CommonUtils.getParseTime(GwiConfigs.WorkTime.MORNING);
        final Date AFTERNOON = CommonUtils.getParseTime(GwiConfigs.WorkTime.AFTERNOON);

        for (TxtEntity entity : list) {
            Date date = CommonUtils.getParseDate(entity.getDate());

            // 打卡异常的员工
            if (date.after(MORNING) && date.before(AFTERNOON)) {
                // 有过打卡异常
                StringBuffer sb = new StringBuffer();
                if (mHashMap.containsKey(entity.getJobNumber())) {
                    String clock = mHashMap.get(entity.getJobNumber());
                    sb.append(clock).append(entity.getDate()).append("<br>");
                    mHashMap.remove(entity.getJobNumber());
                } else {
                    sb.append(entity.getDate()).append("<br>");
                }
                mHashMap.put(entity.getJobNumber(), sb.toString());
            }
        }
    }

    @Override
    public HashMap<String, String> parse(String filePath) {
        readTxtFile(filePath);
        return mHashMap;
    }
}
