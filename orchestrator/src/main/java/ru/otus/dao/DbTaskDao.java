package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.TaskRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.Task;
import ru.otus.model.TaskNext;
import ru.otus.model.TaskRange;

import java.util.List;

@Service
public class DbTaskDao implements TaskDao {
    private static final Logger log = LoggerFactory.getLogger(DbTaskDao.class);

    private final TransactionManager transactionManager;
    private final TaskRepository taskRepository;

    public DbTaskDao(TransactionManager transactionManager,
                     TaskRepository taskRepository) {
        this.transactionManager = transactionManager;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<String> findAllIds() {
        return taskRepository.findAllIds();
    }

    @Override
    public Task findById(String id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        String.format("Task with id: %s not found", id)));
    }

    @Override
    public Task save(Task task) {
        return transactionManager.doInTransaction(() -> {
            task.setNew(true);
            Task savedTask = taskRepository.save(task);
            log.info("Saved task: {}", savedTask.getId());
            return savedTask;
        });
    }

    @Override
    public Task updateRange(TaskRange taskRange) {
        return transactionManager.doInTransaction(() -> {
            String taskId = taskRange.getTaskId();
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    String.format("Task with id: %s not found", taskId)));
            Task updatedTask = task.updateRange(taskRange);
            updatedTask.setNew(false);
            Task savedTask = taskRepository.save(updatedTask);
            log.info("Updated task: {} - range", savedTask.getId());
            return savedTask;
        });
    }

    @Override
    public Task updateNext(TaskNext next) {
        return transactionManager.doInTransaction(() -> {
            String taskId = next.getTaskId();
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    String.format("Task with id: %s not found", taskId)));
            String nextId = next.getNextId();
            Task taskNext = taskRepository.findById(nextId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    String.format("Task for next with id: %s not found", nextId)));
            task.setNext(taskNext);
            task.setNew(false);
            Task savedTask = taskRepository.save(task);
            log.info("Updated task: {} - next", savedTask.getId());
            return savedTask;
        });
    }

    @Override
    public String delete(String id) {
        return transactionManager.doInTransaction(() -> {
            taskRepository.deleteById(id);
            log.info("Deleted task: {}", id);
            return id;
        });
    }
}
