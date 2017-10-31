package luyenthi.luyenthijlpt;


public class ThuocTinh {
    // khao báo các thuộc tính cần lấy ra
    private int maCauHoi;
    private String capDo, KyNang, cauHoi, daA, daB, daC, daD, daDung, hinhAnh, noiDung, script;
    private String dethi,da;

    // khởi tạo contructer
    public ThuocTinh(int maCauHoi, String cauHoi, String daA, String daB, String daC, String daD, String daDung, String noiDung, String script, String made, String capDo, String kyNang, String hinhAnh) {
        this.maCauHoi = maCauHoi;
        this.capDo = capDo;
        KyNang = kyNang;
        this.cauHoi = cauHoi;
        this.daA = daA;
        this.daB = daB;
        this.daC = daC;
        this.daD = daD;
        this.daDung = daDung;
        this.hinhAnh = hinhAnh;
        this.noiDung = noiDung;
        this.script = script;

        this.dethi = made;
    }
    // khởi tạo contructer
    public ThuocTinh(int maCauHoi, String cauHoi, String daA, String daB, String daC, String daD, String daDung, String noiDung, String script, String made, String capDo, String kyNang, String hinhAnh,String da) {
        this.maCauHoi = maCauHoi;
        this.capDo = capDo;
        KyNang = kyNang;
        this.cauHoi = cauHoi;
        this.daA = daA;
        this.daB = daB;
        this.daC = daC;
        this.daD = daD;
        this.daDung = daDung;
        this.hinhAnh = hinhAnh;
        this.noiDung = noiDung;
        this.script = script;
        this.da=da;
        this.dethi = made;
    }
    // thiết lập get và set cho các thuộc tính
    public int getMaCauHoi() {
        return maCauHoi;
    }

    public String getDethi() {
        return dethi;
    }

    public void setDethi(String dethi) {
        this.dethi = dethi;
    }

    public String getDa() {
        return da;
    }

    public void setDa(String da) {
        this.da = da;
    }

    public void setMaCauHoi(int maCauHoi) {
        this.maCauHoi = maCauHoi;
    }

    public String getCapDo() {
        return capDo;
    }

    public void setCapDo(String capDo) {
        this.capDo = capDo;
    }

    public String getKyNang() {
        return KyNang;
    }

    public void setKyNang(String kyNang) {
        KyNang = kyNang;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(String cauHoi) {
        this.cauHoi = cauHoi;
    }

    public String getDaA() {
        return daA;
    }

    public void setDaA(String daA) {
        this.daA = daA;
    }

    public String getDaB() {
        return daB;
    }

    public void setDaB(String daB) {
        this.daB = daB;
    }

    public String getDaC() {
        return daC;
    }

    public void setDaC(String daC) {
        this.daC = daC;
    }

    public String getDaD() {
        return daD;
    }

    public void setDaD(String daD) {
        this.daD = daD;
    }

    public String getDaDung() {
        return daDung;
    }

    public void setDaDung(String daDung) {
        this.daDung = daDung;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }


    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getMade() {
        return dethi;
    }

    public void setMade(String made) {
        this.dethi = made;
    }
}
