import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kitty on 31.05.2017.
 */
public class Solution {
    //Принтер занято - true, свободно - false
    private static final int numOfPrinters = 2;
    private static final boolean[] PRINTERS = new boolean[numOfPrinters];
    //Устанавливаем флаг "справедливый", в таком случае метод
    //aсquire() будет раздавать разрешения в порядке очереди
    private static final Semaphore SEMAPHORE = new Semaphore(numOfPrinters, true);

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        while (true) {
            new Thread(new Job(i++)).start();
            Thread.sleep(ThreadLocalRandom.current().nextInt(1, 6000));
        }
    }

    public static class Job implements Runnable {
        private int jobNumber;

        public Job(int jobNumber) {
            this.jobNumber = jobNumber;
        }

        @Override
        public void run() {
            System.out.printf("Задание №%d добавлено в очередь.\n", jobNumber);
            try {
                //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
                //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
                //пока семафор не разрешит доступ
                SEMAPHORE.acquire();

                int printerNumber = -1;

                //Ищем свободный принтер и печатаем
                synchronized (PRINTERS){
                    for (int i = 0; i < numOfPrinters; i++)
                        if (!PRINTERS[i]) {      //Если принтер свободен
                            PRINTERS[i] = true;  //занимаем его
                            printerNumber = i;   //Наличие свободного принтера, гарантирует семафор
                            System.out.printf("Задание №%d выполняется на принтере %d.\n", jobNumber, i);
                            break;
                        }
                }

                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10000));       //Идет печать

                synchronized (PRINTERS) {
                    PRINTERS[printerNumber] = false;//Освобождаем принтер
                }

                //release(), напротив, освобождает ресурс
                SEMAPHORE.release();
                System.out.printf("Задание №%d завершило печать.\n", jobNumber);
            } catch (InterruptedException e) {
            }
        }
    }
}
