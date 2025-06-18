package com.example.demo.dto;

import java.time.LocalDate;

public interface ReferenceGeneSummary {
	Long getReferenceGeneId();
    String getGeneReferenceName();
    String getPublicationReference();
    LocalDate getDate();
    SampleSummary getSample();   
    int getProjectCount();

    interface SampleSummary {
        String getSampleId();
    }
}
