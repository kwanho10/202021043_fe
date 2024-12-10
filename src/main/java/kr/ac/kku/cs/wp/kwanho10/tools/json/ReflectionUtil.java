package kr.ac.kku.cs.wp.kwanho10.tools.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReflectionUtil {

    /**
     * JSON 데이터를 클래스 객체로 변환하는 메서드
     * 
     * @param clazz 변환할 클래스 타입
     * @param jsonObject JSON 데이터
     * @param <T> 변환할 클래스의 타입 매개변수
     * @return JSON 데이터를 기반으로 생성된 클래스 객체
     * @throws Exception 변환 과정에서 발생할 수 있는 예외
     */
    public static <T> T createObjectFromJson(Class<T> clazz, JSONObject jsonObject) throws Exception {
        // 선언된 기본 생성자를 가져오고 (private도 포함) 접근 가능하도록 설정
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true); // private 생성자도 접근 가능하도록 설정

        // 클래스 인스턴스 생성
        T obj = constructor.newInstance();

        // 클래스의 모든 필드 가져오기
        Field[] fields = clazz.getDeclaredFields();

        // 각 필드에 대해 반복 처리
        for (Field field : fields) {
            field.setAccessible(true); // private 필드에 접근 가능하도록 설정

            // 필드 이름을 가져와 JSON 데이터에 동일한 키가 있는지 확인
            String fieldName = field.getName();
            if (jsonObject.has(fieldName)) {

                // 리스트 타입 처리
                if (List.class.isAssignableFrom(field.getType())) {
                    // 리스트의 제네릭 타입 가져오기
                    ParameterizedType listType = (ParameterizedType) field.getGenericType();
                    Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                    JSONArray jsonArray = jsonObject.getJSONArray(fieldName);
                    List<Object> list = new ArrayList<>();

                    // JSONArray의 각 요소를 적절한 타입으로 변환
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object item;
                        // 기본형 또는 String 타입의 경우
                        if (listClass.isPrimitive() || listClass == String.class || Number.class.isAssignableFrom(listClass)) {
                            item = jsonArray.get(i); // 기본형 및 문자열은 바로 할당
                        } else {
                            // 복잡한 객체는 재귀 호출하여 변환
                            item = createObjectFromJson(listClass, jsonArray.getJSONObject(i));
                        }
                        list.add(item);
                    }
                    // 필드에 변환된 리스트를 설정
                    field.set(obj, list);
                } 
                // 기본형 및 기타 객체 처리
                else {
                    Object value = jsonObject.get(fieldName);

                    // 기본형 타입 처리 (int, boolean 등)
                    if (field.getType().isPrimitive()) {
                        if (field.getType() == int.class) {
                            field.setInt(obj, jsonObject.getInt(fieldName));
                        } else if (field.getType() == boolean.class) {
                            field.setBoolean(obj, jsonObject.getBoolean(fieldName));
                        }
                        // 다른 기본형 타입도 필요한 경우 추가 가능
                    } else {
                        // 객체 타입은 바로 설정
                        field.set(obj, value);
                    }
                }
            }
        }
        return obj; // 변환된 객체 반환
    }
}
