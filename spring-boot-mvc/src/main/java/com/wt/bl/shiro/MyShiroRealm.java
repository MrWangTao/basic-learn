package com.wt.bl.shiro;

import com.wt.bl.entity.User;
import com.wt.bl.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author WangTao
 *         Created at 18/9/6 下午3:33.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String account = token.getUsername();
//        User user = userService.selectByAccount(account);//根据登陆名account从库中查询user对象
//        User user = User.builder().username("wangwang").password(new SimpleHash("MD5", "123456", "111", 2).toString()).build();
        User user = userService.findByUsername(account);
        if(user==null){throw new AuthenticationException("用户不存在");}

        //进行认证，将正确数据给shiro处理
        //密码不用自己比对，AuthenticationInfo认证信息对象，一个接口，new他的实现类对象SimpleAuthenticationInfo
		/**	第一个参数随便放，可以放user对象，程序可在任意位置获取 放入的对象
		 * 第二个参数必须放密码，
		 * 第三个参数放 当前realm的名字，因为可能有多个realm
		 */
        AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user, user.getPassword(), new SimpleByteSource(user.getSalt().getBytes()), this.getName());
//        AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        //AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user,user.getPassword(),new MySimpleByteSource(account), this.getName());

        //清之前的授权信息
        super.clearCachedAuthorizationInfo(authcInfo.getPrincipals());
        SecurityUtils.getSubject().getSession().setAttribute("login", user);
        return authcInfo;//返回给安全管理器，securityManager，由securityManager比对数据库查询出的密码和页面提交的密码

    }
}
