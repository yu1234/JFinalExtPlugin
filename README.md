###  JFinalExtPlugin JFinal框架插件扩展


该插件暂支持JFinal框架Model对象和Controller对象注解注入



#### 以下是使用示例：
#### 控制器注解注入
**1.控制器注解注入（在继承JFinal框架的Controller的子类上加上注解标签@JFinalController）**

```java
@JFinalController({"/", "/hello"})
public class HelloController extends Controller {
    public void index() {
        User user = User.dao.findById("1500453530921");
        renderHtml("id:" + user.getId() + "<br/>" + "name:" + user.getName() + "<br/>" + "age:" + user.getAge());
    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JFinalController {
    String[] value();//访问某个 Controller 所需要的一个字符,相当于controllerKey，可定义多个

    String module() default "";//所属模块，适用于路由拆分

    String viewPath() default "controllerKey";//该 Controller 返回的视图的相对路径
}
```
**2.在JFinal的配置类的路由配置上注册该标签(注入方式：二选一)**
```java
public class JFinalBaseConfig extends JFinalConfig {
.....
  /**
     * 路由配置
     * @param routes
     */
    public void configRoute(Routes routes) {
        System.out.println("configRoute");
        //获取控制器注解注入对象
        ControllerAnnotation controllerAnnotation = new ControllerAnnotation(scanControllerPackage);
        //第一种注入方式（直接传入路由对象，路径统一配置（适合单路由））
        controllerAnnotation.injectController(routes);
        //第二种注入方式（直接传入回调接口，路径配置（适合拆分路由））
        controllerAnnotation.injectController(new IControllerScanReport() {
            /**
             *
             * @param controllerKey 访问某个 Controller 所需要的一个字符,相当于controllerKey，可定义多个
             * @param controllerClass 控制器Class
             * @param module 所属模块，适用于路由拆分,默认“”
             */
            public void report(String controllerKey, Class<?> controllerClass, String module) {

                //do something
            }

            /**
             *
             * @param controllerKey 访问某个 Controller 所需要的一个字符,相当于controllerKey，可定义多个
             * @param controllerClass 控制器Class
             * @param viewPath 该 Controller 返回的视图的相对路径,默认“controllerKey”
             * @param module 所属模块，适用于路由拆分,默认“”
             */
            public void report(String controllerKey, Class<?> controllerClass, String viewPath, String module) {
                //do something
            }
        });
    }
.....
}
```


#### Model实体类注解注入
**1.控制器注解注入（在继承JFinal框架的Model的子类上加上注解标签@JFinalModel）**
```java
//使用例子
@JFinalModel(tableName = "user")
public class User extends Model<User> {
}
//标签类
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JFinalModel {
     String tableName(); //数据库表名
     String primaryKey() default "id"; //主键名称，默认为“id”
}
```
**2.在JFinal的配置类的插件配置方法上注册该标签(注入方式：二选一;注：注解注入插件必须在ActiveRecordPlugin插件之前添加)**
```java
public class JFinalBaseConfig extends JFinalConfig {
.....
   /**
     * 插件添加配置
     * @param plugins
     */
    public void configPlugin(Plugins plugins) {
        System.out.println("configPlugin");
        //连接数据库
        DruidPlugin dp = new DruidPlugin("jdbcUrl", "username", "password");
        plugins.add(dp);
        //建立表和对象映射关系对象
        final ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        //添加表和对象映射关系，扫描@JFinalModel注解(注入方式一：直接传入ActiveRecordPlugin对象实例)
        ModelAnnotationPlugin map1 = new ModelAnnotationPlugin(scanModelPackage);
        map1.injectModel(arp);
        //添加表和对象映射关系，扫描@JFinalModel注解(注入方式二：直接传入ActiveRecordPlugin对象实例)
        ModelAnnotationPlugin map2 = new ModelAnnotationPlugin(scanModelPackage);
        map2.injectModel(new IModelScanReport() {
            public void report(String tableName, String primaryKey, Class<?> modelClass) {
                //do something
            }

            public void report(String tableName, Class<?> modelClass) {
               //do something
            }
        });
        //添加插件(注：注解注入插件必须在ActiveRecordPlugin插件之前添加)
        plugins.add(map1);
        plugins.add(arp);

    }
.....
}
```
#### maven
```java
<dependency>
    <groupId>com.github.yu1234.JFinalPluginExt</groupId>
    <artifactId>annotation</artifactId>
    <version>1.0.0</version>
</dependency>
```
