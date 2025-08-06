import javax.management.MalformedObjectNameException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class MainFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    //Button setting
    private JButton managerBtn = new JButton("問題集管理");
    private JButton importQuestionsBtn = new JButton("問題集選択");
    private JButton preButton = new JButton("前へ");
    private JButton nextButton = new JButton("次へ");
    private JButton okButton = new JButton("提出");

    //Button组
    private  JLabel questionLabel = new JLabel(); //题标签
    private JPanel choicePanel = new JPanel();   //答案选项
    private JPanel controlPanel = new JPanel(); //控制按钮

    private  List<Question> allQuestions = new ArrayList<Question>();
    private  int currentIndex = 0; //当前对应的题号

    private int singleNum = 0;  //单选题答对的次数
    private int multiPartialNum = 0;    //多选题答对部分题的次数
    private int multiNum = 0;   //多选题完全答对的次数

    private File dataFile;

    public MainFrame (){
        //Window
        setTitle("AnswerSofter");
        setBounds(100,100,800,600);
        controlPanel.add(managerBtn);
        controlPanel.add(importQuestionsBtn);
        controlPanel.add(preButton);
        controlPanel.add(nextButton);
        controlPanel.add(okButton);

        //Button响应事件
        managerBtn.addActionListener(this);
        importQuestionsBtn.addActionListener(this);
        preButton.addActionListener(this);
        nextButton.addActionListener(this);
        okButton.addActionListener(this);

        //标签位置
        add(questionLabel, BorderLayout.NORTH);
        add(choicePanel, BorderLayout.CENTER);
        choicePanel.setLayout(new GridLayout(10,1)); //答案选项10个 1行
        add(controlPanel, BorderLayout.SOUTH); //controlPanel的这些按钮 添加到南部（下面）

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == importQuestionsBtn){
            //导入文件的选择方法
            JFileChooser fr = new JFileChooser();
            fr.showOpenDialog(this);
            File f = fr.getSelectedFile();
            this.dataFile = f;

            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = null;
                List<Question> singleOptionQuestions = new ArrayList<Question>(); //单选题库
                List<Question> multiOptionQuestions = new ArrayList<Question>(); //多选题库
                boolean isSingle = true; //判断是否是单选题

                //读取文件里的内容
                while((line = br.readLine()) != null ){
                //这次将用 @SPLIT 来区分答案，题目
                    String[] infos =  line.split(" @SPLIT ");
                    if(infos.length == 4 ) { //题干的数组=4     (1)题目数字 (2)题目内容 （3）答案选项数 （4）正确答案
//                        System.out.println("题目: " + infos[1]);
//                        System.out.println("选项个数: " + infos[2]);
//                        System.out.println("正确选项: " + infos[3]);
                        Question question = new Question(); //初始化题目
                        question.setDesc(infos[1]);
                        question.setCorrectAnswer(infos[3]);

                        if(infos[3].length() == 1 ){ // 单选题
                            singleOptionQuestions.add(question);
                            isSingle = true;
                        }else{  //多选题
                            multiOptionQuestions.add(question);
                            isSingle = false;

                        }
                    }else if (infos.length == 2){   //选项信息=2 （1）答案字母 （2）答案内容
//                        System.out.println("选项序号: " + infos[0]);
//                        System.out.println("选项描述内容: " + infos[1]);
                        Question q = null;
                        if(isSingle){
                            q = singleOptionQuestions.get(singleOptionQuestions.size() - 1);
                            q.add(new JRadioButton(infos[0] + "." + infos[1])); //添加一个单选按钮（用于记忆之前选的答案 单选
                        }else{
                            q = multiOptionQuestions.get(multiOptionQuestions.size() - 1);
                            q.add(new JCheckBox(infos[0] + "." + infos[1])); //添加一个单选按钮（用于记忆之前选的答案 多选
                        }
                    }

/*  用“：” 或”.“来区分答案或者题目 有可能出现错误（题目中出现：.的时候会出错
    所以这次将用 @SPLIT 来区分答案，题目

//                    System.out.println(line);
                    //找出“：”的位置，输出“：”后面的正确答案
                    if(line.indexOf("：") != -1 ){
                        int lastIndex = line.lastIndexOf("：");
                        System.out.println(line.substring(lastIndex + 1));
                        //得到题目答案条数信息
                        String questionAndAnswerNumStr = line.substring(0,lastIndex);
                        lastIndex = questionAndAnswerNumStr.lastIndexOf("：");
                        System.out.println(questionAndAnswerNumStr.substring(lastIndex + 1));
                        //得出题目的描述信息起点的位置
                        int questionStartIndex = questionAndAnswerNumStr.indexOf(".");
                        System.out.println(questionAndAnswerNumStr.substring(questionStartIndex -1 ,lastIndex));
                   }
*/
                }

                Random r = new Random();
                //打乱题目顺序 单选题
                for(int k = 0; k < 1000; k++){
                    int i = r.nextInt(singleOptionQuestions.size());
                    int j = r.nextInt(singleOptionQuestions.size());

                    if(i != j ){
                        Question tmp = singleOptionQuestions.get(i);
                        singleOptionQuestions.set(i, singleOptionQuestions.get(j));
                        singleOptionQuestions.set(j, tmp);
                    }
                }
                //打乱题目顺序 多选题
                for(int k = 0; k < 1000; k++){
                    int i = r.nextInt(multiOptionQuestions.size());
                    int j = r.nextInt(multiOptionQuestions.size());

                    if(i != j ){
                        Question tmp = multiOptionQuestions.get(i);
                        multiOptionQuestions.set(i, multiOptionQuestions.get(j));
                        multiOptionQuestions.set(j, tmp);
                    }
                }

                //加载题目之前 清空之前的内容
                allQuestions.clear();

                //加载单选题
                allQuestions.addAll(singleOptionQuestions);
                allQuestions.addAll(multiOptionQuestions);

/*
    把题加载到窗口里
 */
        //初始化
                showQuestion();

                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

         //前へ
        }else if(e.getSource() == preButton){
            if(currentIndex == 0 ){ //最前面的第一题
                return;
            }else{
                currentIndex--;
            }
            showQuestion();

        //次へ
        }else if(e.getSource() == nextButton){
            if(currentIndex == allQuestions.size() - 1){ //最后一道题
                return;
            }else{
                currentIndex++;
            }
            showQuestion();
        //確認
        }else if(e.getSource() == okButton){
            singleNum = 0;
            multiPartialNum = 0;
            multiNum = 0;

            for(Question q : allQuestions){     //建立所有的题目
                String correctAnswer = q.getCorrectAnswer();   //读取正确答案
                if(correctAnswer.length() == 1){    //正确答案数为1 = 单选题
                    for(JRadioButton btn : q.getBtns()){    //拿到所有单选按钮
                        if(btn.isSelected()){   //如果用户选择的这个答案
                            int index = btn.getText().indexOf("."); //获取第一个 . 的位置
                            String input = btn.getText().substring(0, index); // . 后面的字母
                            if(input.equals(correctAnswer)){
                                singleNum++;
                            }
                            break;
                        }
                    }
                }else{     //正确答案数为 !1 = 多选题
                    String inputAnswer = "";
                    for(JCheckBox checkBox : q.getCheckBoxes()){  //拿到所有多选按钮
                        if(checkBox.isSelected()){  //如果用户选择的这个答案
                            int index = checkBox.getText().indexOf("."); //获取第一个 . 的位置
                            String input = checkBox.getText().substring(0, index);
                            inputAnswer += input;
                        }
                    }

                    if(inputAnswer.equals(correctAnswer)){
                        multiNum++;
                    }else{
                        boolean isCorrect = true;
                        for(char c : inputAnswer.toCharArray()){
                            if(correctAnswer.indexOf(c) == -1){
                                isCorrect = false;
                                break;
                            }
                        }

                        if(isCorrect){
                            multiPartialNum++;
                        }
                    }
                }
            }

            //提交后显示答对个数
//            JOptionPane.showMessageDialog(this," >‘単一選択正解数：" + singleNum
//                                + " >複数選択正解数：" + multiPartialNum + " >多选题全对个数：" + multiNum);
            JOptionPane.showMessageDialog(this," >‘単一選択正解数：" + singleNum
                    + " >複数選択正解数：" + multiNum);
        }else if(e.getSource() == managerBtn){
            if(null == this.dataFile){ //假如这个成员变量没空，就表示还没有到题库
                JOptionPane.showMessageDialog(this,"問題集を選択してください！");
                return;
            }
            new TableDemo(this.dataFile);
        }

    }



    //展示题目
    private void showQuestion() {
        //先对Panel进行清除所有信息（清除上一页的题目）
        choicePanel.removeAll();
        //拿到第一道题
        questionLabel.setText(allQuestions.get(currentIndex).getDesc());
        String correctAnswer = allQuestions.get(currentIndex).getCorrectAnswer().trim();//trim() 去掉左右空格， 防止题库里的答案字母后有空格是，错判断成多选题

        if(correctAnswer.length() == 1){
            for (JRadioButton btn : allQuestions.get(currentIndex).getBtns()){ //如果是单选题，读取单选题按钮
                choicePanel.add(btn);
            }
        }else{
            for (JCheckBox checkBox : allQuestions.get(currentIndex).getCheckBoxes()){
                choicePanel.add(checkBox);
            }
        }
        //对现在这个面板进行刷新
        choicePanel.updateUI();
    }

    public static void main (String[] args){
        new MainFrame();
    }

}
