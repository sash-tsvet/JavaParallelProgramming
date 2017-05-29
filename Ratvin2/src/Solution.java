import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kitty on 30.05.2017.
 */
public class Solution {
    //Устанавливаем флаг "справедливый", в таком случае метод
    //aсquire() будет раздавать разрешения в порядке очереди
    private static final Semaphore W_SEMAPHORE = new Semaphore(1, true);
    private static final Semaphore R_SEMAPHORE = new Semaphore(1, true);
    private static final Semaphore WRITECOUNT_SEMAPHORE = new Semaphore(1, true);
    private static final Semaphore READCOUNT_SEMAPHORE = new Semaphore(1, true);
    private static int readCount = 0;
    private static int writeCount = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 7; i++) {
            new Thread(new Reader(i)).start();
        }
        new Thread(new Writer(1)).start();
        new Thread(new Writer(2)).start();
    }
/*
ЧИТАТЕЛЬ
    r.enter
      mutex1.enter
        readcount = readcount + 1
        if readcount=1 then w.enter
      mutex1.leave
    r.leave

  ...читаем...

  mutex1.enter
    readcount = readcount - 1
    if readcount = 0 then w.leave
  mutex1.leave
*/
    public static class Reader implements Runnable {
        private int readerNumber;

        Reader(int number) {
            readerNumber = number;
        }

        @Override
        public void run() {
            System.out.printf("Читатель №%d зашел в систему.\n", readerNumber);
            try {
                //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
                //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
                //пока семафор не разрешит доступ
                R_SEMAPHORE.acquire(); //Получаем общий доступ к чтению книг, теперь запист не может начаться
                READCOUNT_SEMAPHORE.acquire();
                readCount = readCount + 1;
                if (readCount == 1) {
                    W_SEMAPHORE.acquire(); //Делаем запрет на запись
                }
                READCOUNT_SEMAPHORE.release();
                R_SEMAPHORE.release();

                System.out.printf("Читатель №%d Смотрит авиабилеты.\n", readerNumber);
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1) * 100);       //Чтение книги

                System.out.printf("Читатель №%d уходит.\n", readerNumber);

                READCOUNT_SEMAPHORE.acquire();
                readCount = readCount - 1;
                if (readCount == 0) {
                    W_SEMAPHORE.release(); //Убираем запрет на запись при уходе последнего читателя
                }
                READCOUNT_SEMAPHORE.release();
            } catch (InterruptedException e) {}
        }
    }
    /*
    ПИСАТЕЛЬ
      mutex2.enter
        writecount = writecount + 1
        if writecount=1 then r.enter
      mutex2.leave

      w.enter
        ...пишем...
      w.leave

      mutex2.enter
        writecount = writecount - 1
        if writecount = 0 then r.leave
      mutex2.leave
     */
    public static class Writer implements Runnable {
        private int writerNumber;

        Writer(int number) {
            writerNumber = number;
        }

        @Override
        public void run() {
            System.out.printf("Писатель №%d зашел в систему.\n", writerNumber);
            try {
                //acquire() запрашивает доступ к следующему за вызовом этого метода блоку кода,
                //если доступ не разрешен, поток вызвавший этот метод блокируется до тех пор,
                //пока семафор не разрешит доступ
                WRITECOUNT_SEMAPHORE.acquire();
                writeCount = writeCount + 1;
                if (writeCount == 1) R_SEMAPHORE.acquire(); //Делаем запрет на чтение, теперь читатели не могут начать чтение книг
                WRITECOUNT_SEMAPHORE.release();

                W_SEMAPHORE.acquire(); //Дожидаемся ухода всех читателей и не пускаем других писателей
                System.out.printf("Писатель №%d изменяет данные.\n", writerNumber);
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 50 + 1) * 100);       //Запись...
                W_SEMAPHORE.release();

                System.out.printf("Писатель №%d уходит.\n", writerNumber);

                WRITECOUNT_SEMAPHORE.acquire();
                writeCount = writeCount - 1;
                if (writeCount == 0) R_SEMAPHORE.release(); //Запускаем
                WRITECOUNT_SEMAPHORE.release();
            } catch (InterruptedException e) {}
        }
    }
}
