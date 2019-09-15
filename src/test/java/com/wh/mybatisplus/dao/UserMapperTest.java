package com.wh.mybatisplus.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wh.mybatisplus.MybatisPlusApplicationTests;
import com.wh.mybatisplus.enetity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserMapperTest extends MybatisPlusApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void select(){
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::print);
    }

    @Test
    public void insert(){
        //mp的默认策略是实体类字段为null时，插入时也没有该字段
        User user = new User();
        user.setManagerId(1087982257332887553L);
        user.setName("张三");
        user.setCreateTime(LocalDateTime.now());
        user.setAge(22);
        userMapper.insert(user);
    }

    @Test
    public void selectByWrapper(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::print);
    }

    @Test
    public void selectByWrapper2(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like("name", "雨").between("age",20, 40).isNotNull("email");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::print);
    }

    @Test
    public void selectByWrapper3(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.likeRight("name","王").or().ge("age", 25).
                orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::print);
    }

    @Test
    public void selectByWrapper4(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2019-02-14")
        .inSql("manager_id", "select id from user where name like '王%'");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::print);
    }

    @Test
    public void selectByWrapper5(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.likeRight("name", "王").and(wq->wq.lt("age",40)
        .or().isNotNull("email"));
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::print);
    }

    @Test
    public void selectByWrapper6(){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(User::getName, "雨").lt(User::getAge, 40);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        users.forEach(System.out::print);
    }

    @Test
    public void selectByWrapper7(){
        LambdaQueryChainWrapper<User> userLambdaQueryChainWrapper = new LambdaQueryChainWrapper<>(userMapper);
        List<User> users = userLambdaQueryChainWrapper.like(User::getName, "雨").lt(User::getAge, 40).list();
        users.forEach(System.out::print);
    }

    @Test
    public void selectByWrapper8(){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.like(User::getName, "雨").lt(User::getAge, 40);
        List<User> users = userMapper.selectAll(userLambdaQueryWrapper);
        users.forEach(System.out::print);

    }

    @Test
    public void selectByWrapper9(){
        Page<User> page = new Page<>(1,2); //设置分页的开始页数和每页数据量
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.ge(User::getAge, 26);
        IPage<User> userIPage = userMapper.selectPage(page, userLambdaQueryWrapper);//返回IPage对象
        List<User> records = userIPage.getRecords();
        records.forEach(System.out::print);
        System.out.println("页数："+userIPage.getPages());
        System.out.println("总数量："+userIPage.getTotal());

    }

    @Test
    public void selectByWrapper10(){
        Page<User> page = new Page<>(1,2); //设置分页的开始页数和每页数据量
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.ge(User::getAge, 26);
        IPage<User> userIPage = userMapper.selectAllPage(page, userLambdaQueryWrapper);//返回IPage对象
        List<User> records = userIPage.getRecords();
        records.forEach(System.out::print);
        System.out.println("页数："+userIPage.getPages());
        System.out.println("总数量："+userIPage.getTotal());

    }

    @Test
    public void updateByWrapper(){
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("name", "李艺伟").eq("age", 28);
        User user = new User();
        user.setEmail("lyi@qq.com");
        int row = userMapper.update(user, userUpdateWrapper);
        System.out.println("更新条数："+ row);

    }





}