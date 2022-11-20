package corejava.v2ch11.dndImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageListDnDFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 500;

    private ImageList list1;
    private ImageList list2;

    public ImageListDnDFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        list1 = new ImageList(Paths.get(getClass().getPackage().getName(), "images1"));
        list2 = new ImageList(Paths.get(getClass().getPackage().getName(), "images2"));

        setLayout(new GridLayout(2, 1));
        add(new JScrollPane(list1));
        add(new JScrollPane(list2));
    }
}

class ImageList extends JList<ImageIcon> {
    public ImageList(Path dir) {
        DefaultListModel<ImageIcon> model = new DefaultListModel<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir)) {
            for (Path entry : entries)
                model.addElement(new ImageIcon(entry.toString()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setModel(model);
        setVisibleRowCount(0);
        setLayoutOrientation(JList.HORIZONTAL_WRAP);
        setDragEnabled(true);
        setDropMode(DropMode.ON_OR_INSERT);
        setTransferHandler(new ImageListTransferHandler());
    }
}

class ImageListTransferHandler extends TransferHandler {
    // поддержка перетаскивания

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent source) {
        ImageList list = (ImageList) source;
        int index = list.getSelectedIndex();
        if (index < 0) return null;
        ImageIcon icon = list.getModel().getElementAt(index);
        return new ImageTransferable(icon.getImage());
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == MOVE) {
            ImageList list = (ImageList) source;
            int index = list.getSelectedIndex();
            if (index < 0) return;
            DefaultListModel<?> model = (DefaultListModel<?>) list.getModel();
            model.remove(index);
        }
    }

    // поддержка опускания

    @Override
    public boolean canImport(TransferSupport support) {
        if (support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            if (support.getUserDropAction() == MOVE) support.setDropAction(COPY);
            return true;
        }
        else return support.isDataFlavorSupported(DataFlavor.imageFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        ImageList list = (ImageList) support.getComponent();
        DefaultListModel<ImageIcon> model = (DefaultListModel<ImageIcon>) list.getModel();

        Transferable transferable = support.getTransferable();
        List<DataFlavor> flavors = Arrays.asList(transferable.getTransferDataFlavors());

        List<Image> images = new ArrayList<>();

        try {
            if (flavors.contains(DataFlavor.javaFileListFlavor)) {
                @SuppressWarnings("unchecked") List<File> fileList
                        = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                for (File f : fileList) {
                    try {
                        images.add(ImageIO.read(f));
                    } catch (IOException ignored) {
                        // не удалось прочитать изображение - пропустить
                    }
                }
            } else if (flavors.contains(DataFlavor.imageFlavor)) {
                images.add((Image) transferable.getTransferData(DataFlavor.imageFlavor));
            }

            int index;
            if (support.isDrop()) {
                JList.DropLocation location = (JList.DropLocation) support.getDropLocation();
                index = location.getIndex();
                if (!location.isInsert()) model.remove(index); // сменить место
            }
            else index = model.size();
            for (Image image : images) {
                model.add(index, new ImageIcon(image));
                index++;
            }
            return true;
        } catch (IOException | UnsupportedFlavorException ex) {
            return false;
        }
    }
}