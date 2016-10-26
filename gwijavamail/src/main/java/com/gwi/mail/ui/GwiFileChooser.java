package com.gwi.mail.ui;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.mail.MailManager;
import com.gwi.mail.parse.ParseManager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Created by Administrator on 2016-10-26.
 */
public class GwiFileChooser extends JFrame implements ActionListener {
    private JButton mOpen;

    public GwiFileChooser() {
        mOpen = new JButton(GwiConfigs.LABEL_DOCUMENT);
        //mOpen.setBackground(Color.pink);
        Font font = new Font("宋体", Font.BOLD, 20);
        mOpen.setFont(font);
        mOpen.setBorder(BorderFactory.createRaisedBevelBorder());
        this.add(mOpen);
        this.setBounds(400, 200, 400, 400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mOpen.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.showDialog(new JLabel(), GwiConfigs.LABEL_CHOICE);
        File file = jfc.getSelectedFile();
        if (null != file) {
            if (file.isDirectory()) {
                System.out.println("Folder:" + file.getAbsolutePath());
            } else if (file.isFile()) {
                // "C:/Users/Administrator/Desktop/kaoqin/kq.txt"
                System.out.println("File:" + file.getAbsolutePath());
                work(file.getAbsolutePath());
            }
        }
    }

    private void work(final String filePath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> hashMap = ParseManager.getInstance().parse(filePath);

                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    System.out.println("Email:" + entry.getKey() + " Content:" + entry.getValue());
                    String email = MailManager.getInstance().creatGwiMail(entry.getKey());
                    StringBuffer sb = new StringBuffer();
                    sb.append(GwiConfigs.MAIL_CONTENT).append("<br>").append(entry.getValue());
                    try {
                        MailManager.getInstance().sendMimeMail(email, sb.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                String msg = String.format(GwiConfigs.LABEL_MSG, hashMap.size());
                JOptionPane.showMessageDialog(null, msg, GwiConfigs.LABEL_TITLE, JOptionPane.PLAIN_MESSAGE);
            }
        }).start();
    }
}