public class ThreadTest extends Thread {
    private volatile boolean running = true; // 使用 volatile 保证线程间的可见性

    @Override
    public void run() {
        while (running) {
            // 在这里执行你的线程任务
            System.out.println("线程正在运行...");

            try {
                Thread.sleep(1000); // 每隔1秒钟打印一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("线程已关闭");
    }

    // 提供一个方法来关闭线程
    public void shutdown() {
        this.running = false;
    }

    public static void main(String[] args) {
        ThreadTest myThread = new ThreadTest();
        myThread.start(); // 启动线程

        try {
            Thread.sleep(5000); // 让线程运行5秒钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myThread.shutdown(); // 关闭线程
    }
}
