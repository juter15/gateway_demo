package com.example.gateway_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RefreshTokenEntity implements Serializable {

	@QuerySqlField(index = true)
	private String refreshToken;

	@QuerySqlField(index = true)
	private Long userSeq;

	@QuerySqlField
	private String userNa;

	@QuerySqlField
	private Date createdTime;
}
