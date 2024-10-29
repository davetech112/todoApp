package com.zeero.zeero.service;

import com.zeero.zeero.TestUtil;
import com.zeero.zeero.dto.request.TasksRequest;
import com.zeero.zeero.dto.request.UserDetailRequest;
import com.zeero.zeero.dto.response.TaskResponse;
import com.zeero.zeero.dto.response.GenericResponse;
import com.zeero.zeero.model.Tasks;
import com.zeero.zeero.model.Users;
import com.zeero.zeero.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TasksServiceTest {
    @Mock
    private TaskRepository taskRepositoryMock;

    @Mock
    private BaseService baseServiceMock;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private TasksService tasksService;

    @Test
    void createTasks() {
        TasksRequest request = setRequest();
        UserDetailRequest req = new UserDetailRequest();
        Tasks task = task(request);
        Users users = TestUtil.getUsers(req);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(users);
        SecurityContextHolder.setContext(securityContext);

        when(taskRepositoryMock.save(any(Tasks.class))).thenReturn(task);

        GenericResponse<TaskResponse> result = tasksService.createTasks(request);

        assertNotNull(result);
        verify(taskRepositoryMock, times(1)).save(any(Tasks.class));
    }

    @Test
    void getTask() {
        TasksRequest request = setRequest();
        Tasks task = task(request);
        task.setId(1L);


        when(taskRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(task));

        GenericResponse<TaskResponse> result = tasksService.getTask(1L);

        assertNotNull(result);

    }

    @Test
    void getAllTaskByAUser() {
        Page<Tasks> expectedTaskPage = mock(Page.class);

        when(taskRepositoryMock.findByUserId(2L, pageable)).thenReturn(expectedTaskPage);

        GenericResponse<List<TaskResponse>> result = tasksService.getAllTaskByAUser(2L, pageable);

        assertNotNull(result);
    }

    @Test
    void completeTask() {
        TasksRequest request = setRequest();
        Tasks task = task(request);
        task.setCompleted(true);
        task.setId(2L);

        when(taskRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(task));
        when(taskRepositoryMock.save(any(Tasks.class))).thenReturn(task);

        GenericResponse<TaskResponse> result = tasksService.completeTask(2L);

        assertNotNull(result);
        assertTrue(result.getData().isCompleted());
    }

    private Tasks task(TasksRequest request){
        Tasks tasks = new Tasks();
        tasks.setTitle(request.getTitle());
        tasks.setDueDate(request.getDueDate());
        return tasks;
    }

    private TasksRequest setRequest(){
        TasksRequest request = new TasksRequest();
        request.setTitle("My task title");
        request.setDueDate(LocalDate.parse("2024-07-12"));
        return request;
    }
}