package ru.otus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.db.repository.TaskTemplateRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import ru.otus.model.TaskTemplate;
import ru.otus.model.TaskType;

import java.util.Arrays;
import java.util.List;

@Service
public class DbTaskTemplateDao implements TaskTemplateDao {
    private static final Logger log = LoggerFactory.getLogger(TaskTemplateDao.class);

    private final TransactionManager transactionManager;
    private final TaskTemplateRepository taskTemplateRepository;

    public DbTaskTemplateDao(TransactionManager transactionManager,
                             TaskTemplateRepository taskTemplateRepository) {
        this.transactionManager = transactionManager;
        this.taskTemplateRepository = taskTemplateRepository;
    }

    @Override
    public List<String> findAllIds() {
        return taskTemplateRepository.findAllIds();
    }

    @Override
    public List<String> getAllTaskTypes(){
        return Arrays.stream(TaskType.values())
                .map(TaskType::name)
                .toList();
    }

    @Override
    public TaskTemplate findById(String id) {
        return taskTemplateRepository.findById(id).orElseThrow(() ->
                new RuntimeException(
                        String.format("Task template with id: %s not found", id)));
    }

    @Override
    public TaskTemplate save(TaskTemplate taskTemplate) {
        return transactionManager.doInTransaction(() -> {
            taskTemplate.setNew(true);
            TaskTemplate savedTemplate = taskTemplateRepository.save(taskTemplate);
            log.info("Saved task template: {}", savedTemplate.getId());
            return savedTemplate;
        });
    }

    @Override
    public String delete(String id) {
        return transactionManager.doInTransaction(() -> {
            taskTemplateRepository.deleteById(id);
            log.info("Deleted task template: {}", id);
            return id;
        });
    }
}
