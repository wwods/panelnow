package model;

public class Choice {
    private int 보기ID;
    private int 질문ID;
    private String 내용;

    public int get보기ID() {
        return 보기ID;
    }

    public void set보기ID(int 보기ID) {
        this.보기ID = 보기ID;
    }

    public int get질문ID() {
        return 질문ID;
    }

    public void set질문ID(int 질문ID) {
        this.질문ID = 질문ID;
    }

    public String get내용() {
        return 내용;
    }

    public void set내용(String 내용) {
        this.내용 = 내용;
    }
}
