package org.springframework.samples.petclinic.service;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.rest.dto.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ToolingFacade {
    PetDto findPetById(int id) throws DataAccessException;
    Collection<PetDto> findAllPets() throws DataAccessException;
    void savePet(PetFieldsDto pet) throws DataAccessException;
    void deletePet(int id) throws DataAccessException;

    Collection<VisitDto> findVisitsByPetId(int petId);
    VisitDto findVisitById(int visitId) throws DataAccessException;
    Collection<VisitDto> findAllVisits() throws DataAccessException;
    void saveVisit(VisitFieldsDto visit) throws DataAccessException;
    void deleteVisit(int visitId) throws DataAccessException;

    VetDto findVetById(int id) throws DataAccessException;
    Collection<VetDto> findAllVets() throws DataAccessException;
    void saveVet(VetFieldsDto vet) throws DataAccessException;
    void deleteVet(int vetId) throws DataAccessException;

    OwnerDto findOwnerById(int id) throws DataAccessException;
    Collection<OwnerDto> findAllOwners() throws DataAccessException;
    void saveOwner(OwnerFieldsDto owner) throws DataAccessException;
    void deleteOwner(int ownerId) throws DataAccessException;
    Collection<OwnerDto> findOwnerByLastName(String lastName) throws DataAccessException;

    PetTypeDto findPetTypeById(int petTypeId);
    Collection<PetTypeDto> findAllPetTypes() throws DataAccessException;
    void savePetType(PetTypeFieldsDto petType) throws DataAccessException;
    void deletePetType(int petTypeId) throws DataAccessException;

    SpecialtyDto findSpecialtyById(int specialtyId);
    Collection<SpecialtyDto> findAllSpecialties() throws DataAccessException;
    void saveSpecialty(Specialty specialty) throws DataAccessException;
    void deleteSpecialty(int specialtyId) throws DataAccessException;
    List<SpecialtyDto> findSpecialtiesByNameIn(Set<String> names) throws DataAccessException;
}
