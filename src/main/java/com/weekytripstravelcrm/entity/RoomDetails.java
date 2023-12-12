package com.weekytripstravelcrm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "room_details")
public class RoomDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column
    private String roomType;

    @Column
    private String area;

    @Column
    private String bedInfo;

    @Column
    private Double cost;

    @Column
    private boolean roomAvailable;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "room_amenities_mapping",
            joinColumns = {@JoinColumn(name = "roomId")},
            inverseJoinColumns = {@JoinColumn(name = "roomAmenitiesId")})
    private Set<RoomAmenities> roomAmenities = new HashSet<>();

}
