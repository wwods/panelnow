package model;

public class Member {
    private int 회원ID;
    private String 이메일;
    private String 비밀번호;
    private String 닉네임;
    private boolean 관리자여부;
    private int 포인트;

    // Getters and Setters
    public int get회원ID() { return 회원ID; }
    public void set회원ID(int 회원ID) { this.회원ID = 회원ID; }

    public String get이메일() { return 이메일; }
    public void set이메일(String 이메일) { this.이메일 = 이메일; }

    public String get비밀번호() { return 비밀번호; }
    public void set비밀번호(String 비밀번호) { this.비밀번호 = 비밀번호; }

    public String get닉네임() { return 닉네임; }
    public void set닉네임(String 닉네임) { this.닉네임 = 닉네임; }

    public boolean is관리자여부() { return 관리자여부; }
    public void set관리자여부(boolean 관리자여부) { this.관리자여부 = 관리자여부; }

    public int get포인트() { return 포인트; }
    public void set포인트(int 포인트) { this.포인트 = 포인트; }
}
