package model;

public class Product {
    private int 상품ID;
    private String 상품명;
    private String 설명;
    private int 가격;

    public int get상품ID() { return 상품ID; }
    public void set상품ID(int 상품ID) { this.상품ID = 상품ID; }

    public String get상품명() { return 상품명; }
    public void set상품명(String 상품명) { this.상품명 = 상품명; }

    public String get설명() { return 설명; }
    public void set설명(String 설명) { this.설명 = 설명; }

    public int get가격() { return 가격; }
    public void set가격(int 가격) { this.가격 = 가격; }
}
