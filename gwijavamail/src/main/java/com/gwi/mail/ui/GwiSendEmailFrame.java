package com.gwi.mail.ui;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.mail.MailProcess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class GwiSendEmailFrame extends JFrame implements ActionListener {

    private String[] mAddress;
    private String mMailContent;
    private JTextArea mAddressText;

    public GwiSendEmailFrame(String mailReceiver, String mailContent, String[] receiverAddress) {
        this.mAddress = receiverAddress;
        this.mMailContent = mailContent;

        // 收件人布局
        JPanel receiverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        receiverPanel.add(new JLabel("收件人:"));

        JScrollPane recScrollPane = new JScrollPane(); // 收件人滚动面板
        recScrollPane.setPreferredSize(new Dimension(650, 100));
        mAddressText = new JTextArea();
        mAddressText.setText(mailReceiver);
        recScrollPane.setViewportView(mAddressText);

        receiverPanel.add(recScrollPane);

        this.getContentPane().add(receiverPanel, BorderLayout.NORTH);
        // 邮件内容布局
        JPanel mailContentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        mailContentPanel.add(new JLabel("邮件正文:"));

        JScrollPane contentScrollPane = new JScrollPane();
        contentScrollPane.setPreferredSize(new Dimension(650, 300));
        JLabel contentLabel = new JLabel("<html>" + mailContent + "</html>");
        contentScrollPane.setViewportView(contentLabel);

        mailContentPanel.add(contentScrollPane);
        mailContentPanel.setBackground(Color.WHITE);
        this.getContentPane().add(mailContentPanel, BorderLayout.CENTER);

        // 发送邮件按钮布局
        JButton sendEmailBtn = new JButton(GwiConfigs.LABEL_SEND_EMAIL);
        sendEmailBtn.setBackground(Color.lightGray);
        Font font = new Font("宋体", Font.BOLD, 20);
        sendEmailBtn.setFont(font);
        sendEmailBtn.setBorder(new EmptyBorder(10, 10, 10, 10));
        sendEmailBtn.addActionListener(this);
        this.getContentPane().add(sendEmailBtn, BorderLayout.SOUTH);
        this.setTitle(GwiConfigs.LABEL_SEND_EMAIL_TITLE);
        this.setBounds(200, 50, 800, 600);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mAddress = mAddressText.getText().trim().split(",");
        if (mAddress.length == 0) {
            return;
        }
        MailProcess.getInstance().mailGroupSend(mAddress, mMailContent);
    }
}
