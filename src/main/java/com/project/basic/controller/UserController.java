package com.project.basic.controller;

import com.project.basic.entity.JsonResult;
import com.project.basic.entity.User;
import com.project.basic.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 * @author shengwu ni
 */
@RestController
@Api(value = "用户信息接口")
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/getUser/{id}")
    @ApiOperation(value = "根据用户唯一标识获取用户信息")
    public JsonResult<User> getUserInfo(@PathVariable @ApiParam(value = "用户唯一标识") Long id) {
        User user = userService.getUser(id);
        return new JsonResult<>(user);
    }

    @GetMapping("/getUserByName/{name}")
    @ApiOperation(value = "根据用户名获取用户信息")
    public JsonResult<User> getUserByName(@PathVariable String name) {
        User user = userService.getUserByName(name);
        return new JsonResult<>(user);
    }

    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有用户信息")
    public JsonResult<List<User>> getAll() {
        List<User> list = userService.getAll();
        return new JsonResult<>(list);
    }

    @ApiOperation(value = "测试jackson对null的处理")
    @GetMapping("/map")
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>(3);

        User user = new User(1L, "倪升武", null);
        map.put("作者信息", user);
        map.put("博客地址", "http://www.itcodai.com");
        map.put("CSDN地址", null);
        map.put("微信公众号", "程序员私房菜");
        return map;
    }

    @ApiOperation(value = "测试全局异常处理的处理")
    @GetMapping("/exception")
    public JsonResult testException() {
        // 抛出异常，全局异常会捕获，然后做处理
        User user = userService.getUser((long) 3);
        user.getId();
        return new JsonResult();
    }

    /**
     * 身份认证测试接口
     * @param request
     * @return
     */
    @RequestMapping("/admin")
    public String admin(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        return "success";
    }

    /**
     * 角色认证测试接口
     * @param request
     * @return
     */
    @RequestMapping("/student")
    public String student(HttpServletRequest request) {
        return "success";
    }

    /**
     * 权限认证测试接口
     * @param request
     * @return
     */
    @RequestMapping("/teacher")
    public String teacher(HttpServletRequest request) {
        return "success";
    }
    /**
     * 用户登录接口
     * @param user user
     * @param request request
     * @return string
     */
    @PostMapping("/login")
    public String login(User user, HttpServletRequest request) {

        // 根据用户名和密码创建token
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        // 获取subject认证主体
        Subject subject = SecurityUtils.getSubject();
        try{
            // 开始认证，这一步会跳到我们自定义的realm中
            subject.login(token);
            request.getSession().setAttribute("user", user);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("user", user);
            request.setAttribute("error", "用户名或密码错误！");
            return "login";
        }
    }
}
