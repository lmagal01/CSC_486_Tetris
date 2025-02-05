

public class Engine implements Runnable {

    private boolean wait = true;
    private boolean stop = false;

    @Override
    public void run() {
        while (!stop) {
            try {
                Thread.sleep(1000);
                if (!wait) {
                    Blackboard.getInstance().addValue("time", System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                // log the error
            }
        }
    }

    public void pause(boolean wait) {
        this.wait = wait;
    }

    public void stop(boolean stop) {
        this.stop = this.stop;
    }

}