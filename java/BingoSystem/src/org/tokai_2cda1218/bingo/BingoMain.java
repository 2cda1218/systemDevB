package org.tokai_2cda1218.bingo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.random.RandomGenerator;
import org.tokai_2cda1218.bingo.WindowFrame;

public class BingoMain {
    private Set<Integer> usedNumbers;
    private RandomGenerator random;

    public static void main(String[] args){
        WindowFrame window = new WindowFrame();
        window.CreateWindow();
    }

    public BingoMain(){
        usedNumbers = new HashSet<>();
        random = new Random();
    }

    public int generateNum(){
        if(usedNumbers.size() >= 75){
            throw new IllegalStateException("FINISH");
        }
        int num;
        do{
            num = random.nextInt(75) + 1;
        }
        while(usedNumbers.contains(num));
        usedNumbers.add(num);
        return num;
    }
    public Set<Integer> getUsedNum(){
        return usedNumbers;
    }
}
