public class JavaDz4 {
    private static Object monitor = new Object();
    private static final int QUANTITY = 5;
    private static volatile char lastLetter = 'C';

    public static void main(String[] args) {
        LetterPrinterThread thread1 = new LetterPrinterThread('C', 'A');
        LetterPrinterThread thread2 = new LetterPrinterThread('A', 'B');
        LetterPrinterThread thread3 = new LetterPrinterThread('B', 'C');

        thread1.start();
        thread2.start();
        thread3.start();

    }

    private static class LetterPrinterThread extends Thread {
        private char bef;
        private char aft;

        public LetterPrinterThread(char bef, char aft) {
            this.bef = bef;
            this.aft = aft;
        }

        @Override
        public void run() {
            try{
                for (int i = 0; i < QUANTITY; i++) {
                    synchronized (monitor){
                        while (lastLetter != bef){
                            monitor.wait();
                        }
                        System.out.println(aft);
                        lastLetter = aft;
                        monitor.notifyAll();
                    }

                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
