import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TableDemo extends JFrame implements ActionListener {

    //操控面板，主要用于存放增删改按钮
    private JPanel controlPanel;

    //定义表格的数据模型
    private DefaultTableModel model;

    //定义一个表格
    private JTable table;

    //定义一个滚轮面板，用于放置表格
    private JScrollPane scrollPane;

    //增删改按钮
    private JButton addBtn;

    private File dataFile;

    private List<Question> allQuestions;

    public TableDemo(File dataFile){
        this.dataFile = dataFile;
        setBounds(100,100,750,400);
        //表头
        String[] head = {"序号","题目描述","操作1","操作2"};
        //初始化数据
        Object[][] datas = {};
        //初始化表格数据模型
        model = new DefaultTableModel(datas, head);
        //初始化表格
        table = new JTable(model);
        //初始化滚动面板
        scrollPane = new JScrollPane(table);
        //初始化按钮
        addBtn = new JButton("問題追加");
        //给按钮添加监听事件
        addBtn.addActionListener(this);

        //初始化控制面板，并添加相关按钮
        controlPanel = new JPanel();
        controlPanel.add(addBtn);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //把表格中的 学号，姓名 居中显示
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumn("序号").setCellRenderer(renderer);
        table.getColumn("操作1").setCellRenderer(renderer);
        table.getColumn("操作2").setCellRenderer(renderer);

        //设置表隔断度，以及拖动的最大宽度和最小宽度
        DefaultTableColumnModel dcm = (DefaultTableColumnModel) table.getColumnModel();
        //序号显示大小
        dcm.getColumn(0).setPreferredWidth(40); //列显示的宽度
        dcm.getColumn(0).setMinWidth(40);   //列可以拖动的最小宽度
        dcm.getColumn(0).setMaxWidth(40);   //列可以拖动的最大宽度
        //删除按钮显示大小
        dcm.getColumn(2).setPreferredWidth(60); //列显示的宽度
        dcm.getColumn(2).setMinWidth(45);   //列可以拖动的最小宽度
        dcm.getColumn(2).setMaxWidth(75);   //列可以拖动的最大宽度
        //修改按钮显示大小
        dcm.getColumn(3).setPreferredWidth(60); //列显示的宽度
        dcm.getColumn(3).setMinWidth(45);   //列可以拖动的最小宽度
        dcm.getColumn(3).setMaxWidth(75);   //列可以拖动的最大宽度

        //设置表格行高
        table.setRowHeight(35);

        //鼠标操作事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                String value = table.getValueAt(row, col).toString();
                if("削除".equals(value)){
                    allQuestions.remove(row);
                    save();
                    initTableData(); //将表格的数据重新刷新
                }else if("修正".equals(value)){
                    new UpdateQuestionDialog(TableDemo.this, row);
                }
            }
        });

        setVisible(true);
        initTableData();
    }

    public void updateQuestion(Question question, int index){
        allQuestions.set(index, question);
    }

    public void addQuestion(Question question){
        allQuestions.add(question);
    }

    public List<Question> getAllQuestions(){
        return allQuestions;
    }

    //将操作保存到题库文档
    public void save(){
        try {
            FileWriter fw = new FileWriter(this.dataFile);
            int sequence = 1;
            for(Question q : allQuestions){
                String desc = q.getDesc();
                String correctAnswer = q.getCorrectAnswer();

                if(correctAnswer.length() == 1 ){   // 单选题
                    fw.write(sequence + " @SPLIT " + desc + " @SPLIT "
                            + q.getBtns().size() + " @SPLIT " + correctAnswer + "\n");

                    for(JRadioButton btn : q.getBtns()) {
                        String content = btn.getText();
                        fw.write(content.replace("."," @SPLIT ") + "\n");
                    }
                }else{      //双选题
                    fw.write(sequence + " @SPLIT " + desc + " @SPLIT "
                            + q.getCheckBoxes() + " @SPLIT " + correctAnswer + "\n");

                    for(JCheckBox btn : q.getCheckBoxes()) {
                        String content = btn.getText();
                        fw.write(content.replace("."," @SPLIT ") + "\n");
                    }
                }
                sequence++;
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initTableData(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.dataFile));
            String line = null;
            allQuestions = new ArrayList<Question>(); //单选题库
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
                        allQuestions.add(question);
                        isSingle = true;
                    }else{  //多选题
                        allQuestions.add(question);
                        isSingle = false;

                    }
                }else if (infos.length == 2){   //选项信息=2 （1）答案字母 （2）答案内容
//                        System.out.println("选项序号: " + infos[0]);
//                        System.out.println("选项描述内容: " + infos[1]);
                    Question q = null;
                    if(isSingle){
                        q = allQuestions.get(allQuestions.size() - 1);
                        q.add(new JRadioButton(infos[0] + "." + infos[1])); //添加一个单选按钮（用于记忆之前选的答案 单选
                    }else{
                        q = allQuestions.get(allQuestions.size() - 1);
                        q.add(new JCheckBox(infos[0] + "." + infos[1])); //添加一个单选按钮（用于记忆之前选的答案 多选
                    }
                }
            }

            br.close();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            int sequence = 1;
            for(Question q : allQuestions){
                model.addRow(new Object[]{sequence, q.getDesc(),"削除","修正"});
                sequence++;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //按钮事件
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addBtn) {
            new AddQuestionDialog(this);
        }
    }

}

