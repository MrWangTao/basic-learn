package com.wt.bl.boot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wt.bl.enums.ValuableEnum;
import com.wt.bl.enums.ValuableEnumDeserializer;
import com.wt.bl.enums.formatter.StatusEnumFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.Set;

/**
 * @author WangTao
 *         Created at 18/9/3 上午10:14.
 */
@Configuration
//@EnableWebMvc
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${pathpackage:null}")
    private String valuableEnumPackage;

    @Autowired
    ObjectMapper mapper;

    /**
     * @description 用于添加格式转换
     *
     * @param 
     * @author wangtao
     * @return 
     * @date 18/9/3
     * @time 下午5:16
     **/
    @Override
    public void addFormatters(FormatterRegistry registry) {
        /**
         * 实现 Converter
         */
        // registry.addConverter(new StatusEnumConverter());
        registry.addFormatter(new StatusEnumFormatter());
    }

    /**
     * 配置所有实现 ValuableEnum 接口的枚举对象的 Jackson 反序列化策略
     * 构造方法执行之后执行此方法
     */
    @PostConstruct
    public void valuableEnumDeserializer() {

        /**
         * @see https://blog.csdn.net/Ideality_hunter/article/details/53337535
         *
         * ObjectMapper类是jackson库的主要类。提供了一些功能将转换java对象匹配到json
         * @see http://www.yiibai.com/jackson/jackson_objectmapper.html
         * JsonNode类是Jackson库的一个类，该类可以很容易的操作Json格式的数据，如①获取某个简单json串中某个key的值②获取某个层层嵌套的json串中某个key的值
         * SimpleModule，在自定义序列化时，才用得到
         * @see http://jackyrong.iteye.com/blog/2005323  序列化反序列化
         */
        SimpleModule module = new SimpleModule();

        /**
         * spring 自定义类扫描器
         * @see https://fangjian0423.github.io/2017/06/11/spring-custom-component-provider/
         * 一般情况下，要自定义扫描功能的话，可以直接使用 ClassPathScanningCandidateComponentProvider， 加上一些自定义的TypeFilter即可
         */
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

        // Add include filters which matches all the classes (or use your own)
        /**
         * 添加自定义TypeFilter, 常用如下
         * 1. new AssignableTypeFilter(ValuableEnum.class)   添加所有实现了这个接口的类
         * 2. new AnnotationTypeFilter(RestController.class) 添加所有添加了@RestController的类
         */
        provider.addIncludeFilter(new AssignableTypeFilter(ValuableEnum.class));
        // provider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));

        // Aet matching classes defined in the package
        final Set<BeanDefinition> enumClasses = provider.findCandidateComponents(valuableEnumPackage);

        enumClasses.forEach(beanDefinition -> {
            try {
                // 得到每个bean对应的class
                Class<Object> clazz = (Class<Object>) Class.forName(beanDefinition.getBeanClassName());
                // 如果这类的class是这两种类型
                if (ValuableEnum.class.isAssignableFrom(clazz) && Enum.class.isAssignableFrom(clazz)) {
                    module.addDeserializer(clazz, new ValuableEnumDeserializer<>(clazz));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        // Jackson won't pick it up unless you tell it there's a module to use
        // 需要注册
        mapper.registerModule(module);
    }

}
