package com.example.demo.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.dto.ProjectSummary;
import com.example.demo.model.Project;

public interface IProjectRepo extends CrudRepository<Project, Long> {

	public abstract boolean existsByProjectNr(int projectNr);

	public abstract boolean existsByTitle(String title);

	public abstract ArrayList<Project> findAllByOrderByProjectIdAsc();

	public abstract boolean existsByProjectNrAndProjectIdNot(int projectNr, long id);

	public abstract boolean existsByTitleAndProjectIdNot(String title, long id);

	@Query("""
			SELECT p.projectId AS projectId,
			p.projectNr AS projectNr,
			p.title AS title,
			p.description AS description,
			SIZE(p.geneFragments) AS geneFragmentCount,
			(SELECT COUNT(r) FROM ReferenceGene r JOIN r.projects proj WHERE proj.projectId = p.projectId) AS referenceGeneCount,
			(SELECT COUNT(s) FROM Sequencing s JOIN s.projects proj WHERE proj.projectId = p.projectId) AS sequencingCount,
			(SELECT COUNT(g) FROM GeneExpression g JOIN g.projects proj WHERE proj.projectId = p.projectId) AS geneExpressionCount
			FROM Project p
			ORDER BY p.projectId ASC
		""")
	public abstract List<ProjectSummary> findAllProjectSummaryWithCounts();
}
