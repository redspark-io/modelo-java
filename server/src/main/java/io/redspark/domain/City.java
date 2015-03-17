package io.redspark.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="city")
@EqualsAndHashCode(exclude="hotels")
@ToString(exclude="hotels")
public class City {
    
    @GeneratedValue
    @Id
    @Column(name="city_id")
    private Long id;
    
    @Column(name="city_name", nullable=false, length=200)
    private String name;
    
    @Column(name="city_state", nullable=false, length=100)
    private String state;
    
    @Column(name="city_country", nullable=false, length=100)
    private String country;
    
    @OneToMany(mappedBy="city")
    private Set<Hotel> hotels = new HashSet<Hotel>();

}
