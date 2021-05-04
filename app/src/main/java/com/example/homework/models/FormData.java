package com.example.homework.models;

import java.net.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormData {
    /** account-related data to pre-fill a form */
    private AccountFormData account;
    /** customer-related data to pre-fill a form */
    private CustomerFormData customer;
    /** installments plans data */
    private Installments installments;
    /** An URL to the data privacy consent document */
    private URL dataPrivacyConsentUrl;
}