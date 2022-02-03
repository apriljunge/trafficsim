package trafficsim.view.window;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {
    private JPanel panel1;
    private JButton jButtonStart;
    private JButton jButtonPause;
    private JSlider timeSlider;
    private JPanel simPanel;
    private JComboBox presetBox;
    private JButton button1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Verkehrssimulator 2021");
        frame.setContentPane(new Window().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public Window() {
        presetBox.addItem("SingleRoad");
        presetBox.addItem("TwoRoadsWithJunction");
        presetBox.addItem("Rudolfplatz");

        jButtonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationPanel simulationPanel = (SimulationPanel) simPanel;
                if (simulationPanel.isStopped()) {
                    simulationPanel.startSimulation();
                    prepareButtonsForStart();
                } else {
                    simulationPanel.stopSimulation();
                    prepareButtonsForStop();
                }
            }
        });
        jButtonPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!((SimulationPanel) simPanel).isPaused()) {
                    ((SimulationPanel) simPanel).pauseSimulation();
                    jButtonPause.setText("Continue");
                } else if (((SimulationPanel) simPanel).isPaused()) {
                    ((SimulationPanel) simPanel).pauseSimulation();
                    jButtonPause.setText("Pause");
                }
            }
        });

        timeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                    ((SimulationPanel) simPanel).setTimesteps(timeSlider.getValue());
                }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                SimulationPanel simulationPanel = (SimulationPanel) simPanel;
                simulationPanel.stopSimulation();
                prepareButtonsForStop();
                ((SimulationPanel) simPanel).reloadMap((String)presetBox.getSelectedItem());}
        });
    }

    private void createUIComponents() {
        simPanel = new SimulationPanel();
        simPanel.setBackground(null);
        simPanel.setForeground(null);
        simPanel.setAlignmentX(0.0F);
        simPanel.setAlignmentY(0.0F);
        simPanel.setFocusable(false);
        simPanel.setMaximumSize(new java.awt.Dimension(1200, 750));
        simPanel.setMinimumSize(new java.awt.Dimension(1200, 750));
        simPanel.setName("simPanel"); // NOI18N
        simPanel.setPreferredSize(new java.awt.Dimension(1200, 750));
    }

    private void prepareButtonsForStop() {
        jButtonStart.setText("Start");
        jButtonPause.setText("Pause");
        jButtonPause.setEnabled(false);
    }

    private void prepareButtonsForStart() {
        jButtonStart.setText("Stop");
        jButtonPause.setText("Pause");
        jButtonPause.setEnabled(true);
    }
}
