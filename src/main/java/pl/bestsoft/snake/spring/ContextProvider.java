package pl.bestsoft.snake.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Author: Daniel
 */

@Component
public class ContextProvider {

    private static ApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext("base_context.xml");
    }

    public static ApplicationContext getSpringContext() {
        return context;
    }
}
