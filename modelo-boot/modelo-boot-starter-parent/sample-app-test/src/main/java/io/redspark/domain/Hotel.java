package io.redspark.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "hotel")
@EqualsAndHashCode(exclude = "city")
@ToString(exclude = "city")
public class Hotel {

  @Id
  @GeneratedValue
  @Column(name = "hote_id")
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "city_id", foreignKey = @ForeignKey(name = "FK_CITY_HOTEL") )
  private City city;

  @Column(name = "hote_tipo", nullable = false)
  private String name;

  @Column(name = "hote_address", nullable = false)
  private String address;

  @Column(name = "hote_zip", nullable = false)
  private String zip;

}
