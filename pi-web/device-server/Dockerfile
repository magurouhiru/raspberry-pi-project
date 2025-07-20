FROM python:3.13.5-slim-bullseye

# 作業ディレクトリ
WORKDIR /app

ARG MOCK_MODE=false

# 環境変数
ENV PYTHONDONTWRITEBYTECODE=1 \
    PYTHONUNBUFFERED=1 \
    MOCK_MODE=${MOCK_MODE}

# 依存関係コピー＆インストール
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# アプリケーションコードをコピー
COPY . .

# ポート公開
EXPOSE 8080

# サーバー起動 (host=0.0.0.0 にしないと外から見えない)
CMD ["python", "main.py"]
