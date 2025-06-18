package com.example.demo.dto;

public interface ProjectSummary {
	long getProjectId();
	int getProjectNr();
	String getTitle();
	String getDescription();
	int getGeneFragmentCount();
	long getReferenceGeneCount();
	int getSequencingCount();
	long getGeneExpressionCount();
}
