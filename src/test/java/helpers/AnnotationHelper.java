package helpers;

import cucumber.runtime.Reflections;
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
import java.util.HashMap;
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
               String[] followedSteps = ((String[])annotation.annotationType().getDeclaredMethod("followedSteps").invoke(annotation));
               JSONArray stepFollowedSteps = new JSONArray();
                stepFollowedSteps.addAll(Arrays.asList(followedSteps));
                annotationMap.put(annotatedStep.getShortName(), stepFollowedSteps);
            }
        }
        return  new JSONObject(annotationMap);
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        System.out.println(new AnnotationHelper().prepareStepsMatrix());
    }
}