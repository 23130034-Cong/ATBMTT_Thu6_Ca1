package tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Tool extends JFrame {

    public Tool() {
        setTitle("Tool Chũ Ký Điẹn Tử");
        setSize(950,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10,10));

        JPanel pnMain = new JPanel();
        pnMain.setLayout(new BoxLayout(pnMain, BoxLayout.Y_AXIS));
        pnMain.setBorder(new EmptyBorder(15, 15, 15, 15));

        //HASH
        JPanel pnA = new JPanel(new BorderLayout(5, 5));
        pnA.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "NHẬP MÃ BĂM TỪ WEBSITE",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12)));
        JTextArea txtinputA = new JTextArea(3,50);
        txtinputA.setLineWrap(true);
        txtinputA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        pnA.add(new JScrollPane(txtinputA), BorderLayout.CENTER);

        JPanel pnActl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnLoadFileHash = new JButton("Nhập bằng file");
        pnActl.add(btnLoadFileHash);
        pnA.add(pnActl, BorderLayout.SOUTH);

        //KEY
        JPanel pnB = new JPanel(new BorderLayout(5,5) );
        pnB.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "NHẬP PRIVATE KEY",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12)));
        JTextArea txtinputB = new JTextArea(3,50);
        txtinputB.setLineWrap(true);
        txtinputB.setFont(new Font("Monospaced", Font.PLAIN, 12));
        pnB.add(new JScrollPane(txtinputB), BorderLayout.CENTER);

        JPanel pnBctl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnLoadFileKey = new JButton("Nhập bằng file");
        pnBctl.add(btnLoadFileKey);
        pnB.add(pnBctl, BorderLayout.SOUTH);

        pnMain.add(pnA);
        pnMain.add(Box.createVerticalStrut(15));
        pnMain.add(pnB);

        add(pnMain, BorderLayout.CENTER);

        //KẾT QUẢ
        JPanel pnC = new JPanel(new BorderLayout(5,5) );
        pnC.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), "Chữ Ký Điện Tử",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12)));
        JTextArea txtResult = new JTextArea(3,50);
        txtResult.setLineWrap(true);
        txtResult.setFont(new Font("Monospaced", Font.BOLD, 12));
        txtResult.setEditable(false);
        txtResult.setBackground(new Color(245, 245, 245));
        pnC.add(new JScrollPane(txtResult), BorderLayout.CENTER);

        JPanel pnCctl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCopyHash = new JButton("Copy chữ ký số");
        pnCctl.add(btnCopyHash);
        pnC.add(pnCctl, BorderLayout.SOUTH);

        pnMain.add(pnA);
        pnMain.add(Box.createVerticalStrut(15));
        pnMain.add(pnB);
        pnMain.add(Box.createVerticalStrut(15));
        pnMain.add(pnC);

        //hướng dẫn tạo chữ ký bên phải
        JPanel pnHD = new JPanel();
        pnHD.setLayout(new BoxLayout(pnHD, BoxLayout.Y_AXIS));
        pnHD.setBorder(new EmptyBorder(15,5,15,15));
        pnHD.setPreferredSize(new Dimension(250,700));

        JPanel pnH = new JPanel(new BorderLayout());
        pnH.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "HƯỚNG DẪN LẤY CHỮ KÝ ĐIỆN TỬ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12)));

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

        add(pnMain, BorderLayout.CENTER);
        add(pnHD, BorderLayout.EAST);


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