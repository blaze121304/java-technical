package com.rusty.replication.common.validation;

import com.rusty.replication.common.exception.FieldNotUniqueException;
import com.rusty.replication.common.exception.MissingRequiredFieldException;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

/**
 * Component about some most used validation utilities. Implementation is based on
 * generic classes and types, in order to be specified on runtime. This technique helps
 * us to avoid boilerplate code and multiple different implementations for the exact
 * same functionality.
 *
 * @param <T>
 *     Class of the object that we check the validity.
 * @param <S>
 *     Class of the object maybe found on db.
 *
 * In most cases <T> is a DTO class, <S> is an entity class and these utilities
 * are used before save/update in order to throw custom exceptions.
 */
@Component
public class CommonValidatorUtils<T, S> {

    //특정 필드 값이 유일한지(중복이 없는지) 확인.
    //이미 데이터베이스에 존재하는 객체(`foundObj`)와 비교하여 중복 데이터에 대한 예외를 발생시킴.

    public void validateUniqueField(String fieldName, T checkingObj, S foundObj) {
        // Use BeanWrapper to get the value of a field that is specified on runtime
        BeanWrapperImpl checkingObjWrapper = new BeanWrapperImpl(checkingObj);
        Object checkingObjId = checkingObjWrapper.getPropertyValue("id");
        String checkingObjFieldValue = checkingObjWrapper.getPropertyValue(fieldName).toString();

        BeanWrapperImpl foundObjWrapper = new BeanWrapperImpl(foundObj);
        Object foundObjId = foundObjWrapper.getPropertyValue("id");

        // Save case
        if (checkingObjId == null) {
            throw new FieldNotUniqueException(checkingObjFieldValue);
        }

        // Update case
        if(checkingObjId != null) {
            // Trying to update an object, using a taken(not unique) value
            if(checkingObjId != foundObjId) {
                throw new FieldNotUniqueException(checkingObjFieldValue);
            }
        }
    }


    //지정된 필드가 `null` 또는 비어 있는지 확인.
    //값이 없거나 필수 필드가 누락되었을 경우 사용자 정의 예외를 발생시킴.
    public void validateRequiredField(String fieldLabel, T fieldValue) {
        if(fieldValue == null)
            throw new MissingRequiredFieldException(fieldLabel);
    }

}
