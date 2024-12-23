class Count {
    private int count = 1;
    private final int max = 10;

    public synchronized void print() {
        while (count <= max) {
            System.out.println("Number: " + count);
            count++;  // Increment the count after printing the number
            notify();  // Notify the other thread
            try {
                if (count <= max) {
                    wait();  // Wait for the square to be printed
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void square() {
        while (count <= max) {
            System.out.println("Square: " + (count - 1) * (count - 1));
            notify();  // Notify the other thread
            try {
                if (count <= max) {
                    wait();  // Wait for the number to be printed
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class NumberThread extends Thread {
    private Count Count;

    public NumberThread(Count Count) {
        this.Count = Count;
    }

    @Override
    public void run() {
        Count.print();
    }
}

class SquareThread extends Thread {
    private Count Count;

    public SquareThread(Count Count) {
        this.Count = Count;
    }

    @Override
    public void run() {
        Count.square();
    }
}

public class Task1 {
    public static void main(String[] args) {
        Count Count = new Count();

        NumberThread numbersThread = new NumberThread(Count);
        SquareThread squaresThread = new SquareThread(Count);

        numbersThread.start();
        squaresThread.start();

        try {
            numbersThread.join();
            squaresThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
