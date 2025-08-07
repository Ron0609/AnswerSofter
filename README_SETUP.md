# AnswerSofter セットアップガイド 🔧

このガイドでは、AnswerSofter（問題集管理システム）の詳細なセットアップ手順を説明します。

## 📋 事前準備

### システム要件
- **Java Development Kit (JDK)**: 8以上推奨
- **OS**: Windows 10/11, macOS 10.12+, Ubuntu 18.04+
- **メモリ**: 最小256MB RAM
- **ディスク容量**: 50MB以上の空き容量

### 必要なツール
- **Java開発環境**: JDK + テキストエディタ または IDE
- **推奨IDE**: IntelliJ IDEA, Eclipse, VS Code
- **文字エンコーディング**: UTF-8対応エディタ

## ☕ Java環境のセットアップ

### Windows での Java インストール
```bash
# Java のバージョン確認
java -version
javac -version

# インストールされていない場合
# Oracle JDK または OpenJDK をダウンロードしてインストール
# https://www.oracle.com/java/technologies/downloads/
# または
# https://adoptium.net/
```

## 🚀 プロジェクトのセットアップ

### 1. ファイルの準備
プロジェクトフォルダを作成し、以下のファイルを配置：
```
AnswerSofter/
├── MainFrame.java
├── Question.java
├── TableDemo.java
├── AddQuestionDialog.java
├── UpdateQuestionDialog.java
├── MyTableCellRenderer.java
├── AnswerSofter.iml（IntelliJ用）
├── 問題集例.txt（サンプル問題集）
└── 答题例.txt（回答例）
```

### 2. コマンドラインでのコンパイル・実行
```bash
# プロジェクトディレクトリに移動
cd AnswerSofter

# 全Javaファイルのコンパイル
javac -encoding UTF-8 *.java

# メインアプリケーションの実行
java MainFrame

# 問題管理画面のテスト実行（オプション）
java TableDemo
```

### 3. IDEでのセットアップ

#### IntelliJ IDEA の場合
1. **プロジェクトを開く**
   - 「Open or Import」→ プロジェクトフォルダを選択
   - `.iml` ファイルが自動認識されます

2. **プロジェクト設定**
   - File → Project Structure
   - Project SDK: JDK 8+ を選択
   - Language Level: SDK default

3. **文字エンコーディング設定**
   - File → Settings → Editor → File Encodings
   - Global Encoding: UTF-8
   - Project Encoding: UTF-8

4. **実行設定**
   - `MainFrame.java` を右クリック → Run 'MainFrame.main()'


## 📚 問題集ファイルの作成

### 基本フォーマット
```
問題番号 @SPLIT 問題文 @SPLIT 選択肢数 @SPLIT 正解
選択肢記号 @SPLIT 選択肢内容
```

### 単選択問題の作成例
```
1 @SPLIT Javaでクラスを定義するキーワードは？ @SPLIT 4 @SPLIT B
A @SPLIT interface
B @SPLIT class
C @SPLIT package
D @SPLIT import
```

### 複数選択問題の作成例
```
2 @SPLIT Javaの基本データ型はどれですか？ @SPLIT 4 @SPLIT AC
A @SPLIT int
B @SPLIT String
C @SPLIT boolean
D @SPLIT Object
```

### 問題集作成のコツ
1. **@SPLIT の前後にスペース**を入れる
2. **正解が1文字**：単選択問題
3. **正解が複数文字**：複数選択問題（例：AB, ACD）
4. **日本語・英語混合**OK
5. **改行に注意**：各行は独立した要素

## 🎯 動作テスト

### 1. 基本動作確認
```bash
# コンパイルエラーがないことを確認
javac *.java

# 警告メッセージの確認
java MainFrame
# → GUI ウィンドウが表示されることを確認
```

### 2. 機能テスト手順

#### 問題集読み込みテスト
1. アプリケーション起動
2. 「問題集選択」ボタンをクリック
3. `問題集例.txt` を選択
4. 問題が表示されることを確認

#### 解答機能テスト
1. 単選択問題で選択肢をクリック
2. 「次へ」ボタンで次の問題に移動
3. 複数選択問題で複数選択
4. 「提出」ボタンで採点結果を確認

#### 問題管理機能テスト
1. 「問題集管理」ボタンをクリック
2. 問題一覧が表示されることを確認
3. 「問題追加」で新問題を追加
4. 「修正」「削除」機能を確認
