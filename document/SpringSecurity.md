> 核心功能 
+ 认证（who are you）
+ 授权（what you can do）
+ 攻击防护（防止伪造身份）

> 基本原理
+ 请求 -> 表单验证`UsernamePassworkAuthenticationFilter`(httpBasic验证`BasicAuthenticationFilter`) -> ...一些自定义 filter..
    -> ExceptionTranslationFilter(捕获最后FilterSecurityInterceptor抛出的异常) -> FilterSecurityInterceptor -> REST api
+ 响应 也需要通过一系列的filter

> 表单验证流程
+ 表单提交
+ UsernamePasswordAuthenticationFilter 传递未认证的 UsernamePasswordAuthenticationToken
+ AuthenticationManager 接受并处理token
+ DaoAuthenticationProvider 认证token
+ UserDetailsService 获取自定义信息
+ UserDetail
+ Authentication(已认证)
    