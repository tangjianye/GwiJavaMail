package com.gwi.mail.mail;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.parse.ParseExcelJxl;
import com.gwi.mail.parse.ParseStrategy;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 * Created by Administrator on 2016-10-31.
 */
public class MailProcess {
    private static final long DELAY = 200L;

    private static MailProcess ourInstance = new MailProcess();

    public static MailProcess getInstance() {
        return ourInstance;
    }

    public MailProcess() {
    }

    public void mailSinge(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ParseStrategy parse = new ParseStrategy(new ParseExcelJxl());
                HashMap<String, String> hashMap = parse.doParse(filePath);

                int count = 0;
                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    System.out.println("Email:" + entry.getKey() + " Content:" + entry.getValue());
                    String email = creatGwiMail(entry.getKey());
                    StringBuffer sb = new StringBuffer();
                    sb.append(GwiConfigs.MAIL_CONTENT).append("<br>").append(entry.getValue());
                    try {
                        Thread.sleep(DELAY);
                        count++;
                        MailManager.getInstance().sendMimeMail(makeGwiMailSingle(email), sb.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("hashMap.size = " + hashMap.size() + " ;count = " + count);
                String msg = String.format(GwiConfigs.LABEL_MSG, count);
                JOptionPane.showMessageDialog(null, msg, GwiConfigs.LABEL_TITLE, JOptionPane.PLAIN_MESSAGE);
            }
        }).start();
    }

    public void mailGroup(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ParseStrategy parse = new ParseStrategy(new ParseExcelJxl());
                HashMap<String, String> hashMap = parse.doParse(filePath);

                int count = 0;
                String[] address = new String[hashMap.size()];
                StringBuffer sb = new StringBuffer();
                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    System.out.println("Email:" + entry.getKey() + " Content:" + entry.getValue());
                    String email = creatGwiMail(entry.getKey());
                    address[count] = email;
                    sb.append(entry.getKey()).append(GwiConfigs.MAIL_CONTENT).append("<br>").append(entry.getValue()).append("<br>");
                    count++;
                }

                // 群发邮件
                count = 0;
                try {
                    count++;
                    MailManager.getInstance().sendMimeMail(address, sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("hashMap.size = " + hashMap.size() + " ;count = " + count);
                String msg = String.format(GwiConfigs.LABEL_MSG, count);
                JOptionPane.showMessageDialog(null, msg, GwiConfigs.LABEL_TITLE, JOptionPane.PLAIN_MESSAGE);
            }
        }).start();
    }

    private String creatGwiMail(String jobNumber) {
        StringBuffer sb = new StringBuffer();
        sb.append(jobNumber).append(GwiConfigs.Mail.RECEIVE_EMAIL_SUFFIX);
        return sb.toString();
    }

    private String[] makeGwiMailSingle(String address) {
        String[] group = new String[1];
        group[0] = address;
        return group;
    }
}
