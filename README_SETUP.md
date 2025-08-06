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

### macOS での Java インストール
```bash
# Homebrew を使用してインストール
brew install openjdk@11

# パスの設定
echo 'export PATH="/opt/homebrew/opt/openjdk@11/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# バージョン確認
java -version
```

### Ubuntu/Linux での Java インストール
```bash
# OpenJDK のインストール
sudo apt update
sudo apt install default-jdk

# バージョン確認
java -version
javac -version
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

#### Eclipse の場合
1. **プロジェクトのインポート**
   - File → Import → General → Existing Projects into Workspace
   - プロジェクトフォルダを選択

2. **文字エンコーディング設定**
   - Project Properties → Resource → Text file encoding
   - UTF-8 を選択

3. **ビルド・実行**
   - Project → Build Project
   - `MainFrame.java` を右クリック → Run As → Java Application

#### VS Code の場合
```bash
# Java Extension Pack のインストール
# VS Code で拡張機能「Java Extension Pack」をインストール

# プロジェクトフォルダを開く
code AnswerSofter

# settings.json に文字エンコーディング設定
{
    "java.compile.nullAnalysis.mode": "automatic",
    "files.encoding": "utf8"
}
```

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

### 3. エラーケースのテスト
```bash
# 存在しないファイルを選択した場合の動作確認
# 不正な形式のファイルを読み込んだ場合の動作確認
# 空の問題集ファイルを読み込んだ場合の動作確認
```

## 🛠️ カスタマイズ設定

### 1. ウィンドウサイズの調整
```java
// MainFrame.java のコンストラクタで変更
setBounds(100, 100, 1000, 700); // 幅1000px, 高さ700px
```

### 2. フォントサイズの変更
```java
// 各コンポーネントのフォント設定
questionLabel.setFont(new Font("メイリオ", Font.BOLD, 16));
```

### 3. 色テーマのカスタマイズ
```java
// 背景色の変更
choicePanel.setBackground(Color.LIGHT_GRAY);
controlPanel.setBackground(Color.DARK_GRAY);
```

### 4. ボタンテキストの変更
```java
// 日本語から他の言語に変更
private JButton managerBtn = new JButton("Manage Questions");
private JButton importQuestionsBtn = new JButton("Select Question Set");
```

## 🔧 トラブルシューティング

### コンパイルエラー

#### ❌ `javac: command not found`
**原因**: Java Development Kit がインストールされていない
**解決**: 
```bash
# JDK をインストール
# Windows: Oracle JDK または OpenJDK をダウンロード
# macOS: brew install openjdk
# Linux: sudo apt install default-jdk
```

#### ❌ `error: unmappable character for encoding`
**原因**: 文字エンコーディングの不一致
**解決**:
```bash
javac -encoding UTF-8 *.java
```

### 実行時エラー

#### ❌ `Exception in thread "main" java.io.FileNotFoundException`
**原因**: 問題集ファイルが見つからない
**解決**:
1. ファイルパスを確認
2. ファイル名に日本語が含まれている場合は英語に変更
3. ファイルがプロジェクトフォルダにあることを確認

#### ❌ `java.lang.ArrayIndexOutOfBoundsException`
**原因**: 問題ファイルのフォーマットエラー
**解決**:
```bash
# 問題ファイルの各行をチェック
# @SPLIT 区切り文字があることを確認
# 問題行: 4つのフィールド
# 選択肢行: 2つのフィールド
```

#### ❌ 文字化けが発生
**原因**: 文字エンコーディングの問題
**解決**:
```java
// ファイル読み込み部分を修正
BufferedReader br = new BufferedReader(
    new InputStreamReader(new FileInputStream(f), "UTF-8")
);
```

### GUI表示の問題

#### ❌ ボタンが表示されない
**原因**: レイアウト設定の問題
**解決**:
```java
// レイアウトマネージャーの明示的設定
setLayout(new BorderLayout());
// コンポーネント追加後に再描画
revalidate();
repaint();
```

#### ❌ 日本語フォントが正しく表示されない
**原因**: システムフォントの問題
**解決**:
```java
// システムの Look & Feel を使用
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());

// または明示的にフォント指定
Font japaneseFont = new Font("メイリオ", Font.PLAIN, 12);
UIManager.put("Label.font", japaneseFont);
```

## 📦 配布用パッケージの作成

### JAR ファイルの作成
```bash
# Manifest ファイル作成
echo "Main-Class: MainFrame" > MANIFEST.MF

# JAR ファイル作成
jar cfm AnswerSofter.jar MANIFEST.MF *.class

# 実行テスト
java -jar AnswerSofter.jar
```

### 配布パッケージの構成
```
AnswerSofter_Release/
├── AnswerSofter.jar
├── README.md
├── 問題集例.txt
├── 使用方法.pdf
└── run.bat (Windows用起動スクリプト)
```

### Windows用起動スクリプト（run.bat）
```batch
@echo off
java -jar AnswerSofter.jar
pause
```

### macOS/Linux用起動スクリプト（run.sh）
```bash
#!/bin/bash
java -jar AnswerSofter.jar
```

## 🎓 学習・活用方法

### 教育現場での活用
1. **授業準備**: 各科目の問題集を事前作成
2. **小テスト**: 授業中のクイック確認
3. **復習用**: 学生の自習教材として提供
4. **試験対策**: 模擬試験の実施

### 企業研修での活用
1. **新人研修**: 基礎知識確認テスト
2. **スキル評価**: 技術レベル測定
3. **資格対策**: 認定試験の準備
4. **知識共有**: 社内勉強会での活用

## 📞 サポート

### よくある質問
1. **Q: 問題数に上限はありますか？**
   A: ファイルサイズとメモリの制限内であれば制限なし

2. **Q: 画像を問題に含められますか？**
   A: 現在のバージョンではテキストのみ対応

3. **Q: 問題の順番を固定できますか？**
   A: MainFrame.java のシャッフル処理をコメントアウト

4. **Q: 時間制限機能はありますか？**
   A: 現在は未実装。javax.swing.Timer で追加可能

### 技術サポート
- ソースコード解析によるトラブル特定
- ログ出力による動作確認
- デバッグ実行による問題箇所の特定

---

**AnswerSofter** で効率的な学習システムを構築しましょう！📚