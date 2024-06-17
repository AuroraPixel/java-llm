package com.hailiang.langchain4jdemo.remote.apiAspect;


import com.hailiang.langchain4jdemo.annotations.ExternalApi;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
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
        Method method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), pjp.getArgs().getClass());
        Object[] args = pjp.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();


        String path = externalApi.path();
        String httpMethod = externalApi.method();
        String clientName = externalApi.client();

        WebClient webClient = clients.get(clientName);
        if (webClient == null) {
            throw new IllegalArgumentException("无效的客户端: " + clientName);
        }

        WebClient.RequestHeadersSpec<?> request;

        if ("GET".equalsIgnoreCase(httpMethod)) {
            WebClient.RequestHeadersUriSpec<?> uriSpec = webClient.get();
            request = uriSpec.uri(uriBuilder -> {
                for (int i = 0; i < args.length; i++) {
                    for (Annotation annotation : parameterAnnotations[i]) {
                        if (annotation instanceof RequestParam) {
                            String paramName = ((RequestParam) annotation).value();
                            uriBuilder.queryParam(paramName, args[i]);
                        }
                    }
                }
                return uriBuilder.path(path).build();
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

        Mono<String> response = request.retrieve().bodyToMono(String.class);
        return response.block();
    }
}
