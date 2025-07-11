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

# --- Production Stage ---
FROM nginx:alpine

# Nginxのデフォルト設定を置き換え
COPY nginx.conf /etc/nginx/conf.d/default.conf

# ビルド成果物をnginxにコピー
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Expose Port 80
EXPOSE 80

# 起動
CMD ["nginx", "-g", "daemon off;"]
