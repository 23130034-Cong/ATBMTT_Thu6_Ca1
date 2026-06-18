package tool;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Base64;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Tool extends JFrame {
    private CardLayout cardLayout;
    private JPanel pnHoldAllPage;
    private JTextArea txtinputA;
    private JTextArea txtinputB;
    private JTextArea txtResult;

    public Tool() {
        setTitle("Tool Chữ Ký Điện Tử");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 215, 215)));
        JMenu mMain = new JMenu("Tạo Chữ Ký Số");
        mMain.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JMenu mHelp = new JMenu("Trợ Giúp / Liên Hệ");
        mHelp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        menuBar.add(mMain);
        menuBar.add(mHelp);
        setJMenuBar(menuBar);

        cardLayout = new CardLayout();
        pnHoldAllPage = new JPanel(cardLayout);

        //Main
        JPanel pageM = new JPanel(new BorderLayout(10, 10));
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.setBorder(new EmptyBorder(15, 15, 15, 15));

        //HASH
        JPanel pnA = new JPanel(new BorderLayout(5, 5));
        TitledBorder titledBorderA = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Nhập Mã Băm Từ Web",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14));
        EmptyBorder marginBorder = new EmptyBorder(0, 15, 0, 15);
        pnA.setBorder(BorderFactory.createCompoundBorder(titledBorderA, marginBorder));

        txtinputA = new JTextArea(3, 50);
        txtinputA.setLineWrap(true);
        txtinputA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        pnA.add(new JScrollPane(txtinputA), BorderLayout.CENTER);

        JPanel pnActl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnPasteHash = new JButton("Dán mã băm vào"); // Nút mới
        btnPasteHash.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnPasteHash.setPreferredSize(new Dimension(150, 35));

        pnActl.add(btnPasteHash);
        pnA.add(pnActl, BorderLayout.SOUTH);

        //KEY
        JPanel pnB = new JPanel(new BorderLayout(5, 5));
        TitledBorder titledBorderB = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Nhập Private Key",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14));
        pnB.setBorder(BorderFactory.createCompoundBorder(titledBorderB, marginBorder));

        txtinputB = new JTextArea(3, 50);
        txtinputB.setLineWrap(true);
        txtinputB.setFont(new Font("Monospaced", Font.PLAIN, 12));
        pnB.add(new JScrollPane(txtinputB), BorderLayout.CENTER);

        JPanel pnBctl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnPasteKey = new JButton("Dán private key"); // Nút mới
        btnPasteKey.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnPasteKey.setPreferredSize(new Dimension(150, 35));

        JButton btnLoadFileKey = new JButton("Nhập bằng file");
        btnLoadFileKey.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLoadFileKey.setPreferredSize(new Dimension(150, 35));

        pnBctl.add(btnPasteKey);
        pnBctl.add(btnLoadFileKey);
        pnB.add(pnBctl, BorderLayout.SOUTH);

        //btn ký
        JPanel pnSignbtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSign = new JButton("KÝ");
        btnSign.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSign.setPreferredSize(new Dimension(150, 45));
        pnSignbtn.add(btnSign);

        //KẾT QUẢ
        JPanel pnC = new JPanel(new BorderLayout(5, 5));
        TitledBorder titledBorderC = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Chữ Ký Điện Tử",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14));
        pnC.setBorder(BorderFactory.createCompoundBorder(titledBorderC, marginBorder));

        txtResult = new JTextArea(3, 50);
        txtResult.setLineWrap(true);
        txtResult.setFont(new Font("Monospaced", Font.BOLD, 12));
        txtResult.setEditable(false);
        txtResult.setBackground(new Color(245, 245, 245));

        pnC.add(new JScrollPane(txtResult), BorderLayout.CENTER);
        JPanel pnCctl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCopyHash = new JButton("Copy chữ ký số");
        btnCopyHash.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCopyHash.setPreferredSize(new Dimension(150, 35));

        pnCctl.add(btnCopyHash);
        pnC.add(pnCctl, BorderLayout.SOUTH);

        pnMain.add(pnA);
        pnMain.add(Box.createVerticalStrut(15));
        pnMain.add(pnB);
        pnMain.add(Box.createVerticalStrut(15));
        pnMain.add(pnSignbtn);
        pnMain.add(Box.createVerticalStrut(15));
        pnMain.add(pnC);

        //hướng dẫn tạo chữ ký
        JPanel pnHD = new JPanel();
        pnHD.setLayout(new BoxLayout(pnHD, BoxLayout.Y_AXIS));
        pnHD.setBorder(new EmptyBorder(15, 0, 15, 15));
        pnHD.setPreferredSize(new Dimension(300, 700));

        JPanel pnH = new JPanel(new BorderLayout());
        pnH.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JLabel lblTitleHD = new JLabel("HƯỚNG DẪN LẤY CHỮ KÝ ĐIỆN TỬ");
        lblTitleHD.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitleHD.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        pnH.add(lblTitleHD, BorderLayout.NORTH);
        JTextArea txtHD = new JTextArea();
        txtHD.setEditable(false);
        txtHD.setLineWrap(true);
        txtHD.setWrapStyleWord(true);
        txtHD.setBackground(getBackground());
        txtHD.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHD.setText(
                "CÁC BƯỚC THỰC HIỆN KÝ SỐ\n\n" +
                        "Bước 1: Sao chép mã băm thông tin đơn hàng từ website hệ thống và dán vào ô 'Nhập Mã Băm Từ Web'.\n\n" +
                        "Bước 2: Nhập private key - khóa bí mật của bạn vào ô 'Nhập Private Key' (hoặc nhấn nút 'nhập bằng file' để tải file từ máy lên).\n\n" +
                        "Bước 3: Nhấn nút 'KÝ' để tạo chữ chữ ký điện tử cho đơn hàng của bạn.\n\n" +
                        "Bước 4: Nhấn nút 'Sao chép chữ ký' để lấy kết quả và ký vào đơn hàng để tiếp tục thanh toán đơn trên hệ thống website."
        );
        pnH.add(new JScrollPane(txtHD), BorderLayout.CENTER);
        pnHD.add(pnH);

        //TRANG TRỢ GIÚP
        JPanel pageH = new JPanel(new BorderLayout());
        pageH.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel pnHelp = new JPanel(new BorderLayout(5, 5));
        TitledBorder titledBorderHelp = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Thông Tin Liên Hệ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14));
        pnHelp.setBorder(BorderFactory.createCompoundBorder(titledBorderHelp, marginBorder));

        JTextArea txtHelpInfo = new JTextArea();
        txtHelpInfo.setEditable(false);
        txtHelpInfo.setLineWrap(true);
        txtHelpInfo.setWrapStyleWord(true);
        txtHelpInfo.setBackground(new Color(255, 255, 255));
        txtHelpInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtHelpInfo.setText(
                "TRỢ GIÚP & LIÊN HỆ\n\n" +
                        "Nếu bạn gặp khó khăn trong quá trình sử dụng hệ thống ký số, vui lòng liên hệ cho chúng tôi qua:\n\n" +
                        "- Website:  \n" +
                        "- Email:   \n" +
                        "- Hotline:  \n" +
                        "Xin cảm ơn!"
        );

        pageM.add(pnMain, BorderLayout.CENTER);
        pageM.add(pnHD, BorderLayout.EAST);

        pnHelp.add(new JScrollPane(txtHelpInfo), BorderLayout.CENTER);
        pageH.add(pnHelp, BorderLayout.CENTER);

        pnHoldAllPage.add(pageM, "TrangKySo");
        pnHoldAllPage.add(pageH, "TrangTroGiup");

        setLayout(new BorderLayout());
        add(pnHoldAllPage, BorderLayout.CENTER);

        //SỰ KIỆN MENU CHUYỂN TRANG
        mMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnHoldAllPage, "TrangKySo");
            }
        });

        mHelp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(pnHoldAllPage, "TrangTroGiup");
            }
        });

        //DÁN MÃ BĂM
        btnPasteHash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pasteFromClipboard(txtinputA);
            }
        });

        //DÁN KEY
        btnPasteKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pasteFromClipboard(txtinputB);
            }
        });

        //ĐỌC

        btnLoadFileKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readKeyFile(txtinputB);
            }
        });

        // COPY CHỮ KÝ
        btnCopyHash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String resultText = txtResult.getText().trim();
                if (!resultText.isEmpty() && !resultText.startsWith("mã băm") && !resultText.startsWith("Đã xảy ra lỗi")) {
                    java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(resultText);
                    java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
            }
        });

        //SK NÚT KÝ
        btnSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hashText = txtinputA.getText().trim();
                String keyText = txtinputB.getText().trim();

                if (hashText.isEmpty() || keyText.isEmpty()) {
                    txtResult.setText("mã băm hoặc khóa bí mật trống");
                    return;
                }
                try {
                    byte[] encryptByte = encryptKeyWithPassword(hashText, keyText);
                    String out = Base64.getEncoder().encodeToString(encryptByte);
                    txtResult.setText(out);
                } catch (Exception ex) {
                    txtResult.setText("Đã xảy ra lỗi trong quá trình mã hóa:\n" + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
    }

    private static byte[] encryptKeyWithPassword(String privateKeyPem, String password) throws Exception {
        byte[] key = password.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");

        byte[] ivBytes = new byte[16];
        System.arraycopy(key, 0, ivBytes, 0, 16);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        return cipher.doFinal(privateKeyPem.getBytes("UTF-8"));
    }

    private void readKeyFile(JTextArea targetText) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file dữ liệu đầu vào");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("File văn bản (*.pem, *.txt)", "pem", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(Tool.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                targetText.setText(content.toString().trim());
            } catch (Exception ex) {
                txtResult.setText("Lỗi khi đọc file: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }


    private void pasteFromClipboard(JTextArea targetTextArea) {
        try {
            String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            if (data != null) {
                targetTextArea.setText(data.trim());
            }
        } catch (Exception ex) {
            txtResult.setText("Không thể lấy dữ liệu từ bảng tạm: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tool().setVisible(true);
            }
        });
    }
}