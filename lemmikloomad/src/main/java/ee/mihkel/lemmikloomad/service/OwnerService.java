package ee.mihkel.lemmikloomad.service;

import ee.mihkel.lemmikloomad.dto.OwnerDTO;
import ee.mihkel.lemmikloomad.entity.Owner;
import ee.mihkel.lemmikloomad.repository.OwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    ModelMapper modelMapper;

   public List<OwnerDTO> findAllOwners() {
       List<Owner> owners = ownerRepository.findAll();
       List<OwnerDTO> ownerDTOs = new ArrayList<>();
//       ModelMapper modelMapper = new ModelMapper();
       System.out.println(modelMapper); // logib välja konsooli
       for (Owner o: owners) {
           OwnerDTO ownerDTO = modelMapper.map(o, OwnerDTO.class);
           ownerDTOs.add(ownerDTO);
       }
       return ownerDTOs;
   }
}
