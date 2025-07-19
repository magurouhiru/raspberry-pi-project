# --- Stage 1: Build Play app ---
FROM sbtscala/scala-sbt:eclipse-temurin-17.0.15_6_1.11.3_2.13.16 AS builder

WORKDIR /app

# sbt依存解決のキャッシュのため最初にこれだけコピー
COPY build.sbt .
COPY project/ ./project

# sbt依存ダウンロード（これで後のソース変更はここに影響しない）
RUN sbt update

# ソースコード全コピー
COPY . .

# ローカルにビルド成果物があれば削除
RUN rm -rf target/universal

# アプリをビルドしてディストリビューションtgz作成
RUN sbt Universal/packageZipTarball

# 解凍
RUN tar -xzf target/universal/api-server-*.tgz

# --- Stage 2: Run on eclipse temurin ---
FROM eclipse-temurin:17.0.15_6-jre

WORKDIR /opt

# ディストリビューションをコピー
COPY --from=builder /app/api-server-* /opt/api-server

# ポート公開
EXPOSE 9000

# 環境変数を設定
ENV DB_URL=jdbc:mysql://db:3306/playdb
ENV DB_USER=playuser
ENV DB_PASSWORD=playpass

# アプリ起動
CMD ["/opt/api-server/bin/api-server"]
