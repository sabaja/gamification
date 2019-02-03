package microservices.book.gamification.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration

/**
 * https://www.logicbig.com/how-to/code-snippets/jcode-spring-mvc-webmvcconfigurer.html
 * 
 * Enables Cross-Origin Resource Sharing (CORS) More info:
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cors.html
 * 
 * @param registry
 * 
 * @author sabaja
 *
 */
public class WebConfigurationForCORS implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		WebMvcConfigurer.super.addCorsMappings(registry);
		registry.addMapping("/**");
	}

}
