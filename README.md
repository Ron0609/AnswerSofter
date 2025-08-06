# AnswerSofter（問題集管理システム） 📚

**AnswerSofter**は、日本語対応の問題集管理・学習支援システムです。単選択問題と複数選択問題の両方に対応し、問題の作成・編集・削除、およびランダム出題によるクイズ機能を提供します。

## 機能概要 ✨

### 🎯 主要機能
- **問題集管理**: 問題の追加・編集・削除
- **ランダム出題**: 問題順序をシャッフルして出題
- **単選択・複数選択対応**: 両方の問題形式をサポート
- **採点機能**: 自動採点と結果表示
- **ファイルベース**: テキストファイルでの問題データ管理
- **直感的GUI**: Java Swingによる使いやすいインターフェース

### 📊 対応問題形式
- **単選択問題（単選）**: ラジオボタンで1つの答えを選択
- **複数選択問題（複選）**: チェックボックスで複数答えを選択

## システム構成 🏗️

### メインクラス構造
```
AnswerSofter/
├── MainFrame.java           # メインウィンドウ（問題表示・解答）
├── TableDemo.java          # 問題管理画面（一覧・CRUD操作）
├── Question.java           # 問題データモデル
├── AddQuestionDialog.java  # 問題追加ダイアログ
├── UpdateQuestionDialog.java # 問題編集ダイアログ
└── MyTableCellRenderer.java # テーブル表示用（未実装）
```

### データ形式
問題ファイルは以下の形式で保存されます：
```
1 @SPLIT 問題文 @SPLIT 選択肢数 @SPLIT 正解
A @SPLIT 選択肢1の内容
B @SPLIT 選択肢2の内容
...
```

## 使用方法 🎮

### 1. 問題集の準備
1. テキストファイルで問題集を作成
2. 指定フォーマットで問題と選択肢を記述

### 2. アプリケーション起動
```bash
javac *.java
java MainFrame
```

### 3. 基本操作フロー

#### 問題集の読み込み
1. **「問題集選択」** ボタンをクリック
2. ファイル選択ダイアログで問題集ファイルを選択
3. 問題が自動的にランダムシャッフルされて読み込まれます

#### 問題解答
1. **「前へ」「次へ」** ボタンで問題間を移動
2. 単選択問題：ラジオボタンで1つ選択
3. 複数選択問題：チェックボックスで複数選択
4. **「提出」** ボタンで採点実行

#### 問題管理
1. **「問題集管理」** ボタンをクリック
2. 問題一覧画面が開きます
3. **「問題追加」**: 新しい問題を追加
4. **「修正」**: 既存問題を編集
5. **「削除」**: 問題を削除

## 技術仕様 ⚙️

### 開発環境
- **言語**: Java
- **GUI Framework**: Swing
- **IDE**: IntelliJ IDEA（.imlファイルから推定）
- **データ保存**: テキストファイル（UTF-8）

### システム要件
- **Java**: JDK 8以上
- **OS**: Windows, macOS, Linux
- **メモリ**: 最小256MB RAM
- **ストレージ**: 50MB以上

### アーキテクチャ特徴
- **MVC パターン**: モデル、ビュー、コントローラーの分離
- **イベント駆動**: ActionListener による操作処理
- **ファイルI/O**: BufferedReader/FileWriter によるデータ永続化
- **動的UI**: 問題形式に応じた動的コンポーネント生成

## 問題集ファイル形式 📝

### 単選択問題の例
```
1 @SPLIT OSI参照モデルの6層の名称は() @SPLIT 4 @SPLIT B
A @SPLIT アプリケーション層
B @SPLIT プレゼンテーション層  ← 正解
C @SPLIT ネットワーク層
D @SPLIT データリング層
```

### 複数選択問題の例
```
7 @SPLIT 該当するプロトコルを選択せよ @SPLIT 4 @SPLIT AD
A @SPLIT ARP      ← 正解1
B @SPLIT TCP
C @SPLIT PIP
D @SPLIT SMTP     ← 正解2
```

