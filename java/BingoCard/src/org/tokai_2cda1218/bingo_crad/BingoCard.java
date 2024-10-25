package org.tokai_2cda1218.bingo_crad;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BingoCard extends JFrame{
    //ボタン配列
    private JButton[][] buttons = new JButton[5][5];
    //使用済みの数
    private Set<Integer> usedNum = new HashSet<>();
    //カードのハッシュ値生成用String
    public String CardNumString = "";
    //ナンバー登録実行回数カウント
    private int NumGenCNT = 0;
    //ビンゴした並びの保存
    private int[][] bingos = new int[5][2];

    public BingoCard(){
        //ウィンドウタイトル
        setTitle("BINGO CARD");
        //ウィンドウレイアウト
        setLayout(new GridLayout(5,5));
        //カード生成
        createCard();
        //カードの固有String確認用デバッグメッセージ
        System.out.println(CardNumString);
        String hash = CardHash.hashString(CardNumString);
        System.out.println(hash);
        //ウィンドウデフォルトサイズ
        setSize(600,600);
        //終了時プログラム終了
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ウィンドウデフォルト位置
        setLocation(200,200);
        //ウィンドウ表示
        setVisible(true);
    }

    private void createCard(){
        //各列最小値
        int[] ranges = {
            1,  //B
            16, //I
            31, //N
            46, //G
            61  //O
        };
        String[] bingo_message = {"B","I","N","G","O"};

        for(int i = 0;i < 5;i++){
            for(int j = 0;j < 5;j++){
                //中央生成
                if(i == 2 && j == 2){
                    buttons[i][j] = new JButton("free");
                }
                //中央以外生成
                else{
                    //マスの番号取得
                    int num = getNum(ranges[j]);
                    //ボタンに番号割り当て
                    buttons[i][j] = new JButton(String.valueOf(num));
                }
                //ボタンを押されたときの処理追加(λ式)
                buttons[i][j].addActionListener(e -> {
                    //押されたボタンの取得
                    JButton clickButton = (JButton) e.getSource();
                    //押したボタンの色を変える
                    clickButton.setBackground(Color.YELLOW);
                    //そろったかどうかの判定を行う
                    if(checkCard()){
                        //暫定の処理としてコンソールにビンゴを表示している。
                        System.out.println("bingo!");
                        for(int k = 0;k < 5;k++){
                            buttons[bingos[k][0]][bingos[k][1]].setText(bingo_message[k]);
                        }
                    }
                });
                //ボタンをウィンドウに追加
                add(buttons[i][j]);
            }
        }
    }

    private int getNum(int min){
        //乱数の生成
        Random random = new Random();
        int num;
        do{
            //最小値から+15の範囲内の数字の生成
            num = random.nextInt(15) + min;
        }
        //既存の数字である場合は再生成
        while(usedNum.contains(num));
        //既存の数字として追加
        usedNum.add(num);
        //生成回数インクリメント
        NumGenCNT++;
        //初回の数字用
        if(CardNumString.equals("")){
            CardNumString = num + "-";
        }
        //最後の数字用
        else if(NumGenCNT == 24){
            CardNumString = CardNumString + num;
        }
        else{
            CardNumString = CardNumString + num + "-";
        }
        return num;
    }

    private boolean checkCard(){
        //横
        for(int i = 0;i < 5;i++){
            if(rowscheck(i)){
                return true;
            }
        }

        //縦
        for(int j = 0;j < 5;j++){
            if(colchecl(j)){
                return true;
            }
        }

        //斜め
        return diagonalchcek();
    }

    private boolean rowscheck(int row){
        for(int j = 0;j < 5;j++){
            if(!buttons[row][j].getBackground().equals(Color.YELLOW)){
                return false;
            }
            bingos[j][0] = row;
            bingos[j][1] = j;
        }
        return true;
    }

    private boolean colchecl(int col){
        for(int i = 0;i < 5;i++){
            if(!buttons[i][col].getBackground().equals(Color.YELLOW)){
                return false;
            }
            bingos[i][0] = i;
            bingos[i][1] = col;
        }
        return true;
    }

    private boolean diagonalchcek(){
        boolean diagonal1 = true, diagonal2 = true;
        for(int i = 0;i < 5;i++){
            if(!buttons[i][i].getBackground().equals(Color.YELLOW)){
                diagonal1 = false;
            }
            if(!buttons[i][4 - i].getBackground().equals(Color.YELLOW)){
                diagonal2 = false;
            }
        }
        if(diagonal1){
            for(int i = 0;i < 5;i++){
                bingos[i][0] = i;
                bingos[i][1] = i;
            }
        }
        else if(diagonal2){
            for(int i = 0;i < 5;i++){
                bingos[i][0] = 4 - i;
                bingos[i][1] = i;
            }
        }
        return diagonal1 || diagonal2;
    }

    public static void main(String[] args) {
        new BingoCard();
    }
}
