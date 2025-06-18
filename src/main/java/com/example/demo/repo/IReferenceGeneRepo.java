package com.example.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.dto.ReferenceGeneLinkView;
import com.example.demo.dto.ReferenceGeneSummary;
import com.example.demo.model.ReferenceGene;

public interface IReferenceGeneRepo extends CrudRepository<ReferenceGene, Long> {

	public abstract boolean existsByGeneReferenceName(String trim);

	public abstract ArrayList<ReferenceGene> findAllByOrderByReferenceGeneIdAsc();
	
	@Query("""
		    SELECT r.referenceGeneId AS referenceGeneId,
		           r.geneReferenceName AS geneReferenceName,
		           r.publicationReference AS publicationReference,
		           r.date AS date,
		           r.sample AS sample,
		           SIZE(r.projects) AS projectCount
		    FROM ReferenceGene r
		    ORDER BY r.referenceGeneId ASC
		""")
	public abstract	List<ReferenceGeneSummary> findSummaryList();
	
	 @Query("SELECT r.referenceGeneId AS referenceGeneId, r.geneReferenceName AS geneReferenceName " +
	           "FROM ReferenceGene r WHERE r.sample.sampleId = :sampleId")
	public abstract List<ReferenceGeneLinkView> findLinkViewsBySampleId(long sampleId);

	public abstract Optional<ReferenceGene> findByGeneReferenceName(String trim);
	
	@Query("""
			SELECT r.referenceGeneId AS referenceGeneId, r.geneReferenceName AS geneReferenceName
			FROM ReferenceGene r JOIN r.projects p
			WHERE p.projectId = :projectId
			""")
	public abstract	List<ReferenceGeneLinkView> findLinkViewsByProjectId(long projectId);

}
