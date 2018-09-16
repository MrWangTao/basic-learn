### shiro 
+ 认证
    - 配置ShiroConfig
    - 自定义Realm，继承 AuthorizingRealm， 并重写其方法
+ 控制并发登录问题
    - 自定义AccessControlFilter， 继承 AccessControlFileter
    - 将自定义Filter添加到过滤链中
    - **注意** 因为SessionManager中的cookieID与服务端的名称冲突，因此需要自定义CookieId
    