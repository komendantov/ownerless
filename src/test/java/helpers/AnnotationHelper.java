package helpers;

import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Step;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for searching annotated methods.
 */
public class AnnotationHelper {


    private AnnotationHelper() {
    }

    /**
     * Get all annotated methods from the package.
     *
     * @param targetPackage package for searching
     * @param annotation annotation class
     * @return all annotated methods from target package
     */
    private static Set<Method> giveAllAnnotatedMethods(String targetPackage, Class<? extends Annotation> annotation) {
        org.reflections.Reflections reflections = new org.reflections.Reflections(
                new ConfigurationBuilder().setUrls(
                        ClasspathHelper.forPackage(targetPackage)).setScanners(
                        new MethodAnnotationsScanner()));
        return reflections.getMethodsAnnotatedWith(annotation);
    }

    /**
     * Checking whether the Cucumber annotation is an annotation.
     *
     * @param annotation
     * @return is it cucumber annotation
     */
    private static boolean isCucumberAnnotation(Annotation annotation) {
        List<Class> gherkinClasses = new ArrayList<>();
        Collections.addAll(gherkinClasses, Given.class, When.class, And.class, Then.class, But.class);
        return gherkinClasses.contains(annotation.annotationType());
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println(new AnnotationHelper().prepareStepsMatrix());
        System.out.println(new AnnotationHelper().prepareStepsMap());
    }

    public JSONObject prepareStepsMatrix() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Set<Method> methods = giveAllAnnotatedMethods("tests", Step.class);
        ArrayList<Annotation> annotations = new ArrayList<>();
        for (Method method : methods) {
            annotations.addAll(Arrays.asList(method.getAnnotations()));
        }
        HashMap<String, JSONArray> annotationMap = new HashMap<>();
        for (Annotation annotation : annotations) {
            AnnotationData annotatedStep = new AnnotationData();
            if (annotation.annotationType() == Step.class) {
                annotatedStep.setShortName((String) annotation.annotationType().getDeclaredMethod("shortName").invoke(annotation));
                String[] preconditionSteps = ((String[]) annotation.annotationType().getDeclaredMethod("preconditionSteps").invoke(annotation));
                JSONArray stepPreconditionSteps = new JSONArray();
                stepPreconditionSteps.addAll(Arrays.asList(preconditionSteps));
                annotationMap.put(annotatedStep.getShortName(), stepPreconditionSteps);
            }
        }
        return new JSONObject(annotationMap);
    }

    public HashMap<String, String> prepareStepsMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap<String, String> stepsMap = new HashMap<>();
        Set<Method> methods = new HashSet<>();

        methods.addAll(AnnotationHelper.giveAllAnnotatedMethods("tests", Step.class));

        Set<Annotation[]> gherkinAnnotations = new HashSet<>();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (isCucumberAnnotation(annotation)) {
                    gherkinAnnotations.add(annotations);
                }
            }
        }
        for (Annotation[] annotations : gherkinAnnotations) {
            AnnotationData annotatedStep = new AnnotationData();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Step.class)
                    annotatedStep.setShortName((String) annotation.annotationType().getDeclaredMethod("shortName").invoke(annotation));
                if (AnnotationHelper.isCucumberAnnotation(annotation)) {
                    annotatedStep.setGherkinKeyword(annotation.annotationType().getSimpleName());
                    annotatedStep.setGherkinName((String) annotation.annotationType().getDeclaredMethod("value").invoke(annotation));
                }
            }
            stepsMap.put(annotatedStep.getShortName(), annotatedStep.getGherkinName());
        }
        return stepsMap;
    }
}