package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
//import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;

import controller.RSAController;
import model.RSA;

import java.awt.Color;

import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.EtchedBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RSAView extends JFrame {

	private JPanel contentPane;
	private JTextField txtNhapP;
	private JTextField txtNhapQ;
	private JTextField txtNhapE;
	private JTextPane txtXuatMaHoa;
	private JTextPane txtNhapMaHoa;
	private JTextPane txtXuatGiaiMa;
	private JTextPane txtNhapGiaiMa;
	private JRadioButton rdbtnTaoKhoaTuDong;
	private JRadioButton rdbtnTaoKhoaTuyChon;
	private JLabel lblKhoaCongKhai;
	private JLabel lblKhoaBiMat;
	private boolean checkKhoa = false;
	private JTextField txtLinkFileMH;
	private int typeFile = 0;
	private int typeFile2 = 0;
	private JTable table;
	private JTextField txtLinkFileGM;
	private List<RSA> listKhoa = new ArrayList<RSA>();
	private RSAController rsaController = new RSAController();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RSAView frame = new RSAView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public RSAView() throws IOException {
		setBackground(new Color(255, 255, 255));
		luuKhoa();
		docKhoa();
		setTitle("BTL ATBMTT - NHÓM 13: MÃ HÓA, GIẢI MÃ RSA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(130, 50, 1100, 620);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);

		setContentPane(contentPane);

		JPanel panelMaHoa = new JPanel();
		panelMaHoa.setBackground(new Color(220, 220, 220));
		panelMaHoa.setLayout(null);
		panelMaHoa.setBorder(new LineBorder(new Color(0, 0, 128), 2, true));
		panelMaHoa.setBounds(360, 10, 350, 560);
		contentPane.add(panelMaHoa);

		txtXuatMaHoa = new JTextPane();
		txtXuatMaHoa.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, null));
		txtXuatMaHoa.setBackground(new Color(255, 255, 255));
		txtXuatMaHoa.setFont(new Font("Arial", Font.PLAIN, 14));
		txtXuatMaHoa.setBounds(10, 320, 330, 170);
		txtXuatMaHoa.setEditable(false);
		panelMaHoa.add(txtXuatMaHoa);

		JScrollPane sc1 = new JScrollPane(txtXuatMaHoa);
		sc1.setBounds(8, 318, 334, 174);
		panelMaHoa.add(sc1);

		JLabel lblXuatMaHoa = new JLabel("Nội dung bản mã:");
		lblXuatMaHoa.setFont(new Font("Arial", Font.BOLD, 14));
		lblXuatMaHoa.setBounds(10, 285, 168, 25);
		panelMaHoa.add(lblXuatMaHoa);

		JButton btnMaHoa = new JButton("Mã hóa");
		btnMaHoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				maHoaView();
			}
		});
		btnMaHoa.setBounds(10, 260, 168, 25);
		panelMaHoa.add(btnMaHoa);

		txtNhapMaHoa = new JTextPane();
		txtNhapMaHoa.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, null));
		txtNhapMaHoa.setBackground(new Color(255, 255, 255));
		txtNhapMaHoa.setFont(new Font("Arial", Font.PLAIN, 14));
		txtNhapMaHoa.setBounds(10, 40, 320, 150);
		panelMaHoa.add(txtNhapMaHoa);

		JScrollPane sc2 = new JScrollPane(txtNhapMaHoa);
		sc2.setBounds(10, 40, 320, 150);
		panelMaHoa.add(sc2);

		JLabel lblNhapMaHoa = new JLabel("Nội dung bản rõ:");
		lblNhapMaHoa.setFont(new Font("Arial", Font.BOLD, 14));
		lblNhapMaHoa.setBounds(10, 11, 200, 25);
		panelMaHoa.add(lblNhapMaHoa);

		JButton btnChonFileMaHoa = new JButton("Chọn file");
		btnChonFileMaHoa.setBounds(10, 200, 100, 25);
		btnChonFileMaHoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chonFileMaHoa();
			}
		});
		panelMaHoa.add(btnChonFileMaHoa);

		txtLinkFileMH = new JTextField();
		txtLinkFileMH.setBounds(10, 230, 320, 25);
		panelMaHoa.add(txtLinkFileMH);

		JButton btnChuyen = new JButton("CHUYỂN");
		btnChuyen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtXuatMaHoa.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Chuyển bản mã thất bại, bản mã không được rỗng!");
				} else {
					txtNhapGiaiMa.setText(txtXuatMaHoa.getText());
					JOptionPane.showMessageDialog(contentPane, "Chuyển bản mã đi thành công!");
				}
			}
		});
		btnChuyen.setBounds(229, 511, 89, 23);
		panelMaHoa.add(btnChuyen);

		JButton btnNewButton = new JButton("Lưu File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					luuFileMaHoa();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(130, 511, 89, 23);
		panelMaHoa.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Mã hóa mới");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNhapMaHoa.setText("");
				txtXuatMaHoa.setText("");
				txtLinkFileMH.setText("");
				typeFile=0;
			}
		});
		btnNewButton_2.setBounds(18, 511, 103, 23);
		panelMaHoa.add(btnNewButton_2);

		JPanel panelGiaiMa = new JPanel();
		panelGiaiMa.setLayout(null);
		panelGiaiMa.setBackground(new Color(220, 220, 220));
		panelGiaiMa.setBorder(new LineBorder(new Color(0, 0, 128), 2, true));
		panelGiaiMa.setBounds(740, 20, 340, 560);
		contentPane.add(panelGiaiMa);

		txtXuatGiaiMa = new JTextPane();
		txtXuatGiaiMa.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, null));
		txtXuatGiaiMa.setBackground(new Color(255, 255, 255));
		txtXuatGiaiMa.setFont(new Font("Arial", Font.PLAIN, 14));
		txtXuatGiaiMa.setBounds(20, 353, 320, 170);
		panelGiaiMa.add(txtXuatGiaiMa);

		JScrollPane sc3 = new JScrollPane(txtXuatGiaiMa);
		sc3.setBounds(20, 353, 320, 170);
		panelGiaiMa.add(sc3);

		JLabel lblXuatGiaiMa = new JLabel("Nội dung bản rõ:");
		lblXuatGiaiMa.setFont(new Font("Arial", Font.BOLD, 14));
		lblXuatGiaiMa.setBounds(20, 317, 120, 25);
		panelGiaiMa.add(lblXuatGiaiMa);

		JButton btnGiaiMa = new JButton("Giải mã");
		btnGiaiMa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				giaiMaView();
			}
		});
		btnGiaiMa.setBounds(20, 281, 168, 25);
		panelGiaiMa.add(btnGiaiMa);

		txtNhapGiaiMa = new JTextPane();
		txtNhapGiaiMa.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, null));
		txtNhapGiaiMa.setBackground(new Color(255, 255, 255));
		txtNhapGiaiMa.setFont(new Font("Arial", Font.PLAIN, 14));
		txtNhapGiaiMa.setBounds(20, 40, 320, 170);
		panelGiaiMa.add(txtNhapGiaiMa);

		JScrollPane sc4 = new JScrollPane(txtNhapGiaiMa);
		sc4.setBounds(20, 40, 320, 170);
		panelGiaiMa.add(sc4);

		JLabel lblNhapGiaiMa = new JLabel("Nội dung bản mã:");
		lblNhapGiaiMa.setFont(new Font("Arial", Font.BOLD, 14));
		lblNhapGiaiMa.setBounds(10, 11, 200, 25);
		panelGiaiMa.add(lblNhapGiaiMa);

		JButton btnNewButton_1 = new JButton("Lưu File");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					luuFileGiaiMa();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(229, 526, 89, 23);
		panelGiaiMa.add(btnNewButton_1);
		
		txtLinkFileGM = new JTextField();
		txtLinkFileGM.setBounds(20, 251, 320, 25);
		panelGiaiMa.add(txtLinkFileGM);
		
		JButton btnChonFileGiaiMa = new JButton("Chọn file");
		btnChonFileGiaiMa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chonFileGiaiMa();
			}
		});
		btnChonFileGiaiMa.setBounds(20, 221, 100, 25);
		panelGiaiMa.add(btnChonFileGiaiMa);
		
		JButton btnNewButton_2_1 = new JButton("Giải mã mới");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNhapGiaiMa.setText("");
				txtXuatGiaiMa.setText("");
				txtLinkFileGM.setText("");
				typeFile2=0;
			}
		});
		btnNewButton_2_1.setBounds(20, 526, 103, 23);
		panelGiaiMa.add(btnNewButton_2_1);

		JPanel panelTaoKhoa = new JPanel();
		panelTaoKhoa.setBackground(new Color(220, 220, 220));
		panelTaoKhoa.setBorder(new LineBorder(new Color(0, 0, 128), 2, true));
		panelTaoKhoa.setBounds(20, 20, 340, 560);
		panelTaoKhoa.setLayout(null);
		contentPane.add(panelTaoKhoa);

		JLabel lblTaoKhoa = new JLabel("TẠO KHÓA RSA");
		lblTaoKhoa.setFont(new Font("Arial", Font.BOLD, 16));
		lblTaoKhoa.setBounds(10, 11, 200, 25);
		panelTaoKhoa.add(lblTaoKhoa);

		rdbtnTaoKhoaTuDong = new JRadioButton("Tạo khóa tự động");
		rdbtnTaoKhoaTuDong.setSelected(true);
		rdbtnTaoKhoaTuDong.setBounds(20, 43, 140, 23);
		panelTaoKhoa.add(rdbtnTaoKhoaTuDong);

		rdbtnTaoKhoaTuyChon = new JRadioButton("Tạo khóa tùy chọn");
		rdbtnTaoKhoaTuyChon.setBounds(170, 43, 132, 23);
		panelTaoKhoa.add(rdbtnTaoKhoaTuyChon);

		ButtonGroup groupTaoKhoa = new ButtonGroup();
		groupTaoKhoa.add(rdbtnTaoKhoaTuDong);
		groupTaoKhoa.add(rdbtnTaoKhoaTuyChon);

		JLabel lblNhapP = new JLabel("Số nguyên tố bí mật p:");
		lblNhapP.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNhapP.setBounds(10, 73, 155, 25);
		panelTaoKhoa.add(lblNhapP);

		txtNhapP = new JTextField();
		txtNhapP.setBounds(180, 76, 132, 20);
		panelTaoKhoa.add(txtNhapP);
		txtNhapP.setColumns(10);

		JLabel lblNhapQ = new JLabel("Số nguyên tố bí mật q:");
		lblNhapQ.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNhapQ.setBounds(10, 96, 155, 25);
		panelTaoKhoa.add(lblNhapQ);

		txtNhapQ = new JTextField();
		txtNhapQ.setColumns(10);
		txtNhapQ.setBounds(180, 99, 132, 20);
		panelTaoKhoa.add(txtNhapQ);

		JLabel lblNhapB = new JLabel("Chọn khóa mã hóa e:");
		lblNhapB.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNhapB.setBounds(10, 119, 155, 25);
		panelTaoKhoa.add(lblNhapB);

		txtNhapE = new JTextField();
		txtNhapE.setColumns(10);
		txtNhapE.setBounds(180, 122, 132, 20);
		panelTaoKhoa.add(txtNhapE);

		JButton btnTaoKhoa = new JButton("Tạo khóa mới");
		btnTaoKhoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				taoKhoaView();
			}
		});
		btnTaoKhoa.setBounds(82, 154, 140, 23);
		panelTaoKhoa.add(btnTaoKhoa);

		JLabel lblThongTinKhoa = new JLabel("THÔNG TIN KHÓA RSA");
		lblThongTinKhoa.setFont(new Font("Arial", Font.BOLD, 16));
		lblThongTinKhoa.setBounds(10, 204, 200, 25);
		panelTaoKhoa.add(lblThongTinKhoa);

		lblKhoaCongKhai = new JLabel("Khóa công khai (e,n):");
		lblKhoaCongKhai.setFont(new Font("Arial", Font.BOLD, 14));
		lblKhoaCongKhai.setBounds(10, 234, 302, 25);
		panelTaoKhoa.add(lblKhoaCongKhai);

		lblKhoaBiMat = new JLabel("Khóa bí mật (d,p,q):");
		lblKhoaBiMat.setFont(new Font("Arial", Font.BOLD, 14));
		lblKhoaBiMat.setBounds(10, 257, 302, 25);
		panelTaoKhoa.add(lblKhoaBiMat);
		
		JLabel lblChnKha = new JLabel("CHỌN KHÓA ĐÃ LƯU");
		lblChnKha.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblChnKha.setBounds(10, 313, 200, 25);
		panelTaoKhoa.add(lblChnKha);
		
		JButton btnNewButton_3 = new JButton("Lưu khóa");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSA tmp = rsaController.getRSA();
				listKhoa.add(tmp);
				try {
					luuKhoa();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_3.setBounds(121, 285, 89, 23);
		panelTaoKhoa.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Danh sách khóa");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayListKhoa();
			}
		});
		btnNewButton_4.setBounds(10, 338, 132, 23);
		panelTaoKhoa.add(btnNewButton_4);

		JButton btnResetForm = new JButton("RESET FORM");
		btnResetForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetForm();
			}
		});
		btnResetForm.setBounds(105, 533, 133, 23);
		contentPane.add(btnResetForm);
		
		table = new JTable();
		table.setForeground(new Color(0, 0, 0));
		table.setBounds(10, 366, 200, 114);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "e", "n", "d", "p", "q" }));
		panelTaoKhoa.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(new Color(240, 240, 240));
		scrollPane.setBounds(10, 366, 200, 114);
		panelTaoKhoa.add(scrollPane);
		
		JButton btnNewButton_5 = new JButton("Chọn khóa");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model_table = (DefaultTableModel) table.getModel();
				int i_row = table.getSelectedRow();
				if(i_row==-1) {
					JOptionPane.showMessageDialog(contentPane, "Vui lòng chọn khóa trong dánh sách!");
				} else {
					RSA rsa = getSelected();
					rsaController.setRSA(rsa);
					hienThiKhoa();
					JOptionPane.showMessageDialog(contentPane, "Chọn thành công!");
				}
			}
		});
		btnNewButton_5.setBounds(220, 369, 99, 23);
		panelTaoKhoa.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("Xóa");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xoaKhoa();
			}
		});
		btnNewButton_6.setBounds(220, 414, 96, 25);
		panelTaoKhoa.add(btnNewButton_6);
		
		
		getContentPane().setBackground(new Color(240, 240, 240));
		setBounds(100, 100, 1200, 700);
		
		panelMaHoa.setBounds(380, 20, 340, 560);	

		btnMaHoa.setFont(new Font("Arial", Font.BOLD, 14));
		btnGiaiMa.setFont(new Font("Arial", Font.BOLD, 14));
		btnTaoKhoa.setFont(new Font("Arial", Font.BOLD, 14));

		btnMaHoa.setBackground(new Color(0, 128, 0));
		btnMaHoa.setForeground(new Color(255, 255, 255));
		btnGiaiMa.setBackground(new Color(0, 128, 0));
		btnGiaiMa.setForeground(new Color(255, 255, 255));
		btnTaoKhoa.setBackground(new Color(0, 128, 0));
		btnTaoKhoa.setForeground(new Color(255, 255, 255));

	}

	public boolean checkMaHoa() {
		if (this.checkKhoa == false || this.txtNhapMaHoa.getText().equals("")) {
			return false;
		}
		return true;
	}

	public boolean checkGiaiMa() {
		if (this.checkKhoa == false || this.txtNhapGiaiMa.getText().equals("")) {
			return false;
		}
		return true;
	}

	public boolean checkTaoKhoa() {
		if (this.txtNhapP.getText().equals("") || this.txtNhapQ.getText().equals("")
				|| this.txtNhapE.getText().equals("")) {
			return false;
		}
		return true;
	}

	public boolean checkDieuKienKhoa() {
		long p, q, e;
		p = Long.valueOf(txtNhapP.getText());
		q = Long.valueOf(txtNhapQ.getText());
		e = Long.valueOf(txtNhapE.getText());
		
		if (rsaController.checkManualKeyGeneration(p, q, e))
			return true;
		JOptionPane.showMessageDialog(contentPane,
				"Tạo khóa tùy chọn thất bại, hãy chọn khóa thỏa mãn chuẩn hệ mã RSA!");
		return false;
	}

	public void resetForm() {
		this.checkKhoa = false;
		this.txtNhapMaHoa.setText("");
		this.txtXuatMaHoa.setText("");
		this.txtNhapGiaiMa.setText("");
		this.txtXuatGiaiMa.setText("");
		this.txtNhapP.setText("");
		this.txtNhapQ.setText("");
		this.txtNhapE.setText("");
		this.lblKhoaCongKhai.setText("Khóa công khai (b,n):");
		this.lblKhoaBiMat.setText("Khóa bí mật (a,p,q):");
		this.txtLinkFileMH.setText("");
		this.typeFile = 0;
	}

	public void hienThiKhoa() {
		RSA rsa = rsaController.getRSA();
		
		txtNhapP.setText(""+rsa.getP());
		txtNhapQ.setText(""+rsa.getQ());
		txtNhapE.setText(""+rsa.getE());
		this.lblKhoaCongKhai.setText("Khóa công khai (e, n):  (" + rsa.getE() + ", " + rsa.getN() + ")");
		this.lblKhoaBiMat.setText("Khóa bí mật (d, p, q):  (" + rsa.getD() + ", " + rsa.getP() + ", " + rsa.getQ() + ")");
	}

	public void chonFileMaHoa() {
		JFileChooser fChon = new JFileChooser();
		FileNameExtensionFilter fLocTxt = new FileNameExtensionFilter("txt", "txt");
		FileNameExtensionFilter fLocDoc = new FileNameExtensionFilter("docx", "docx");
		fChon.addChoosableFileFilter(fLocTxt);
		fChon.addChoosableFileFilter(fLocDoc);
		fChon.setMultiSelectionEnabled(false);
		int x = fChon.showDialog(this, "Tải file");

		if (x == JFileChooser.APPROVE_OPTION) {
			File f = fChon.getSelectedFile();
			if (f.getName().contains(".txt")) {
				txtLinkFileMH.setText(f.getPath());
				try {
					File nd = new File(txtLinkFileMH.getText());
					Scanner sc = new Scanner(nd);
					String content = "";
					while (sc.hasNextLine()) {
						content += sc.nextLine() + "\r\n";
					}
					txtNhapMaHoa.setText(content);
					typeFile = 1;
					sc.close();
					JOptionPane.showMessageDialog(contentPane, "Tải lên File hoàn tất!");
				} catch (FileNotFoundException ex) {
					JOptionPane.showMessageDialog(contentPane, "Tải lên File thất bại!");
				}
			} else if (f.getAbsolutePath().contains(".docx")) {
				txtLinkFileMH.setText(f.getPath());
				try {
//					FileInputStream fis = new FileInputStream(txtLinkFileMH.getText());
//					XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
//					XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
//					txtNhapMaHoa.setText(wordExtractor.getText());
//					wordExtractor.close();
//					document.close();
					typeFile = 2;
					JOptionPane.showMessageDialog(contentPane, "Tải lên File hoàn tất!");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(contentPane,
						"Tải lên File thất bại, ứng dụng chỉ hỗ trợ xử lý file .txt và .docx!");
			}

		}
	}
	
	public void chonFileGiaiMa() {
		JFileChooser fChon = new JFileChooser();
		FileNameExtensionFilter fLocTxt = new FileNameExtensionFilter("txt", "txt");
		FileNameExtensionFilter fLocDoc = new FileNameExtensionFilter("docx", "docx");
		fChon.addChoosableFileFilter(fLocTxt);
		fChon.addChoosableFileFilter(fLocDoc);
		fChon.setMultiSelectionEnabled(false);
		int x = fChon.showDialog(this, "Tải file");

		if (x == JFileChooser.APPROVE_OPTION) {
			File f = fChon.getSelectedFile();
			if (f.getName().contains(".txt")) {
				txtLinkFileGM.setText(f.getPath());
				try {
					File nd = new File(txtLinkFileGM.getText());
					Scanner sc = new Scanner(nd);
					String content = "";
					while (sc.hasNextLine()) {
						content += sc.nextLine() + "\r\n";
					}
					txtNhapGiaiMa.setText(content);
					typeFile2 = 1;
					sc.close();
					JOptionPane.showMessageDialog(contentPane, "Tải lên File hoàn tất!");
				} catch (FileNotFoundException ex) {
					JOptionPane.showMessageDialog(contentPane, "Tải lên File thất bại!");
				}
			} else if (f.getAbsolutePath().contains(".docx")) {
				txtLinkFileGM.setText(f.getPath());
				try {
//					FileInputStream fis = new FileInputStream(txtLinkFileGM.getText());
//					XWPFDocument document = new XWPFDocument(OPCPackage.open(fis));
//					XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document);
//					txtNhapGiaiMa.setText(wordExtractor.getText());
//					wordExtractor.close();
//					document.close();
					typeFile2 = 2;
					JOptionPane.showMessageDialog(contentPane, "Tải lên File hoàn tất!");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(contentPane,
						"Tải lên File thất bại, ứng dụng chỉ hỗ trợ xử lý file .txt và .docx!");
			}

		}
	}

	// Tạo khóa
	public void taoKhoaView() {
		if (rdbtnTaoKhoaTuDong.isSelected()) {
			rsaController.automaticKeyGeneration();
			this.checkKhoa = true;
			hienThiKhoa();
			JOptionPane.showMessageDialog(contentPane, "Tạo khóa tự động thành công, có thể bắt đầu mã hóa ngay!");
		} else {
			if (checkTaoKhoa()) {
				if (checkDieuKienKhoa()) {
					this.checkKhoa = true;
					hienThiKhoa();
					JOptionPane.showMessageDialog(contentPane,
							"Tạo khóa tùy chọn thành công, có thể bắt đầu mã hóa ngay!");
				}
			} else {
				JOptionPane.showMessageDialog(contentPane,
						"Vui lòng nhập đầy đủ thông tin khóa để tiến hành tạo khóa!");
			}
		}
	}

	// Mã hóa
	public void maHoaView() {
		if (checkMaHoa()) {
			String banRo = txtNhapMaHoa.getText();
			String banMa;
			banMa = rsaController.encryptRSA(banRo);
			txtXuatMaHoa.setText(banMa);
			JOptionPane.showMessageDialog(contentPane, "Mã hóa thành công!");
		} else {
			JOptionPane.showMessageDialog(contentPane, "Mã hóa thất bại, vui lòng kiểm tra lại khóa và bản rõ!");
		}
	}

	// Giải mã
	public void giaiMaView() {
		if (checkGiaiMa()) {
			try {
				String banMa = txtNhapGiaiMa.getText();
				String banRo;
				banRo = rsaController.decryptRSA(banMa);
				txtXuatGiaiMa.setText(banRo);
				JOptionPane.showMessageDialog(contentPane, "Giải mã thành công!");
			} catch(Exception e) {
				JOptionPane.showMessageDialog(contentPane, "Giải mã thất bại, bản mã bị lỗi!");
			}
		} else {
			JOptionPane.showMessageDialog(contentPane, "Giải mã thất bại, vui lòng kiểm tra lại khóa và bản mã!");
		}
	}

	public void luuFileMaHoa() throws IOException {
		if (txtXuatMaHoa.getText().equals("")) {
			JOptionPane.showMessageDialog(contentPane, "Bản mã không được trống!");
		} else {
			if (typeFile == 0 || typeFile == 1) {
				FileOutputStream fos = new FileOutputStream(new File("outMaHoa.txt"));
				FileWriter out = new FileWriter("outMaHoa.txt");
				out.write(txtXuatMaHoa.getText());
				out.close();
				fos.close();
				JOptionPane.showMessageDialog(contentPane, "Lưu file thành công! Kiểm tra File trong thư mục Project!");
			} else {
//				FileOutputStream fos = new FileOutputStream(new File("outMaHoa.docx"));
//				XWPFDocument doc = new XWPFDocument();
//				XWPFParagraph para = doc.createParagraph();
//				XWPFRun run = para.createRun();
//				run.setText(txtXuatMaHoa.getText());
//				doc.write(fos);
//				fos.close();
				JOptionPane.showMessageDialog(contentPane, "Lưu file thành công! Kiểm tra File trong thư mục Project!");
			}
		}
	}

	public void luuFileGiaiMa() throws IOException {
		if (txtXuatGiaiMa.getText().equals("")) {
			JOptionPane.showMessageDialog(contentPane, "Bản rõ không được trống!");
		} else {
			if (typeFile == 0 || typeFile == 1) {
				FileOutputStream fos = new FileOutputStream(new File("outGiaiMa.txt"));
				FileWriter out = new FileWriter("outGiaiMa.txt");
				out.write(txtXuatGiaiMa.getText());
				out.close();
				fos.close();
				JOptionPane.showMessageDialog(contentPane, "Lưu file thành công! Kiểm tra File trong thư mục Project!");
			} else {
//				FileOutputStream fos = new FileOutputStream(new File("outGiaiMa.docx"));
//				XWPFDocument doc = new XWPFDocument();
//				XWPFParagraph para = doc.createParagraph();
//				XWPFRun run = para.createRun();
//				run.setText(txtXuatGiaiMa.getText());
//				doc.write(fos);
//				fos.close();
				JOptionPane.showMessageDialog(contentPane, "Lưu file thành công! Kiểm tra File trong thư mục Project!");
			}
		}
	}
	public void luuKhoa() throws IOException {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("Khoa.bin");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(listKhoa);
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if(fos!=null) {
				fos.close();
			}
			if(oos!=null) {
				oos.close();
			}
		}
	}
	
	public void docKhoa() throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream("Khoa.bin");
			ois = new ObjectInputStream(fis);
			listKhoa.clear();
			listKhoa = (List<RSA>)ois.readObject();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if(fis!=null) {
				fis.close();
			}
			if(ois!=null) {
				ois.close();
			}
		}
	}
	public RSA getSelected() {
		DefaultTableModel model_table = (DefaultTableModel) table.getModel();
		int i_row = table.getSelectedRow();
		int e = Integer.valueOf(model_table.getValueAt(i_row, 0) + "");
		int n = Integer.valueOf(model_table.getValueAt(i_row, 0) + "");
		int d = Integer.valueOf(model_table.getValueAt(i_row, 0) + "");
		int p = Integer.valueOf(model_table.getValueAt(i_row, 0) + "");
		int q = Integer.valueOf(model_table.getValueAt(i_row, 0) + "");
		int phiN = (p-1)*(q-1);
		RSA r = new RSA(p,q,e,n,phiN,d);
		return r;
	}
	
	public void xoaKhoa() {
		DefaultTableModel model_table = (DefaultTableModel) table.getModel();
		int i_row = table.getSelectedRow();
		if(i_row==-1) {
			JOptionPane.showMessageDialog(contentPane, "Vui lòng chọn khóa muốn xóa!");
		} else {
			model_table.removeRow(i_row);
			JOptionPane.showMessageDialog(contentPane, "Xóa khóa thành công!");
		}
	}
	
	public void displayRow(RSA rsa) {
		DefaultTableModel model_table = (DefaultTableModel) table.getModel();
		model_table.addRow(new Object[] { rsa.getE() + "", rsa.getN() + "", rsa.getD() + "", rsa.getP() + "", rsa.getQ()+ "" });
	}
	
	public void displayListKhoa() {
		for(RSA r:listKhoa) {
			displayRow(r);
		}
	}
}