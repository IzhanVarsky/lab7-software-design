package profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DemoAspect {
    final static ProfilerStatistics profiler = new ProfilerStatistics();
    static String profiledPackage;

    @Around("execution(* *(..))")
    public Object aroundProfile(ProceedingJoinPoint joinPoint) throws Throwable {
        final Signature signature = joinPoint.getSignature();

        if (!signature.getDeclaringTypeName().startsWith(profiledPackage)) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        profiler.enterMethod(signature);

        try {
            return joinPoint.proceed();
        } finally {
            profiler.exitMethod(signature, System.currentTimeMillis() - startTime);
        }
    }
}
