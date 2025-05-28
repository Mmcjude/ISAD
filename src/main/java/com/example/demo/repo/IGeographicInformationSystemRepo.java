package com.example.demo.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.GeographicInformationSystem;

public interface IGeographicInformationSystemRepo extends CrudRepository<GeographicInformationSystem, Long> {
	
	public abstract ArrayList<GeographicInformationSystem> findAllByOrderByGisIdAsc();

	public abstract Optional<GeographicInformationSystem> findByLatitudeAndLongitude(double inputLatitude,
			double inputLongitude);
	
	@Query("""
		    SELECT g 
		    FROM GeographicInformationSystem g 
		    WHERE g.localLocation IS NULL 
		    ORDER BY 
		        g.municipality.country.countryName,
		        g.municipality.municipalityName,
		        g.latitude,
		        g.longitude
		""")
	public abstract List<GeographicInformationSystem> findGisWithoutLocalCoordinates();

	@Query("""
		    SELECT g 
		    FROM GeographicInformationSystem g 
		    WHERE g.localLocation IS NOT NULL 
		    ORDER BY g.localLocation.fieldNumber, g.localLocation.row, g.localLocation.number
		""")
	public abstract List<GeographicInformationSystem> findGisWithLocalCoordinates();

	@Query("""
		    SELECT g FROM GeographicInformationSystem g
		    WHERE g.localLocation IS NOT NULL AND NOT EXISTS (
		    SELECT p FROM Plant p WHERE p.gis = g AND p.status = 'Planted')
		    ORDER BY g.localLocation.fieldNumber, g.localLocation.row, g.localLocation.number
		""")
	public abstract List<GeographicInformationSystem> findGisWithAvailableLocalCoordinates();
}
