package com.example.demo.dto;

import java.time.LocalDate;

public interface ReferenceGeneSummary {
	Long getReferenceGeneId();
    String getGeneReferenceName();
    String getPublicationReference();
    LocalDate getDate();
    SampleSummary getSample();

    interface SampleSummary {
        String getSampleId();
    }
}
