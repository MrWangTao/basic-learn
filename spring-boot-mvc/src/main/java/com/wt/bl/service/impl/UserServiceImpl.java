package com.wt.bl.service.impl;

import com.wt.bl.entity.User;
import com.wt.bl.mapper.UserMapper;
import com.wt.bl.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author WangTao
 *         Created at 18/9/3 下午4:44.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        String id = UUID.randomUUID().toString().replace("-", "");
        SimpleHash md5 = new SimpleHash("MD5", user.getPassword(), user.getSalt(), 2);
        user.setPassword(md5.toString());
        LocalDateTime now = LocalDateTime.now();
        userMapper.addUser(user.toBuilder().id(id).gmtCreate(now).gmtModified(now).build());
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }


    public static void main(String[] args) throws Exception {
        System.out.println("================ java8 之前 ===============");
        /**
         * 方法1
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(i);
        /**
         * 方法2
         */
        String time = "2013-04-21";
        DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        Date date = fm.parse(time);
        //Date date2 = new Date(System.currentTimeMillis());
        String str = String.format("%tj",date);//得到time日期是在这年的第几天
        System.out.println(str);

        System.out.println("================ java8 之后 ===============");
        LocalDate now = LocalDate.now();
        int dayOfMonth = now.getDayOfMonth();
        System.out.println(dayOfMonth);

    }

}
