# --- Build Stage ---
FROM node:20-alpine AS build

# 作業ディレクトリ
WORKDIR /app

# package.jsonとlockをコピー(ここが変わらなければnpm ci までキャッシュされるらしい)
COPY package*.json ./

# 依存関係インストール
RUN npm ci

# ソースコードをコピー
COPY . .

# ビルド
RUN npm run build

# ビルド引数で環境指定
ARG API_SERVER_URL=api-server-pi-web-api-server:9000

# ビルド時に置換して nginx.conf を作成
RUN sed "s|__API_SERVER_URL__|${API_SERVER_URL}|g" nginx.template.conf > nginx.conf

# --- Production Stage ---
FROM nginx:alpine

# ビルド成果物をnginxにコピー
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Nginxのデフォルト設定を置き換え
COPY --from=build /app/nginx.conf /etc/nginx/conf.d/default.conf

# Expose Port 80
EXPOSE 80

# 起動
CMD ["nginx", "-g", "daemon off;"]
