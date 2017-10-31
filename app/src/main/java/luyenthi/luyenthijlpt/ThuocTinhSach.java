package luyenthi.luyenthijlpt;



public class ThuocTinhSach {
    // khai báo thuộc tính đối tượng sách
    private int id;
    private String ten,hinhanh,link;

    public ThuocTinhSach(int id, String ten, String hinhanh, String link) {
        this.id = id;
        this.ten = ten;
        this.hinhanh = hinhanh;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
