package com.abhinav.hospitalManagement.HospitalManagement.dto;

import com.abhinav.hospitalManagement.HospitalManagement.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodGroupCountResponseEntity {
    private BloodGroupType bloodGroupType;
    private Long count;

    @Override
    public String toString(){

        return bloodGroupType+" "+count;
    }
}
//copy reference to use in query