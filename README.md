###  JFinalExtPlugin JFinal框架插件扩展


改插件暂支持JFinal框架Model对象和Controller对象注解注入



#### 以下是使用示例：

**1.控制器注解注入（在继承JFinal框架的Controller的子类上加上注解标签@JFinalController**

```java
@JFinalController({"/", "/hello"})
public class HelloController extends Controller {
    public void index() {
        User user = User.dao.findById("1500453530921");
        renderHtml("id:" + user.getId() + "<br/>" + "name:" + user.getName() + "<br/>" + "age:" + user.getAge());
    }
}


