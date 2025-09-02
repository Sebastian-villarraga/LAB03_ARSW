package edu.eci.arst.concprg.prodcons;

import java.util.Queue;

public class Consumer extends Thread {

    private final Queue<Integer> queue;

    public Consumer(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            int elem;
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                elem = queue.poll();
                queue.notifyAll();
            }
            System.out.println("Consumer consumes " + elem);
        }
    }
}
