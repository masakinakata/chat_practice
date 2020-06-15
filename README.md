# chat_practice

## server
server用のjavaファイル関連がおいてあります. デフォルトではlocalhostにつなぐようになっているので, 他のipアドレスを使用する際は適宜変更して使用して下さい.

コンパイル・実行の仕方
~~~
javac *.java
java Mainserver
~~~

## client
client用のjavaファイル関連がおいてあります. 使う際にはjavafxが使える環境である必要があります. またfxmlのバージョンも自分のjavaのバージョンと比べて使用して下さい. javafxの仕様上, linux上で日本語入力ができないことがあります. 

コンパイル・実行の仕方(server側が既に起動してある前提です)
~~~
javac *.java
java Mainchat
~~~
