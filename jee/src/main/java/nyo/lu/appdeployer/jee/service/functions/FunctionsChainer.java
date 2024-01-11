package nyo.lu.appdeployer.jee.service.functions;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.Optional.of;

@Service
@Lazy
public class FunctionsChainer {

    @Autowired
    private BeanFactory beanFactory;

    @SafeVarargs
    public final <OUT> OUT runWithResult(Object firstInput,
                                         OUT finalOutputDefault,
                                         Class<? extends Function>... stepsName) {
        AtomicReference<Optional<Function>> fullyCombinedFunction = new AtomicReference<>(Optional.empty());

        stream(stepsName)
                .map(c -> beanFactory.getBean(c))
                .forEach(stepFunction -> {
                    Optional<Function> combineWithCurrent = fullyCombinedFunction.get().map(function -> function.andThen(stepFunction));
                    fullyCombinedFunction.set(combineWithCurrent.or(() -> of(stepFunction)));
                });

        return fullyCombinedFunction.get()
                .map(f -> f.apply(firstInput))
                .map(out -> (OUT) out)
                .orElse(finalOutputDefault);
    }

    @SafeVarargs
    public final void run(Object firstInput,
                          Class<? extends Function>... stepsName) {
        runWithResult(firstInput, null, stepsName);
    }
}
