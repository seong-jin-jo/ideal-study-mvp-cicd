package com.idealstudy.mvp.application.service.classroom.inclass;

import com.idealstudy.mvp.infrastructure.repository.inclass.MaterialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MaterialsService {

    @Autowired
    private final MaterialsRepository materialsRepository;

    public void upload() {

    }

    public void getDetail(){

    }

    public void getListByClassroom(){

    }

    public void getListForStudent(){

    }

    public void getListForTeacher(){

    }

    public void update(){

    }

    public void delete(){

    }
}
