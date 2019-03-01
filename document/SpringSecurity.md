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

> 授权
+ RBAC
+ 实现自定义步骤
    - 方式1： 自定义 AccessDecisionManager
        * AccessDecisionManager 来判断所请求的url + httpmethod 是否符合我们数据库的配置，但是我们没有判断类似需求相关的voter，
            因此，我们需要自定义一个Voter的实现（默认注册的AffirmativeBased的策略是只要有Voter投出ACCESS_GRANTED票，则判定为通过，这也正符合我们的需求）
        * 实现步骤
            - 自定义voter实现。AccessDecisionManager是基于AccessDecisionVoter实现权限认证的
            - 自定义ConfigAttribute实现。
            - 自定义SecurityMetadataSource实现。 实现从数据库中加载configAttribute
            - Authentication包含用户实例（这个其实不用说，大家应该都已经这么做了）。
            - 自定义GrantedAuthority实现。
    - 方式2： 自定义 SecurityMetadataSource
    