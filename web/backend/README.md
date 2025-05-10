# server
axum で作成作成されたサーバー  
静的ファイルサーバーとAPIサーバーを兼任  

## 静的ファイルサーバー
Angular で作成されたアプリのファイルサーバー  
[詳細はこちら](./web/frontend/README.md)

## APIサーバー
REST API で構築。  
DB にはSQLite を使用。  

# memo
バリデーションにaxum-valid を使用したが、クロスコンパイル時にエラーが出た。なので、バリデーションは自作した。  
