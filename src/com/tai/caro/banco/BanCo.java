package com.tai.caro.banco;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.tai.caro.nguoichoi.NguoiChoi;
import com.tai.caro.quanco.QuanCo;

public class BanCo extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int SO_HANG = 10;
	public static final int SO_COT = 10;
	public static final int SIZE = 70;
	public static final String RESET = "0:0";
	public static final int PADDING = 29;

	private String tySo;
	private Color mauSac;
	private char loaiQC = 'X';
	private NguoiChoi nguoiChoi1, nguoiChoi2;

	public BanCo(Color mauSac, String ten1, String ten2) {
		this.mauSac = mauSac;
		nguoiChoi1 = new NguoiChoi(QuanCo.O, ten1);
		nguoiChoi2 = new NguoiChoi(QuanCo.X, ten2);
		tySo = RESET;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				char type = danhCo(e.getX(), e.getY(), loaiQC);
				loaiQC = type;
				kiemTraThangCuoc();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		veMenu(g);
		veBanCo((Graphics2D) g);
	}

	private void veMenu(Graphics g) {
		Font f = new Font("Tahoma", Font.BOLD, 40);
		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString(nguoiChoi1.getTen(), 750, 200);

		Font f1 = new Font("Tahoma", Font.PLAIN, 30);
		g.setFont(f1);
		g.setColor(Color.WHITE);
		g.drawString("VS", 938, 200);

		Font f2 = new Font("Tahoma", Font.BOLD, 40);
		g.setFont(f2);
		g.setColor(Color.YELLOW);
		g.drawString(nguoiChoi2.getTen(), 1000, 200);

		Font f3 = new Font("ITALIC", Font.CENTER_BASELINE, 100);
		g.setFont(f3);
		g.setColor(Color.WHITE);
		g.drawString(tySo, 880, 350);

		Font f4 = new Font("Plain", Font.ITALIC, 70);
		g.setFont(f4);
		g.setColor(Color.GREEN);
		g.drawString("GAME CARO", 750, 100);
	}

	public void veBanCo(Graphics2D g) {
		setBackground(mauSac);
		// Vẽ khung bàn cờ
		for (int i = 0; i <= SO_HANG; i++) {
			g.setColor(Color.cyan);
			g.drawLine(PADDING, i * SIZE + PADDING, SO_COT * SIZE + PADDING, i * SIZE + PADDING);
			g.setColor(Color.white);
			g.drawLine(i * SIZE + PADDING, PADDING, i * SIZE + PADDING, SO_COT * SIZE + PADDING);
		}

		// Vẽ quân cờ
		Font f = new Font("Tahoma", Font.BOLD, 25);
		g.setFont(f);
		int h = getFontMetrics(f).getHeight();
		int w = getFontMetrics(f).stringWidth("X");

		for (int i = 0; i <= SO_HANG; i++) {
			for (int j = 0; j <= SO_COT; j++) {
				int x = j * SIZE + SIZE / 2;
				int y = i * SIZE + SIZE / 2;

				QuanCo qc = new QuanCo(x + PADDING, y + PADDING, ' ');
				if (nguoiChoi1.getListQC().indexOf(qc) >= 0) {
					g.setColor(Color.RED);
					g.drawString("X", x + PADDING - w / 2, y + PADDING + h * 1 / 3);
				} else if (nguoiChoi2.getListQC().indexOf(qc) >= 0) {
					g.setColor(Color.YELLOW);
					g.drawString("O", x + PADDING - w / 2, y + PADDING + h * 1 / 3);
				}
			}
		}
	}

	public char danhCo(int x, int y, char loaiQC) {
		if (loaiQC == nguoiChoi1.getLoaiQC()) {
			if (nguoiChoi1.danhCo(x, y, nguoiChoi2)) {
				repaint();
				return nguoiChoi2.getLoaiQC();
			}
		} else {
			if (nguoiChoi2.danhCo(x, y, nguoiChoi1)) {
				repaint();
				return nguoiChoi1.getLoaiQC();
			}
		}
		return loaiQC;
	}

	public void xoaBanCo() {
		nguoiChoi1.xoaCo();
		nguoiChoi2.xoaCo();
		tySo = RESET;
	}

	public void choiLai() {
		xoaBanCo();
		tySo = RESET;
	}

	public void kiemTraThangCuoc() {
		if (nguoiChoi1.getListQC().size() + nguoiChoi2.getListQC().size() < 9) {
			return;
		}

		if (kiemTraThangNgang(nguoiChoi1) || kiemTraThangCheo1(nguoiChoi1) || kiemTraThangDoc(nguoiChoi1)
				|| kiemTraThangCheo2(nguoiChoi1)) {
			String tmp[] = tySo.split(":");
			tySo = Integer.parseInt(tmp[0] + 1) + ":" + tmp[1];
			JOptionPane.showMessageDialog(null, "Người chơi " + nguoiChoi1.getTen() + " thắng: " + tySo);

			xoaBanCo();
		} else if (kiemTraThangNgang(nguoiChoi2) || kiemTraThangCheo1(nguoiChoi2) || kiemTraThangDoc(nguoiChoi2)
				|| kiemTraThangCheo2(nguoiChoi1)) {
			String tmp[] = tySo.split(":");
			tySo = tmp[0] + ":" + Integer.parseInt(tmp[1] + 1);
			JOptionPane.showMessageDialog(null, "Người chơi " + nguoiChoi2.getTen() + " thắng: " + tySo);

			xoaBanCo();
		}
	}

	private boolean kiemTraThangDoc(NguoiChoi nguoiChoi) {
		nguoiChoi.getListQC().sort(sapXepDoc);
		for (int i = 0; i < nguoiChoi.getListQC().size(); i++) {
			int dem = 1;
			QuanCo qcTruoc = nguoiChoi.getListQC().get(i);
			for (int j = 1; j < nguoiChoi.getListQC().size(); j++) {
				QuanCo qcSau = nguoiChoi.getListQC().get(j);
				if (qcSau.getY() == qcTruoc.getY() + SIZE && qcSau.getX() == qcTruoc.getX()) {
					dem++;
					qcTruoc = qcSau;
				}
				if (dem >= 5) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean kiemTraThangNgang(NguoiChoi nguoiChoi) {
		nguoiChoi.getListQC().sort(sapXepNgang);
		for (int i = 0; i < nguoiChoi.getListQC().size(); i++) {
			int dem = 1;
			QuanCo qcTruoc = nguoiChoi.getListQC().get(i);
			for (int j = 1; j < nguoiChoi.getListQC().size(); j++) {
				QuanCo qcSau = nguoiChoi.getListQC().get(j);
				if (qcSau.getX() == qcTruoc.getX() + SIZE && qcSau.getY() == qcTruoc.getY()) {
					dem++;
					qcTruoc = qcSau;
				}
				if (dem >= 5) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean kiemTraThangCheo1(NguoiChoi nguoiChoi) {
		nguoiChoi.getListQC().sort(sapXepNgang);
		for (int i = 0; i < nguoiChoi.getListQC().size(); i++) {
			int dem = 1;
			QuanCo qcTruoc = nguoiChoi.getListQC().get(i);
			for (int j = 1; j < nguoiChoi.getListQC().size(); j++) {
				QuanCo qcSau = nguoiChoi.getListQC().get(j);
				if (qcSau.getY() == qcTruoc.getY() + SIZE || qcSau.getX() == qcTruoc.getX() + SIZE) {
					dem++;
					qcTruoc = qcSau;
				}
				if (dem >= 5) {
					return true;
				}
			}
		}
		return false;

	}

	private boolean kiemTraThangCheo2(NguoiChoi nguoiChoi) {
		nguoiChoi.getListQC().sort(sapXepNgangGiam);
		for (int i = 0; i < nguoiChoi.getListQC().size(); i++) {
			int dem = 1;
			QuanCo qcTruoc = nguoiChoi.getListQC().get(i);
			for (int j = 1; j < nguoiChoi.getListQC().size(); j++) {
				QuanCo qcSau = nguoiChoi.getListQC().get(j);
				if (qcSau.getY() == qcTruoc.getY() + SIZE && qcSau.getX() == qcTruoc.getX() - SIZE) {
					dem++;
					qcTruoc = qcSau;
				}
				if (dem >= 5) {
					return true;
				}
			}
		}
		return false;

	}

	private Comparator<QuanCo> sapXepNgang = new Comparator<QuanCo>() {

		@Override
		public int compare(QuanCo qc1, QuanCo qc2) {
			return qc1.getX() - qc2.getX();
		}
	};

	private Comparator<QuanCo> sapXepNgangGiam = new Comparator<QuanCo>() {

		@Override
		public int compare(QuanCo qc1, QuanCo qc2) {
			return -(qc1.getX() - qc2.getX());
		}
	};

	private Comparator<QuanCo> sapXepDoc = new Comparator<QuanCo>() {

		@Override
		public int compare(QuanCo qc1, QuanCo qc2) {
			return qc1.getY() - qc2.getY();
		}
	};
}
