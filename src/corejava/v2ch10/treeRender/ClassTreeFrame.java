package corejava.v2ch10.treeRender;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

/**
 * В этом фрейме отображается дерево иерархии классов,
 * текстовое поле и кнопка Add для ввода новых классов в дерево
 */
public class ClassTreeFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private JTree tree;
    private JTextField textField;
    private JTextArea textArea;

    public ClassTreeFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // в корне дерева иерархии классов находится класс Object
        root = new DefaultMutableTreeNode(java.lang.Object.class);
        model = new DefaultTreeModel(root);
        tree = new JTree(model);

        // ввести этот класс, чтобы заполнить дерево рядом данных
        addClass(getClass());

        // установить пиктограммы для обозначения узлов
        ClassNameTreeCellRenderer renderer = new ClassNameTreeCellRenderer();
        renderer.setClosedIcon(new ImageIcon(getClass().getResource("red-ball.gif")));
        renderer.setOpenIcon(new ImageIcon(getClass().getResource("yellow-ball.gif")));
        renderer.setLeafIcon(new ImageIcon(getClass().getResource("blue-ball.gif")));
        tree.setCellRenderer(renderer);

        // установить режим выбора узлов дерева
        tree.addTreeSelectionListener(event -> {
            // пользователь выбрал другой узел -
            // обновить его расписание
            TreePath path = tree.getSelectionPath();
            if (path == null) return;
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            Class<?> c = (Class<?>) selectedNode.getUserObject();
            String description = getFieldDescription(c);
            textArea.setText(description);
        });
        int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
        tree.getSelectionModel().setSelectionMode(mode);

        // в этой текстовой области находится описание класса
        textArea = new JTextArea();

        // добавить дерево и текстовую область
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JScrollPane(tree));
        panel.add(new JScrollPane(textArea));

        add(panel, BorderLayout.CENTER);

        addTextField();
    }

    /**
     * Добавляет в фрейм текстовое поле и кнопку Add
     * для ввода нового класса
     */
    public void addTextField() {
        JPanel panel = new JPanel();

        ActionListener addListener = event -> {
            // ввести класс, имя которого находится в текстовом поле
            try {
                String text = textField.getText();
                addClass(Class.forName(text));
                textField.setText("");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Class not found");
            }
        };

        // в этом текстовом поле вводятся имена новых классов
        textField = new JTextField(20);
        textField.addActionListener(addListener);
        panel.add(textField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(addListener);
        panel.add(addButton);

        add(panel, BorderLayout.SOUTH);
    }

    /**
     * Находит искомый объект в дереве
     * @param obj Искомый объект
     * @return Возвращает узел, содержащий объект, или пустое
     *         значение null, если объект отсутствует в дереве
     */
    @SuppressWarnings("unchecked")
    public DefaultMutableTreeNode findUserObject(Object obj) {
        // найти узел, содержащий пользовательский объект
        Enumeration<TreeNode> e = (Enumeration<TreeNode>) root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.getUserObject().equals(obj)) return node;
        }
        return null;
    }

    /**
     * Вводит новый класс и любые его родительские классы,
     * пока еще отсутствующие в дереве
     * @param c Вводимый класс
     * @return Возвращает узел с вновь введенным классом
     */
    public DefaultMutableTreeNode addClass(Class<?> c) {
        // ввести новый класс в дерево

        // пропустить типы данных, не относящиеся к классам
        if (c.isInterface() || c.isPrimitive()) return null;

        // если класс уже присутствует в дереве, возвратить его узел
        DefaultMutableTreeNode node = findUserObject(c);
        if (node != null) return node;

        // класс отсутствует в дереве - ввести сначала его
        // родительские классы рекурсивным способом

        Class<?> s = c.getSuperclass();

        DefaultMutableTreeNode parent;
        if (s == null) parent = root;
        else parent = addClass(s);

        // ввести затем класс как потомок его родительского класса
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(c);
        model.insertNodeInto(newNode, parent, parent.getChildCount());

        // сделать видимым узел с вновь введенным классом
        TreePath path = new TreePath(model.getPathToRoot(newNode));
        tree.makeVisible(path);
        return newNode;
    }

    /**
     * Возвращает описание полей класса
     * @param c Описываемый класс
     * @return Возвращает символьную строку, содержащую все
     *         типы и имена полей описываемого класса
     */
    public static String getFieldDescription(Class<?> c) {
        // использовать рефлексию для обнаружения типов и имен полей
        StringBuilder r = new StringBuilder();
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if ((f.getModifiers() & Modifier.STATIC) != 0) r.append("static ");
            r.append(f.getType().getName());
            r.append(" ");
            r.append(f.getName());
            r.append("\n");
        }
        return r.toString();
    }
}