package tool;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;
import java.util.Arrays;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Tool extends JFrame {
    private CardLayout cardLayout;
    private JPanel pnHoldAllPage;

    public Tool() {
        setTitle("Tool Chũ Ký Điẹn Tử");
        setSize(950,700);
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
        JPanel pageM = new JPanel(new BorderLayout(10,10));
        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.setBorder(new EmptyBorder(15, 15, 15, 15));

        //HASH
        JPanel pnA = new JPanel(new BorderLayout(5, 5));
        TitledBorder titledBorderA = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Nhập Mã Băm Từ Wed",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14));
        EmptyBorder marginBorder = new EmptyBorder(0, 15, 0, 15);
        pnA.setBorder(BorderFactory.createCompoundBorder(titledBorderA, marginBorder));

        JTextArea txtinputA = new JTextArea(3,50);
        txtinputA.setLineWrap(true);
        txtinputA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        pnA.add(new JScrollPane(txtinputA), BorderLayout.CENTER);

        JPanel pnActl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLoadFileHash = new JButton("Nhập bằng file");
        btnLoadFileHash.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLoadFileHash.setPreferredSize(new Dimension(150, 35));

        pnActl.add(btnLoadFileHash);
        pnA.add(pnActl, BorderLayout.SOUTH);

        //KEY
        JPanel pnB = new JPanel(new BorderLayout(5,5) );
        TitledBorder titledBorderB = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Nhập Private Key",
                TitledBorder.LEFT, TitledBorder.TOP,  new Font("Segoe UI", Font.BOLD, 14));
        pnB.setBorder(BorderFactory.createCompoundBorder(titledBorderB, marginBorder));

        JTextArea txtinputB = new JTextArea(3,50);
        txtinputB.setLineWrap(true);
        txtinputB.setFont(new Font("Monospaced", Font.PLAIN, 12));
        pnB.add(new JScrollPane(txtinputB), BorderLayout.CENTER);

        JPanel pnBctl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLoadFileKey = new JButton("Nhập bằng file");
        btnLoadFileKey.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLoadFileKey.setPreferredSize(new Dimension(150, 35));

        pnBctl.add(btnLoadFileKey);
        pnB.add(pnBctl, BorderLayout.SOUTH);

        //btn ký
        JPanel pnSignbtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSign = new JButton("KÝ");
        btnSign.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnSign.setPreferredSize(new Dimension(150,45));

        pnSignbtn.add(btnSign);


        //KẾT QUẢ
        JPanel pnC = new JPanel(new BorderLayout(5,5) );
        TitledBorder titledBorderC = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Chữ Ký Điện Tử",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14));
        pnC.setBorder(BorderFactory.createCompoundBorder(titledBorderC, marginBorder));

        JTextArea txtResult = new JTextArea(3,50);
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
        pnHD.setBorder(new EmptyBorder(15,0,15,15));
        pnHD.setPreferredSize(new Dimension(300,700));

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
                "CÁC BƯỚC THỰC HIỆN KÝ SỐ:\n\n"

        );
        pnH.add(new JScrollPane(txtHD),BorderLayout.CENTER);
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
                        "Nếu bạn gặp khó khăn trong quá trình sử dụng hệ thống ký số, vui lòng liên hệ qua:\n\n" +
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



    }
    public static byte[] encryptKeyWithPassword(String privateKeyPem, String password) throws Exception {
        // Chuyển password thành key AES 256-bit (32 bytes)
        byte[] key = password.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        //  Khởi tạo Cipher (AES/CBC/PKCS5Padding)
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //  Trong thực tế bạn nên tạo IV ngẫu nhiên và lưu cùng file
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // Mã hóa
        return cipher.doFinal(privateKeyPem.getBytes("UTF-8"));
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