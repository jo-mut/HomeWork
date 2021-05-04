package com.example.homework.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputElement {
    /** name */
    private String name;
    /** type */
    private String type;
    /** options */
    private List<SelectOption> options;
}