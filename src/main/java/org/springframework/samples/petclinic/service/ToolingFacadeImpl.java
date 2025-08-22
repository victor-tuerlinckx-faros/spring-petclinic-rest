package org.springframework.samples.petclinic.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.mapper.*;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.rest.dto.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ToolingFacadeImpl implements ToolingFacade {
    private final ClinicService clinicService;
    private final OwnerMapper ownerMapper;
    private final PetMapper petMapper;
    private final PetTypeMapper petTypeMapper;
    private final SpecialtyMapper specialtyMapper;
    private final VetMapper vetMapper;
    private final VisitMapper visitMapper;

    public ToolingFacadeImpl(ClinicService clinicService,
                             OwnerMapper ownerMapper,
                             PetMapper petMapper,
                             PetTypeMapper petTypeMapper,
                             SpecialtyMapper specialtyMapper,
                             VetMapper vetMapper,
                             VisitMapper visitMapper) {
        this.clinicService = clinicService;
        this.ownerMapper = ownerMapper;
        this.petMapper = petMapper;
        this.petTypeMapper = petTypeMapper;
        this.specialtyMapper = specialtyMapper;
        this.vetMapper = vetMapper;
        this.visitMapper = visitMapper;
    }

    @Override
    public PetDto findPetById(int id) throws DataAccessException {
        return petMapper.toPetDto(clinicService.findPetById(id));
    }

    @Override
    @Tool(description = "Return all pets in the system")
    public Collection<PetDto> findAllPets() throws DataAccessException {
        return clinicService.findAllPets().stream().map(petMapper::toPetDto).collect(Collectors.toList());
    }

    @Override
    public void savePet(PetFieldsDto pet) throws DataAccessException {
        clinicService.savePet(petMapper.toPet(pet));
    }

    @Override
    public void deletePet(int id) throws DataAccessException {
        clinicService.deletePet(clinicService.findPetById(id));
    }

    @Override
    public Collection<VisitDto> findVisitsByPetId(int petId) {
        return clinicService.findVisitsByPetId(petId).stream().map(visitMapper::toVisitDto).collect(Collectors.toList());
    }

    @Override
    public VisitDto findVisitById(int visitId) throws DataAccessException {
        return visitMapper.toVisitDto(clinicService.findVisitById(visitId));
    }

    @Override
    @Tool(description = "Return all visits in the system")
    public Collection<VisitDto> findAllVisits() throws DataAccessException {
        return clinicService.findAllVisits().stream().map(visitMapper::toVisitDto).collect(Collectors.toList());
    }

    @Override
    public void saveVisit(VisitFieldsDto visit) throws DataAccessException {
        clinicService.saveVisit(visitMapper.toVisit(visit));
    }

    @Override
    public void deleteVisit(int visitId) throws DataAccessException {
        clinicService.deleteVisit(clinicService.findVisitById(visitId));
    }

    @Override
    public VetDto findVetById(int id) throws DataAccessException {
        return vetMapper.toVetDto(clinicService.findVetById(id));
    }

    @Override
    @Tool(description = "Return all veterinarians in the system")
    public Collection<VetDto> findAllVets() throws DataAccessException {
        return clinicService.findAllVets().stream().map(vetMapper::toVetDto).collect(Collectors.toList());
    }

    @Override
    public void saveVet(VetFieldsDto vet) throws DataAccessException {
        clinicService.saveVet(vetMapper.toVet(vet));
    }

    @Override
    public void deleteVet(int vetId) throws DataAccessException {
        clinicService.deleteVet(clinicService.findVetById(vetId));
    }

    @Override
    public OwnerDto findOwnerById(int id) throws DataAccessException {
        return ownerMapper.toOwnerDto(clinicService.findOwnerById(id));
    }

    @Override
    @Tool(description = "Return all pet owners in the system")
    public Collection<OwnerDto> findAllOwners() throws DataAccessException {
        return clinicService.findAllOwners().stream().map(ownerMapper::toOwnerDto).collect(Collectors.toList());
    }

    @Override
    public void saveOwner(OwnerFieldsDto owner) throws DataAccessException {
        clinicService.saveOwner(ownerMapper.toOwner(owner));
    }

    @Override
    public void deleteOwner(int ownerId) throws DataAccessException {
        clinicService.deleteOwner(clinicService.findOwnerById(ownerId));
    }

    @Override
    public Collection<OwnerDto> findOwnerByLastName(String lastName) throws DataAccessException {
        return clinicService.findOwnerByLastName(lastName).stream().map(ownerMapper::toOwnerDto).collect(Collectors.toList());
    }

    @Override
    public PetTypeDto findPetTypeById(int petTypeId) {
        return petTypeMapper.toPetTypeDto(clinicService.findPetTypeById(petTypeId));
    }

    @Override
    @Tool(description = "Return all pet types in the system")
    public Collection<PetTypeDto> findAllPetTypes() throws DataAccessException {
        return clinicService.findAllPetTypes().stream().map(petTypeMapper::toPetTypeDto).collect(Collectors.toList());
    }

    @Override
    public void savePetType(PetTypeFieldsDto petType) throws DataAccessException {
        clinicService.savePetType(petTypeMapper.toPetType(petType));
    }

    @Override
    public void deletePetType(int petTypeId) throws DataAccessException {
        clinicService.deletePetType(clinicService.findPetTypeById(petTypeId));
    }

    @Override
    public SpecialtyDto findSpecialtyById(int specialtyId) {
        return specialtyMapper.toSpecialtyDto(clinicService.findSpecialtyById(specialtyId));
    }

    @Override
    @Tool(description = "Return all veterinarian specialties in the system")
    public Collection<SpecialtyDto> findAllSpecialties() throws DataAccessException {
        return clinicService.findAllSpecialties().stream().map(specialtyMapper::toSpecialtyDto).collect(Collectors.toList());
    }

    @Override
    public void saveSpecialty(Specialty specialty) throws DataAccessException {
        clinicService.saveSpecialty(specialty);
    }

    @Override
    public void deleteSpecialty(int specialtyId) throws DataAccessException {
        clinicService.deleteSpecialty(clinicService.findSpecialtyById(specialtyId));
    }

    @Override
    public List<SpecialtyDto> findSpecialtiesByNameIn(Set<String> names) throws DataAccessException {
        return clinicService.findSpecialtiesByNameIn(names).stream().map(specialtyMapper::toSpecialtyDto).collect(Collectors.toList());
    }
}
