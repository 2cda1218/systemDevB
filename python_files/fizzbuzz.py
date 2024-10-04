def judge(i):
    if i % 15 == 0:
        print(f"{i}:FizzBuzz\n")
    elif i % 5 == 0:
        print(f"{i}:Buzz\n")
    elif i % 3 == 0:
        print(f"{i}:Fizz\n")
    else:
        print(f"{i}\n")

def main():
    print("FizzBuzzを行う.\n1から数え始め終了の値は任意とする.\n")
    end_number = input("終了の値を入力:")
    end_number = int(end_number)
    for i in range(end_number):
        judge(i + 1)
    #2CDA1218

if __name__ == "__main__":
    main()