import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private String desc; //题干描述信息
    private String correctAnswer; //正确答案
    private String inputAnswer; //用户输入的答案

    private ButtonGroup group = new ButtonGroup();
    private List<JRadioButton> btns = new ArrayList<JRadioButton>(); //存放单选题按钮
    private List<JCheckBox> checkBoxs = new ArrayList<JCheckBox>(); //存放多选题按钮（记忆之前选择的答案）

    public void add(JRadioButton btn){
        btns.add(btn);
        group.add(btn);

    }
    public void add(JCheckBox checkBox){
        checkBoxs.add(checkBox);
    }

    public List<JRadioButton> getBtns() {
        return btns;
    }

    public void setBtns(List<JRadioButton> btns) {
        this.btns = btns;
    }

    public List<JCheckBox> getCheckBoxes() {
        return checkBoxs;
    }

    public void setCheckBoxes(List<JCheckBox> checkBoxes) {
        this.checkBoxs = checkBoxes;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getInputAnswer() {
        return inputAnswer;
    }

    public void setInputAnswer(String inputAnswer) {
        this.inputAnswer = inputAnswer;
    }
}
