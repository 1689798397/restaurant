package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.Student;
import com.example.service.StudentService;
import com.example.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author Jack
* @description 针对表【student(学生信息)】的数据库操作Service实现
* @createDate 2023-04-06 20:28:47
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

}




