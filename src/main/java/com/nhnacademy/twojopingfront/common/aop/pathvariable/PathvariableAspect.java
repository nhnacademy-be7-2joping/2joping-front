package com.nhnacademy.twojopingfront.common.aop.pathvariable;

import com.nhnacademy.twojopingfront.common.annotation.ValidPathVariable;
import com.nhnacademy.twojopingfront.common.error.exception.common.InvalidPathVariableException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * PathvariableAspect는 경로 변수에 대한 유효성 검사를 수행하는 AOP 클래스입니다.
 * 메서드에 선언된 @ValidPathVariable 어노테이션을 통해 경로 변수의 유효성을 검증합니다.
 * 음수 값이 경로 변수로 사용될 경우, InvalidPathVariableException 예외를 발생시켜 잘못된 경로 변수를 처리합니다.
 *
 * <p>주요 기능:
 * <ul>
 *     <li>@ValidPathVariable 어노테이션이 적용된 메서드에 대해 유효성 검사 수행</li>
 *     <li>경로 변수에 음수 값이 들어오는 경우 예외 발생</li>
 * </ul>
 * </p>
 *
 * @see com.nhnacademy.twojopingfront.common.annotation.ValidPathVariable
 * @see com.nhnacademy.twojopingfront.common.error.exception.common.InvalidPathVariableException
 * @author Luha
 * @since 1.0
 */

@Aspect
@Component
public class PathvariableAspect {

    /**
     * validPathVariableMethods()는 @ValidPathVariable 어노테이션이 적용된 모든 메서드의 포인트컷을 정의합니다.
     * com.nhnacademy.twojopingfront 패키지 내에서 유효성 검사를 수행할 메서드들을 포함합니다.
     */
    @Pointcut("execution(* com.nhnacademy.twojopingfront..*(.., @com.nhnacademy.twojopingfront.common.annotation.ValidPathVariable (*), ..))")
    public void validPathVariableMethods() {}


    /**
     * checkPathVariable()는 validPathVariableMethods() 포인트컷에서 지정된 메서드가 성공적으로 실행된 후
     * 경로 변수 값의 유효성을 검사하는 메서드입니다.
     *
     * @param joinPoint 실행된 메서드의 정보를 담고 있는 JoinPoint 객체
     * @throws InvalidPathVariableException 경로 변수에 음수 값이 전달될 경우 발생하는 예외
     */
    @AfterReturning("validPathVariableMethods()")
    public void checkPathVariable(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Parameter[] parameters = joinPoint.getSignature()
                .getDeclaringType()
                .getMethods()[0]
                .getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(ValidPathVariable.class) && args[i] instanceof Integer) {
                int pathVarValue = (int) args[i];
                if (pathVarValue < 0) {
                    throw new InvalidPathVariableException("음수 값은 허용되지 않습니다.");
                }
            }
        }
    }
}
