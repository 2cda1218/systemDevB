#include <stdio.h>

void main(){
    int coin,price;
    int coins[4] = {500,100,50,10},result[4];
    printf("投入金額を入力してください:");
    scanf("%d",&coin);
    printf("購入額を入力してください:");
    scanf("%d",&price);
    if(coin < price){
        printf("金額が不足しています");
        return;
    }
    else{
        coin = coin - price;
        printf("おつりは\n");
        for(int i = 0;i < 4;i++){
            result[i] = coin / coins[i];
            coin = coin % coins[i];
            printf("%d円玉:%d枚\n",coins[i],result[i]);
        }
        return;
    } 
}