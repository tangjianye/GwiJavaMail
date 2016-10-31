package com.gwi.mail.ui;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.mail.MailProcess;
import com.gwi.mail.utils.FileFilterXLS;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
        jfc.setFileFilter(new FileFilterXLS());
        jfc.showDialog(new JLabel(), GwiConfigs.LABEL_CHOICE);
        File file = jfc.getSelectedFile();
        if (null != file) {
            if (file.isDirectory()) {
                System.out.println("Folder:" + file.getAbsolutePath());
            } else if (file.isFile()) {
                // "C:/Users/Administrator/Desktop/kaoqin/002.xls"
                System.out.println("File:" + file.getAbsolutePath());
                MailProcess.getInstance().mailGroupBuild(file.getAbsolutePath());
            }
        }
    }
}