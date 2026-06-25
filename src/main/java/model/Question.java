package model;

public class Question {
    private int 질문ID;
    private int 설문ID;
    private String 질문내용;

    public int get질문ID() {
        return 질문ID;
    }

    public void set질문ID(int 질문ID) {
        this.질문ID = 질문ID;
    }

    public int get설문ID() {
        return 설문ID;
    }

    public void set설문ID(int 설문ID) {
        this.설문ID = 설문ID;
    }

    public String get질문내용() {
        return 질문내용;
    }

    public void set질문내용(String 질문내용) {
        this.질문내용 = 질문내용;
    }
}