### フォーマット説明
- `@SPLIT` : フィールド区切り文字
- 問題行：`番号 @SPLIT 問題文 @SPLIT 選択肢数 @SPLIT 正解`
- 選択肢行：`記号 @SPLIT 選択肢内容`
- 正解が1文字 = 単選択、複数文字 = 複数選択

## インストール・実行 🚀

### 1. 開発環境セットアップ
```bash
# Javaコンパイラの確認
javac -version
java -version

# プロジェクトディレクトリに移動
cd AnswerSofter

# 全Javaファイルのコンパイル
javac *.java

# アプリケーション実行
java MainFrame
```

### 2. IDEでの実行（IntelliJ IDEA推奨）
1. プロジェクトを開く
2. `MainFrame.java` の `main` メソッドを実行
3. GUIアプリケーションが起動

### 3. 問題集サンプル
付属の `問題集例.txt` を使用して動作確認が可能です。

## カスタマイズ 🛠️

### 1. 新しいボタンの追加
```java
// MainFrame.java に新機能ボタンを追加
private JButton newFeatureBtn = new JButton("新機能");

// コンストラクタで初期化
controlPanel.add(newFeatureBtn);
newFeatureBtn.addActionListener(this);
```

### 2. 問題形式の拡張
```java
// Question.java に新しいフィールドを追加
private String category;  // 問題カテゴリ
private int difficulty;   // 難易度レベル
```

### 3. 採点アルゴリズムのカスタマイズ
```java
// MainFrame.java の採点部分を修正
if(inputAnswer.equals(correctAnswer)){
    // 完全正解の処理
    multiNum++;
} else {
    // 部分正解の判定ロジック
}
```

## トラブルシューティング 🔧

### よくある問題

#### ❌ `java.io.FileNotFoundException`
**原因**: 問題集ファイルが見つからない
**解決**: 
- ファイルパスを確認
- ファイルが存在するか確認
- 読み取り権限があるか確認

#### ❌ `ArrayIndexOutOfBoundsException`
**原因**: 問題ファイルの形式が正しくない
**解決**:
- `@SPLIT` 区切り文字を確認
- 問題行に4つのフィールドがあるか確認
- 選択肢行に2つのフィールドがあるか確認

#### ❌ GUI表示の問題
**原因**: Java Swing の Look & Feel 問題
**解決**:
```java
// MainFrame コンストラクタに追加
try {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
} catch (Exception e) {
    e.printStackTrace();
}
```

#### ❌ 文字化け
**原因**: ファイルエンコーディングの不一致
**解決**:
```java
// UTF-8 でファイル読み込み
BufferedReader br = new BufferedReader(
    new InputStreamReader(new FileInputStream(f), "UTF-8")
);
```

### デバッグ方法
```java
// コンソール出力でデバッグ情報を確認
System.out.println("問題文: " + question.getDesc());
System.out.println("正解: " + question.getCorrectAnswer());
System.out.println("選択肢数: " + question.getBtns().size());
```

## 開発者向け情報 👨‍💻

### 拡張可能な機能
- **統計機能**: 正答率、学習履歴の追跡
- **タイマー機能**: 制限時間付きクイズ
- **データベース対応**: ファイルベースからDB移行
- **オンライン機能**: サーバーとの同期
- **問題カテゴリ**: 分野別問題管理

### コードの改善点
- **例外処理**: より詳細なエラーハンドリング
- **設定ファイル**: アプリケーション設定の外部化
- **国際化対応**: 多言語サポート
- **テスト**: JUnit によるユニットテスト追加

### 貢献方法
1. Issueを作成して機能要求や不具合を報告
2. Forkしてプルリクエストを送信
3. ドキュメントの改善
4. 新しい問題集の作成・共有

## ライセンス 📄

このプロジェクトは教育目的で作成されています。自由にご利用・改変いただけます。

## サポート 📞

問題や質問がある場合：
1. コードの動作を確認
2. ログ出力でエラー内容を特定
3. 問題ファイルのフォーマットを確認
4. Java環境の確認

---

**AnswerSofter** で効率的な学習を始めましょう！🎓