package com.hb.service;

import org.springframework.stereotype.Service;

import com.hb.models.StudentInfo;

/**
 * @author liuzh
 */
@Service
public class StudentInfoService extends BaseService<StudentInfo>{

    public int save(StudentInfo studentInfo) {
        if (studentInfo == null) {
            throw new NullPointerException("保存的对象不能为空!");
        }
        return super.save(studentInfo);
    }

}
