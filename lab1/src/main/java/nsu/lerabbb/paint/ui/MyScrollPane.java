package nsu.lerabbb.paint.ui;

import lombok.Getter;

import javax.swing.*;

public class MyScrollPane extends JScrollPane {
    @Getter
    private JPanel myPanel;

    public MyScrollPane(){
        super();
        myPanel = new MyPanel(this);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setViewportView(myPanel);
    }
}
