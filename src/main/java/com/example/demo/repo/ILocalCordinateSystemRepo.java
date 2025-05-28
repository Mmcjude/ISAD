package com.example.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.LocalCordinateSystem;

public interface ILocalCordinateSystemRepo extends CrudRepository<LocalCordinateSystem, Long> {

	public abstract boolean existsByFieldNumberAndRowAndNumber(int inputFieldNumber, int inputRow, int inputNumber);

	public abstract Optional<LocalCordinateSystem> findByFieldNumberAndRowAndNumber(int inputFieldNumber, int inputRow,
			int inputNumber);

	@Query("SELECT lc FROM LocalCordinateSystem lc WHERE lc.gis IS NULL ORDER BY lc.fieldNumber ASC, lc.row ASC, lc.number ASC")
	public abstract List<LocalCordinateSystem> findAvailableOrdered();
	
	@Query("""
		    SELECT lc FROM LocalCordinateSystem lc
		    WHERE lc.gis IS NULL OR lc.lcsId = :currentId
		    ORDER BY lc.fieldNumber ASC, lc.row ASC, lc.number ASC
		""")
		List<LocalCordinateSystem> findAvailableIncludingCurrent(@Param("currentId") long currentId);

	public abstract ArrayList<LocalCordinateSystem> findAlByOrderByLcsIdAsc();


}
