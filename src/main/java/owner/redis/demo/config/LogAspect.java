package owner.redis.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import owner.redis.demo.Result;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 日志切面
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    //execution([方法的可见性] 返回类型 [方法所在类的全路径名] 方法名(参数类型列表) [方法抛出的异常类型])
    @Pointcut("execution(* owner.redis.demo.controller.*.*(..)) && !execution(* owner.redis.demo.controller.HealthController.*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        try {
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = signature.getMethod();
            ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            String name = "";

            if (annotation != null && !StringUtils.isEmpty(annotation.value())) {
                name = annotation.value();
            } else {
                name = method.getName();
            }
            log.info("{},方法名:{},入参:{}", name.trim(), method.getName(), JSON.toJSONString(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint), SerializerFeature.IgnoreErrorGetter));
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            result = proceedingJoinPoint.proceed();
            stopWatch.stop();
            log.info("{},方法名:{},出参:{}", name.trim(), method.getName(), JSON.toJSONString(result));
            log.info("{},方法名:{},耗时:{}ms", name.trim(), method.getName(), stopWatch.getLastTaskTimeMillis());
            if (Objects.nonNull(result) && result instanceof Result) {
                ((Result<?>) result).setTimeCost(stopWatch.getLastTaskTimeMillis());
            }
            return result;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 获取入参
     *
     * @param proceedingJoinPoint
     * @return
     */
    protected Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }


    protected Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            requestParams.put(paramNames[i], value);
        }
        return requestParams;
    }
}
