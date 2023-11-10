# sky_take_out

## 介绍

黑马程序员实战项目：苍穹外卖（前后端分离）

## Swagger（Springdoc）

使用Swagger，你只需要按照它的规范去定义接口及接口相关的信息，就可以做到生成接口文档，以及在线接口调试页面。官网：https://swagger.io/。

Knife4j是为Java MVC框架集成Swagger生成Api文档的增强解决方案，但是它是基于Springfox的，Springfox已经被淘汰，Springboot3.0已不再支持。

---

Springdoc官网：https://springdoc.org/

## 完善登录功能

### 密码加密

数据库存储加密后的密码，密码加密方式是MD5。

> MD5算法
>
> MD5,全称Message Digest Algorithm 5，翻译过来就是消息摘要算法第5版，是计算机安全领域广泛使用的一种散列函数，用于确保信息传输的完整性。MD5算法是由MD2、MD3、MD4演变而来，是一种单向加密算法[^1]，一种不可逆的加密方式。

[^1]:单向加密算法：在介绍MD5算法前，很有必要解释一下单向加密算法。单向加密，人如其名，就是只能单向对明文进行加密，而不能逆向通过密文得到明文。该算法在加密过程中，在得到明文后，经过加密算法得到密文，不需要使用密钥。因为没有密钥，所以就无法通过密文得到明文。

## 用户管理



### 新增用户

#### ThreadLocal

本模块使用到了`ThreadLocal`技术。

`ThreadLocal`叫做***线程变量***，意思是ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的，也就是说该变量是当前线程独有的变量。`ThreadLocal`为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。

### 分页查询

在Spring MVC中，`HttpMessageConverter`用于在HTTP请求和响应之间转换对象。默认情况下，Spring提供了一些常用的`HttpMessageConverter`，例如`StringHttpMessageConverter`、`MappingJackson2HttpMessageConverter`等。其中，`MappingJackson2HttpMessageConverter`使用Jackson库将Java对象转换为JSON格式，并将JSON格式的数据转换为Java对象。

项目中，我们自建了一个`MappingJackson2HttpMessageConverter`，目的是将日期对象（`LocalDate`/`LocalTime`/`LocalDateTime`）格式化为我们需要的格式。



`MappingJackson2HttpMessageConverter`需要一个`ObjectMapper`，下面是我们自建的`ObjectMapper`的子类：



```java
/**
 * 对象映射器:基于jackson将Java对象转为json，或者将json转为Java对象<br>
 * 将JSON解析为Java对象的过程称为 [从JSON反序列化Java对象]<br>
 * 从Java对象生成JSON的过程称为 [序列化Java对象到JSON]
 */
public class JacksonObjectMapper extends ObjectMapper {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    //public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper() {
        super();
        //收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        //反序列化时，属性不存在的兼容处理
        // this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        //注册功能模块 例如，可以添加自定义序列化器和反序列化器
        this.registerModule(simpleModule);
    }
}
```



添加消息转换器：

```java
    /**
     * 拓展消息转换器
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("拓展消息转换器...");
        // MappingJackson2HttpMessageConverter的实现只能有一个生效，如果不删除原本的实现，我们新增的就不会被使用。
        converters.removeIf(httpMessageConverter -> httpMessageConverter instanceof MappingJackson2HttpMessageConverter);
        // 当然，我们也可以将我们添加的转换器放到其他 MappingJackson2HttpMessageConverter实现类 的前面。
        converters.add(0, new MappingJackson2HttpMessageConverter(new JacksonObjectMapper()));
    }
```

