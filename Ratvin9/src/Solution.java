import java.util.ArrayList;

/**
 * Created by kitty on 01.06.2017.
 */
public class Solution {
    public static void main(String[] args) {
        int numServers = 4;
        Server[] servers = new Server[numServers];

        servers[0] = new Server(0, 2047);
        servers[1] = new Server(0, 511);
        servers[2] = new Server(0, 1023);
        servers[3] = new Server(0, 511);

        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Task> privateTasks = new ArrayList<>();
        for (int i = 0; i < numServers; i++) {
            Task newTask = new Task(servers[i].getStartByte(), servers[i].getEndByte());
            for (Task task: tasks) {
                if (task.getStartByte() < newTask.getStartByte()) {
                    if (task.getEndByte() > newTask.getStartByte()) {
                        if (task.getEndByte() < newTask.getEndByte()) {
                            tasks.remove(tasks.indexOf(task));//Split into 3 tasks
                            tasks.add(new Task(task.getStartByte(), newTask.getStartByte()-1));
                            tasks.add(new Task(newTask.getStartByte(), task.getEndByte()));
                            tasks.add(new Task(task.getEndByte()+1, newTask.getEndByte()));
                        }
                        else {
                            tasks.remove(tasks.indexOf(task));//Split into 3 tasks
                            tasks.add(new Task(task.getStartByte(), newTask.getStartByte()-1));
                            tasks.add(newTask);
                            tasks.add(new Task(newTask.getEndByte()+1, task.getEndByte()));
                        }
                    }
                    else tasks.add(newTask);
                }
                else {
                    //No way out..
                }
            }
        }

    }
}
