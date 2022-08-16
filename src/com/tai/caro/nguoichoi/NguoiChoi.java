package com.tai.caro.nguoichoi;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.tai.caro.banco.BanCo;
import com.tai.caro.quanco.QuanCo;

public class NguoiChoi {
	private char loaiQuanCo;
	private ArrayList<QuanCo> listQC;
	private String tenNguoiChoi;

	public NguoiChoi(char loaiQC, String ten) {
		listQC = new ArrayList<QuanCo>();
		this.loaiQuanCo = loaiQC;
		this.tenNguoiChoi = ten;
	}

	public boolean danhCo(int x, int y, NguoiChoi nguoiChoi2) {
		// Kiểm tra vị trí của ô mà quân cờ sẽ được đánh vào
		if (x < BanCo.PADDING || y < BanCo.PADDING || x > BanCo.SO_COT * BanCo.SIZE + BanCo.PADDING
				|| y > BanCo.SO_HANG * BanCo.SIZE + BanCo.PADDING) {
			JOptionPane.showMessageDialog(null, "Vị trí đánh không phù hợp");
			return false;
		}

		int cot = (x - BanCo.PADDING) / BanCo.SIZE;
		int hang = (y - BanCo.PADDING) / BanCo.SIZE;

		// Điều chỉnh vị trí sẽ đánh quân cờ
		x = cot * BanCo.SIZE + BanCo.SIZE / 2 + BanCo.PADDING;
		y = hang * BanCo.SIZE + BanCo.SIZE / 2 + BanCo.PADDING;

		// Kiểm tra vị trí khi đánh đã tồn tại quân cờ chưa
		// Tồn tại => Báo lỗi => return false
		// Chưa tồn tại => Thêm quân cờ vào listQC => return true
		QuanCo quanCo = new QuanCo(x, y, loaiQuanCo);

		// Người chơi 1
		int viTri = listQC.indexOf(quanCo);
		if (viTri >= 0) {
			JOptionPane.showMessageDialog(null, "Vị trí này đã tồn tại quân cờ!");
			return false;
		}

		// Người chơi 2
		viTri = nguoiChoi2.listQC.indexOf(quanCo);
		if (viTri >= 0) {
			JOptionPane.showMessageDialog(null, "Vị trí này đã tồn tại quân cờ!");
			return false;
		}

		listQC.add(quanCo);
		return true;
	}

	public void xoaCo() {
		listQC.clear();
	}

	public ArrayList<QuanCo> getListQC() {
		return listQC;
	}

	public String getTen() {
		return tenNguoiChoi;
	}

	public char getLoaiQC() {
		return loaiQuanCo;
	}
}
