package com.hailiang.langchain4jdemo.remote.apiAspect;


import com.hailiang.langchain4jdemo.annotations.ExternalApi;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class ExternalApiAspect {
    private final Map<String, WebClient> clients = new ConcurrentHashMap<>();

    @Autowired
    public ExternalApiAspect(@Qualifier("gitClient") WebClient gitClient) {
        clients.put("gitClient", gitClient);
    }

    @Around("@annotation(externalApi)")
    public Object handleExternalApi(ProceedingJoinPoint pjp, ExternalApi externalApi) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = pjp.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        String path = externalApi.path();
        String httpMethod = externalApi.method();
        String clientName = externalApi.client();

        WebClient webClient = clients.get(clientName);
        if (webClient == null) {
            throw new IllegalArgumentException("无效的客户端: " + clientName);
        }

        // @PathVariable注解解析
        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof PathVariable) {
                    String paramName = ((PathVariable) annotation).value();
                    path = path.replace("{" + paramName + "}", args[i].toString());
                }
            }
        }

        WebClient.RequestHeadersSpec<?> request;

        if ("GET".equalsIgnoreCase(httpMethod)) {
            WebClient.RequestHeadersUriSpec<?> uriSpec = webClient.get();
            String finalPath = path;
            request = uriSpec.uri(uriBuilder -> {
                for (int i = 0; i < args.length; i++) {
                    for (Annotation annotation : parameterAnnotations[i]) {
                        if (annotation instanceof RequestParam) {
                            String paramName = ((RequestParam) annotation).value();
                            uriBuilder.queryParam(paramName, args[i]);
                        }
                    }
                }
                return uriBuilder.path(finalPath).build();
            });
        } else if ("POST".equalsIgnoreCase(httpMethod)) {
            WebClient.RequestBodyUriSpec uriSpec = webClient.post();
            WebClient.RequestBodySpec bodySpec = uriSpec.uri(path);

            Object body = null;
            for (int i = 0; i < args.length; i++) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation instanceof RequestBody) {
                        body = args[i];
                    }
                }
            }
            request = bodySpec.bodyValue(body);
        } else {
            throw new UnsupportedOperationException("该请求方法还未支持: " + httpMethod);
        }

        Mono<String> response = request.retrieve()
                .onStatus(status -> status.isError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new RuntimeException(
                                    "请求失败，状态码: " + clientResponse.statusCode() + ", 错误信息: " + errorBody)));
                })
                .bodyToMono(String.class);
        return response.block();
    }
}
