package trafficsim.view.window;

import trafficsim.resources.GeoDataParser;
import trafficsim.view.grid.SimulationObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

public class SimulationPanel extends JPanel {

    private boolean isPaused = true;
    private boolean isStopped = true;
    private int TIMESTEPS_PER_SEC = 24;
    private boolean isRudolf = false;

    private List<SimulationObject> simulationObjects = new LinkedList<>();

    private ImageIcon img = new ImageIcon("src/trafficsim/resources/img/rudolfplatz.png");
    private Timer repaintTimer;
    private java.util.Timer simulationTimer;

    public SimulationPanel() {
        GeoDataParser geo = new GeoDataParser();
        simulationObjects.addAll(geo.getSimulationObjects("SingleRoad"));
    }

    public void reloadMap(String preset) {
        if (preset == "Rudolfplatz"){
            isRudolf = true;
        }else{
            isRudolf = false;
        }
        simulationObjects.clear();
        GeoDataParser geo = new GeoDataParser();
        simulationObjects.addAll(geo.getSimulationObjects(preset));
        this.paint(this.getGraphics());
    }
    private void initializeSimulation() {
        this.paint(this.getGraphics());
        this.isStopped = false;
        this.isPaused = false;
    }

    public boolean isPaused() {
        return this.isPaused;
    }

    public boolean isStopped() {
        return this.isStopped;
    }

    public void startSimulation() {
        this.initializeSimulation();

        repaintTimer = new Timer(16, e -> {
            this.repaint();
        });

        runSimulationTimer();
        repaintTimer.start();
    }

    private void runSimulationTimer() {
        int period = getSimulationInterval();
        simulationTimer = new java.util.Timer();
        simulationTimer.schedule(getSimulationTimerTask(), 0, period);
    }

    private int getSimulationInterval() {
        return 1000 / TIMESTEPS_PER_SEC;
    }

    private TimerTask getSimulationTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                for (SimulationObject s : simulationObjects)
                    s.update();
            }
        };
    }

    public void pauseSimulation() {
        this.isPaused = !this.isPaused;
        if (!isStopped)
            if (isPaused) {
                simulationTimer.cancel();
                repaintTimer.stop();
            } else {
                runSimulationTimer();
                repaintTimer.start();
            }
    }

    public void stopSimulation() {
        if (!this.isStopped) {
            this.isStopped = true;
            this.isPaused = true;
            if (repaintTimer != null)
                repaintTimer.stop();

            for (SimulationObject s : simulationObjects)
                s.reset();
            simulationTimer.cancel();

            this.repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isRudolf) g2d.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
        paintResults(g2d, isRudolf);
    }

    private void paintResults(Graphics2D g2d, boolean isRudolf) {
        for (SimulationObject s : simulationObjects)
            s.paint(g2d, isRudolf);
    }

    public int getTimesteps() {
        return this.TIMESTEPS_PER_SEC;
    }

    public void setTimesteps(int value) {
        this.TIMESTEPS_PER_SEC = value;
        if (simulationTimer != null)
            simulationTimer.cancel();
        runSimulationTimer();
    }


}
