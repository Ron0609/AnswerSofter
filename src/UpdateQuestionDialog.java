import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateQuestionDialog extends JDialog implements ActionListener {

    private JScrollPane scrollPane;
    private JTextArea textArea; //文本区
    private JButton okBtn;
    private TableDemo demo;
    private int index;

    public UpdateQuestionDialog(TableDemo demo, int index){
        this.demo = demo;
        this.index = index;
        textArea = new JTextArea();
        Question q = demo.getAllQuestions().get(index);

        String desc = q.getDesc();
        String correctAnswer = q.getCorrectAnswer();

        if(correctAnswer.length() == 1 ){   // 单选题
            textArea.setText(textArea.getText() + (index + 1) + " @SPLIT " + desc + " @SPLIT "
                    + q.getBtns().size() + " @SPLIT " + correctAnswer + "\n");

            for(JRadioButton btn : q.getBtns()) {
                String content = btn.getText();
                textArea.setText(textArea.getText() + content.replace("."," @SPLIT ") + "\n");
            }
        }else{      //双选题
            textArea.setText(textArea.getText() + (index + 1) + " @SPLIT " + desc + " @SPLIT "
                    + q.getCheckBoxes() + " @SPLIT " + correctAnswer + "\n");

            for(JCheckBox btn : q.getCheckBoxes()) {
                String content = btn.getText();
                textArea.setText(textArea.getText() + content.replace("."," @SPLIT ") + "\n");
            }
        }

        setBounds(100,100,750,500);
        scrollPane = new JScrollPane(textArea);
        okBtn = new JButton("確認");
        add(scrollPane, BorderLayout.CENTER);
        add(okBtn, BorderLayout.SOUTH);
        okBtn.addActionListener(this);
        setAlwaysOnTop(true);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //将questionInfos里输入的内容用\n进行分割代入questionInfoArr里
        String questionInfos = textArea.getText();
        String[] questionInfoArr = questionInfos.split("\n");

        Question question = new Question();
        boolean isSingle = true;

        //读取文件里的内容
        for(int i = 0; i < questionInfoArr.length; i++){
            String line = questionInfoArr[i];
            //这次将用 @SPLIT 来区分答案，题目
            String[] infos =  line.split(" @SPLIT ");
            if(infos.length == 4 ) { //题干的数组=4     (1)题目数字 (2)题目内容 （3）答案选项数 （4）正确答案
                question.setDesc(infos[1]);
                question.setCorrectAnswer(infos[3]);

                if(infos[3].length() == 1 ){ // 单选题
                    isSingle = true;
                }else{  //多选题
                    isSingle = false;

                }
            }else if (infos.length == 2){   //选项信息=2 （1）答案字母 （2）答案内容
                if(isSingle){
                    question.add(new JRadioButton(infos[0] + "." + infos[1])); //添加一个单选按钮（用于记忆之前选的答案 单选
                }else{
                    question.add(new JCheckBox(infos[0] + "." + infos[1])); //添加一个单选按钮（用于记忆之前选的答案 多选
                }
            }

        }

        demo.updateQuestion(question, index);
        demo.save();
        demo.initTableData();
        this.dispose();
    }
}
