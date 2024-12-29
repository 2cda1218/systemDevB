# import modules
# discord
import discord
from discord import app_commands as dac

# AI
from transformers import pipeline as pipe
from transformers import T5Tokenizer as t5t

# omikuji
import random

# TOKEN
import os
from dotenv import load_dotenv

load_dotenv()
TOKEN = os.getenv("TOKEN")

intents = discord.Intents.default()
bot = discord.Client(intents = intents)
tree = dac.CommandTree(bot)

generator = pipe("text-generation",model="rinna/japanese-gpt2-medium",tokenizer=t5t.from_pretrained("rinna/japanese-gpt2-medium"))

def get_mikuji():
    fortunes = ["大吉","吉","中吉","小吉","末吉","凶","大凶"]
    return fortunes[random.randint(0,len(fortunes) - 1)]

def generate_omikuji():
    fortune = get_mikuji()
    sentence = generator(f"今日の運勢は{fortune}です!ラッキーカラーは",
                        max_length = 100,          #生成する文の最大トークン数
                        num_return_sequences = 1,  #生成する文章の数
                        truncation = True,         #最大値を越える際にテキストを切り落としエラーを回避する
                        no_repeat_ngram_size = 2,  #変なスタックをして連続した記号にならないようにする
                        temperature = 0.5,         #メッセージの固定度合いの調整(小さいほど同じようなメッセージに近づく)
                        top_k = 75,                #ワードの選択肢
                        eos_token_id = 50256       #生成終了トークンを明示的に設定
                        )
    return sentence[0]["generated_text"]

@bot.event
async def on_ready():
    print('botが起動しました')
    activity = 'AIがおみくじ生成'
    await bot.change_presence(activity = discord.Game(activity))

    await tree.sync() #コマンドツリー有効化

#--------------
# bot command
#--------------

@tree.command(name = 'exit',description = 'botを終了します')
async def stop(ctx: discord.Interaction):
    await ctx.response.send_message('botを停止します')
    exit()

@tree.command(name = 'omikuji',description = 'AIによっておみくじを生成します')
async def omikuji(ctx: discord.Interaction):
    await ctx.response.defer()
    user = ctx.user.display_name
    result = generate_omikuji()

    color_list = [[128,0,128],[25,25,112],[230,230,250],[255,215,0],[192,192,192],[80,200,120],[75,0,130]]
    select = random.randint(0,6)
    emb = discord.Embed(title = 'AIのよく当たるかもしれない占い',
                        description = f'今日の{user}の運勢を占います',
                        color = discord.Colour.from_rgb(color_list[select][0],color_list[select][1],color_list[select][2])
                        )
    emb.add_field(name = f'{user}の占い結果', value = result)
    await ctx.followup.send(embed = emb)

bot.run(TOKEN)