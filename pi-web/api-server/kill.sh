#!/bin/bash

# RUNNING_PID のパス（必要に応じて変更）
cd /home/elpi/app/api-server/api-server-*/
PID_FILE="RUNNING_PID"

# ファイルが存在するか確認
if [ ! -f "$PID_FILE" ]; then
  echo "PID ファイル ($PID_FILE) が見つかりません"
  exit 1
fi

# PID を読み込む
PID=$(cat "$PID_FILE")

# プロセスが存在するか確認して kill
if ps -p "$PID" > /dev/null 2>&1; then
  echo "Play アプリケーション (PID: $PID) を停止します..."
  kill "$PID"

  # 成功したら RUNNING_PID を削除（任意）
  rm -f "$PID_FILE"
  echo "停止しました"
else
  echo "PID $PID は存在しないプロセスです"
  rm -f "$PID_FILE"  # 不整合がある場合は削除してもよい
  exit 1
fi
