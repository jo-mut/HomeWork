package com.example.homework.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectOption {
    /** value */
    private String value;
    /** a flag for the option to be preselected - shown first in the drop-down list */
    private Boolean selected;
}