package com.huzeji.todomanager.svc;

import com.huzeji.model.Task;
import com.huzeji.model.User;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface UserSvc extends GenericCrudSvc<User, Long>{
    public Page<Task> getTasks(Long userId, Map<String, Object> filters );
}
