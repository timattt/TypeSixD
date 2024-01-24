package org.shlimtech.typesevendcore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.shlimtech.typesevendatabasecommon.service.MetadataService;
import org.shlimtech.typesevendcore.task.FindMatchForUserTask;
import org.shlimtech.typesixdatabasecommon.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log
public class TaskController {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final UserService userService;
    private final MetadataService metadataService;

    @PostMapping("/task/{id}")
    public ResponseEntity<?> createTask(@PathVariable(name = "id") int id) {
        log.info("Received task creation event for user: [" + id + "]");
        threadPoolTaskExecutor.execute(new FindMatchForUserTask(metadataService, userService, id));
        return ResponseEntity.ok().build();
    }

}
