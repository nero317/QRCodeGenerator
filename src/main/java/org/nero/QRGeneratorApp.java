package org.nero;

import com.google.zxing.WriterException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.nero.QRGenerator.generateQR;

public class QRGeneratorApp extends JFrame {
    private JTextField inputField;
    private JLabel qrCodeLabel;
    private BufferedImage qrCodeImage;

    public QRGeneratorApp() {
        setTitle("QR 코드 생성기");
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);


        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // 세로 배치
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
        JLabel inputLabel = new JLabel("텍스트 입력(URL, 일반 텍스트):");
        inputField = new JTextField(20);
        JButton generateButton = new JButton("QR 코드 생성");
        JButton saveButton = new JButton("QR 코드 저장");

        inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputField.setMaximumSize(new Dimension(200, 20)); //inputField 사이즈 지정

        inputPanel.add(Box.createVerticalStrut(60)); // 간격 추가
        inputPanel.add(inputLabel);
        inputPanel.add(Box.createVerticalStrut(10)); // 간격 추가
        inputPanel.add(inputField);
        inputPanel.add(Box.createVerticalStrut(10)); // 간격 추가
        inputPanel.add(generateButton);
        inputPanel.add(Box.createVerticalStrut(50)); // 간격 추가
        inputPanel.add(saveButton);

        qrCodeLabel = new JLabel();
        qrCodeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        qrCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputField.getText();
                if (!text.isEmpty()) {
                    try {
                        qrCodeImage = generateQR(text, 250, 250);
                        qrCodeLabel.setIcon(new ImageIcon(qrCodeImage));
                    } catch (IOException | WriterException ex) {
                        JOptionPane.showMessageDialog(QRGeneratorApp.this,
                                "QR 생성 오류 : " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(QRGeneratorApp.this,
                            "텍스트나 링크를 입력해주세요.",
                            "Notice",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (qrCodeImage != null) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("QR 코드 저장");
                    chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG Image", "png"));
                    int userSelection = chooser.showSaveDialog(QRGeneratorApp.this);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File file = chooser.getSelectedFile();
                        if (!file.getName().toLowerCase().endsWith(".png")) {
                            file = new File(file.getAbsolutePath() + ".png");
                        }
                        try {
                            ImageIO.write(qrCodeImage, "PNG", file);
                            JOptionPane.showMessageDialog(QRGeneratorApp.this,
                                    "QR 코드가 저장되었습니다: " + file.getAbsolutePath(),
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(QRGeneratorApp.this,
                                    "저장 실패: " + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(QRGeneratorApp.this,
                            "QR 코드를 먼저 생성해야 합니다.",
                            "Notice",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        add(inputPanel, BorderLayout.WEST);
        add(qrCodeLabel, BorderLayout.EAST);
    }
}
