# ssm-shiro
待更新，简单实现而已。

#### 简单版本

#### URL 级别验证
#### 方法级别验证
~~~
@RequiresAuthentication
要求当前Subject 已经在当前的session 中被验证通过才能被访问或调用

@RequiresGuest 
必须是在之前的session 中没有被验证或被记住才能被访问或调用

@RequiresPermissions("account:create")
要求当前的Subject 被允许一个或多个权限

@RequiresRoles("administrator")
要求当前的Subject 拥有所有指定的角色。如果他们没有，则该方法将不会被执行，而且AuthorizationException 异常将会被抛出

@RequiresUser
当前的Subject 是一个应用程序用户才能被注解的类/实例/方法访问或调用
~~~

#### 编程式验证
#### 页面内部验证
