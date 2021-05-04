package com.example.homework.models;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Customer data what should be used to pre-fill payment form.
 */
@Getter
@Setter
public class CustomerFormData {
    /** optional */
    private Date birthday;
}
