package com.xm.entity;

import static com.xm.configuration.XmConstants.PRICE_SCALE;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TICK", uniqueConstraints = {
		@UniqueConstraint(name = "UNIQUE_SYMBOL_PRICE_DATE_TIME", columnNames = {"SYMBOL_ID", "PRICE_DATE_TIME"})
})
public class Tick {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TICK")
	@GenericGenerator(
			name = "SEQ_TICK",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "SEQ_TICK"),
					@Parameter(name = "increment_size", value = "1")
			})
	@Column(name = "ID")
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "SYMBOL_ID", nullable = false)
	private Symbol symbol;

	@Column(name = "PRICE_DATE_TIME", nullable = false)
	private LocalDateTime dateTime;

	@Column(name = "PRICE", precision = 15, scale = PRICE_SCALE, nullable = false)
	private BigDecimal price;

}
